package notes_app;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a note in the application.
 */
public class Note {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    /**
     * Default constructor.
     */
    public Note() {
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    /**
     * Constructor with title and content.
     * 
     * @param title the note title
     * @param content the note content
     */
    public Note(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    /**
     * Constructor with all fields.
     * 
     * @param id the note id
     * @param title the note title
     * @param content the note content
     * @param createdOn the creation timestamp
     * @param updatedOn the last update timestamp
     */
    public Note(Long id, String title, String content, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedOn = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.updatedOn = LocalDateTime.now();
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
