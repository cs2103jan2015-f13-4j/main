package logic;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utility.MessageList;
import data.Data;
import data.Task;

public class HintHandlerTest {
	
	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}
	
	/**************************************************** Testing workable command ****************************************************/
	
	/*============================= This is to test normal hint with word add regular =============================*/
	@Test
	public void testHintWithWordAddRegular1(){
		String input = "a";
		String expected = MessageList.MESSAGE_ADD_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordAddRegular2() {
		String input = "ad";
		String expected = MessageList.MESSAGE_ADD_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordAddRegular3() {
		String input = "add";
		String expected = MessageList.MESSAGE_ADD_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/*============================= This is to test normal hint with word delete regular =============================*/
	@Test
	public void testHintWithWordDeleteRegular1(){
		String input = "de";
		String expected = MessageList.MESSAGE_DELETE_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordDeleteRegular2(){
		String input = "del";
		String expected = MessageList.MESSAGE_DELETE_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordDeleteRegular5(){
		String input = "delete";
		String expected = MessageList.MESSAGE_DELETE_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/*============================= This is to test normal hint with word display regular =============================*/
	@Test
	public void testHintWithWordDisplayRegular1(){
		String input = "di";
		String expected = MessageList.MESSAGE_DISPLAY_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordDisplayRegular2(){
		String input = "dis";
		String expected = MessageList.MESSAGE_DISPLAY_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordDisplayRegular6(){
		String input = "display";
		String expected = MessageList.MESSAGE_DISPLAY_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/*============================= This is to test normal hint with word update regular =============================*/
	@Test
	public void testHintWithWordUpdateRegular1(){
		String input = "up";
		String expected = MessageList.MESSAGE_UPDATE_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordUpdateRegular2(){
		String input = "upd";
		String expected = MessageList.MESSAGE_UPDATE_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordUpdateRegular5(){
		String input = "update";
		String expected = MessageList.MESSAGE_UPDATE_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/*============================= This is to test normal hint with word search regular =============================*/
	@Test
	public void testHintWithWordSearchRegular1(){
		String input = "se";
		String expected = MessageList.MESSAGE_SEARCH_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordSearchRegular2(){
		String input = "sea";
		String expected = MessageList.MESSAGE_SEARCH_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordSearchRegular3(){
		String input = "search";
		String expected = MessageList.MESSAGE_SEARCH_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/*============================= This is to test normal hint with word sort regular =============================*/
	@Test
	public void testHintWithWordSortRegular1(){
		String input = "so";
		String expected = MessageList.MESSAGE_SORT_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordSortRegular2(){
		String input = "sort";
		String expected = MessageList.MESSAGE_SORT_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/*============================= This is to test normal hint with word undo regular =============================*/
	@Test
	public void testHintWithWordUndoRegular1(){
		String input = "und";
		String expected = MessageList.MESSAGE_UNDO_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}

	@Test
	public void testHintWithWordUndoRegular2(){
		String input = "undo";
		String expected = MessageList.MESSAGE_UNDO_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/*============================= This is to test normal hint with word redo regular =============================*/
	@Test
	public void testHintWithWordRedoRegular1(){
		String input = "re";
		String expected = MessageList.MESSAGE_REDO_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordRedoRegular2(){
		String input = "redo";
		String expected = MessageList.MESSAGE_REDO_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/*============================= This is to test normal hint with word block regular =============================*/
	@Test
	public void testHintWithWordBlockRegular1(){
		String input = "b";
		String expected = MessageList.MESSAGE_BLOCK_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordBlockRegular2(){
		String input = "blo";
		String expected = MessageList.MESSAGE_BLOCK_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordBlockRegular3(){
		String input = "block";
		String expected = MessageList.MESSAGE_BLOCK_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	/*============================= This is to test normal hint with word unblock regular =============================*/
	@Test
	public void testHintWithWordUnblockRegular1(){
		String input = "unb";
		String expected = MessageList.MESSAGE_UNBLOCK_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordUnblockRegular2(){
		String input = "unbl";
		String expected = MessageList.MESSAGE_UNBLOCK_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
	
	@Test
	public void testHintWithWordUnblockRegular3(){
		String input = "unblock";
		String expected = MessageList.MESSAGE_UNBLOCK_HELP + "\n";
		assertEquals(expected, HintHandler.executeHint(input));
	}
}
