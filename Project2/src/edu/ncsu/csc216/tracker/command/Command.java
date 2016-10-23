package edu.ncsu.csc216.tracker.command;

public class Command {

	public static final String F_DUPLICATE = "Duplicate";
	public static final String F_INAPPROPRIATE = "Inappropriate";
	public static final String F_RESOLVED = "Resolved";
	
	private String owner;
	private String note;
	private String noteAuthor;
	
	public Command(CommandValue cv, String s, Flag f, String s1, String s2) {
		
	}
	
	public CommandValue getCommand() {
		return null;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public Flag getFlag() {
		return null;
	}
	
	public String getNoteText() {
		return note;
	}
	
	public String getNoteAuthor() {
		return noteAuthor;
	}
	
}
