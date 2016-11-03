package edu.ncsu.csc216.tracker.ticket;

import java.util.ArrayList;

import edu.ncsu.csc216.ticket.xml.NoteItem;
import edu.ncsu.csc216.ticket.xml.NoteList;
import edu.ncsu.csc216.ticket.xml.Ticket;
import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.command.Command.CommandValue;
import edu.ncsu.csc216.tracker.command.Command.Flag;

/**
 * Class representing a ticket object.  Includes an inner interface 
 * and inner classes representing the different states.
 * @author NBoar
 */
public class TrackedTicket {

	/** The numerical id of the ticket. */
	private int ticketId;
	/** The title of the ticket. */
	private String title;
	/** The submitter of the ticket. */
	private String submitter;
	/** The owner of the ticket. */
	private String owner;
	/** The static counter.  Increments upon TrackedTicket construction to set ticketId. */
	private static int counter = 1;
	/** The ArrayList containing the notes associated with the ticket. */
	ArrayList<Note> notes = new ArrayList<Note>();
	/** The Flag enumeration associated with the TrackedTicket. */
	public Flag flag;
	/** The CommandValue enumeration associated with the TrackedTicket. */
	public CommandValue command;
	
	/** Represents the TrackedTicket's state. */
	public TicketState state;
	/** Represents the TrackedTicket's NewState. */
	public NewState newState = new NewState();
	/** Represents the TrackedTicket's AssignedState. */
	public AssignedState assignedState = new AssignedState();
	/** Represents the TrackedTicket's WorkingState. */
	public WorkingState workingState = new WorkingState();
	/** Represents the TrackedTicket's FeedbackState. */
	public FeedbackState feedbackState = new FeedbackState();
	/** Represents the TrackedTicket's ClosedState. */
	public ClosedState closedState = new ClosedState();;
	
	/** String representation of NewState. */
	public static final String NEW_NAME = "New";
	/** String representation of AssignedState. */
	public static final String ASSIGNED_NAME = "Assigned";
	/** String representation of WorkingState. */
	public static final String WORKING_NAME = "Working";
	/** String representation of FeedbackState. */
	public static final String FEEDBACK_NAME = "Feedback";
	/** String representation of ClosedState. */
	public static final String CLOSED_NAME = "Closed";
	
 
	/**
	 * The default constructor for a TrackedTicket object.
	 * Takes in three strings for the title, submitter, and note.
	 * The id is set to the counter, which is incremented at the end of each construction.
	 * @param newTitle The title of the new TrackedTicket.
	 * @param newSubmitter The submitter of the new TrackedTicket.
	 * @param newNote The initial note of the new TrackedTicket.
	 */
	public TrackedTicket(String newTitle, String newSubmitter, String newNote) {
		if (newTitle == null || newTitle.equals("")) {
			throw new IllegalArgumentException();
		}
		else {
			title = newTitle;
		}
		ticketId = counter;
		submitter = newSubmitter;

		this.setState(NEW_NAME);
		notes.add(new Note(newSubmitter, newNote));
		
		incrementCounter();
	}
	
	/**
	 * Constructor for TrackedTicket that takes in a Ticket object.
	 * Delegates to the default constructor.
	 * @param t The Ticket object used to create a TrackedTicket.
	 */
	public TrackedTicket(Ticket t) {
		this.ticketId = counter;
		this.title = t.getTitle();
		this.submitter = t.getSubmitter();
		Note n = new Note(t.getNoteList().getNotes().get(0).getNoteAuthor(), t.getNoteList().getNotes().get(0).getNoteText());
		this.notes.add(n);
		
		if (t.getOwner() != null) {
			owner = t.getOwner();
		}
		
		if (t.getFlag() != null) {
			this.setFlag(t.getFlag().toLowerCase());
		}
		
		if (t.getState() != null) {
			this.setState(t.getState());
		}
		else {
			this.setState(NEW_NAME);
		}
		
		incrementCounter();
	} 
	
	/**
	 * Increments the static integer counter by 1 when called.
	 */
	public static void incrementCounter() {
		counter++;
	}
	
