package notes_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Service class for managing notes with basic CRUD operations.
 */
public class NoteService {
    private final Map<Long, Note> notes = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Adds a new note to the system.
     * 
     * @param title the note title
     * @param content the note content
     * @return the created note with generated id
     */
    public Note addNote(String title, String content) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }

        Note note = new Note(title.trim(), content);
        note.setId(idGenerator.getAndIncrement());
        notes.put(note.getId(), note);
        return note;
    }

    /**
     * Updates an existing note.
     * 
     * @param id the note id
     * @param title the new title (can be null to keep existing)
     * @param content the new content (can be null to keep existing)
     * @return the updated note
     * @throws IllegalArgumentException if note with given id doesn't exist
     */
    public Note updateNote(Long id, String title, String content) {
        Note note = notes.get(id);
        if (note == null) {
            throw new IllegalArgumentException("Note with id " + id + " not found");
        }

        if (title != null && !title.trim().isEmpty()) {
            note.setTitle(title.trim());
        }
        if (content != null) {
            note.setContent(content);
        }

        return note;
    }

    /**
     * Deletes a note by id.
     * 
     * @param id the note id
     * @return true if note was deleted, false if note didn't exist
     */
    public boolean deleteNote(Long id) {
        Note removed = notes.remove(id);
        return removed != null;
    }

    /**
     * Finds a note by id.
     * 
     * @param id the note id
     * @return the note if found, null otherwise
     */
    public Note findNoteById(Long id) {
        return notes.get(id);
    }

    /**
     * Finds notes by title (case-insensitive partial match).
     * 
     * @param title the title to search for
     * @return list of matching notes
     */
    public List<Note> findNotesByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchTitle = title.toLowerCase().trim();
        return notes.values().stream()
                .filter(note -> note.getTitle().toLowerCase().contains(searchTitle))
                .collect(Collectors.toList());
    }

    /**
     * Gets all notes.
     * 
     * @return list of all notes
     */
    public List<Note> getAllNotes() {
        return new ArrayList<>(notes.values());
    }

    /**
     * Gets the total number of notes.
     * 
     * @return the count of notes
     */
    public int getNoteCount() {
        return notes.size();
    }

    /**
     * Clears all notes (useful for testing).
     */
    public void clearAllNotes() {
        notes.clear();
        idGenerator.set(1);
    }
}
