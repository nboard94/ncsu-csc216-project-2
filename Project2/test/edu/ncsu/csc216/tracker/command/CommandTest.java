package edu.ncsu.csc216.tracker.command;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Class to test the Command class.
 * @author NBoar
 */
public class CommandTest {

	/**
	 * Tests the commands's constructor, as well as the
	 * getters and setters.
	 */
	@Test
	public void testCommand() {
		Command c1 = new Command(Command.CommandValue.ACCEPTED, "ndboard", Command.Flag.DUPLICATE, "note", "ndboard");
		Command c2 = new Command(Command.CommandValue.CLOSED, "ndboard", Command.Flag.INAPPROPRIATE, "note", "ndboard");
		Command c3 = new Command(Command.CommandValue.FEEDBACK, "ndboard", Command.Flag.RESOLVED, "note", "ndboard");
		Command c4 = new Command(Command.CommandValue.POSSESSION, "ndboard", Command.Flag.DUPLICATE, "note", "ndboard");
		Command c5 = new Command(Command.CommandValue.PROGRESS, "ndboard", Command.Flag.DUPLICATE, "note", "ndboard");
		
		assertEquals(Command.CommandValue.ACCEPTED, c1.getCommand());
		assertEquals("ndboard", c1.getOwner());
		assertEquals(Command.Flag.DUPLICATE, c1.getFlag());
		assertEquals("note", c1.getNoteText());
		assertEquals("ndboard", c1.getNoteAuthor());
		
		assertEquals(Command.Flag.INAPPROPRIATE, c2.getFlag());
		assertEquals(Command.Flag.RESOLVED, c3.getFlag());
		
		assertEquals(Command.CommandValue.CLOSED, c2.getCommand());
		assertEquals(Command.CommandValue.POSSESSION, c4.getCommand());
		assertEquals(Command.CommandValue.PROGRESS, c5.getCommand());
		
	}
}
