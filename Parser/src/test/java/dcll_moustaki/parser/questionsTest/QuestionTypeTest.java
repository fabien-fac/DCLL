package dcll_moustaki.parser.questionsTest;

import dcll_moustaki.parser.questions.QuestionType;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class QuestionTypeTest extends TestCase {

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public QuestionTypeTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(QuestionTypeTest.class);
	}
	
	/**
	 * Test getCode
	 */
	public void test_get_code() {	
		QuestionType type = QuestionType.ExclusiveChoice;
		
		assertEquals(type.getCode(), 1);
	}
}
