from data_loader import load_demo_data
from library_service import LibraryService

# Singleton-like service instance for the app lifecycle.
library_service = LibraryService()
load_demo_data(library_service)


def get_library_service() -> LibraryService:
    return library_service
