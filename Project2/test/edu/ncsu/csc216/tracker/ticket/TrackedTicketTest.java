package edu.ncsu.csc216.tracker.ticket;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.ticket.xml.NoteItem;
import edu.ncsu.csc216.ticket.xml.NoteList;
import edu.ncsu.csc216.ticket.xml.Ticket;
import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.command.Command.CommandValue;
import edu.ncsu.csc216.tracker.command.Command.Flag;
import edu.ncsu.csc216.tracker.ticket_tracker.TicketTrackerModel;

/**
 * Class that contains testing methods for
 * the TrackedTicket class.
 * @author NBoar
 */
public class TrackedTicketTest {

	
	
	/**
	 * Tests the TrackedTicket constructor that
	 * takes in three stings as parameters.
	 */
	@Test
	public void testTrackedTicketStringStringString() {
		TicketTrackerModel.getInstance().createNewTicketList();
		
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
		
		assertEquals("New", t1.getStateName());
	}
	
	/**
	 * Tests the TrackedTicket constructor that
	 * uses a Ticket object as a parameter.
	 */
	@Test
	public void testTrackedTicketTicket() {
		Ticket t = new Ticket();
		t.setTitle("Ticket1");
		t.setSubmitter("ndboard");
		
		NoteItem li = new NoteItem();
		li.setNoteAuthor("ndboard");
		li.setNoteText("testtest");
		NoteItem ni = new NoteItem();
		ni.setNoteAuthor("ndboard");
		ni.setNoteText("testtest");
		NoteList nl = new NoteList();
		nl.getNotes().add(ni);
		t.setNoteList(nl);


		TrackedTicket t1 = new TrackedTicket(t);
		
		
		assertEquals("Ticket1", t1.getTitle());
		assertEquals("ndboard", t1.getSubmitter());
		assertEquals("ndboard", t1.getNotes().get(0).getNoteAuthor());
		assertEquals("testtest", t1.getNotes().get(0).getNoteText());
	}
	
	/**
	 * Tests getXMLTicket in TrackedTicket.
	 */
	@Test
	public void testGetXMLTicket() {
		TrackedTicket t1 = new TrackedTicket("testTicket", "ndboard", "PLZ WORK");

		Ticket t = t1.getXMLTicket();
		
		assertEquals("testTicket", t.getTitle());
		assertEquals("ndboard", t.getSubmitter());
		assertEquals("ndboard", t.getNoteList().getNotes().get(0).getNoteAuthor());
		assertEquals("PLZ WORK", t.getNoteList().getNotes().get(0).getNoteText());	
	}
	
	/**
	 * Tests getNotesArray in TrackedTicket.
	 */
	@Test
	public void testGetNotesArray() {
		TrackedTicket t1 = new TrackedTicket("testTicket", "ndboard1", "note1");
		t1.notes.add(new Note("ndboard2", "note2"));
		t1.notes.add(new Note("ndboard3", "note3"));		

		String[][] noteArray = t1.getNotesArray();
		assertEquals(3, noteArray.length);
		assertEquals("ndboard1", noteArray[0][0]);
		assertEquals("ndboard2", noteArray[1][0]);
		assertEquals("ndboard3", noteArray[2][0]);
		assertEquals("note1", noteArray[0][1]);
		assertEquals("note2", noteArray[1][1]);
		assertEquals("note3", noteArray[2][1]);

	}
	
	/**
	 * Tests the getStateName method in TrackedTicket.
	 */
	@Test
	public void testGetStateName() {
		TrackedTicket t1 = new TrackedTicket("testTicket", "ndboard", "PLZ WORK");
		assertEquals("New", t1.getStateName());
		
	}
	
