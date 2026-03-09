#!/usr/bin/env python3
"""
Simple test script to validate the Library API endpoints
"""
import requests
import json
from uuid import uuid4

BASE_URL = "http://localhost:8000"

def test_list_books():
    print("Testing GET /api/v1/books...")
    response = requests.get(f"{BASE_URL}/api/v1/books?page=0&size=3")
    assert response.status_code == 200
    books = response.json()
    assert len(books) == 3
    print(f"✓ Retrieved {len(books)} books")
    return books

def test_get_book(book_id):
    print(f"Testing GET /api/v1/books/{book_id}...")
    response = requests.get(f"{BASE_URL}/api/v1/books/{book_id}")
    assert response.status_code == 200
    book = response.json()
    assert book['id'] == book_id
    print(f"✓ Retrieved book: {book['title']}")
    return book

def test_create_book():
    print("Testing POST /api/v1/books...")
    new_book = {
        "title": "Test Book",
        "author": "Test Author",
        "isbn": "TEST-123",
        "pages": 100,
        "synopsis": "A test book for API validation"
    }
    response = requests.post(
        f"{BASE_URL}/api/v1/books",
        json=new_book,
        headers={"Content-Type": "application/json"}
    )
    assert response.status_code == 201
    created_book = response.json()
    assert created_book['title'] == new_book['title']
    assert 'id' in created_book
    print(f"✓ Created book with ID: {created_book['id']}")
    return created_book

def test_update_book(book_id):
    print(f"Testing PUT /api/v1/books/{book_id}...")
    update_data = {
        "title": "Updated Test Book",
        "synopsis": "Updated synopsis"
    }
    response = requests.put(
        f"{BASE_URL}/api/v1/books/{book_id}",
        json=update_data,
        headers={"Content-Type": "application/json"}
    )
    assert response.status_code == 200
    updated_book = response.json()
    assert updated_book['title'] == "Updated Test Book"
    print("✓ Updated book successfully")
    return updated_book

def test_delete_book(book_id):
    print(f"Testing DELETE /api/v1/books/{book_id}...")
    response = requests.delete(f"{BASE_URL}/api/v1/books/{book_id}")
    assert response.status_code == 204
    print("✓ Deleted book successfully")

def test_filters():
    print("Testing filtering...")
    # Test author filter
    response = requests.get(f"{BASE_URL}/api/v1/books?author=Joshua")
    assert response.status_code == 200
    books = response.json()
    assert len(books) > 0
    assert all("Joshua" in book.get('author', '') for book in books)
    print(f"✓ Author filter returned {len(books)} books")

    # Test genre filter
    response = requests.get(f"{BASE_URL}/api/v1/books?genre=TECHNOLOGY")
    assert response.status_code == 200
    books = response.json()
    assert len(books) > 0
    assert all(book.get('genre') == 'TECHNOLOGY' for book in books)
    print(f"✓ Genre filter returned {len(books)} books")

if __name__ == "__main__":
    print("Starting Library API tests...\n")

    try:
        # Test listing books
        books = test_list_books()
        print()

        # Test getting a specific book
        demo_book_id = "33333333-3333-3333-3333-333333333333"
        test_get_book(demo_book_id)
        print()

        # Test creating a book
        created_book = test_create_book()
        created_id = created_book['id']
        print()

        # Test updating the created book
        test_update_book(created_id)
        print()

        # Test deleting the created book
        test_delete_book(created_id)
        print()

        # Test filters
        test_filters()
        print()

        print("🎉 All tests passed!")

    except Exception as e:
        print(f"❌ Test failed: {e}")
        raise