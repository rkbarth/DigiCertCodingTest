from fastapi import FastAPI

from dependencies import library_service as shared_library_service
from routers.books import router as books_router
from routers.meta import router as meta_router


def create_app() -> FastAPI:
    """Application factory to keep startup wiring centralized and testable."""
    app = FastAPI(
        title="Library API",
        description="A RESTful API for managing a library of books",
        version="1.0.0",
    )
    app.include_router(meta_router)
    app.include_router(books_router)
    return app


# Expose shared service instance for compatibility with existing tests/scripts.
library_service = shared_library_service

app = create_app()

if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
