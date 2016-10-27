package edu.ncsu.csc216.tracker.ticket_tracker;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.ticket.xml.Ticket;
import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.ticket.TrackedTicket;

/**
 * Maintains a list of TrackedTickets and includes methods for creating,
 * loading, saving, et cetera.
 * @author NBoar
 */
public class TrackedTicketList {

	/** The counter value to use when dealing with any new list. */
	private static final int INITIAL_COUNTER_VALUE = 1;
	/** The ArrayList containing the TrackedTickets in a TrackedTicketList. */
	ArrayList<TrackedTicket> tickets = new ArrayList<TrackedTicket>();
	
	
	/**
	 * The constructor for a TrackedTicketList.
	 * Sets the counter back to 1.
	 */
	public TrackedTicketList() {
		TrackedTicket.setCounter(INITIAL_COUNTER_VALUE);
	}
	
	/**
	 * Adds a new TrackedTicket to the tickets list.
	 * @param title The title of the new ticket to add.
	 * @param submitter The submitter of the new ticket to add.
	 * @param note The note of the new ticket to add.
	 * @return The ticketId of the newly created/added ticket.
	 */
	public int addTrackedTicket(String title, String submitter, String note) {
		TrackedTicket newTick = new TrackedTicket(title, submitter, note);
		tickets.add(newTick);
		
		return newTick.getTicketId();
	}
	
	//TODO
	public void addXMLTickets(List<Ticket> list) {
		
	}
	
	/**
	 * Retrieves all tickets.
	 * @return tickets The ArrayList containing all tickets.
	 */
	public List<TrackedTicket> getTrackedTickets() {
		return tickets;
	}
	
	/**
	 * Filters all the tickets by the specified owner.
	 * @param owner The owner that you want to filter the tickets by.
	 * @return ticketsByOwner An ArrayList containing tickets only associated with the specified owner.
	 */
	public List<TrackedTicket> getTicketsByOwner(String owner) {
		ArrayList<TrackedTicket> ticketsByOwner = new ArrayList<TrackedTicket>();
		
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).getOwner() == owner) {
				ticketsByOwner.add(tickets.get(i));
			}
		}
		
		return ticketsByOwner;
	}
	
	/**
	 * Filters all the tickets by the specified submitter.
	 * @param submitter The submitter that you want to filter the tickets by.
	 * @return ticketsBySubmitter An ArrayList containing tickets only associated with the specified submitter.
	 */
	public List<TrackedTicket> getTicketsBySubmitter(String submitter) {
		ArrayList<TrackedTicket> ticketsBySubmitter = new ArrayList<TrackedTicket>();
		
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).getSubmitter() == submitter) {
				ticketsBySubmitter.add(tickets.get(i));
			}
		}
		
		return ticketsBySubmitter;
	}
	
	/**
	 * Finds and returns the single ticket with the unique ID passed.
	 * @param id The ID of the ticket you want to find.
	 * @return ticketById The ticket that has the specified ID.
	 */
	public TrackedTicket getTicketById(int id) {
	TrackedTicket ticketById = null;
		
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).getTicketId() == id) {
				ticketById = tickets.get(i);
			}
		}
		
		return ticketById;
	}
	
	//TODO
	public void executeCommand(int i, Command c) {
		
	}
	
	/**
	 * Finds and deletes the single ticket with the unique ID passed.
	 * @param id The ID of the ticket you want to delete.
	 */
	public void deleteTicketById(int id) {
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).getTicketId() == id) {
				tickets.remove(i);
			}
		}
	}
}