	/**
	 * Retrieves the ticket's ticketId.
	 * @return ticketId The numerical ID for the ticket.
	 */
	public int getTicketId() {
		return ticketId;
	}
	
	/**
	 * Retrieves the name of the state the ticket is currently in.
	 * @return The name of the state, otherwise null.
	 */
	public String getStateName() {
		if (this.state == newState) {
			return newState.getStateName();
		}
		else if (this.state == assignedState) {
			return assignedState.getStateName();
		}
		else if (this.state == workingState) {
			return workingState.getStateName();
		}
		else if (this.state == feedbackState) {
			return feedbackState.getStateName();
		}
		else if (this.state == closedState) {
			return closedState.getStateName();
		}
		else {
			return null;
		}
	}

	/**
	 * Sets the TrackedTicket's state to the desired new state.
	 * @param newStateName The string representation of the state you wish to set.
	 */
	private void setState(String newStateName) {
		if (newStateName.equals(NEW_NAME)) {
			state = newState;
		}
		else if (newStateName.equals(ASSIGNED_NAME)) {
			state = assignedState;
		}
		else if (newStateName.equals(WORKING_NAME)) {
			state = workingState;
		}
		else if (newStateName.equals(FEEDBACK_NAME)) {
			state = feedbackState;
		}
		else if (newStateName.equals(CLOSED_NAME)) {
			state = closedState;
		}
	}
	
	/**
	 * Retrieves the flag associated with the ticket.
	 * @return flag The flag associated with the ticket.
	 */
	public Flag getFlag() {
		return flag;
	}
	
	/**
	 * Sets the flag of the TrackedTicket.
	 * @param flagStr The string representation of the Flag enumeration to set flag to.
	 */
	private void setFlag(String flagStr) {
		if (flagStr.equals("Duplicate")) {
			this.flag = Flag.DUPLICATE;
		}
		else if (flagStr.equals("Inappropriate")) {
			this.flag = Flag.INAPPROPRIATE;
		}
		else if (flagStr.equals("Resolved")) {
			this.flag = Flag.RESOLVED;
		}
	}                                                                                    
	
	/**
	 * Retrieves the ticket's owner.
	 * @return owner The owner of the ticket.
	 */
	public String getOwner() {
		return owner;
	}
	
	/**
	 * Retrieves the ticket's title.
	 * @return title The title of the ticket.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Retrieves the ticket's submitter.
	 * @return submitter The submitter of the ticket.
	 */
	public String getSubmitter() {
		return submitter;
	}
	
	/**
	 * Retrieves all note objects associated with the ticket.
	 * @return notes The ArrayList containing all associated Note objects.
	 */
	public ArrayList<Note> getNotes() {
		return notes;
	}
	
	/**
	 * Delegates to the state's updateState method.
	 * @param c The command to pass.
	 * @throws UnsupportedOperationException Thrown is UnsupportedOperationException is caught.
	 */
	public void update(Command c) throws UnsupportedOperationException {
		state.updateState(c);
	}
	
	/**
	 * Converts a TrackedTicket object to a Ticket object.
	 * @return newTick The newly created Ticket object based off of the TrackedTicket.
	 */
	public Ticket getXMLTicket() {
		Ticket newTick = new Ticket();
		NoteList newNotes = new NoteList();
		NoteItem curNote = new NoteItem();

		newTick.setId(this.getTicketId());
		newTick.setTitle(this.getTitle());
		newTick.setSubmitter(this.getSubmitter());
		newTick.setOwner(this.getOwner());
		if (this.getFlag() != null) {
			newTick.setFlag(this.getFlagString());
		}
		newTick.setState(this.getStateName());
		
		for (int i = 0; i < this.getNotes().size(); i++) {
			curNote.setNoteAuthor(this.getNotes().get(i).getNoteAuthor());
			curNote.setNoteText(this.getNotes().get(i).getNoteText());
			newNotes.getNotes().add(curNote);
		}
		newTick.setNoteList(newNotes);
		
		return newTick;
	}
	
	/**
	 * Sets the counter integer to a new integer.
	 * @param newCount The integer you want to set the counter to.
	 */
	public static void setCounter(int newCount) {
		if (newCount <= 0) {
			throw new IllegalArgumentException();
		}
		else {
			counter = newCount;
		}
	}
	
