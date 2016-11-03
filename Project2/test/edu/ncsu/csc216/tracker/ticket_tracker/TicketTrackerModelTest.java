package edu.ncsu.csc216.tracker.ticket_tracker;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.command.Command.CommandValue;
import edu.ncsu.csc216.tracker.command.Command.Flag;
import edu.ncsu.csc216.tracker.ticket.TrackedTicket;

/**
 * Class that contains tests for the
 * methods in TicketTrackerModel.
 * @author NBoar
 */
public class TicketTrackerModelTest {
	
	
	/**
	 * Tests the constructor for TicketTrackerModel.
	 */
	@Test
	public void testTicketTrackerModel() {
		TicketTrackerModel tickMod = TicketTrackerModel.getInstance();
		assertEquals(TicketTrackerModel.getInstance(), tickMod);
	}
	
	/**
	 * Tests the saveTicketToFile method in TicketTrackerModel.
	 */
	@Test
	public void testSaveTicketToFile() {
		TicketTrackerModel ttm = TicketTrackerModel.getInstance();
		ttm.createNewTicketList();
		
		try {
			ttm.saveTicketsToFile("test-files/outputTest.xml");
			
		} catch (IllegalArgumentException e) {
			assertEquals(0, ttm.getTicketListAsArray().length);
		}
		
		assertEquals(0, ttm.getTicketListAsArray().length);
	}
	
	/**
	 * Tests the loadTicketsFromFile method in TicketTrackerModel.
	 */
	@Test
	public void testLoadTicketFromFile() {
		TicketTrackerModel ttm = TicketTrackerModel.getInstance();
		ttm.createNewTicketList();

		//try to load from file that doesn't exist
		try {
			ttm.loadTicketsFromFile("test-files/ticket6.xml");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, ttm.getTicketListAsArray().length);
		}

		//try to load from valid file
		try {
			ttm.loadTicketsFromFile("test-files/ticket1.xml");
			assertEquals(5, ttm.getTicketListAsArray().length);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	/**
	 * Tests the createNewTicketList method in TicketTrackerModel.
	 */
	@Test
	public void testCreateNewTicketList() {
		TicketTrackerModel ttm = TicketTrackerModel.getInstance();
		ttm.addTicketToList("bing", "bong", "bung");
		
		assertFalse(ttm.getTicketListAsArray().length == 0);
		
		ttm.createNewTicketList();
		
		assertTrue(ttm.getTicketListAsArray().length == 0);
	}
	
	/**
	 * Tests the getTicketsBySubmitterAsArray method in TicketTrackerModel.
	 */
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
	
	/**
	 * Tests the getTicketsById method in TicketTrackerModel.
	 */
	@Test
	public void testGetTicketsByID() {
		TicketTrackerModel ttm = TicketTrackerModel.getInstance();
		ttm.createNewTicketList();
		
		ttm.addTicketToList("title", "submitter", "note");
		ttm.addTicketToList("title", "submitter1", "note");
		ttm.addTicketToList("title", "submitter2", "note");
		
		TrackedTicket t1 = ttm.getTicketById(1);
		TrackedTicket t2 = ttm.getTicketById(2);
		TrackedTicket t3 = ttm.getTicketById(3);

		assertEquals("submitter", t1.getSubmitter());
		assertEquals("submitter1", t2.getSubmitter());
		assertEquals("submitter2", t3.getSubmitter());

	}
	
	/**
	 * Tests the deleteTicktById method in TicketTrackerModel.
	 */
	@Test
	public void testDeleteTicketById() {
		TicketTrackerModel ttm = TicketTrackerModel.getInstance();
		ttm.createNewTicketList();
		
		ttm.addTicketToList("title", "submitter", "note");
		ttm.addTicketToList("title", "submitter1", "note");
		ttm.addTicketToList("title", "submitter2", "note");
		
		ttm.deleteTicketById(1);
		assertNull(ttm.getTicketById(1));
		ttm.deleteTicketById(2);
		assertNull(ttm.getTicketById(2));
		ttm.deleteTicketById(3);
		assertNull(ttm.getTicketById(3));
	}
	
	/**
	 * Tests the getTicketsByOwnerAsArray method in TicketTrackerModel.
	 */
	@Test
	public void testGetTicketByOwnerAsArray() {
		TicketTrackerModel ttm = TicketTrackerModel.getInstance();
		ttm.createNewTicketList();
		
		ttm.addTicketToList("title1", "submitter", "note");
		ttm.addTicketToList("title2", "submitter1", "note");
		ttm.addTicketToList("title3", "submitter2", "note");
		
		TrackedTicket t1 = ttm.getTicketById(1);
		TrackedTicket t2 = ttm.getTicketById(2);
		TrackedTicket t3 = ttm.getTicketById(3);
		
		Command n2a = new Command(CommandValue.POSSESSION, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		t1.update(n2a);
		t2.update(n2a);
		t3.update(n2a);
		
		Object[][] objArray = ttm.getTicketListByOwnerAsArray("ndboard");

		assertEquals(3, objArray.length);

	}
	
	/**
	 * Tests the executeCommand method in TicketTrackerModel.
	 */
	@Test
	public void testExecuteCommand() {
		TicketTrackerModel ttm = TicketTrackerModel.getInstance();
		ttm.createNewTicketList();
		ttm.addTicketToList("title1", "submitter", "note");
		TrackedTicket t1 = ttm.getTicketById(1);
		Command n2a = new Command(CommandValue.POSSESSION, "ndboard", Flag.DUPLICATE, "Note", "NoteText");

		ttm.executeCommand(1, n2a);
		assertEquals("Assigned", t1.getStateName());


	}

}
