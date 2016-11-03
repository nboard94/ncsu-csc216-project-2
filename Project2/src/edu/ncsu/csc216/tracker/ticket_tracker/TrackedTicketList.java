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
	
	/**
	 * Takes in a List of Ticket objects.  Iterates through the List,
	 * converts each Ticket to a TrackedTicket, adds the TrackedTicket
	 * to the TrackedTicketList.
	 * @param list The List of Tickets that are to be added to the TrackedTicketList.
	 */
	public void addXMLTickets(List<Ticket> list) {
		for (int i = 0; i < list.size(); i++) {
			tickets.add(new TrackedTicket(list.get(i)));
		}
	}
	
	/**
	 * Retrieves all tickets.
	 * @return tickets The ArrayList containing all tickets.
	 */
	public List<TrackedTicket> getTrackedTickets() {
		if (tickets == null) {
			throw new IllegalArgumentException();
		}
		else {
			return tickets;
		}
	}
	
	/**
	 * Filters all the tickets by the specified owner.
	 * @param owner The owner that you want to filter the tickets by.
	 * @return ticketsByOwner An ArrayList containing tickets only associated with the specified owner.
	 */
	public List<TrackedTicket> getTicketsByOwner(String owner) throws IllegalArgumentException {
		if (owner == null || owner.equals("")) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<TrackedTicket> ticketsByOwner = new ArrayList<TrackedTicket>();
		
		for (int i = 0; i < tickets.size(); i++) {
			if (owner.equals(tickets.get(i).getOwner())) {
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
	public List<TrackedTicket> getTicketsBySubmitter(String submitter) throws IllegalArgumentException {
		if (submitter == null || submitter.equals("")) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<TrackedTicket> ticketsBySubmitter = new ArrayList<TrackedTicket>();
		
		for (int i = 0; i < tickets.size(); i++) {
			if (submitter.equals(tickets.get(i).getSubmitter())) {
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
	
	/**
	 * Sends the command down the the TrackedTicket with
	 * the id matching the parameter.
	 * @param id The ID of the ticket to be updated.
	 * @param c The command to apply to the ticket.
	 */
	public void executeCommand(int id, Command c) {
		this.getTicketById(id).update(c);
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
