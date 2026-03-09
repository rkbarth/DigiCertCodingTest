"""
Unit tests for LibraryService - testing business logic without HTTP
"""

from datetime import datetime
from uuid import uuid4

import pytest

from library_service import LibraryService
from models import Book, Genre


@pytest.fixture
def service():
    """Create a fresh LibraryService instance for each test"""
    svc = LibraryService()
    # Add some test data
    svc.create_book(
        Book(
            id=uuid4(),
            title="Test Book 1",
            author="Test Author 1",
            genre=Genre.TECHNOLOGY,
        )
    )
    svc.create_book(
        Book(
            id=uuid4(), title="Test Book 2", author="Test Author 2", genre=Genre.SCIENCE
        )
    )
    return svc


def test_list_books_empty(service):
    """Test listing books on empty service"""
    empty_service = LibraryService()
    books = empty_service.list_books()
    assert len(books) == 0


def test_list_books_with_data(service):
    """Test listing books with data"""
    books = service.list_books()
    assert len(books) == 2
    assert all(isinstance(book, Book) for book in books)


def test_create_book(service):
    """Test creating a new book"""
    new_book = Book(title="New Book", author="New Author", genre=Genre.FICTION)

    created = service.create_book(new_book)

    assert created.id is not None
    assert created.title == "New Book"
    assert created.author == "New Author"
    assert created.genre == Genre.FICTION
    assert created.published_at is not None


def test_get_book_existing(service):
    """Test getting an existing book"""
    # Get first book from the service
    all_books = service.list_books()
    first_book = all_books[0]

    retrieved = service.get_book(first_book.id)
    assert retrieved is not None
    assert retrieved.id == first_book.id
    assert retrieved.title == first_book.title


def test_get_book_nonexistent(service):
    """Test getting a nonexistent book"""
    fake_id = uuid4()
    result = service.get_book(fake_id)
    assert result is None


def test_update_book_existing(service):
    """Test updating an existing book"""
    # Get first book
    all_books = service.list_books()
    first_book = all_books[0]
    original_title = first_book.title

    # Update it
    update_data = Book(title="Updated Title")
    result = service.update_book(first_book.id, update_data)

    assert result is not None
    assert result.title == "Updated Title"
    assert result.id == first_book.id


def test_update_book_nonexistent(service):
    """Test updating a nonexistent book"""
    fake_id = uuid4()
    update_data = Book(title="Should not work")
    result = service.update_book(fake_id, update_data)
    assert result is None


def test_delete_book_existing(service):
    """Test deleting an existing book"""
    # Get first book
    all_books = service.list_books()
    first_book = all_books[0]

    # Delete it
    result = service.delete_book(first_book.id)
    assert result is True

    # Verify it's gone
    retrieved = service.get_book(first_book.id)
    assert retrieved is None

    # Verify count decreased
    remaining = service.list_books()
    assert len(remaining) == 1


def test_delete_book_nonexistent(service):
    """Test deleting a nonexistent book"""
    fake_id = uuid4()
    result = service.delete_book(fake_id)
    assert result is False


def test_filter_by_author(service):
    """Test filtering books by author"""
    # Add a book with specific author
    service.create_book(
        Book(title="Author Test Book", author="Specific Author", genre=Genre.TECHNOLOGY)
    )

    # Filter by author
    results = service.list_books(author="Specific")
    assert len(results) == 1
    assert results[0].author == "Specific Author"

    # Filter by non-matching author
    results = service.list_books(author="NonExistent")
    assert len(results) == 0


def test_filter_by_genre(service):
    """Test filtering books by genre"""
    results = service.list_books(genre=Genre.TECHNOLOGY)
    assert len(results) == 1
    assert results[0].genre == Genre.TECHNOLOGY

    results = service.list_books(genre=Genre.FICTION)
    assert len(results) == 0


def test_pagination(service):
    """Test pagination functionality"""
    # Add more books for pagination testing
    for i in range(5):
        service.create_book(
            Book(
                title=f"Pagination Book {i}",
                author=f"Author {i}",
                genre=Genre.TECHNOLOGY,
            )
        )

    # Test page 0, size 3
    page1 = service.list_books(page=0, size=3)
    assert len(page1) == 3

    # Test page 1, size 3
    page2 = service.list_books(page=1, size=3)
    assert len(page2) == 3

    # Books should be different
    page1_ids = {book.id for book in page1}
    page2_ids = {book.id for book in page2}
    assert page1_ids.isdisjoint(page2_ids)


def test_pagination_invalid_params(service):
    """Test pagination with invalid parameters"""
    with pytest.raises(ValueError, match="page must be >= 0"):
        service.list_books(page=-1, size=10)

    with pytest.raises(ValueError, match="size must be > 0"):
        service.list_books(page=0, size=0)


def test_clear_service(service):
    """Test clearing all books from service"""
    assert len(service.list_books()) > 0

    service.clear()

    assert len(service.list_books()) == 0