	/**
	 * Retrieves an array of all notes associated with the TrackedTicket.
	 * @return noteArray A 2D array that contains all notes associated with the TrackedTicket.
	 */
	public String[][] getNotesArray() {
		String[][] noteArray = new String[notes.size()][2];
		
		for (int i = 0; i < notes.size(); i++) {
			noteArray[i][0] = notes.get(i).getNoteArray()[0];
			noteArray[i][1] = notes.get(i).getNoteArray()[1];

		}
		return noteArray;
	}

	/**
	 * Retrieves a string representation of the TrackedTicket's flag enumeration.
	 * @return A string value based on the flag enumeration, otherwise null.
	 */
	public String getFlagString() {
		if (this.flag == Flag.DUPLICATE) {
			return "Duplicate";
		}
		else if (this.flag == Flag.INAPPROPRIATE) {
			return "Inappropriate";
		}
		else if (this.flag == Flag.RESOLVED) {
			return "Resolved";
		}
		else {
			return null;
		}
	}

	/**
	 * Interface for states in the Ticket State Pattern.  All 
	 * concrete ticket states must implement the TicketState interface.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
	 */
	private interface TicketState {
		
		/**
		 * Update the {@link TrackedTicket} based on the given {@link Command}.
		 * An {@link UnsupportedOperationException} is throw if the {@link CommandValue}
		 * is not a valid action for the given state.  
		 * @param c {@link Command} describing the action that will update the {@link TrackedTicket}'s
		 * state.
		 * @throws UnsupportedOperationException if the {@link CommandValue} is not a valid action
		 * for the given state.
		 */
		void updateState(Command c);
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		String getStateName();
	
	}
	
	/**
	 * Class for implementing the behavior
	 * of the NewState.  Implements the methods in
	 * the TicketState interface.
	 * @author NBoar
	 */
	class NewState implements TicketState {
	
		/**
		 * Constructor for the NewState object.
		 */
		private NewState() {
			
		}
		
