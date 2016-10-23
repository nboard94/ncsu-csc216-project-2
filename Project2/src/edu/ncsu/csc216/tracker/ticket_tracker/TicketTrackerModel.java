package edu.ncsu.csc216.tracker.ticket_tracker;

import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.ticket.TrackedTicket;

public class TicketTrackerModel {

	private TicketTrackerModel() {
		
	}
	
	public static TicketTrackerModel getInstance() {
		return null;
	}
	
	public void saveTicketsToFile(String s) {
		
	}
	
	public void loadTicketsFromFile(String s) {
		
	}
	
	public void createNewTicketList() {
		
	}
	
	public Object[][] getTicketListAsArray() {
		return null;
	}
	
	public Object[][] getTicketListByOwnerAsArray(String o) {
		return null;
	}
	
	public Object[][] getTicketListBySubmitterAsArray(String s) {
		return null;
	}
	
	public TrackedTicket getTicketById(int i) {
		return null;
	}
	
	public void executeCommand(int i, Command c) {
		
	}
	
	public void deleteTicketById(int i) {
		
	}
	
	public void addTicketToList(String s1, String s2, String s3) {
		
	}
}
