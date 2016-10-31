package edu.ncsu.csc216.tracker.ticket;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.ticket.xml.Ticket;

public class TrackedTicketTest {

	
	@Test
	public void testTrackedTicketStringStringString() {
		TrackedTicket t1 = new TrackedTicket("Ticket1", "ndboard", "apple");
		TrackedTicket t2 = new TrackedTicket("Ticket2", "ndboard", "banana");
		TrackedTicket t3 = new TrackedTicket("Ticket3", "ndboard", "cherry");

		assertEquals("Ticket1", t1.getTitle());
		assertEquals("ndboard", t2.getSubmitter());
		assertEquals("ndboard", t3.getNotes().get(0).getNoteAuthor());
		assertEquals("cherry", t3.getNotes().get(0).getNoteText());
		
		assertEquals(1, t1.getTicketId());
		assertEquals(2, t2.getTicketId());
		assertEquals(3, t3.getTicketId());
		
		assertEquals("new", t1.getStateName());
	}
	
	@Test
	public void testTrackedTicketTicket() {
		Ticket t = new Ticket();
		t.setTitle("Ticket1");
		t.setSubmitter("ndboard");
	}
	
}
