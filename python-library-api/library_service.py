from datetime import datetime
from typing import Dict, List, Optional
from uuid import UUID

from models import Book, Genre


class LibraryService:
    def __init__(self):
        self._store: Dict[UUID, Book] = {}

    def list_books(
        self,
        author: Optional[str] = None,
        genre: Optional[Genre] = None,
        dewey: Optional[str] = None,
        page: int = 0,
        size: int = 10
    ) -> List[Book]:
        if page < 0 or size <= 0:
            raise ValueError("page must be >= 0 and size must be > 0")

        books = list(self._store.values())

        # Apply filters
        if author:
            books = [b for b in books if b.author and author.lower() in b.author.lower()]

        if genre:
            books = [b for b in books if b.genre == genre]

        if dewey:
            books = [b for b in books if b.dewey_decimal and b.dewey_decimal.startswith(dewey)]

        # Sort by title (case-insensitive)
        books.sort(key=lambda b: (b.title or "").lower())

        # Apply pagination
        start = page * size
        end = start + size
        return books[start:end]

    def get_book(self, book_id: UUID) -> Optional[Book]:
        return self._store.get(book_id)

    def create_book(self, book: Book) -> Book:
        if book.id is None:
            book.id = uuid4()
        if book.published_at is None:
            book.published_at = datetime.utcnow()
        self._store[book.id] = book
        return book

    def update_book(self, book_id: UUID, book_data: Book) -> Optional[Book]:
        if book_id not in self._store:
            return None

        existing = self._store[book_id]
        update_data = book_data.model_dump(exclude_unset=True)
        updated_book = existing.model_copy(update=update_data)
        self._store[book_id] = updated_book
        return updated_book

    def delete_book(self, book_id: UUID) -> bool:
        return self._store.pop(book_id, None) is not None

    def clear(self):
        self._store.clear()