# Notes App

A simple Java application for managing notes with basic CRUD operations.

## Project Structure

```
src/
├── main/java/notes_app/
│   ├── Note.java          # Note entity class
│   ├── NoteService.java   # Service layer with business logic
│   └── Main.java          # Demo application
└── test/java/notes_app/
    ├── NoteTest.java      # Unit tests for Note class
    └── NoteServiceTest.java # Unit tests for NoteService class

# Build and Test Files
pom.xml                   # Maven project configuration
show-coverage.sh          # Terminal coverage display script
target/site/jacoco/       # Generated coverage reports

# CI/CD
.github/workflows/        # GitHub Actions workflows
  ├── ci.yml             # Full CI with matrix testing
  └── test.yml           # Simple test workflow
```

## Features

- **Note Management**: Create, read, update, and delete notes
- **Automatic Timestamps**: Tracks `created_on` and `updated_on` timestamps
- **Input Validation**: Validates title and content requirements
- **Search Functionality**: Find notes by title (case-insensitive partial match)
- **Sequential IDs**: Automatically generates unique IDs for notes

## Classes

### Note
- Represents a note with id, title, content, and timestamps
- Automatically updates `updated_on` when title or content changes
- Includes proper equals/hashCode implementation

### NoteService
- Manages all note operations (CRUD)
- In-memory storage using HashMap
- Input validation and error handling
- Search and utility methods

## Building and Running

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Continuous Integration
This project includes GitHub Actions workflows that automatically run tests on every push and pull request:

- **`.github/workflows/ci.yml`**: Full CI with Java 11 & 17 matrix testing, coverage uploads, and artifacts
- **`.github/workflows/test.yml`**: Simple test workflow for basic CI needs

The workflows will:
✅ Run all tests automatically  
✅ Generate coverage reports  
✅ Cache Maven dependencies for faster builds  
✅ Provide test summaries in pull requests  
✅ Upload coverage reports as build artifacts

### Build the Project
```bash
mvn clean compile
```

### Run Tests
```bash
# Run all tests
mvn test

# Run tests with verbose output
mvn test -X

# Run tests and skip compilation (if already compiled)
mvn surefire:test
```

### Run Tests with Code Coverage
```bash
# Run tests with JaCoCo coverage
mvn clean test

# View coverage report in terminal
./show-coverage.sh

# Open detailed HTML coverage report
open target/site/jacoco/index.html
```

### Coverage Information
The project uses JaCoCo for code coverage reporting. After running tests, you can:

- **Terminal Summary**: Run `./show-coverage.sh` for a quick overview
- **HTML Report**: Open `target/site/jacoco/index.html` for detailed coverage analysis
- **CSV Report**: Use `target/site/jacoco/jacoco.csv` for programmatic analysis

Current coverage metrics:
- **Note class**: 100% coverage (fully tested)
- **NoteService class**: 100% coverage (fully tested)
- **Main class**: 0% coverage (demo class, not tested)
- **Overall**: 67% instruction coverage, 100% branch coverage

### Run the Demo Application
```bash
mvn exec:java -Dexec.mainClass="notes_app.Main"
```

Or compile and run manually:
```bash
mvn compile
java -cp target/classes notes_app.Main
```

## Example Usage

```java
NoteService noteService = new NoteService();

// Add a note
Note note = noteService.addNote("Shopping List", "Milk, Bread, Eggs");

// Update a note
noteService.updateNote(note.getId(), "Updated Shopping List", "Milk, Bread, Eggs, Butter");

// Find notes by title
List<Note> results = noteService.findNotesByTitle("shopping");

// Delete a note
boolean deleted = noteService.deleteNote(note.getId());
```

## Testing

The project includes comprehensive unit tests covering:
- All CRUD operations
- Input validation
- Edge cases
- Error conditions
- Timestamp behavior

### Test Structure
- **NoteTest.java**: 9 test methods covering Note class functionality
- **NoteServiceTest.java**: 24 test methods covering all service operations
- **Total**: 33 test methods with 100% pass rate

### Running Tests
```bash
# Basic test execution
mvn test

# With coverage reporting
mvn clean test

# View coverage summary in terminal
./show-coverage.sh
```

### Test Coverage
The test suite provides excellent coverage of the core business logic:
- **100% method coverage** for Note and NoteService classes
- **100% branch coverage** ensuring all conditional logic is tested
- **100% line coverage** for business logic classes
- Comprehensive validation of edge cases and error conditions

## Design Decisions

- **In-memory Storage**: Simple HashMap for demonstration purposes
- **Atomic ID Generation**: Thread-safe ID generation using AtomicLong
- **Immutable Timestamps**: Created timestamp never changes, updated timestamp changes on modifications
- **Input Validation**: Strict validation for title (non-null, non-empty) and content (non-null)
- **Case-insensitive Search**: User-friendly search functionality

## Future Enhancements

- Database persistence
- User authentication
- Note categories/tags
- Rich text content
- File attachments
- REST API endpoints
