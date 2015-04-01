package logic;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utility.MessageList;
import data.Data;
import data.Task;

public class HintHandlerTest {

	String fileName = "testHint.txt";
	Data smtDataTest;
	
	@Before
	public void setUp() {
		smtDataTest = new Data();
	}

	@After
	public void tearDown() {
		smtDataTest = null;
	}
	
	/* ------------------------ Testing workable command ------------------------ */

	@Test
	public void testHintWithWordAddRegular() {
		String input = "ad";
		String expected = MessageList.MESSAGE_ADD_HELP;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordDeleteRegular(){
		String input = "del";
		String expected = MessageList.MESSAGE_DELETE_HELP;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordDisplayRegular(){
		String input = "dis";
		String expected = MessageList.MESSAGE_DISPLAY_HELP;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordUpdateRegular(){
		String input = "up";
		String expected = MessageList.MESSAGE_UPDATE_HELP;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordSearchRegular(){
		String input = "se";
		String expected = MessageList.MESSAGE_SEARCH_HELP;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordSortRegular(){
		String input = "so";
		String expected = MessageList.MESSAGE_SORT_HELP;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordUndoRegular(){
		String input = "und";
		String expected = MessageList.MESSAGE_UNDO_HELP;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordRedoRegular(){
		String input = "red";
		String expected = MessageList.MESSAGE_REDO_HELP;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordBlockRegular(){
		String input = "bl";
		String expected = MessageList.MESSAGE_BLOCK_HELP;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordUnblockRegular(){
		String input = "unb";
		String expected = MessageList.MESSAGE_UNBLOCK_HELP;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/* ------------------------ Testing invalid command for add commands ------------------------ */
	@Test
	public void testHintWithInvalidAdd1() {
		String input = "adde";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithInvalidAdd2() {
		String input = "add2";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/* ------------------------ Testing invalid command for delete commands ------------------------ */
	
	@Test
	public void testHintWithInvalidDelete1() {
		String input = "dels";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithInvalidDelete2() {
		String input = "del2";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/* ------------------------ Testing invalid command for display commands ------------------------ */
	@Test
	public void testHintWithInvalidDisplay1() {
		String input = "disl";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithInvalidDisplay2() {
		String input = "dis2";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/* ------------------------ Testing invalid command for update commands ------------------------ */
	@Test
	public void testHintWithInvalidUpdate1() {
		String input = "ups";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithInvalidUpdate2() {
		String input = "up2";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/* ------------------------ Testing invalid command for search commands ------------------------ */
	@Test
	public void testHintWithInvalidSearch1() {
		String input = "seas";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithInvalidSearch3() {
		String input = "sea1";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/* ------------------------ Testing invalid command for sort commands ------------------------ */
	@Test
	public void testHintWithInvalidSort1() {
		String input = "sors";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithInvalidSort3() {
		String input = "sor1";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/* ------------------------ Testing invalid command for undo commands ------------------------ */
	@Test
	public void testHintWithInvalidUndo1() {
		String input = "unds";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithInvalidUndo2() {
		String input = "und1";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/* ------------------------ Testing invalid command for redo commands ------------------------ */
	@Test
	public void testHintWithInvalidRedo1() {
		String input = "reds";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithInvalidRedo2() {
		String input = "red1";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/* ------------------------ Testing invalid command for block commands ------------------------ */
	@Test
	public void testHintWithInvalidBlock1() {
		String input = "blos";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithInvalidBlock2() {
		String input = "blo1";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/* ------------------------ Testing invalid command for unblock commands ------------------------ */
	@Test
	public void testHintWithInvalidUnBlock1() {
		String input = "unblocks";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithInvalidUnBlock2() {
		String input = "unbloc1";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/*Testing out of bound*/
	@Test
	public void testHintWithOutOfBound() {
		String input = "add 2";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
}
