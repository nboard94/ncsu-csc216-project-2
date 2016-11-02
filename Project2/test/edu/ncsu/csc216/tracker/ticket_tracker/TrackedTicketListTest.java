package edu.ncsu.csc216.tracker.ticket_tracker;

import static org.junit.Assert.*;

import org.junit.Test;

public class TrackedTicketListTest {

	@Test
	public void testTrackedTicketList() {
		TrackedTicketList tList = new TrackedTicketList();
		assertEquals(0, tList.getTrackedTickets().size());
	}
	
	@Test
	public void testAddTrackedTicket() {
		TrackedTicketList tList = new TrackedTicketList();
		tList.addTrackedTicket("Ticket1", "ndboard", "apple");
		
		assertEquals("Ticket1", tList.getTrackedTickets().get(0).getTitle());
		assertEquals("ndboard", tList.getTrackedTickets().get(0).getSubmitter());
		assertEquals("ndboard", tList.getTrackedTickets().get(0).getNotes().get(0).getNoteAuthor());
		assertEquals("apple", tList.getTrackedTickets().get(0).getNotes().get(0).getNoteText());
	}

}
