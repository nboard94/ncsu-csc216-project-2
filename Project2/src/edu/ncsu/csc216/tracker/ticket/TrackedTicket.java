package edu.ncsu.csc216.tracker.ticket;

import java.lang.Thread.State;
import java.util.ArrayList;

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
	
	public Flag flag;
	public CommandValue command;
	
	public TicketState state;
	public NewState newState;
	public AssignedState assignedState;
	public WorkingState workingState;
	public FeedbackState feedbackState;
	public ClosedState closedState;
	
	//TODO are these right?
	public static final String NEW_NAME = "New";
	public static final String ASSIGNED_NAME = "Assigned";
	public static final String WORKING_NAME = "Working";
	public static final String FEEDBACK_NAME = "Feedback";
	public static final String CLOSED_NAME = "Closed";
	
	
	
	public TrackedTicket(String newTitle, String newSubmitter, String newNote) {
		ticketId = counter;
		title = newTitle;

		this.setState(NEW_NAME);
		notes.add(new Note(newSubmitter, newNote));
		
		incrementCounter();
	}
	
	public TrackedTicket(Ticket t) {
		this(t.getTitle(), t.getSubmitter(), "");
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
		else {
			state = null;
		}
	}
	
	/**
	 * Retrieves the flag associated with the ticket.
	 * @return flag The flag associated with the ticket.
	 */
	public Flag getFlag() {
		return flag;
	}
	
	private void setFlag(String s) {
		
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
	
	public void update(Command c) {
		
	}
	
	public Ticket getXMLTicket() {
		return null;
	}
	
	/**
	 * Sets the counter integer to a new integer.
	 * @param newCount The integer you want to set the counter to.
	 */
	public static void setCounter(int newCount) {
		counter = newCount;
	}
	
	public String[][] getNotesArray() {
		
		String[][] noteArray = new String[notes.size()][2];
		
		for (int i = 0; i < notes.size(); i++) {
			noteArray[i][0] = notes.get(i).getNoteArray()[0];
			noteArray[i][1] = notes.get(i).getNoteArray()[1];

		}
		
		return noteArray;
	}

	public String getFlagString() {
		return null;
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
	
	class NewState implements TicketState {
	
		private NewState() {
			
		}
		
		public void updateState(Command c) {
			
		}
		
		public String getStateName() {
			return NEW_NAME;
		}
	
	}
	
	class AssignedState implements TicketState{
	
		private AssignedState() {
			
		}
		
		public void updateState(Command c) {
			
		}
		
		public String getStateName() {
			return ASSIGNED_NAME;
		}
	}
	
	class WorkingState implements TicketState {
	
		private WorkingState() {
			
		}
		
		public void updateState(Command c) {
			
		}
		
		public String getStateName() {
			return WORKING_NAME;
		}
	}
	
	class FeedbackState implements TicketState {
	
		private FeedbackState() {
			
		}
		
		public void updateState(Command c) {
			
		}
		
		public String getStateName() {
			return FEEDBACK_NAME;
		}
	}
	
	class ClosedState implements TicketState {
		
		private ClosedState() {

		}
		
		public void updateState(Command c) {
			
		}
		
		public String getStateName() {
			return CLOSED_NAME;
		}
	}
}
