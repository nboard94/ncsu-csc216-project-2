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
		TrackedTicket n2a = tNew;
		Command assVal = new Command(CommandValue.POSSESSION, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		n2a.update(assVal);
		assertEquals("assigned", n2a.getStateName());
		
		//test invalid transition from NewState
		TrackedTicket tAss2 = tNew;
		Command n2inv = new Command(CommandValue.CLOSED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		tAss2.update(n2inv);
		assertEquals("new", tAss2.getStateName());

		//test transition from AssignedState to WorkingState
		TrackedTicket tWork = n2a;
		Command a2w = new Command(CommandValue.ACCEPTED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		tWork.update(a2w);
		assertEquals("working", tWork.getStateName());
		
		//test transition from AssignedState to ClosedState
		TrackedTicket tWork2 = n2a;
		Command a2c = new Command(CommandValue.CLOSED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		tWork2.update(a2c);
		assertEquals("closed", tWork2.getStateName());
		
		//test invalid transition from AssignedState
		TrackedTicket tWork3 = n2a;
		Command a2inv = new Command(CommandValue.FEEDBACK, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		tWork3.update(a2inv);	
		
		//test transition from WorkingState to WorkingState
		TrackedTicket tWork4 = tWork;
		Command W2W = new Command(CommandValue.PROGRESS, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		tWork4.update(W2W);
		assertEquals("working", tWork4.getStateName());
		
		//test transition from WorkingState to FeedbackState
		TrackedTicket tWork5 = tWork;
		Command W2F = new Command(CommandValue.FEEDBACK, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		tWork5.update(W2F);
		assertEquals("feedback", tWork5.getStateName());
		
		//test transition from WorkingState to ClosedState
		TrackedTicket tWork6 = tWork;
		Command W2C = new Command(CommandValue.CLOSED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		tWork6.update(W2C);
		assertEquals("closed", tWork6.getStateName());
		
		//test transition from WorkingState to AssignedState
		TrackedTicket tWork7 = tWork;
		Command W2A = new Command(CommandValue.POSSESSION, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		tWork7.update(W2C);
		assertEquals("assigned", tWork7.getStateName());

		//test transition from FeedbackState to WorkingState
		TrackedTicket tWork8 = tWork5;
		Command F2W = new Command(CommandValue.FEEDBACK, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		tWork8.update(F2W);
		assertEquals("working", tWork8.getStateName());

		//test transition from ClosedState to WorkingState
		//test transition from ClosedState to AssignedState
	}
	
}
