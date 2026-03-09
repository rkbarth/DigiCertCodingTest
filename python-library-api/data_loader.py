from datetime import datetime
from uuid import UUID
from models import Book, Genre
from library_service import LibraryService

def load_demo_data(service: LibraryService):
    """Seed demo books for quick manual checks (11 total)"""
    books = [
        Book(
            id=UUID("33333333-3333-3333-3333-333333333333"),
            title="Demo Book",
            author="Seed Author",
            isbn="ISBN-DEMO",
            published_at=datetime.utcnow(),
            pages=123,
            synopsis="A seeded demo book",
            genre=Genre.TECHNOLOGY,
            dewey_decimal="000.0"
        ),
        Book(
            id=UUID("33333334-3333-3333-3333-333333333334"),
            title="Java Basics",
            author="Jane Doe",
            isbn="978-0000000001",
            published_at=datetime.utcnow(),
            pages=200,
            synopsis="Intro to Java",
            genre=Genre.TECHNOLOGY,
            dewey_decimal="005.133"
        ),
        Book(
            id=UUID("33333335-3333-3333-3333-333333333335"),
            title="Spring Boot in Action",
            author="John Smith",
            isbn="978-0000000002",
            published_at=datetime.utcnow(),
            pages=320,
            synopsis="Getting started with Spring Boot",
            genre=Genre.TECHNOLOGY,
            dewey_decimal="005.133"
        ),
        Book(
            id=UUID("33333336-3333-3333-3333-333333333336"),
            title="Effective Java",
            author="Joshua Bloch",
            isbn="978-0134685991",
            published_at=datetime.utcnow(),
            pages=416,
            synopsis="Best practices for Java",
            genre=Genre.TECHNOLOGY,
            dewey_decimal="005.133"
        ),
        Book(
            id=UUID("33333337-3333-3333-3333-333333333337"),
            title="Clean Code",
            author="Robert C. Martin",
            isbn="978-0132350884",
            published_at=datetime.utcnow(),
            pages=464,
            synopsis="Writing clean and maintainable code",
            genre=Genre.TECHNOLOGY,
            dewey_decimal="005.1"
        ),
        Book(
            id=UUID("33333338-3333-3333-3333-333333333338"),
            title="Design Patterns",
            author="Erich Gamma",
            isbn="978-0201633610",
            published_at=datetime.utcnow(),
            pages=395,
            synopsis="Classic design patterns",
            genre=Genre.TECHNOLOGY,
            dewey_decimal="005.12"
        ),
        Book(
            id=UUID("33333339-3333-3333-3333-333333333339"),
            title="Refactoring",
            author="Martin Fowler",
            isbn="978-0201485677",
            published_at=datetime.utcnow(),
            pages=448,
            synopsis="Improving the design of existing code",
            genre=Genre.TECHNOLOGY,
            dewey_decimal="005.1"
        ),
        Book(
            id=UUID("3333333a-3333-3333-3333-33333333333a"),
            title="Concurrency in Practice",
            author="Brian Goetz",
            isbn="978-0321349606",
            published_at=datetime.utcnow(),
            pages=384,
            synopsis="Concurrency concepts and patterns",
            genre=Genre.TECHNOLOGY,
            dewey_decimal="005.1"
        ),
        Book(
            id=UUID("3333333b-3333-3333-3333-33333333333b"),
            title="Test-Driven Development",
            author="Kent Beck",
            isbn="978-0321146533",
            published_at=datetime.utcnow(),
            pages=220,
            synopsis="TDD practices and techniques",
            genre=Genre.TECHNOLOGY,
            dewey_decimal="005.13"
        ),
        Book(
            id=UUID("3333333c-3333-3333-3333-33333333333c"),
            title="The Pragmatic Programmer",
            author="Andrew Hunt",
            isbn="978-0201616224",
            published_at=datetime.utcnow(),
            pages=352,
            synopsis="Practical programming advice",
            genre=Genre.TECHNOLOGY,
            dewey_decimal="005.1"
        ),
        Book(
            id=UUID("3333333d-3333-3333-3333-33333333333d"),
            title="Microservices Patterns",
            author="Chris Richardson",
            isbn="978-1111111111",
            published_at=datetime.utcnow(),
            pages=280,
            synopsis="Patterns for microservices architectures",
            genre=Genre.TECHNOLOGY,
            dewey_decimal="005.1"
        )
    ]

    for book in books:
        service.create_book(book)