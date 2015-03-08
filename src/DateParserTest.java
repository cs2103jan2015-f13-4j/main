import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DateParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDateParser() {
		String dateValue = "3-3-2015";
		//String expected = "3 March, 2015";
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTime expectedDateTimeObj = DateTime.parse(dateValue);
		assertTrue(expectedDateTimeObj.equals(DateParser.generateDate(dateValue)));
     	
		
		//assertEquals(expected, DateParser.generateDate(dateValue));
	}
}
