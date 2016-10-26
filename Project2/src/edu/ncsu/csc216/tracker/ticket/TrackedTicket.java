package edu.ncsu.csc216.tracker.ticket;

import java.util.ArrayList;

import edu.ncsu.csc216.ticket.xml.Ticket;
import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.command.Command.Flag;

public class TrackedTicket {

	private int ticketId;
	private String title;
	private String submitter;
	private String owner;
	private static int counter = 1;
	ArrayList<Note> notes = new ArrayList<Note>();
	
	//TODO are these right?
	public static final String NEW_NAME = "New";
	public static final String ASSIGNED_NAME = "Assigned";
	public static final String WORKING_NAME = "Working";
	public static final String FEEDBACK_NAME = "Feedback";
	public static final String CLOSED_NAME = "Closed";
	
	
	
	public TrackedTicket(String newTitle, String newSubmitter, String newNote) {
		ticketId = counter;
		title = newTitle;

		notes.add(new Note(newSubmitter, newNote));
		
		incrementCounter();
	}
	
	public TrackedTicket(Ticket t) {
		this(t.getTitle(), t.getSubmitter(), "");
	}
	
	public static void incrementCounter() {
		counter++;
	}
	
	public int getTicketId() {
		return ticketId;
	}
	
	public String getStateName() {
		return null;
	}
	
	private void setState(String s) {
		
	}
	
	public Flag getFlag() {
		return null;
	}
	
	private void setFlag(String s) {
		this.setFlag(s);
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getSubmitter() {
		return submitter;
	}
	
	public ArrayList<Note> getNotes() {
		return null;
	}
	
	public void update(Command c) {
		
	}
	
	public Ticket getXMLTicket() {
		return null;
	}
	
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
