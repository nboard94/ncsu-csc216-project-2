package edu.ncsu.csc216.tracker.command;

/**
 * Command objects encapsulates users actions and passes them to the GUI.
 * @author NBoar
 */
public class Command {

	/**
	 * CommandValue enumeration to represent different command checks.
	 * @author NBoar
	 */
	public static enum CommandValue {
		POSSESSION, ACCEPTED, CLOSED, PROGRESS, FEEDBACK
	}
	
	/**
	 * Flag enumeration to represent the different types of flags a ticket can have.
	 * @author NBoar
	 */
	public static enum Flag {
		DUPLICATE, INAPPROPRIATE, RESOLVED
	}

	/** The string associated with the DUPLICATE flag*/
	public static final String F_DUPLICATE = "Duplicate";
	/** The string associated with the INAPPROPRIATE flag*/
	public static final String F_INAPPROPRIATE = "Inappropriate";
	/** The string associated with the RESOLVED flag*/
	public static final String F_RESOLVED = "Resolved";
	
	/** The type of command.*/
	public CommandValue command;
	/** The owner of the ticket.*/
	private String owner;
	/** The type of flag associated with the ticket.*/
	public Flag flag;
	/** A note message associated with the ticket.*/
	private String note;
	/** The author of the note. */
	private String noteAuthor;
	
	/**
	 * The constructor for a Command object.  Sets the state of the Command.
	 * @param newCommand The new Command to set command to.
	 * @param newOwner The new Owner to set owner to.
	 * @param newFlag The new Flag to set flag to.
	 * @param newNote The new Note to set note to.
	 * @param newNoteAuthor The new NoteAuthor to set noteAuthor to.
	 * @throws IllegalArgumentException If newCommand is null.
	 * @throws IllegalArgumentException If newNoteAuthor or newNote is null or empty.
	 * @throws IllegalArgumentException If the newCommand is POSSESSION and the newOwner is null or empty.
	 * @throws IllegalArgumentException If the newCommand is CLOSED and newFlag is null.
	 */
	public Command(CommandValue newCommand, String newOwner, Flag newFlag, String newNoteAuthor, String newNote) throws IllegalArgumentException {
		
		if (newCommand == null) {
			throw new IllegalArgumentException();
		}
		else if (newNoteAuthor == null || newNoteAuthor == "") {
			throw new IllegalArgumentException();
		}
		else if (newNote == null || newNote == "") {
			throw new IllegalArgumentException();
		}
		else if (newCommand == CommandValue.POSSESSION && (newOwner == null || newOwner == "")) {
			throw new IllegalArgumentException();
		}
		else if (newCommand == CommandValue.CLOSED &&  newFlag == null) {
			throw new IllegalArgumentException();
		}
		else {
			command = newCommand;
			owner = newOwner;
			flag = newFlag;
			note = newNote;
			noteAuthor = newNoteAuthor;
		}
	}
	
	/**
	 * Retrieves the value of the command.
	 * @return command The type of the command.
	 */
	public CommandValue getCommand() {
		return command;
	}
	
	/**
	 * Retrieves the owner of the command.
	 * @return owner The owner of the command.
	 */
	public String getOwner() {
		return owner;
	}
	
	/**
	 * Retrieves the flag associated with the command.
	 * @return flag The flag associated with the command.
	 */
	public Flag getFlag() {
		return flag;
	}
	
	/**
	 * Retrieves the note associated with the command.
	 * @return note The note associated with the command.
	 */
	public String getNoteText() {
		return note;
	}
	
	/**
	 * Retrieves the noteAuthor associated with the command.
	 * @return noteAuthor The noteAuthor associated with the command.
	 */
	public String getNoteAuthor() {
		return noteAuthor;
	}
	
}
