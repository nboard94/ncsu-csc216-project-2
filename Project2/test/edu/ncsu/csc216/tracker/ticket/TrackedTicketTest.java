package edu.ncsu.csc216.tracker.ticket;

import static org.junit.Assert.*;

import org.junit.Test;

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
	}
	
	@Test
	public void testGetStateName() {
		TrackedTicket t1 = new TrackedTicket("testTicket", "ndboard", "PLZ WORK");
		assertEquals("new", t1.getStateName());
		
	}
	
	@Test
	public void testStatePattern() {
		//Command(CommandValue newCommand, String newOwner, Flag newFlag, String newNote, String newNoteAuthor)
		
		//create new ticket and test for NewState
		TrackedTicket t1 = new TrackedTicket("testTicket", "ndboard", "PLZ WORK");
		assertEquals("new", t1.getStateName());
		
		//test transition from NewState to AssignedState
		Command c21 = new Command(CommandValue.POSSESSION, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			t1.update(c21);
			assertEquals("assigned", t1.getStateName());

		} catch (UnsupportedOperationException e) {
			fail();
		}

		//test transition from AssignedState to WorkingState
		Command c31 = new Command(CommandValue.ACCEPTED, "ndboard", Flag.DUPLICATE, "Note", "NoteText");
		try {
			t1.update(c31);
			assertEquals("working", t1.getStateName());

		} catch (UnsupportedOperationException e) {
			fail();
		}
		//test transition from AssignedState to ClosedState
		
		//test transition from WorkingState to WorkingState
		//test transition from WorkingState to FeedbackState
		//test transition from WorkingState to ClosedState
		//test transition from WorkingState to AssignedState

		//test transition from FeedbackState to WorkingState

		//test transition from ClosedState to WorkingState
		//test transition from ClosedState to AssignedState
	}
	
}
