from fastapi import FastAPI, HTTPException, Query, Response
from fastapi.responses import RedirectResponse
from typing import List, Optional
from uuid import UUID
from models import Book, Genre
from library_service import LibraryService
from data_loader import load_demo_data

app = FastAPI(
    title="Library API",
    description="A RESTful API for managing a library of books",
    version="1.0.0"
)

# Initialize service and load demo data
library_service = LibraryService()
load_demo_data(library_service)

@app.get("/", include_in_schema=False)
async def root():
    """Redirect to API documentation"""
    return RedirectResponse(url="/docs")

@app.get("/api", include_in_schema=False)
async def api_info():
    """API information"""
    return {
        "message": "Library API",
        "version": "1.0.0",
        "documentation": "/docs",
        "openapi_spec": "/openapi.json",
        "endpoints": {
            "books": "/api/v1/books"
        }
    }

@app.get("/api/v1/books", response_model=List[Book], tags=["Library"])
async def list_books(
    author: Optional[str] = Query(None, description="Filter by author name"),
    genre: Optional[Genre] = Query(None, description="Filter by genre"),
    dewey: Optional[str] = Query(None, description="Filter by Dewey Decimal (prefix match)"),
    page: int = Query(0, ge=0, description="Page number (0-based)"),
    size: int = Query(10, gt=0, description="Page size")
):
    """List books with optional filtering and pagination"""
    try:
        return library_service.list_books(author=author, genre=genre, dewey=dewey, page=page, size=size)
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))

@app.get("/api/v1/books/{book_id}", response_model=Book, tags=["Library"])
async def get_book(book_id: UUID):
    """Get a book by ID"""
    book = library_service.get_book(book_id)
    if not book:
        raise HTTPException(status_code=404, detail="Book not found")
    return book

@app.post("/api/v1/books", response_model=Book, status_code=201, tags=["Library"])
async def create_book(book: Book, response: Response):
    """Create a new book"""
    created_book = library_service.create_book(book)
    response.headers["Location"] = f"/api/v1/books/{created_book.id}"
    return created_book

@app.put("/api/v1/books/{book_id}", response_model=Book, tags=["Library"])
async def update_book(book_id: UUID, book: Book):
    """Update an existing book"""
    updated_book = library_service.update_book(book_id, book)
    if not updated_book:
        raise HTTPException(status_code=404, detail="Book not found")
    return updated_book

@app.delete("/api/v1/books/{book_id}", status_code=204, tags=["Library"])
async def delete_book(book_id: UUID):
    """Delete a book"""
    if not library_service.delete_book(book_id):
        raise HTTPException(status_code=404, detail="Book not found")
    return {"message": "Book deleted successfully"}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)