		/**
		 * Update the {@link TrackedTicket} based on the given {@link Command}.
		 * An {@link UnsupportedOperationException} is throw if the {@link CommandValue}
		 * is not a valid action for the given state.  
		 * @param c {@link Command} describing the action that will update the {@link TrackedTicket}'s
		 * state.
		 * @throws UnsupportedOperationException if the {@link CommandValue} is not a valid action
		 * for the given state.
		 */
		public void updateState(Command c) throws UnsupportedOperationException {
			if (c.getCommand() == CommandValue.POSSESSION) {
				
				setState(ASSIGNED_NAME);
				owner = c.getOwner();
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
			}
			else {
				throw new UnsupportedOperationException();
			}
		}
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return NEW_NAME;
		}
	
	}
	
	/**
	 * Class for implementing the behavior
	 * of the AssignedState.  Implements the methods in
	 * the TicketState interface.
	 * @author NBoar
	 */
	class AssignedState implements TicketState{
	
		/**
		 * Constructor for the AssignedState object.
		 */
		private AssignedState() {
			
		}
		
		/**
		 * Update the {@link TrackedTicket} based on the given {@link Command}.
		 * An {@link UnsupportedOperationException} is throw if the {@link CommandValue}
		 * is not a valid action for the given state.  
		 * @param c {@link Command} describing the action that will update the {@link TrackedTicket}'s
		 * state.
		 * @throws UnsupportedOperationException if the {@link CommandValue} is not a valid action
		 * for the given state.
		 */
		public void updateState(Command c) throws UnsupportedOperationException {
			if (c.command == CommandValue.ACCEPTED) {
				setState(WORKING_NAME);
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
			}
			else if (c.command == CommandValue.CLOSED) {
				
				if (c.getFlag() == Flag.DUPLICATE || c.getFlag() == Flag.INAPPROPRIATE) {
					setState(CLOSED_NAME);
					flag = c.getFlag();
					notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				}
				else {
					throw new IllegalArgumentException("Invalid flag.");
				}
				
			}
			else {
				throw new UnsupportedOperationException();
			}
		}
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return ASSIGNED_NAME;
		}
	}
	
	/**
	 * Class for implementing the behavior
	 * of the Workingtate.  Implements the methods in
	 * the TicketState interface.
	 * @author NBoar
	 */
	class WorkingState implements TicketState {
	
		/**
		 * Constructor for the WorkingState object.
		 */
		private WorkingState() {
			
		}
		
		/**
		 * Update the {@link TrackedTicket} based on the given {@link Command}.
		 * An {@link UnsupportedOperationException} is throw if the {@link CommandValue}
		 * is not a valid action for the given state.  
		 * @param c {@link Command} describing the action that will update the {@link TrackedTicket}'s
		 * state.
		 * @throws UnsupportedOperationException if the {@link CommandValue} is not a valid action
		 * for the given state.
		 */
		public void updateState(Command c) throws UnsupportedOperationException {
			if (c.command == CommandValue.PROGRESS) {
				
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				setState(WORKING_NAME);			
			}
			else if (c.command == CommandValue.FEEDBACK) {
				
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				setState(FEEDBACK_NAME);
			}
			else if (c.command == CommandValue.CLOSED) {
				
				if (c.getFlag() == Flag.DUPLICATE || c.getFlag() == Flag.INAPPROPRIATE || c.getFlag() == Flag.RESOLVED) {

					notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
					flag = c.getFlag();
					setState(CLOSED_NAME);
				}
			}
			else if (c.command == CommandValue.POSSESSION) {
				if (c.getOwner() == null || c.getOwner() == "") {
					throw new IllegalArgumentException("Invalid owner id.");
				}
				else {
					owner = c.getOwner();
					setState(ASSIGNED_NAME);
					notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				}
			}
			else {
				throw new UnsupportedOperationException();
			}
		}
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return WORKING_NAME;
		}
	}
	
	/**
	 * Class for implementing the behavior
	 * of the FeedbackState.  Implements the methods in
	 * the TicketState interface.
	 * @author NBoar
	 */
	class FeedbackState implements TicketState {
	
		/**
		 * Constructor for the FeedbackState object.
		 */
		private FeedbackState() {
		
		}
		
		/**
		 * Update the {@link TrackedTicket} based on the given {@link Command}.
		 * An {@link UnsupportedOperationException} is throw if the {@link CommandValue}
		 * is not a valid action for the given state.  
		 * @param c {@link Command} describing the action that will update the {@link TrackedTicket}'s
		 * state.
		 * @throws UnsupportedOperationException if the {@link CommandValue} is not a valid action
		 * for the given state.
		 */
		public void updateState(Command c) throws UnsupportedOperationException {
			if (c.command == CommandValue.FEEDBACK) {
				
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				setState(WORKING_NAME);
			}
			else {
				throw new UnsupportedOperationException();
			}
		}
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return FEEDBACK_NAME;
		}
	}
	
	/**
	 * Class for implementing the behavior
	 * of the ClosedState.  Implements the methods in
	 * the TicketState interface.
	 * @author NBoar
	 */
	class ClosedState implements TicketState {
		
		/**
		 * Constructor for the ClosedState object.
		 */
		private ClosedState() {

		}
		
		/**
		 * Update the {@link TrackedTicket} based on the given {@link Command}.
		 * An {@link UnsupportedOperationException} is throw if the {@link CommandValue}
		 * is not a valid action for the given state.  
		 * @param c {@link Command} describing the action that will update the {@link TrackedTicket}'s
		 * state.
		 * @throws UnsupportedOperationException if the {@link CommandValue} is not a valid action
		 * for the given state.
		 */
		public void updateState(Command c) throws UnsupportedOperationException {
			if (c.command == CommandValue.PROGRESS) {
				
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				setState(WORKING_NAME);
				flag = null;
			}
			else if (c.command == CommandValue.POSSESSION) {
				
				if (c.getOwner() == null || c.getOwner() == "") {
					throw new IllegalArgumentException("Invalid owner id");
				}
				else {
					owner = c.getOwner();
					notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
					setState(ASSIGNED_NAME);
				}
			}
			else {
				throw new UnsupportedOperationException();
			}
			

		}
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return CLOSED_NAME;
		}
	}
}
