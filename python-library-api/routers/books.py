from typing import List, Optional
from uuid import UUID

from fastapi import APIRouter, Depends, HTTPException, Query, Response

from dependencies import get_library_service
from library_service import LibraryService
from models import Book, Genre

router = APIRouter(prefix="/api/v1/books", tags=["Library"])


@router.get("", response_model=List[Book])
async def list_books(
    author: Optional[str] = Query(None, description="Filter by author name"),
    genre: Optional[Genre] = Query(None, description="Filter by genre"),
    dewey: Optional[str] = Query(
        None, description="Filter by Dewey Decimal (prefix match)"
    ),
    page: int = Query(0, ge=0, description="Page number (0-based)"),
    size: int = Query(10, gt=0, description="Page size"),
    service: LibraryService = Depends(get_library_service),
):
    """List books with optional filtering and pagination."""
    try:
        return service.list_books(
            author=author,
            genre=genre,
            dewey=dewey,
            page=page,
            size=size,
        )
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))


@router.get("/{book_id}", response_model=Book)
async def get_book(
    book_id: UUID,
    service: LibraryService = Depends(get_library_service),
):
    """Get a book by ID."""
    book = service.get_book(book_id)
    if not book:
        raise HTTPException(status_code=404, detail="Book not found")
    return book


@router.post("", response_model=Book, status_code=201)
async def create_book(
    book: Book,
    response: Response,
    service: LibraryService = Depends(get_library_service),
):
    """Create a new book."""
    created_book = service.create_book(book)
    response.headers["Location"] = f"/api/v1/books/{created_book.id}"
    return created_book


@router.put("/{book_id}", response_model=Book)
async def update_book(
    book_id: UUID,
    book: Book,
    service: LibraryService = Depends(get_library_service),
):
    """Update an existing book."""
    updated_book = service.update_book(book_id, book)
    if not updated_book:
        raise HTTPException(status_code=404, detail="Book not found")
    return updated_book


@router.delete("/{book_id}", status_code=204)
async def delete_book(
    book_id: UUID,
    service: LibraryService = Depends(get_library_service),
):
    """Delete a book."""
    if not service.delete_book(book_id):
        raise HTTPException(status_code=404, detail="Book not found")
    return {"message": "Book deleted successfully"}
