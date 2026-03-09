from fastapi import APIRouter
from fastapi.responses import RedirectResponse

router = APIRouter()


@router.get("/", include_in_schema=False)
async def root():
    """Redirect to API documentation."""
    return RedirectResponse(url="/docs")


@router.get("/api", include_in_schema=False)
async def api_info():
    """API information."""
    return {
        "message": "Library API",
        "version": "1.0.0",
        "documentation": "/docs",
        "openapi_spec": "/openapi.json",
        "endpoints": {
            "books": "/api/v1/books"
        },
    }
