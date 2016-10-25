package edu.ncsu.csc216.tracker.ticket;

/**
 * The class that represents the note object and stores a note's data.
 * @author NBoar
 */
public class Note {

	/** The author of the note. */
	private String noteAuthor;
	/** The content of the note. */
	private String noteText;
	
	/**
	 * The Note constructor that sets the fields of a Note object.
	 * @param newAuthor The author of the new note.
	 * @param newText The content of the new note.
	 */
	public Note(String newAuthor, String newText) {
		this.setNoteAuthor(newAuthor);
		this.setNoteText(newText);
	}
	
	/**
	 * Retrieves the note's author.
	 * @return noteAuthor The author of the note.
	 */
	public String getNoteAuthor() {
		return noteAuthor;
	}
	
	/**
	 * Sets the note's author.
	 * @param newAuthor The author of the note.
	 */
	private void setNoteAuthor(String newAuthor) {
		noteAuthor = newAuthor;
	}
	
	/**
	 * Retrieves the note's contents.
	 * @return noteText The content of the note.
	 */
	public String getNoteText() {
		return noteText;
	}
	
	/**
	 * Sets the note's contents.
	 * @param newText The content of the note.
	 */
	private void setNoteText(String newText) {
		noteText = newText;
	}
	
	/**
	 * Retrieves all of the features of the note in an array.
	 * @return noteArray A 2D array containing both the noteAuthor and noteText
	 */
	public String[] getNoteArray() {
		String[] noteArray = {noteAuthor, noteText};
		return noteArray;
	}
}
