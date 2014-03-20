package dcll_moustaki.parser.question.impl;

import static org.junit.Assert.*;
import dcll_moustaki.parser.questions.Quiz;
import dcll_moustaki.parser.questions.impl.DefaultQuiz;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

public class DefaultQuizTest extends TestCase
{

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultQuizTest(String testName)
	{
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultQuizTest.class);
	}
	
	/**
	 * Test getQuestionList
	 */
	public void testGetQuestionList()
	{
		Quiz q = new DefaultQuiz();
		
		assertNotNull(q.getQuestionList());
	}

}
