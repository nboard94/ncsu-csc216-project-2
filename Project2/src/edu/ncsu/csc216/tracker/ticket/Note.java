package edu.ncsu.csc216.tracker.ticket;

public class Note {

	private String noteAuthor;
	private String noteText;
	
	public Note(String s1, String s2) {
		
	}
	
	public String getNoteAuthor() {
		return noteAuthor;
	}
	
	private void setNoteAuthor(String newAuthor) {
		noteAuthor = newAuthor;
	}
	
	public String getNoteText() {
		return noteText;
	}
	
	private void setNoteText(String newText) {
		noteText = newText;
	}
	
	public String[] getNoteArray() {
		return null;
	}
}
