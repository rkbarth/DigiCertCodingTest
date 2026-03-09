# DigiCert Coding Test

This repository contains two implementations of the same Library API:

## Project Structure

- **`java-library-api/`** - Java Spring Boot implementation
- **`python-library-api/`** - Python FastAPI implementation

Both projects implement the same RESTful API for managing a library of books with identical functionality, endpoints, and demo data.

## Java Spring Boot Version

Located in `java-library-api/`

### Running the Java version:
```bash
cd java-library-api
./mvnw spring-boot:run
```

### Access points:
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## Python FastAPI Version

Located in `python-library-api/`

### Running the Python version:
```bash
cd python-library-api
pip install -r requirements.txt
python main.py
```

### Access points:
- **Interactive API docs**: `http://localhost:8000/docs`
- **API info**: `http://localhost:8000/api`
- **OpenAPI spec**: `http://localhost:8000/openapi.json`

## API Endpoints

Both implementations provide the same REST API:

- `GET /api/v1/books` — List books with filtering and pagination
  - Query params: `author`, `genre`, `dewey`, `page`, `size`
- `GET /api/v1/books/{id}` — Get single book
- `POST /api/v1/books` — Create book
- `PUT /api/v1/books/{id}` — Update book
- `DELETE /api/v1/books/{id}` — Delete book

## Demo Data

Both projects seed 11 demo books on startup with identical IDs starting from `33333333-3333-3333-3333-333333333333`.

## Quick Test

Create a book (works with both implementations):

```bash
# Java version (port 8080)
curl -X POST -H "Content-Type: application/json" \
  -d '{"title":"My Book","author":"You","isbn":"ISBN-1","pages":320,"synopsis":"Short blurb"}' \
  http://localhost:8080/api/v1/books

# Python version (port 8000)
curl -X POST -H "Content-Type: application/json" \
  -d '{"title":"My Book","author":"You","isbn":"ISBN-1","pages":320,"synopsis":"Short blurb"}' \
  http://localhost:8000/api/v1/books
```

Get a demo book:
```bash
curl "http://localhost:8080/api/v1/books/33333333-3333-3333-3333-333333333333"  # Java
curl "http://localhost:8000/api/v1/books/33333333-3333-3333-3333-333333333333"  # Python
```
