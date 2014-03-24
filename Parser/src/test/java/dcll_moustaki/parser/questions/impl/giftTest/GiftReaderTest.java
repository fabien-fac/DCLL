package dcll_moustaki.parser.questions.impl.giftTest;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import dcll_moustaki.parser.questions.QuizContentHandler;
import dcll_moustaki.parser.questions.impl.gift.GiftQuizContentHandler;
import dcll_moustaki.parser.questions.impl.gift.GiftReader;
import dcll_moustaki.parser.questions.impl.gift.GiftReaderException;
import dcll_moustaki.parser.questions.impl.gift.GiftReaderNotEscapedCharacterException;
import dcll_moustaki.parser.questions.impl.gift.GiftReaderQuestionWithInvalidFormatException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GiftReaderTest extends TestCase {

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public GiftReaderTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(GiftReaderTest.class);
	}

	/**
	 * Test setQuizContentHandler et getQuizContentHandler
	 */
	public void testSetQuizContentHandler() {
		QuizContentHandler gq = new GiftQuizContentHandler();
		GiftReader gr = new GiftReader();

		gr.setQuizContentHandler(gq);

		assertEquals(gr.getQuizContentHandler(), gq);
	}

	/**
	 * Test parse
	 * 
	 * @throws IOException
	 * @throws GiftReaderException
	 */
	public void testParse() throws IOException, GiftReaderException {
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);
		
		String s = new String("{Question\\:12|type=\"()\"}+ 6\\%.- \\~12.- \\#0672.- http\\:\\test.");
		Reader r = new StringReader(s);

		// curChar = r.read(); // IOException

		gr.parse(r); // giftReaderException
	}

	/**
	 * Test parse GiftReaderNotEscapedCharacterException
	 */
	public void testParseGiftReaderNotEscapedCharacterException() throws IOException, GiftReaderException {
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);

		String s = new String(
				"{Question\\:12|type=\"()\"}+ 6\\::\\%.- \\~12.- \\#0672.- http\\:\\test.");
		Reader r = new StringReader(s);
		try {
			gr.parse(r);
			fail("My method didn't throw when I expected it to");
		} catch (GiftReaderNotEscapedCharacterException e) {
		}
	}
	
	/**
	 * Test parse GiftReaderQuestionWithInvalidFormatException
	 */
	public void testParseGiftReaderQuestionWithInvalidFormatException() throws IOException, GiftReaderException {
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);

		String s = new String(
				"{Question\\:12|type=\"()\"+ 6\\%.- \\~12.- \\#0672.- http\\:\\test.");
		Reader r = new StringReader(s);
		try {
			gr.parse(r);
			fail("My method didn't throw when I expected it to");
		} catch (GiftReaderQuestionWithInvalidFormatException e) {
		}

	}
}
