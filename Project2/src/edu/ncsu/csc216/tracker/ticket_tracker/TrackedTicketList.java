package edu.ncsu.csc216.tracker.ticket_tracker;

import java.util.List;

import edu.ncsu.csc216.ticket.xml.Ticket;
import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.ticket.TrackedTicket;

public class TrackedTicketList {

	private static final int INITIAL_COUNTER_VALUE = 1;
	
	public TrackedTicketList() {
		
	}
	
	public int addTrackedTicket(String s1, String s2, String s3) {
		return 0;
	}
	
	public void addXMLTickets(List<Ticket> l) {
		
	}
	
	public List<TrackedTicket> getTrackedTickets() {
		return null;
	}
	
	public List<TrackedTicket> getTicketsByOwner(String o) {
		return null;
	}
	
	public List<TrackedTicket> getTicketBySubmitter(String s) {
		return null;
	}
	
	public TrackedTicket getTicketById(int i) {
		return null;
	}
	
	public void executeCommand(int i, Command c) {
		
	}
	
	public void deleteTicketById(int i) {
		
	}
}
