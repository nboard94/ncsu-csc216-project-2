package edu.ncsu.csc216.tracker.ticket_tracker;

import static org.junit.Assert.*;

import org.junit.Test;

public class TicketTrackerModelTest {
	
	
	@Test
	public void testTicketTrackerModel() {
		TicketTrackerModel tickMod = TicketTrackerModel.getInstance();
		assertEquals(TicketTrackerModel.getInstance(), tickMod);
	}
	
	@Test
	public void testSaveTicketToFile() {
		
	}
	
	@Test
	public void testLoadTicketFromFile() {
		TicketTrackerModel ttm = TicketTrackerModel.getInstance();

		//try to load from file that doesn't exist
		try {
			ttm.loadTicketsFromFile("/Project2/test-files/ticket6.xml");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0,ttm.getTicketListAsArray().length);
		}

		//try to load from valid file
		try {
			ttm.loadTicketsFromFile("/Project2/test-files/ticket1.xml");
			assertEquals(1,ttm.getTicketListAsArray().length);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	
}
