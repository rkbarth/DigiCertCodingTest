from enum import Enum
from datetime import datetime
from typing import Optional
from uuid import UUID, uuid4
from pydantic import BaseModel, Field

class Genre(str, Enum):
    FICTION = "FICTION"
    NON_FICTION = "NON_FICTION"
    SCIENCE = "SCIENCE"
    HISTORY = "HISTORY"
    TECHNOLOGY = "TECHNOLOGY"
    SCIENCE_FICTION = "SCIENCE_FICTION"
    FANTASY = "FANTASY"
    ROMANCE = "ROMANCE"
    MYSTERY = "MYSTERY"
    POETRY = "POETRY"
    BIOGRAPHY = "BIOGRAPHY"

class Book(BaseModel):
    id: Optional[UUID] = Field(default_factory=uuid4, description="Unique identifier")
    title: str = Field(..., description="Title of the book", example="Effective Java")
    author: Optional[str] = Field(None, description="Author name", example="Joshua Bloch")
    isbn: Optional[str] = Field(None, description="ISBN identifier", example="978-0134685991")
    published_at: Optional[datetime] = Field(default_factory=datetime.utcnow, description="Publication date/time (UTC)", example="2018-01-06T00:00:00Z")
    pages: Optional[int] = Field(None, description="Number of pages", example=320)
    synopsis: Optional[str] = Field(None, description="Short synopsis", example="A classic book about practices and patterns.")
    genre: Optional[Genre] = Field(None, description="Genre of the book", example="TECHNOLOGY")
    dewey_decimal: Optional[str] = Field(None, description="Dewey Decimal classification", example="005.133")

    class Config:
        from_attributes = True