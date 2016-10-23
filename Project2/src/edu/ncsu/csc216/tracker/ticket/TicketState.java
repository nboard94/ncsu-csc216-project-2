package edu.ncsu.csc216.tracker.ticket;

import edu.ncsu.csc216.tracker.command.Command;

public interface TicketState {

	public void updateState(Command c);
	
	public String getStateName();
}
