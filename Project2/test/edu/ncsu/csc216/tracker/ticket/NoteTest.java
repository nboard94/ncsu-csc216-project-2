package edu.ncsu.csc216.tracker.ticket;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Class to test the Note class.
 * @author NBoar
 */
public class NoteTest {
	
	/**
	 * Tests the note's constructor, as well as the
	 * getters and setters.
	 */
	@Test
	public void testNote() {
		Note n = new Note("ndboard", "This is a note.");
		
		assertEquals("ndboard", n.getNoteAuthor());
		assertEquals("This is a note.", n.getNoteText());
		assertEquals("ndboard", n.getNoteArray()[0]);
		assertEquals("This is a note.", n.getNoteArray()[1]);
		
	}

}
