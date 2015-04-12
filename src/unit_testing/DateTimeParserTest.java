//@author A0112978W
package unit_testing;
import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.DateTimeParser;
import utility.MessageList;

public class DateTimeParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	// To test empty date value
	@Test
	public void testDateParserEmpty() {
		String dateValue = "";
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected, DateTimeParser.checkDateFormat(dateValue));
	}
	
	// To test empty time value
	@Test
	public void testTimeParserEmpty() {
		String timeValue = "";
		String expected = MessageList.MESSAGE_INCORRECT_TIME_FORMAT;
		assertEquals(expected, DateTimeParser.checkTimeFormat(timeValue));
	}
	
	// To test valid numeric date formats
	@Test
	public void testNumericDateParser() {
		String dateValue1 = "25-12-2015";
		DateTimeFormatter dtf1 = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTime expectedDateTimeObj1 = dtf1.parseDateTime(dateValue1);
		assertTrue(expectedDateTimeObj1.toLocalDate().equals(DateTimeParser.generateDate(dateValue1).toLocalDate()));
		
		String dateValue2 = "25/12/2015";
		DateTimeFormatter dtf2 = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime expectedDateTimeObj2 = dtf2.parseDateTime(dateValue2);
		assertTrue(expectedDateTimeObj2.toLocalDate().equals(DateTimeParser.generateDate(dateValue2).toLocalDate()));
		
		String dateValue3 = "2015-12-25";
		DateTimeFormatter dtf3 = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime expectedDateTimeObj3 = dtf3.parseDateTime(dateValue3);
		assertTrue(expectedDateTimeObj3.toLocalDate().equals(DateTimeParser.generateDate(dateValue3).toLocalDate()));
		
		String dateValue4 = "2015/12/25";
		DateTimeFormatter dtf4 = DateTimeFormat.forPattern("yyyy/MM/dd");
		DateTime expectedDateTimeObj4 = dtf4.parseDateTime(dateValue4);
		assertTrue(expectedDateTimeObj4.toLocalDate().equals(DateTimeParser.generateDate(dateValue4).toLocalDate()));
		
		String dateValue5 = "25 December, 2015";
		DateTimeFormatter dtf5 = DateTimeFormat.forPattern("d MMMM, yyyy");
		DateTime expectedDateTimeObj5 = dtf5.parseDateTime(dateValue5);
		assertTrue(expectedDateTimeObj5.toLocalDate().equals(DateTimeParser.generateDate(dateValue5).toLocalDate()));
	}
	
	// To test valid alphabetic date formats
	@Test
	public void testAlphabetDateParser() {
		String dateValue1 = "today";
		DateTime expectedDateTimeObj1 = new DateTime();
		assertTrue(expectedDateTimeObj1.toLocalDate().equals(DateTimeParser.generateDate(dateValue1).toLocalDate()));
		
		String dateValue2 = "tomorrow";
		DateTime expectedDateTimeObj2 = new DateTime();
		assertTrue(expectedDateTimeObj2.plusDays(1).toLocalDate().equals(DateTimeParser.generateDate(dateValue2).toLocalDate()));
	}
	
	// To test past date [invalid scenario]
	@Test
	public void testPastDateParser() {
		String dateValue = "25-12-2014";
		String expected = MessageList.MESSAGE_DATE_IS_BEFORE_TODAY;
		assertEquals(expected, DateTimeParser.checkDateFormat(dateValue));
	}
	
	// To test invalid numeric date formats
	@Test
	public void testInvalidNumericDateParser() {
		String dateValue1 = "25.12.2015";
		String expected1 = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected1, DateTimeParser.checkDateFormat(dateValue1));
		
		String dateValue2 = "251215";
		String expected2 = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected2, DateTimeParser.checkDateFormat(dateValue2));
		
		String dateValue3 = "25122015";
		String expected3 = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected3, DateTimeParser.checkDateFormat(dateValue3));
	}
		
	// To test invalid alphabetic date formats
	@Test
	public void testInvalidAlphabetDateParser() {
		String dateValue1 = "tues";
		String expected1 = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected1, DateTimeParser.checkDateFormat(dateValue1));
		
		String dateValue2 = "thurs";
		String expected2 = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected2, DateTimeParser.checkDateFormat(dateValue2));
	}
		
	// To test boundary date value [invalid scenarios]
	@Test
	public void testInvalidNumericDateBoundary() {
		String dateValue1 = "32-12-2015";
		String expected1 = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected1, DateTimeParser.checkDateFormat(dateValue1));
		
		String dateValue2 = "25-13-2015";
		String expected2 = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected2, DateTimeParser.checkDateFormat(dateValue2));
	}
	
	// To test valid time formats
	@Test
	public void testTimeParser() {
		String timeValue1 = "6pm";
		DateTimeFormatter dtf1 = DateTimeFormat.forPattern("ha");
		DateTime expectedDateTimeObj1 = dtf1.parseDateTime(timeValue1);
		assertTrue(expectedDateTimeObj1.equals(DateTimeParser.generateTime(timeValue1)));
		
		String timeValue2 = "6 pm";
		DateTimeFormatter dtf2 = DateTimeFormat.forPattern("h a");
		DateTime expectedDateTimeObj2 = dtf2.parseDateTime(timeValue2);
		assertTrue(expectedDateTimeObj2.equals(DateTimeParser.generateTime(timeValue2)));
		
		String timeValue3 = "6.30pm";
		DateTimeFormatter dtf3 = DateTimeFormat.forPattern("h.ma");
		DateTime expectedDateTimeObj3 = dtf3.parseDateTime(timeValue3);
		assertTrue(expectedDateTimeObj3.equals(DateTimeParser.generateTime(timeValue3)));
		
		String timeValue4 = "6.30 pm";
		DateTimeFormatter dtf4 = DateTimeFormat.forPattern("h.m a");
		DateTime expectedDateTimeObj4 = dtf4.parseDateTime(timeValue4);
		assertTrue(expectedDateTimeObj4.equals(DateTimeParser.generateTime(timeValue4)));
	}
	
	// To test invalid time formats
	@Test
	public void testInvalidTimeParser() {
		String timeValue1 = "6";
		String expected1 = MessageList.MESSAGE_INCORRECT_TIME_FORMAT;
		assertEquals(expected1, DateTimeParser.checkTimeFormat(timeValue1));
		
		String timeValue2 = "6:30pm";
		String expected2 = MessageList.MESSAGE_INCORRECT_TIME_FORMAT;
		assertEquals(expected2, DateTimeParser.checkTimeFormat(timeValue2));
		
		String timeValue3 = "1830";
		String expected3 = MessageList.MESSAGE_INCORRECT_TIME_FORMAT;
		assertEquals(expected3, DateTimeParser.checkTimeFormat(timeValue3));
	}
	
	// To test boundary time value [invalid scenarios]
	@Test
	public void testInvalidTimeBoundary() {
		String timeValue1 = "13pm";
		String expected1 = MessageList.MESSAGE_INCORRECT_TIME_FORMAT;
		assertEquals(expected1, DateTimeParser.checkTimeFormat(timeValue1));
		
		String timeValue2 = "12.60pm";
		String expected2 = MessageList.MESSAGE_INCORRECT_TIME_FORMAT;
		assertEquals(expected2, DateTimeParser.checkTimeFormat(timeValue2));
	}
}