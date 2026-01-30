# DigiCertCodingTest

This is just a simple web app template that can be used to fake an API.  If you want to expand on it, you can fork from this commit.

## API Documentation (OpenAPI / Swagger) 🔍

- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

Quick curl example (create a certificate):

```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"owner":"Alice","serialNumber":"SN123"}' \
  http://localhost:8080/api/v1/certificates
```

Notes:
- The project uses `springdoc-openapi` to generate docs from controllers and models.
- If running the app in a non-local environment, secure or disable the Swagger UI as needed.

Notes for Codespaces / preview hosts ⚠️
- Previews sometimes serve the UI under a path prefix (e.g., `/proxy/...`). The Swagger UI must issue API requests relative to the UI path for 'Try it out' to work in that case.
- If you get a 404 from the "Try it out" request in the preview, either:
  - Use the POST endpoint to create a resource first and then use the returned `Location` from the response to GET the created resource, or
  - Open the API spec (`/v3/api-docs`) and confirm the `servers` object has a relative URL (it should be `"../"`), which lets the UI issue requests relative to where the UI is served.

Demo data available on startup ✅
- The app seeds a demo certificate on startup with id `11111111-1111-1111-1111-111111111111`.
- Example: GET the demo certificate:

```bash
curl -v "http://localhost:8080/api/v1/certificates/11111111-1111-1111-1111-111111111111"
```

Library (Books) 📚
- Base path: `/api/v1/books`
- Seeded demo books include ids: `33333333-3333-3333-3333-333333333333`, `33333334-3333-3333-3333-333333333334`, `33333335-3333-3333-3333-333333333335` (and others). 

Endpoints:
- `GET /api/v1/books` — list all books, supports query params: `author`, `genre`, `dewey`, `page` (0-based), `size`
- `GET /api/v1/books/{id}` — get a single book
- `POST /api/v1/books` — create a book (JSON body: `{"title":"...","author":"...","isbn":"...","pages":123,"synopsis":"..."}`)
- `PUT /api/v1/books/{id}` — update a book
- `DELETE /api/v1/books/{id}` — delete a book

Quick curl examples:

Create a book:

```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"title":"My Book","author":"You","isbn":"ISBN-1","pages":320,"synopsis":"Short blurb"}' \
  http://localhost:8080/api/v1/books
```

Get the seeded book:

```bash
curl -v "http://localhost:8080/api/v1/books/33333333-3333-3333-3333-333333333333"
```
