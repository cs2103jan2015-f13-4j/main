//@author A0112502A
package unit_testing;

import static org.junit.Assert.*;
import logic.HintHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utility.MessageList;

public class HintHandlerTest {
	
	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}
	
	/**************************************************** Testing valid command ****************************************************/
	
	// This is to test normal hint with word add valid
	@Test
	public void testHintWithWordAddValid1(){
		String input = "a";
		String expected = MessageList.MESSAGE_ADD_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordAddValid2() {
		String input = "ad";
		String expected = MessageList.MESSAGE_ADD_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordAddValid3() {
		String input = "add";
		String expected = MessageList.MESSAGE_ADD_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	// This is to test normal hint with word delete valid
	@Test
	public void testHintWithWordDeleteValid1(){
		String input = "de";
		String expected = MessageList.MESSAGE_DELETE_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordDeleteValid2(){
		String input = "del";
		String expected = MessageList.MESSAGE_DELETE_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordDeleteValid3(){
		String input = "delete";
		String expected = MessageList.MESSAGE_DELETE_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	// This is to test normal hint with word display valid
	@Test
	public void testHintWithWordDisplayValid1(){
		String input = "di";
		String expected = MessageList.MESSAGE_DISPLAY_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordDisplayValid2(){
		String input = "dis";
		String expected = MessageList.MESSAGE_DISPLAY_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordDisplayValid3(){
		String input = "display";
		String expected = MessageList.MESSAGE_DISPLAY_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	// This is to test normal hint with word update valid
	@Test
	public void testHintWithWordUpdateValid1(){
		String input = "up";
		String expected = MessageList.MESSAGE_UPDATE_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordUpdateValid2(){
		String input = "upd";
		String expected = MessageList.MESSAGE_UPDATE_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordUpdateValid3(){
		String input = "update";
		String expected = MessageList.MESSAGE_UPDATE_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	// This is to test normal hint with word search valid
	@Test
	public void testHintWithWordSearchValid1(){
		String input = "se";
		String expected = MessageList.MESSAGE_SEARCH_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordSearchValid2(){
		String input = "sea";
		String expected = MessageList.MESSAGE_SEARCH_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordSearchValid3(){
		String input = "search";
		String expected = MessageList.MESSAGE_SEARCH_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	// This is to test normal hint with word sort valid
	@Test
	public void testHintWithWordSortValid1(){
		String input = "so";
		String expected = MessageList.MESSAGE_SORT_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordSortValid2(){
		String input = "sort";
		String expected = MessageList.MESSAGE_SORT_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	// This is to test normal hint with word undo valid
	@Test
	public void testHintWithWordUndoValid1(){
		String input = "und";
		String expected = MessageList.MESSAGE_UNDO_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}

	@Test
	public void testHintWithWordUndoValid2(){
		String input = "undo";
		String expected = MessageList.MESSAGE_UNDO_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	// This is to test normal hint with word redo valid
	@Test
	public void testHintWithWordRedoValid1(){
		String input = "re";
		String expected = MessageList.MESSAGE_REDO_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordRedoValid2(){
		String input = "redo";
		String expected = MessageList.MESSAGE_REDO_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	//This is to test normal hint with word block valid
	@Test
	public void testHintWithWordBlockValid1(){
		String input = "b";
		String expected = MessageList.MESSAGE_BLOCK_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordBlockValid2(){
		String input = "blo";
		String expected = MessageList.MESSAGE_BLOCK_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordBlockValid3(){
		String input = "block";
		String expected = MessageList.MESSAGE_BLOCK_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	//This is to test normal hint with word unblock valid
	@Test
	public void testHintWithWordUnblockValid1(){
		String input = "unb";
		String expected = MessageList.MESSAGE_UNBLOCK_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordUnblockValid2(){
		String input = "unbl";
		String expected = MessageList.MESSAGE_UNBLOCK_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordUnblockValid3(){
		String input = "unblock";
		String expected = MessageList.MESSAGE_UNBLOCK_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/**************************************************** Testing invalid command ****************************************************/

	// This is to test empty command entered
	@Test
	public void testHintWithEmptyCommand(){
		String input = "";
		String expected = MessageList.MESSAGE_HINT_INVALID;
		assertEquals(expected, HintHandler.executeHint(input));
	}
}
