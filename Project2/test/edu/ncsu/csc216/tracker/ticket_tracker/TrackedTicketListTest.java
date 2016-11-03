package edu.ncsu.csc216.tracker.ticket_tracker;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class that contains methods for
 * testing the TrackedTicketList class.
 * @author NBoar
 */
public class TrackedTicketListTest {

	/**
	 * Tests the TrackedTicketList constructor.
	 */
	@Test
	public void testTrackedTicketList() {
		TrackedTicketList tList = new TrackedTicketList();
		assertEquals(0, tList.getTrackedTickets().size());
	}
	
	/**
	 * Tests the addTrackedTicket method in TrackedTicketList.
	 */
	@Test
	public void testAddTrackedTicket() {
		TrackedTicketList tList = new TrackedTicketList();
		tList.addTrackedTicket("Ticket1", "ndboard", "apple");
		
		assertEquals("Ticket1", tList.getTrackedTickets().get(0).getTitle());
		assertEquals("ndboard", tList.getTrackedTickets().get(0).getSubmitter());
		assertEquals("ndboard", tList.getTrackedTickets().get(0).getNotes().get(0).getNoteAuthor());
		assertEquals("apple", tList.getTrackedTickets().get(0).getNotes().get(0).getNoteText());
	}
	
	@Test
	public void testDeleteTicket() {
		TrackedTicketList tList = new TrackedTicketList();
		tList.addTrackedTicket("Ticket1", "ndboard", "apple");

		assertEquals(1, tList.tickets.size());
		
		tList.deleteTicketById(1);
		assertEquals(0, tList.tickets.size());
		
		
	}

}
