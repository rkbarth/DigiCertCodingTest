"""
Library API tests using pytest
"""

from uuid import uuid4

import pytest
import requests

BASE_URL = "http://localhost:8000"


@pytest.fixture(scope="module")
def demo_book_id():
    """Demo book ID that should exist in the seeded data"""
    return "33333333-3333-3333-3333-333333333333"


def test_list_books():
    """Test GET /api/v1/books endpoint"""
    response = requests.get(f"{BASE_URL}/api/v1/books?page=0&size=3")
    assert response.status_code == 200
    books = response.json()
    assert len(books) == 3
    assert isinstance(books, list)
    # Verify each book has required fields
    for book in books:
        assert "id" in book
        assert "title" in book


def test_get_book(demo_book_id):
    """Test GET /api/v1/books/{id} endpoint"""
    response = requests.get(f"{BASE_URL}/api/v1/books/{demo_book_id}")
    assert response.status_code == 200
    book = response.json()
    assert book["id"] == demo_book_id
    assert "title" in book
    assert "author" in book


def test_get_nonexistent_book():
    """Test GET /api/v1/books/{id} with invalid ID"""
    fake_id = str(uuid4())
    response = requests.get(f"{BASE_URL}/api/v1/books/{fake_id}")
    assert response.status_code == 404


def test_create_book():
    """Test POST /api/v1/books endpoint"""
    new_book = {
        "title": "Test Book",
        "author": "Test Author",
        "isbn": "TEST-123",
        "pages": 100,
        "synopsis": "A test book for API validation",
    }
    response = requests.post(
        f"{BASE_URL}/api/v1/books",
        json=new_book,
        headers={"Content-Type": "application/json"},
    )
    assert response.status_code == 201
    created_book = response.json()
    assert created_book["title"] == new_book["title"]
    assert created_book["author"] == new_book["author"]
    assert "id" in created_book
    # Verify it's a valid UUID
    from uuid import UUID

    UUID(created_book["id"])  # Should not raise exception
    return created_book


def test_update_book():
    """Test PUT /api/v1/books/{id} endpoint"""
    # First create a book to update
    new_book = {"title": "Book to Update", "author": "Original Author", "pages": 50}
    create_response = requests.post(
        f"{BASE_URL}/api/v1/books",
        json=new_book,
        headers={"Content-Type": "application/json"},
    )
    assert create_response.status_code == 201
    created_book = create_response.json()
    book_id = created_book["id"]

    # Now update it
    update_data = {"title": "Updated Test Book", "synopsis": "Updated synopsis"}
    response = requests.put(
        f"{BASE_URL}/api/v1/books/{book_id}",
        json=update_data,
        headers={"Content-Type": "application/json"},
    )
    assert response.status_code == 200
    updated_book = response.json()
    assert updated_book["title"] == "Updated Test Book"
    assert updated_book["synopsis"] == "Updated synopsis"
    # Original fields should be preserved
    assert updated_book["author"] == "Original Author"


def test_update_nonexistent_book():
    """Test PUT /api/v1/books/{id} with invalid ID"""
    fake_id = str(uuid4())
    update_data = {"title": "Should not work"}
    response = requests.put(
        f"{BASE_URL}/api/v1/books/{fake_id}",
        json=update_data,
        headers={"Content-Type": "application/json"},
    )
    assert response.status_code == 404


def test_delete_book():
    """Test DELETE /api/v1/books/{id} endpoint"""
    # First create a book to delete
    new_book = {"title": "Book to Delete", "author": "Delete Author"}
    create_response = requests.post(
        f"{BASE_URL}/api/v1/books",
        json=new_book,
        headers={"Content-Type": "application/json"},
    )
    assert create_response.status_code == 201
    created_book = create_response.json()
    book_id = created_book["id"]

    # Now delete it
    response = requests.delete(f"{BASE_URL}/api/v1/books/{book_id}")
    assert response.status_code == 204

    # Verify it's gone
    get_response = requests.get(f"{BASE_URL}/api/v1/books/{book_id}")
    assert get_response.status_code == 404


def test_delete_nonexistent_book():
    """Test DELETE /api/v1/books/{id} with invalid ID"""
    fake_id = str(uuid4())
    response = requests.delete(f"{BASE_URL}/api/v1/books/{fake_id}")
    assert response.status_code == 404


def test_filter_by_author():
    """Test filtering books by author"""
    response = requests.get(f"{BASE_URL}/api/v1/books?author=Joshua")
    assert response.status_code == 200
    books = response.json()
    assert len(books) > 0
    # Verify all returned books contain "Joshua" in author field
    for book in books:
        assert "author" in book
        assert "Joshua" in book["author"]


def test_filter_by_genre():
    """Test filtering books by genre"""
    response = requests.get(f"{BASE_URL}/api/v1/books?genre=TECHNOLOGY")
    assert response.status_code == 200
    books = response.json()
    assert len(books) > 0
    # Verify all returned books have TECHNOLOGY genre
    for book in books:
        assert book.get("genre") == "TECHNOLOGY"


def test_pagination():
    """Test pagination parameters"""
    # Test page 0, size 2
    response = requests.get(f"{BASE_URL}/api/v1/books?page=0&size=2")
    assert response.status_code == 200
    books_page1 = response.json()
    assert len(books_page1) == 2

    # Test page 1, size 2
    response = requests.get(f"{BASE_URL}/api/v1/books?page=1&size=2")
    assert response.status_code == 200
    books_page2 = response.json()
    assert len(books_page2) == 2

    # Books should be different (assuming more than 2 books exist)
    page1_ids = {book["id"] for book in books_page1}
    page2_ids = {book["id"] for book in books_page2}
    assert page1_ids != page2_ids


def test_invalid_pagination():
    """Test invalid pagination parameters"""
    # Invalid page number - FastAPI returns 422 for validation errors
    response = requests.get(f"{BASE_URL}/api/v1/books?page=-1&size=10")
    assert response.status_code == 422  # FastAPI validation error

    # Invalid size - FastAPI returns 422 for validation errors
    response = requests.get(f"{BASE_URL}/api/v1/books?page=0&size=0")
    assert response.status_code == 422  # FastAPI validation error
