package edu.ncsu.csc216.tracker.ticket_tracker;

import java.util.List;

import edu.ncsu.csc216.ticket.xml.Ticket;
import edu.ncsu.csc216.ticket.xml.TicketIOException;
import edu.ncsu.csc216.ticket.xml.TicketReader;
import edu.ncsu.csc216.ticket.xml.TicketWriter;
import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.ticket.TrackedTicket;

/**
 * Maintains the trackedTicketList and deals with file IO.
 * @author NBoar
 */
public class TicketTrackerModel {
	
	/** The only instance of the TicketTrackerModel, following the Singleton pattern. */
	public static TicketTrackerModel tickTrack = new TicketTrackerModel();
	/** The current TrackedTicketList.*/
	public TrackedTicketList trackedTicketList = new TrackedTicketList();

	/**
	 * Private constructor for the TickeTrackerModel to ensure the Singleton pattern.
	 */
	private TicketTrackerModel() {
		
	}
	
	/**
	 * Retrieves the instance of the TicketTrackerModel.
	 * @return tickTrack The only instance of the TicketTrackerModel.
	 */
	public static TicketTrackerModel getInstance() {
		if (tickTrack == null) {
			tickTrack = new TicketTrackerModel();
		}
		return tickTrack;
	}
	
	/**
	 * Adds tickets from the trackedTicketList to the TicketWriter,
	 * and then marshals the data to the specified file.
	 * @param outputFile The file to save the tickets to.
	 * @throws IllegalArgumentException If TicketIOException is caught.
	 */
	public void saveTicketsToFile(String outputFile) throws IllegalArgumentException {
		
		if (trackedTicketList == null) {
			throw new IllegalArgumentException();
		}
		
		TicketWriter writer = new TicketWriter(outputFile);
		Ticket currentTick;
		
		try {
			for (int i = 0; i < trackedTicketList.getTrackedTickets().size(); i++) {
				currentTick = trackedTicketList.getTrackedTickets().get(i).getXMLTicket();
				writer.addItem(currentTick);
				writer.marshal();
			}
		} catch (TicketIOException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Retrieves XMLTickets from a file and adds them to
	 * the trackedTicketList.
	 * @param inputFile The file to load the tickets from.
	 * @throws IllegalArgumentException If TicketIOException is caught.
	 */
	public void loadTicketsFromFile(String inputFile) throws IllegalArgumentException {
		
		this.createNewTicketList();
		
		try {
			TicketReader tickRead = new TicketReader(inputFile);
			trackedTicketList.addXMLTickets(tickRead.getTickets());
		} catch (TicketIOException e) {
			throw new IllegalArgumentException();
		}		
	}
	
	/**
	 * Creates a new TrackedTicketList and sets
	 * trackedTicketList equal to it.
	 */
	public void createNewTicketList() {
		trackedTicketList = new TrackedTicketList();
	}
	
	/**
	 * Returns an array containing TrackedTickets.
	 * @param submitter The submitter of the tickets you want to view.
	 * @return ticketList A 2D array with the TrackedTicket's Id, state, and title.
	 */
	public Object[][] getTicketListAsArray() {
		Object[][] ticketList = new Object[trackedTicketList.tickets.size()][3];
		
		for (int i = 0; i < trackedTicketList.tickets.size(); i++) {
			ticketList[i][0] = trackedTicketList.getTrackedTickets().get(i).getTicketId();
			ticketList[i][1] = trackedTicketList.getTrackedTickets().get(i).getStateName();
			ticketList[i][2] = trackedTicketList.getTrackedTickets().get(i).getTitle();
		}
		
		return ticketList;
	}
	
	/**
	 * Returns an array containing TrackedTickets with a specified owner.
	 * @param submitter The submitter of the tickets you want to view.
	 * @return ticketList A 2D array with the TrackedTicket's Id, state, and title.
	 */
	public Object[][] getTicketListByOwnerAsArray(String owner) throws IllegalArgumentException {
		if (owner == null) {
			throw new IllegalArgumentException();
		}
		
		
		List<TrackedTicket> list = trackedTicketList.getTicketsByOwner(owner);
		
		Object[][] objArray= new Object[list.size()][3];
		
		for (int i = 0; i < list.size(); i++) {
			objArray[i][0] = list.get(i).getTicketId();
			objArray[i][1] = list.get(i).getStateName();
			objArray[i][2] = list.get(i).getTitle();
		}
		
		return objArray;
	}
	
	/**
	 * Returns an array containing TrackedTickets with a specified submitter.
	 * @param submitter The submitter of the tickets you want to view.
	 * @return ticketList A 2D array with the TrackedTicket's Id, state, and title.
	 */
	public Object[][] getTicketListBySubmitterAsArray(String submitter) throws IllegalArgumentException {
		if (submitter == null) {
			throw new IllegalArgumentException();
		}
		
		List<TrackedTicket> list = trackedTicketList.getTicketsBySubmitter(submitter);
		
		Object[][] objArray = new Object[list.size()][3];
		
		for (int i = 0; i < list.size(); i++) {
			objArray[i][0] = list.get(i).getTicketId();
			objArray[i][1] = list.get(i).getStateName();
			objArray[i][2] = list.get(i).getTitle();
		}
		
		return objArray;
	}
	
	/**
	 * Finds and returns the single ticket with the unique ID passed.
	 * Delegates to TrackedTicketList class.
	 * @param id The ID of the ticket you want to find.
	 * @return trackedTicketList.ticketById The ticket that has the specified ID.
	 */
	public TrackedTicket getTicketById(int id) {
		return trackedTicketList.getTicketById(id);
	}
	
	/**
	 * Sends the command down the the TrackedTicket with
	 * the id matching the parameter.
	 * @param id The ID of the ticket to be updated.
	 * @param c The command to apply to the ticket.
	 */
	public void executeCommand(int id, Command c) {
		trackedTicketList.executeCommand(id, c);
	}
	
	/**
	 * Finds and deletes the single ticket with the unique ID passed.
	 * Delegates to TrackedTicketList class.
	 * @param id The ID of the ticket you want to delete.
	 */
	public void deleteTicketById(int id) {
		trackedTicketList.deleteTicketById(id);
	}
	
	/**
	 * Adds a new TrackedTicket to the trackedTicketList.
	 * Delegates to TrackedTicketList class.
	 * @param title The title of the new ticket to add.
	 * @param submitter The submitter of the new ticket to add.
	 * @param note The note of the new text to add.
	 */
	public void addTicketToList(String title, String submitter, String note) {
		trackedTicketList.addTrackedTicket(title, submitter, note);
	}
}
