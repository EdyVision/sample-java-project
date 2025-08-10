package notes_app;

import java.util.List;

/**
 * Main class demonstrating the Notes App functionality.
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== Notes App Demo ===\n");
        
        NoteService noteService = new NoteService();
        
        // Add some notes
        System.out.println("Adding notes...");
        Note note1 = noteService.addNote("Shopping List", "Milk, Bread, Eggs");
        Note note2 = noteService.addNote("Meeting Notes", "Discuss project timeline");
        Note note3 = noteService.addNote("Ideas", "Build a notes app");
        
        System.out.println("Added note: " + note1);
        System.out.println("Added note: " + note2);
        System.out.println("Added note: " + note3);
        System.out.println();
        
        // Display all notes
        System.out.println("All notes:");
        List<Note> allNotes = noteService.getAllNotes();
        allNotes.forEach(note -> System.out.println("- " + note.getTitle() + ": " + note.getContent()));
        System.out.println();
        
        // Search for notes
        System.out.println("Searching for notes with 'note' in title:");
        List<Note> searchResults = noteService.findNotesByTitle("note");
        searchResults.forEach(note -> System.out.println("- " + note.getTitle()));
        System.out.println();
        
        // Update a note
        System.out.println("Updating note 1...");
        Note updatedNote = noteService.updateNote(note1.getId(), "Updated Shopping List", "Milk, Bread, Eggs, Butter");
        System.out.println("Updated note: " + updatedNote);
        System.out.println();
        
        // Delete a note
        System.out.println("Deleting note 2...");
        boolean deleted = noteService.deleteNote(note2.getId());
        System.out.println("Note deleted: " + deleted);
        System.out.println();
        
        // Final state
        System.out.println("Final notes count: " + noteService.getNoteCount());
        System.out.println("Remaining notes:");
        noteService.getAllNotes().forEach(note -> 
            System.out.println("- " + note.getTitle() + " (ID: " + note.getId() + ")"));
    }
}
