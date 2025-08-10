package notes_app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class NoteTest {

    private Note note;
    private LocalDateTime testTime;

    @BeforeEach
    void setUp() {
        note = new Note();
        testTime = LocalDateTime.now();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(note);
        assertNull(note.getId());
        assertNull(note.getTitle());
        assertNull(note.getContent());
        assertNotNull(note.getCreatedOn());
        assertNotNull(note.getUpdatedOn());
    }

    @Test
    void testConstructorWithTitleAndContent() {
        Note note = new Note("Test Title", "Test Content");
        
        assertNotNull(note);
        assertNull(note.getId());
        assertEquals("Test Title", note.getTitle());
        assertEquals("Test Content", note.getContent());
        assertNotNull(note.getCreatedOn());
        assertNotNull(note.getUpdatedOn());
    }

    @Test
    void testConstructorWithAllFields() {
        Note note = new Note(1L, "Test Title", "Test Content", testTime, testTime);
        
        assertEquals(1L, note.getId());
        assertEquals("Test Title", note.getTitle());
        assertEquals("Test Content", note.getContent());
        assertEquals(testTime, note.getCreatedOn());
        assertEquals(testTime, note.getUpdatedOn());
    }

    @Test
    void testSettersAndGetters() {
        note.setId(1L);
        note.setTitle("Test Title");
        note.setContent("Test Content");
        note.setCreatedOn(testTime);
        note.setUpdatedOn(testTime);

        assertEquals(1L, note.getId());
        assertEquals("Test Title", note.getTitle());
        assertEquals("Test Content", note.getContent());
        assertEquals(testTime, note.getCreatedOn());
        assertEquals(testTime, note.getUpdatedOn());
    }

    @Test
    void testSetTitleUpdatesUpdatedOn() {
        LocalDateTime originalUpdatedOn = note.getUpdatedOn();
        
        // Wait a bit to ensure time difference
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        note.setTitle("New Title");
        
        assertTrue(note.getUpdatedOn().isAfter(originalUpdatedOn));
        assertEquals("New Title", note.getTitle());
    }

    @Test
    void testSetContentUpdatesUpdatedOn() {
        LocalDateTime originalUpdatedOn = note.getUpdatedOn();
        
        // Wait a bit to ensure time difference
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        note.setContent("New Content");
        
        assertTrue(note.getUpdatedOn().isAfter(originalUpdatedOn));
        assertEquals("New Content", note.getContent());
    }

    @Test
    void testToString() {
        note.setId(1L);
        note.setTitle("Test Title");
        note.setContent("Test Content");
        
        String result = note.toString();
        
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("title='Test Title'"));
        assertTrue(result.contains("content='Test Content'"));
        assertTrue(result.contains("createdOn="));
        assertTrue(result.contains("updatedOn="));
    }

    @Test
    void testEquals() {
        Note note1 = new Note(1L, "Title", "Content", testTime, testTime);
        Note note2 = new Note(1L, "Different Title", "Different Content", testTime, testTime);
        Note note3 = new Note(2L, "Title", "Content", testTime, testTime);
        
        assertEquals(note1, note2); // Same ID
        assertNotEquals(note1, note3); // Different ID
        assertNotEquals(note1, null);
        assertNotEquals(note1, "String");
    }

    @Test
    void testHashCode() {
        Note note1 = new Note(1L, "Title", "Content", testTime, testTime);
        Note note2 = new Note(1L, "Different Title", "Different Content", testTime, testTime);
        
        assertEquals(note1.hashCode(), note2.hashCode()); // Same ID should have same hash code
    }
}
