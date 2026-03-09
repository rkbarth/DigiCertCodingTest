# Python Library API

This is a FastAPI-based RESTful API that replicates the LibraryService functionality from the Java Spring Boot project.

## Features

- RESTful API for managing books
- In-memory storage (same as Java version)
- Filtering and pagination support
- Automatic OpenAPI/Swagger documentation
- Demo data seeding on startup

## API Endpoints

- `GET /api/v1/books` — List all books with optional filtering and pagination
  - Query parameters: `author`, `genre`, `dewey`, `page` (0-based), `size`
- `GET /api/v1/books/{id}` — Get a single book by ID
- `POST /api/v1/books` — Create a new book
- `PUT /api/v1/books/{id}` — Update an existing book
- `DELETE /api/v1/books/{id}` — Delete a book

## Running the Application

1. Install dependencies:
   ```bash
   pip install -r requirements.txt
   ```

2. Run the server:
   ```bash
   python main.py
   ```

3. Open your browser to:
   - **Interactive API docs**: `http://localhost:8000/docs`
   - **API info**: `http://localhost:8000/api`
   - **OpenAPI spec**: `http://localhost:8000/openapi.json`

## Demo Data

The application seeds 11 demo books on startup with IDs starting from `33333333-3333-3333-3333-333333333333`.

Example curl commands:

Create a book:
```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"title":"My Book","author":"You","isbn":"ISBN-1","pages":320,"synopsis":"Short blurb"}' \
  http://localhost:8000/api/v1/books
```

Get a seeded book:
```bash
curl -v "http://localhost:8000/api/v1/books/33333333-3333-3333-3333-333333333333"
```
```bash
curl -v "http://localhost:8000/api/v1/books/33333333-3333-3333-3333-333333333333"
```