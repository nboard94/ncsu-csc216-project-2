package edu.ncsu.csc216.tracker.ticket_tracker;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.ticket.xml.TicketIOException;
import edu.ncsu.csc216.tracker.ticket.TrackedTicket;

public class TicketTrackerModelTest {
	
	
	@Test
	public void testTicketTrackerModel() {
		TicketTrackerModel tickMod = TicketTrackerModel.getInstance();
		assertEquals(TicketTrackerModel.getInstance(), tickMod);
	}
	
	@Test
	public void testSaveTicketToFile() {
		TicketTrackerModel ttm = TicketTrackerModel.getInstance();
		ttm.createNewTicketList();
		
		try {
			ttm.saveTicketsToFile("test-files/outputTest.xml");
			
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	@Test
	public void testLoadTicketFromFile() {
		TicketTrackerModel ttm = TicketTrackerModel.getInstance();
		ttm.createNewTicketList();

		//try to load from file that doesn't exist
		try {
			ttm.loadTicketsFromFile("test-files/ticket6.xml");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0,ttm.getTicketListAsArray().length);
		}

		//try to load from valid file
		try {
			ttm.loadTicketsFromFile("test-files/ticket1.xml");
			assertEquals(5,ttm.getTicketListAsArray().length);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	@Test
	public void testCreateNewTicketList() {
		TicketTrackerModel ttm = TicketTrackerModel.getInstance();
		ttm.addTicketToList("bing", "bong", "bung");
		
		assertFalse(ttm.getTicketListAsArray().length == 0);
		
		ttm.createNewTicketList();
		
		assertTrue(ttm.getTicketListAsArray().length == 0);
	}
	
	@Test
	public void testGetTicketsBySubmitterAsArray() {
		TicketTrackerModel ttm = TicketTrackerModel.getInstance();
		ttm.createNewTicketList();
		
		ttm.addTicketToList("title", "submitter", "note");
		ttm.addTicketToList("title", "submitter1", "note");
		ttm.addTicketToList("title", "submitter2", "note");
		ttm.addTicketToList("title", "submitter", "note");
		ttm.addTicketToList("title", "submitter", "note");
		ttm.addTicketToList("title", "submitter2", "note");
		
		Object[][] arr1 = ttm.getTicketListBySubmitterAsArray("submitter");
		assertEquals(3, arr1.length);
		
		Object[][] arr2 = ttm.getTicketListBySubmitterAsArray("submitter1");
		assertEquals(1, arr2.length);

		Object[][] arr3 = ttm.getTicketListBySubmitterAsArray("submitter2");
		assertEquals(2, arr3.length);
	}
	

}
