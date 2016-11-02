package edu.ncsu.csc216.tracker.ticket_tracker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.ticket.xml.Ticket;
import edu.ncsu.csc216.ticket.xml.TicketIOException;
import edu.ncsu.csc216.ticket.xml.TicketList;
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
	public static TrackedTicketList trackedTicketList = new TrackedTicketList();

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
		return tickTrack;
	}
	
	/**
	 * Adds tickets from the trackedTicketList to the TicketWriter,
	 * and then marshals the data to the specified file.
	 * @param outputFile The file to save the tickets to.
	 * @throws IllegalArgumentException If TicketIOException is caught.
	 */
	public void saveTicketsToFile(String outputFile) throws IllegalArgumentException {
		
		TrackedTicket currentTrackedTick;
		Ticket currentTick;
		TicketList ticksToSave = new TicketList();
		
		//TODO
		//Is this implemented right?
		//Why Marshal() twice?
		try {
			TicketWriter tickWrite = new TicketWriter(outputFile);
			
			for (int i = 0; i < trackedTicketList.getTrackedTickets().size(); i++) {
				currentTrackedTick = trackedTicketList.getTrackedTickets().get(i);
				currentTick = currentTrackedTick.getXMLTicket();
				ticksToSave.getTickets().add(currentTick);
				tickWrite.addItem(currentTick);
				tickWrite.marshal();
				tickWrite.marshal();
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
	public Object[][] getTicketListByOwnerAsArray(String owner) {
		
		int count = 0;
		for (int i = 0; i < trackedTicketList.getTrackedTickets().size(); i++) {
			if (trackedTicketList.getTrackedTickets().get(i).getOwner().equals(owner)) {
				count++;
			}
		}
		
		Object[][] ticketList = new Object[trackedTicketList.tickets.size()][3];
		
		int add = 0;
		for (int i = 0; i < trackedTicketList.tickets.size(); i++) {
			if (trackedTicketList.tickets.get(i).getOwner() == null) {
				throw new IllegalArgumentException();
			}
			
			ticketList[add][0] = trackedTicketList.getTicketsByOwner(owner).get(i).getTicketId();
			ticketList[add][1] = trackedTicketList.getTicketsByOwner(owner).get(i).getStateName();
			ticketList[add][2] = trackedTicketList.getTicketsByOwner(owner).get(i).getTitle();
	
			add++;
		}
		
		return ticketList;
	}
	
	/**
	 * Returns an array containing TrackedTickets with a specified submitter.
	 * @param submitter The submitter of the tickets you want to view.
	 * @return ticketList A 2D array with the TrackedTicket's Id, state, and title.
	 */
	public Object[][] getTicketListBySubmitterAsArray(String submitter) {
		
		int count = 0;
		for (int i = 0; i < trackedTicketList.getTrackedTickets().size(); i++) {
			if (trackedTicketList.getTrackedTickets().get(i).getSubmitter().equals(submitter)) {
				count++;
			}
		}
		
		Object[][] ticketList = new Object[count][3];

		int add = 0;
		//TODO
		//pull out tickets with submitter
		for (int i = 0; i < trackedTicketList.tickets.size(); i++) {
			if (trackedTicketList.tickets.get(i).getSubmitter() == null) {
				throw new IllegalArgumentException();
			}
			if (trackedTicketList.tickets.get(i).getSubmitter().equals(submitter)) {
				ticketList[add][0] = trackedTicketList.tickets.get(i).getTicketId();
				ticketList[add][1] = trackedTicketList.tickets.get(i).getStateName();
				ticketList[add][2] = trackedTicketList.tickets.get(i).getTitle();
				
				add++;
			}
			
		}
		
		return ticketList;
		
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