	/**
	 * Tests the state pattern in TrackedTicket.
	 * Creates various tickets and moves them through the
	 * states using the proper commands.
	 */
	@Test
	public void testStateTransition() {
		
		//create new ticket and test for NewState
		TrackedTicket tNew = new TrackedTicket("testTicket", "ndboard", "commentK");
		assertEquals("New", tNew.getStateName());
		
		//test transition from NewState to AssignedState
		Command n2a = new Command(CommandValue.POSSESSION, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tNew.update(n2a);
			assertEquals("Assigned", tNew.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test invalid transition from NewState
		TrackedTicket tNew2 = new TrackedTicket("testTicket", "ndboard", "commentK");
		Command n2inv = new Command(CommandValue.CLOSED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tNew2.update(n2inv);
			fail();
		} catch (UnsupportedOperationException e) {
			//assertEquals("new", tNew.getStateName());
		}

		//test transition from AssignedState to WorkingState
		TrackedTicket tAss = new TrackedTicket("testTicket", "ndboard", "commentK");
		tAss.update(n2a);
		Command a2w = new Command(CommandValue.ACCEPTED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tAss.update(a2w);
			assertEquals("Working", tAss.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test transition from AssignedState to ClosedState
		TrackedTicket tAss2 = new TrackedTicket("testTicket", "ndboard", "commentK");
		tAss2.update(n2a);
		Command a2c = new Command(CommandValue.CLOSED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tAss2.update(a2c);
			assertEquals("Closed", tAss2.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test invalid transition from AssignedState
		TrackedTicket tAss3 = new TrackedTicket("testTicket", "ndboard", "commentK");
		tAss3.update(n2a);
		Command a2inv = new Command(CommandValue.POSSESSION, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tAss3.update(a2inv);
			fail();
		} catch (UnsupportedOperationException e) {
			//assertEquals("assigned", tAss3.getStateName());
		}
		
		//test transition from WorkingState to WorkingState
		TrackedTicket tWork = new TrackedTicket("testTicket", "ndboard", "commentK");
		tWork.update(n2a);
		tWork.update(a2w);
		Command w2w = new Command(CommandValue.PROGRESS, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tWork.update(w2w);
			assertEquals("Working", tWork.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test transition from WorkingState to FeedbackState
		TrackedTicket tWork2 = new TrackedTicket("testTicket", "ndboard", "commentK");
		tWork2.update(n2a);
		tWork2.update(a2w);
		Command w2f = new Command(CommandValue.FEEDBACK, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tWork2.update(w2f);
			assertEquals("Feedback", tWork2.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test transition from WorkingState to ClosedState
		TrackedTicket tWork3 = new TrackedTicket("testTicket", "ndboard", "commentK");
		tWork3.update(n2a);
		tWork3.update(a2w);
		Command w2c = new Command(CommandValue.CLOSED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tWork3.update(w2c);
			assertEquals("Closed", tWork3.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test transition from WorkingState to AssignedState
		TrackedTicket tWork4 = new TrackedTicket("testTicket", "ndboard", "commentK");
		tWork4.update(n2a);
		tWork4.update(a2w);
		Command w2a = new Command(CommandValue.POSSESSION, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tWork4.update(w2a);
			assertEquals("Assigned", tWork4.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test transition from FeedbackState to WorkingState
		TrackedTicket tFeed = new TrackedTicket("testTicket", "ndboard", "commentK");
		tFeed.update(n2a);
		tFeed.update(a2w);
		tFeed.update(w2f);
		Command f2w = new Command(CommandValue.FEEDBACK, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tFeed.update(f2w);
			assertEquals("Working", tFeed.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test transition from ClosedState to WorkingState
		TrackedTicket tClose = new TrackedTicket("testTicket", "ndboard", "commentK");
		tClose.update(n2a);
		tClose.update(a2c);
		Command c2w = new Command(CommandValue.PROGRESS, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tClose.update(c2w);
			assertEquals("Working", tClose.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test transition from ClosedState to AssignedState
		TrackedTicket tClose2 = new TrackedTicket("testTicket", "ndboard", "commentK");
		tClose2.update(n2a);
		tClose2.update(a2c);
		Command c2a = new Command(CommandValue.POSSESSION, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tClose2.update(c2a);
			assertEquals("Assigned", tClose2.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
	}
	
}
