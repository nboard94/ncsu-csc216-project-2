package edu.ncsu.csc216.tracker.ticket;

import edu.ncsu.csc216.tracker.command.Command;

public class ClosedState implements TicketState{

	private ClosedState() {
		
	}
	
	public void updateState(Command c) {
		
	}
	
	public String getStateName() {
		return null;
	}
}
