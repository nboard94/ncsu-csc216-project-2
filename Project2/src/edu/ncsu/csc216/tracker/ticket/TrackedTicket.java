package edu.ncsu.csc216.tracker.ticket;

import java.util.ArrayList;

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
	public NewState newState;
	/** Represents the TrackedTicket's AssignedState. */
	public AssignedState assignedState;
	/** Represents the TrackedTicket's WorkingState. */
	public WorkingState workingState;
	/** Represents the TrackedTicket's FeedbackState. */
	public FeedbackState feedbackState;
	/** Represents the TrackedTicket's ClosedState. */
	public ClosedState closedState;
	
	//TODO are these right?
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
	
	
	//TODO
	public TrackedTicket(String newTitle, String newSubmitter, String newNote) {
		ticketId = counter;
		title = newTitle;

		this.setState(NEW_NAME);
		notes.add(new Note(newSubmitter, newNote));
		
		incrementCounter();
	}
	
	//TODO
	public TrackedTicket(Ticket t) {
		this(t.getTitle(), t.getSubmitter(), "");
		
		ticketId = t.getId();
		owner = t.getOwner();

		this.setFlag(t.getFlag());
		this.setState(t.getState());
		
		
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
		if (state.equals(newState)) {
			return NEW_NAME;
		}
		else if (state.equals(assignedState)) {
			return ASSIGNED_NAME;
		}
		else if (state.equals(workingState)) {
			return WORKING_NAME;
		}
		else if (state.equals(feedbackState)) {
			return FEEDBACK_NAME;
		}
		else if (state.equals(closedState)) {
			return CLOSED_NAME;
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
		if (newStateName.equals(newState.getStateName())) {
			state = newState;
		}
		else if (newStateName.equals(assignedState.getStateName())) {
			state = assignedState;
		}
		else if (newStateName.equals(workingState.getStateName())) {
			state = workingState;
		}
		else if (newStateName.equals(feedbackState.getStateName())) {
			state = feedbackState;
		}
		else if (newStateName.equals(closedState.getStateName())) {
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
		if (flagStr.equals(Flag.DUPLICATE.toString())) {
			this.flag = Flag.DUPLICATE;
		}
		else if (flagStr.equals(Flag.INAPPROPRIATE.toString())) {
			this.flag = Flag.INAPPROPRIATE;
		}
		else if (flagStr.equals(Flag.RESOLVED.toString())) {
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
		try {
			state.updateState(c);
		} catch (UnsupportedOperationException e){
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * Converts a TrackedTicket object to a Ticket object.
	 * @return newTick The newly created Ticket object based off of the TrackedTicket.
	 */
	public Ticket getXMLTicket() {
		Ticket newTick = new Ticket();
		
		newTick.setId(this.getTicketId());
		newTick.setTitle(this.getTitle());
		newTick.setSubmitter(this.getSubmitter());
		newTick.setOwner(this.getOwner());
		newTick.setFlag(this.getFlagString());
		newTick.setState(this.getStateName());
		
		//TODO
		NoteList newNotes = new NoteList();
		newTick.setNoteList(newNotes);
		
		return newTick;
	}
	
	/**
	 * Sets the counter integer to a new integer.
	 * @param newCount The integer you want to set the counter to.
	 */
	public static void setCounter(int newCount) {
		counter = newCount;
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
			return "duplicate";
		}
		else if (this.flag == Flag.INAPPROPRIATE) {
			return "innappropriate";
		}
		else if (this.flag == Flag.RESOLVED) {
			return "resolved";
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
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.POSSESSION) {
				state = assignedState;
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
		public void updateState(Command c) {
			if (c.command == CommandValue.ACCEPTED) {
				state = workingState;
			}
			else if (c.command == CommandValue.CLOSED) {
				state = closedState;
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
		public void updateState(Command c) {
			if (c.command == CommandValue.PROGRESS) {
				state = workingState;
			}
			else if (c.command == CommandValue.FEEDBACK) {
				state = feedbackState;
			}
			else if (c.command == CommandValue.CLOSED) {
				state = closedState;
			}
			else if (c.command == CommandValue.POSSESSION) {
				state = assignedState;
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
		public void updateState(Command c) {
			if (c.command == CommandValue.FEEDBACK) {
				state = workingState;
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
		public void updateState(Command c) {
			if (c.command == CommandValue.ACCEPTED) {
				state = workingState;
			}
			else if (c.command == CommandValue.POSSESSION) {
				state = assignedState;
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
