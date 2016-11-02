package edu.ncsu.csc216.tracker.command;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.tracker.command.Command.Flag;

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
		
		//Initialize command objects for testing construction
		Command c1 = new Command(Command.CommandValue.ACCEPTED, "ndboard", Command.Flag.DUPLICATE, "note", "ndboard");
		Command c2 = new Command(Command.CommandValue.CLOSED, "ndboard", Command.Flag.INAPPROPRIATE, "note", "ndboard");
		Command c3 = new Command(Command.CommandValue.FEEDBACK, "ndboard", Command.Flag.RESOLVED, "note", "ndboard");
		Command c4 = new Command(Command.CommandValue.POSSESSION, "ndboard", Command.Flag.DUPLICATE, "note", "ndboard");
		Command c5 = new Command(Command.CommandValue.PROGRESS, "ndboard", Command.Flag.DUPLICATE, "note", "ndboard");
		
		//test string based fields
		assertEquals("ndboard", c1.getOwner());
		assertEquals("note", c1.getNoteText());
		assertEquals("ndboard", c1.getNoteAuthor());
		
		//test flag field
		assertEquals(Command.Flag.DUPLICATE, c1.getFlag());
		assertEquals(Command.Flag.INAPPROPRIATE, c2.getFlag());
		assertEquals(Command.Flag.RESOLVED, c3.getFlag());
		
		//test command value field
		assertEquals(Command.CommandValue.ACCEPTED, c1.getCommand());
		assertEquals(Command.CommandValue.CLOSED, c2.getCommand());
		assertEquals(Command.CommandValue.FEEDBACK, c3.getCommand());
		assertEquals(Command.CommandValue.POSSESSION, c4.getCommand());
		assertEquals(Command.CommandValue.PROGRESS, c5.getCommand());
		
		//test constructing invalid objects
		try {
			c1 = new Command(null, "ndboard", Command.Flag.DUPLICATE, "note", "ndboard");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(Flag.DUPLICATE, c1.getFlag());
		}
		
		try {
			c1 = new Command(Command.CommandValue.ACCEPTED, "ndboard", Command.Flag.DUPLICATE, "note", null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(Flag.DUPLICATE, c1.getFlag());
		}
		 
		
		try {
			c1 = new Command(Command.CommandValue.ACCEPTED, "ndboard", Command.Flag.DUPLICATE, "note", "");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(Flag.DUPLICATE, c1.getFlag());
		}
		
		try {
			c1 = new Command(Command.CommandValue.ACCEPTED, "ndboard", Command.Flag.DUPLICATE, null, "ndboard");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(Flag.DUPLICATE, c1.getFlag());
		}
		
		try {
			c1 = new Command(Command.CommandValue.ACCEPTED, "ndboard", Command.Flag.DUPLICATE, "", "ndboard");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(Flag.DUPLICATE, c1.getFlag());
		}
		
		try {
			c1 = new Command(Command.CommandValue.POSSESSION, null, Command.Flag.DUPLICATE, "note", "ndboard");
		} catch (IllegalArgumentException e) {
			assertEquals(Flag.DUPLICATE, c1.getFlag());
		}
		
		try {
			c1 = new Command(Command.CommandValue.POSSESSION, "", Command.Flag.DUPLICATE, "note", "ndboard");
		} catch (IllegalArgumentException e) {
			assertEquals(Flag.DUPLICATE, c1.getFlag());
		}
		
		try {
			c1 = new Command(Command.CommandValue.CLOSED, "ndboard", null, "note", "ndboard");
		} catch (IllegalArgumentException e) {
			assertEquals(Flag.DUPLICATE, c1.getFlag());
		}
	}
	
	@Test
	public void testEnum() {
		assertEquals("ACCEPTED", Command.CommandValue.valueOf("ACCEPTED").toString());
		assertEquals("CLOSED", Command.CommandValue.valueOf("CLOSED").toString());
		assertEquals("FEEDBACK", Command.CommandValue.valueOf("FEEDBACK").toString());
		assertEquals("POSSESSION", Command.CommandValue.valueOf("POSSESSION").toString());
		assertEquals("CLOSED", Command.CommandValue.valueOf("CLOSED").toString());
		
		assertEquals("DUPLICATE",Command.Flag.valueOf("DUPLICATE").toString());
		assertEquals("INAPPROPRIATE",Command.Flag.valueOf("INAPPROPRIATE").toString());
		assertEquals("RESOLVED",Command.Flag.valueOf("RESOLVED").toString());

	}
}
