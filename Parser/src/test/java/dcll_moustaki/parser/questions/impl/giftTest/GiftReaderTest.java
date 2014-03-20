package dcll_moustaki.parser.questions.impl.giftTest;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import dcll_moustaki.parser.questions.QuizContentHandler;
import dcll_moustaki.parser.questions.QuizReader;
import dcll_moustaki.parser.questions.impl.DefaultAnswerBlock;
import dcll_moustaki.parser.questions.impl.gift.GiftQuizContentHandler;
import dcll_moustaki.parser.questions.impl.gift.GiftReader;
import dcll_moustaki.parser.questions.impl.gift.GiftReaderException;
import dcll_moustaki.parser.questions.implTest.DefaultAnswerBlockTest;
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
	public void testSetQuizContentHandler()
	{	
		QuizContentHandler gq = new GiftQuizContentHandler();
		GiftReader gr = new GiftReader();
		
		gr.setQuizContentHandler(gq);
		
		assertEquals(gr.getQuizContentHandler(), gq);
	}
	
	/**
	 * Test parse
	 * @throws IOException 
	 * @throws GiftReaderException 
	 */
	public void testParse() throws IOException, GiftReaderException
	{
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);
		
		String s = new String("{Question|type=\"()\"}+ The correct answer.- Distractor.- Distractor.- Distractor.");
		Reader r = new StringReader(s);

		//curChar = r.read(); // IOException

		gr.parse(r); // giftReaderException
		
		/*GiftReader gr2 = new GiftReader();
		QuizContentHandler gq2 = new GiftQuizContentHandler();
		gr2.setQuizContentHandler(gq2);
		String s2 = new String("{Complete each box with a synonym of the following words |type=\"{}\"} #free: { gratis _6 } #well: { good _4 }");
		Reader r2 = new StringReader(s2);
		
		gr2.parse(r2);*/
	}
}
