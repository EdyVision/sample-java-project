package notes_app;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NoteServiceTest {

    private NoteService noteService;

    @BeforeEach
    void setUp() {
        noteService = new NoteService();
    }

    @Test
    void testAddNote() {
        Note note = noteService.addNote("Test Title", "Test Content");
        
        assertNotNull(note);
        assertEquals(1L, note.getId());
        assertEquals("Test Title", note.getTitle());
        assertEquals("Test Content", note.getContent());
        assertNotNull(note.getCreatedOn());
        assertNotNull(note.getUpdatedOn());
        assertEquals(1, noteService.getNoteCount());
    }

    @Test
    void testAddNoteWithNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            noteService.addNote(null, "Test Content");
        });
        assertEquals(0, noteService.getNoteCount());
    }

    @Test
    void testAddNoteWithEmptyTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            noteService.addNote("", "Test Content");
        });
        assertEquals(0, noteService.getNoteCount());
    }

    @Test
    void testAddNoteWithWhitespaceTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            noteService.addNote("   ", "Test Content");
        });
        assertEquals(0, noteService.getNoteCount());
    }

    @Test
    void testAddNoteWithNullContent() {
        assertThrows(IllegalArgumentException.class, () -> {
            noteService.addNote("Test Title", null);
        });
        assertEquals(0, noteService.getNoteCount());
    }

    @Test
    void testAddNoteTrimsTitle() {
        Note note = noteService.addNote("  Test Title  ", "Test Content");
        
        assertEquals("Test Title", note.getTitle());
    }

    @Test
    void testUpdateNote() {
        Note originalNote = noteService.addNote("Original Title", "Original Content");
        Long noteId = originalNote.getId();
        
        // Add a small delay to ensure timestamp difference
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        Note updatedNote = noteService.updateNote(noteId, "New Title", "New Content");
        
        assertEquals("New Title", updatedNote.getTitle());
        assertEquals("New Content", updatedNote.getContent());
        assertEquals(noteId, updatedNote.getId());
        assertTrue(updatedNote.getUpdatedOn().compareTo(originalNote.getUpdatedOn()) >= 0);
    }

    @Test
    void testUpdateNoteWithNullValues() {
        Note originalNote = noteService.addNote("Original Title", "Original Content");
        Long noteId = originalNote.getId();
        
        Note updatedNote = noteService.updateNote(noteId, null, null);
        
        assertEquals("Original Title", updatedNote.getTitle());
        assertEquals("Original Content", updatedNote.getContent());
    }

    @Test
    void testUpdateNoteWithEmptyTitle() {
        Note originalNote = noteService.addNote("Original Title", "Original Content");
        Long noteId = originalNote.getId();
        
        Note updatedNote = noteService.updateNote(noteId, "", "New Content");
        
        assertEquals("Original Title", updatedNote.getTitle());
        assertEquals("New Content", updatedNote.getContent());
    }

    @Test
    void testUpdateNoteWithWhitespaceTitle() {
        Note originalNote = noteService.addNote("Original Title", "Original Content");
        Long noteId = originalNote.getId();
        
        Note updatedNote = noteService.updateNote(noteId, "   ", "New Content");
        
        assertEquals("Original Title", updatedNote.getTitle());
        assertEquals("New Content", updatedNote.getContent());
    }

    @Test
    void testUpdateNonExistentNote() {
        assertThrows(IllegalArgumentException.class, () -> {
            noteService.updateNote(999L, "New Title", "New Content");
        });
    }

    @Test
    void testDeleteNote() {
        Note note = noteService.addNote("Test Title", "Test Content");
        Long noteId = note.getId();
        
        boolean deleted = noteService.deleteNote(noteId);
        
        assertTrue(deleted);
        assertEquals(0, noteService.getNoteCount());
        assertNull(noteService.findNoteById(noteId));
    }

    @Test
    void testDeleteNonExistentNote() {
        boolean deleted = noteService.deleteNote(999L);
        
        assertFalse(deleted);
    }

    @Test
    void testFindNoteById() {
        Note addedNote = noteService.addNote("Test Title", "Test Content");
        Long noteId = addedNote.getId();
        
        Note foundNote = noteService.findNoteById(noteId);
        
        assertNotNull(foundNote);
        assertEquals(addedNote, foundNote);
    }

    @Test
    void testFindNoteByIdNotFound() {
        Note foundNote = noteService.findNoteById(999L);
        
        assertNull(foundNote);
    }

    @Test
    void testFindNotesByTitle() {
        noteService.addNote("Java Programming", "Learn Java");
        noteService.addNote("Python Basics", "Learn Python");
        noteService.addNote("Advanced Java", "Advanced concepts");
        
        List<Note> javaNotes = noteService.findNotesByTitle("Java");
        
        assertEquals(2, javaNotes.size());
        assertTrue(javaNotes.stream().allMatch(note -> 
            note.getTitle().toLowerCase().contains("java")));
    }

    @Test
    void testFindNotesByTitleCaseInsensitive() {
        noteService.addNote("Java Programming", "Learn Java");
        
        List<Note> notes = noteService.findNotesByTitle("java");
        
        assertEquals(1, notes.size());
        assertEquals("Java Programming", notes.get(0).getTitle());
    }

    @Test
    void testFindNotesByTitleWithNull() {
        noteService.addNote("Test Title", "Test Content");
        
        List<Note> notes = noteService.findNotesByTitle(null);
        
        assertTrue(notes.isEmpty());
    }

    @Test
    void testFindNotesByTitleWithEmptyString() {
        noteService.addNote("Test Title", "Test Content");
        
        List<Note> notes = noteService.findNotesByTitle("");
        
        assertTrue(notes.isEmpty());
    }

    @Test
    void testGetAllNotes() {
        noteService.addNote("Title 1", "Content 1");
        noteService.addNote("Title 2", "Content 2");
        
        List<Note> allNotes = noteService.getAllNotes();
        
        assertEquals(2, allNotes.size());
    }

    @Test
    void testGetAllNotesEmpty() {
        List<Note> allNotes = noteService.getAllNotes();
        
        assertTrue(allNotes.isEmpty());
    }

    @Test
    void testGetNoteCount() {
        assertEquals(0, noteService.getNoteCount());
        
        noteService.addNote("Title 1", "Content 1");
        assertEquals(1, noteService.getNoteCount());
        
        noteService.addNote("Title 2", "Content 2");
        assertEquals(2, noteService.getNoteCount());
    }

    @Test
    void testClearAllNotes() {
        noteService.addNote("Title 1", "Content 1");
        noteService.addNote("Title 2", "Content 2");
        
        assertEquals(2, noteService.getNoteCount());
        
        noteService.clearAllNotes();
        
        assertEquals(0, noteService.getNoteCount());
        
        // Adding a new note should start with ID 1 again
        Note newNote = noteService.addNote("New Title", "New Content");
        assertEquals(1L, newNote.getId());
    }

    @Test
    void testMultipleNotesSequentialIds() {
        Note note1 = noteService.addNote("Title 1", "Content 1");
        Note note2 = noteService.addNote("Title 2", "Content 2");
        Note note3 = noteService.addNote("Title 3", "Content 3");
        
        assertEquals(1L, note1.getId());
        assertEquals(2L, note2.getId());
        assertEquals(3L, note3.getId());
    }
}
