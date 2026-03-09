from datetime import UTC, datetime
from enum import Enum
from typing import Optional
from uuid import UUID, uuid4

from pydantic import BaseModel, ConfigDict, Field


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
    title: str = Field(..., description="Title of the book", json_schema_extra={"example": "Effective Java"})
    author: Optional[str] = Field(None, description="Author name", json_schema_extra={"example": "Joshua Bloch"})
    isbn: Optional[str] = Field(None, description="ISBN identifier", json_schema_extra={"example": "978-0134685991"})
    published_at: Optional[datetime] = Field(default_factory=lambda: datetime.now(UTC), description="Publication date/time (UTC)", json_schema_extra={"example": "2018-01-06T00:00:00Z"})
    pages: Optional[int] = Field(None, description="Number of pages", json_schema_extra={"example": 320})
    synopsis: Optional[str] = Field(None, description="Short synopsis", json_schema_extra={"example": "A classic book about practices and patterns."})
    genre: Optional[Genre] = Field(None, description="Genre of the book", json_schema_extra={"example": "TECHNOLOGY"})
    dewey_decimal: Optional[str] = Field(None, description="Dewey Decimal classification", json_schema_extra={"example": "005.133"})

    model_config = ConfigDict(from_attributes=True)