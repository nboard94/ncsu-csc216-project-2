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

public class TrackedTicketTest {

	
	
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
		
		assertEquals("new", t1.getStateName());
	}
	
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
	
	@Test
	public void testGetXMLTicket() {
		TrackedTicket t1 = new TrackedTicket("testTicket", "ndboard", "PLZ WORK");

		Ticket t = t1.getXMLTicket();
		
		assertEquals("testTicket", t.getTitle());
		assertEquals("ndboard", t.getSubmitter());
		assertEquals("ndboard", t.getNoteList().getNotes().get(0).getNoteAuthor());
		assertEquals("PLZ WORK", t.getNoteList().getNotes().get(0).getNoteText());	
	}
	
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
	
	@Test
	public void testGetStateName() {
		TrackedTicket t1 = new TrackedTicket("testTicket", "ndboard", "PLZ WORK");
		assertEquals("new", t1.getStateName());
		
	}
	
	@Test
	public void testStateTransition() {
		
		//create new ticket and test for NewState
		TrackedTicket tNew = new TrackedTicket("testTicket", "ndboard", "PLZ WORK");
		assertEquals("new", tNew.getStateName());
		
		//test transition from NewState to AssignedState
		TrackedTicket tAss = tNew;
		Command assVal = new Command(CommandValue.POSSESSION, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tAss.update(assVal);
			assertEquals("assigned", tAss.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test invalid transition from NewState
		TrackedTicket tAss2 = tNew;
		Command assInval = new Command(CommandValue.CLOSED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tAss2.update(assInval);
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals("new", tAss2.getStateName());
		}

		//test transition from AssignedState to WorkingState
		TrackedTicket tWork = tAss;
		Command workVal = new Command(CommandValue.ACCEPTED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tWork.update(workVal);
			assertEquals("working", tWork.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test transition from AssignedState to ClosedState
		TrackedTicket tWork2 = tAss;
		Command workVal2 = new Command(CommandValue.CLOSED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tWork2.update(workVal2);
			assertEquals("closed", tWork2.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test invalid transition from AssignedState
		TrackedTicket tWork3 = tAss;
		Command workInval = new Command(CommandValue.FEEDBACK, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tWork3.update(workInval);
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals("assigned", tWork3.getStateName());
		}
		
		//test transition from WorkingState to WorkingState
		TrackedTicket tWork4 = tWork;
		Command W2W = new Command(CommandValue.PROGRESS, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tWork4.update(W2W);
			assertEquals("working", tWork4.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test transition from WorkingState to FeedbackState
		TrackedTicket tWork5 = tWork;
		Command W2F = new Command(CommandValue.FEEDBACK, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tWork5.update(W2F);
			assertEquals("feedback", tWork5.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test transition from WorkingState to ClosedState
		TrackedTicket tWork6 = tWork;
		Command W2C = new Command(CommandValue.CLOSED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tWork6.update(W2C);
			assertEquals("closed", tWork6.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		//test transition from WorkingState to AssignedState
		TrackedTicket tWork7 = tWork;
		Command W2A = new Command(CommandValue.POSSESSION, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tWork7.update(W2C);
			assertEquals("assigned", tWork7.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}

		//test transition from FeedbackState to WorkingState
		TrackedTicket tWork8 = tWork5;
		Command F2W = new Command(CommandValue.FEEDBACK, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			tWork8.update(F2W);
			assertEquals("working", tWork8.getStateName());
		} catch (UnsupportedOperationException e) {
			fail();
		}

		//test transition from ClosedState to WorkingState
		//test transition from ClosedState to AssignedState
	}
	
}
