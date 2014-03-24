package dcll_moustaki.parser.questionsTest;

import dcll_moustaki.parser.questions.QuizContentHandler;
import dcll_moustaki.parser.questions.impl.gift.GiftQuizContentHandler;
import dcll_moustaki.parser.questions.impl.gift.GiftReader;
import dcll_moustaki.parser.questions.impl.gift.GiftReaderQuestionWithInvalidFormatException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class QuizReaderExceptionTest extends TestCase {

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public QuizReaderExceptionTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(QuizReaderExceptionTest.class);
	}

	
	/**
	 * Test endQuiz
	 */
	public void testEndQuiz()
	{
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);
		
		gr.setQuestionHasEnded(false);
		gr.setAnswerFragmentHasEnded(false);
		
		try
		{
			gr.endQuiz();
		}
		catch (GiftReaderQuestionWithInvalidFormatException ex)
		{
			ex.printStackTrace();
			
		}
	}
}
