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
	
	//TODO are these right?
	public static final String NEW_NAME = "New";
	public static final String ASSIGNED_NAME = "Assigned";
	public static final String WORKING_NAME = "Working";
	public static final String FEEDBACK_NAME = "Feedback";
	public static final String CLOSED_NAME = "Closed";
	
	private static int counter;
	
	public TrackedTicket(String s1, String s2, String s3) {
		
	}
	
	public TrackedTicket(Ticket t) {
		
	}
	
	public static void incrementCounter() {
		
	}
	
	public int getTicketId() {
		return 0;
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
		
	}
	
	public String getOwner() {
		return null;
	}
	
	public String getTitle() {
		return null;
	}
	
	public String getSubmitter() {
		return null;
	}
	
	public ArrayList<Note> getNotes() {
		return null;
	}
	
	public void update(Command c) {
		
	}
	
	public Ticket getXMLTicket() {
		return null;
	}
	
	public static void setCounter(int i) {
		
	}
	
	public String[][] getNotesArray() {
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

	public String getFlagString() {
		return null;
	}
}

class NewState implements TicketState {

	private NewState() {
		
	}
	
	public void updateState(Command c) {
		
	}
	
	public String getStateName() {
		return null;
	}
}

class AssignedState implements TicketState{

	private AssignedState() {
		
	}
	
	public void updateState(Command c) {
		
	}
	
	public String getStateName() {
		return null;
	}
}

class WorkingState implements TicketState {

	private WorkingState() {
		
	}
	
	public void updateState(Command c) {
		
	}
	
	public String getStateName() {
		return null;
	}
}

class FeedbackState implements TicketState{

	private FeedbackState() {
		
	}
	
	public void updateState(Command c) {
		
	}
	
	public String getStateName() {
		return null;
	}
}


class ClosedState implements TicketState{

	private ClosedState() {
		
	}
	
	public void updateState(Command c) {
		
	}
	
	public String getStateName() {
		return null;
	}
}
