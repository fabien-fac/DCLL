package dcll_moustaki.parser.question.impl;

import dcll_moustaki.parser.questions.impl.DefaultAnswer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DefaultAnswerTest extends TestCase {

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultAnswerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultAnswerTest.class);
	}

	/**
	 * Test create
	 */
	public void test_create() {	
		DefaultAnswer answer = new DefaultAnswer();
		assertNotNull(answer);
	}
	
	/**
	 * Test set et get textValue
	 */
	public void test_text_value() {	
		String textValue = "textValue";
		DefaultAnswer answer = new DefaultAnswer();
		answer.setTextValue(textValue);
		
		assertEquals(textValue, answer.getTextValue());	
	}
	
	/**
	 * Test set et get percentCredit
	 */
	public void test_percent_credit() {	
		float percentCredit = 0.7f;
		DefaultAnswer answer = new DefaultAnswer();
		answer.setPercentCredit(percentCredit);
		
		assertEquals(percentCredit, answer.getPercentCredit());
	}
	
	/**
	 * Test set et get identifier
	 */
	public void test_identifier() {	
		String identifier = "identifier";
		DefaultAnswer answer = new DefaultAnswer();
		answer.setIdentifier(identifier);
		
		assertEquals(identifier, answer.getIdentifier());	
	}
	
	/**
	 * Test set et get feedback
	 */
	public void test_feedback() {	
		String feedback = "feedback";
		DefaultAnswer answer = new DefaultAnswer();
		answer.setFeedback(feedback);
		
		assertEquals(feedback, answer.getFeedBack());	
	}
	
	/**
	 * Test hashcode
	 */
	public void test_hashcode() {	
		String identifier = "identifier";
		int hash = identifier.hashCode();
		DefaultAnswer answer = new DefaultAnswer();
		answer.setIdentifier(identifier);
		
		assertEquals(hash, answer.hashCode());
	}

	/**
	 * Test equals
	 */
	public void test_equals() {	
		String identifier = "identifier";
		DefaultAnswer answer = new DefaultAnswer();
		answer.setIdentifier(identifier);
		
		DefaultAnswer answer2 = new DefaultAnswer();
		answer2.setIdentifier(identifier);
		
		assertEquals(answer, answer2);
	}
	
	/**
	 * Test not equals
	 */
	public void test_not_equals() {	
		String identifier = "identifier";
		String identifier2 = "identifier2";
		DefaultAnswer answer = new DefaultAnswer();
		DefaultAnswer answer3 = null;
		answer.setIdentifier(identifier);
		
		DefaultAnswer answer2 = new DefaultAnswer();
		answer2.setIdentifier(identifier2);
		
		assertFalse(answer.equals(answer2));
		
		assertFalse(answer.equals(new String("oij")));
		
		assertFalse(answer.equals(answer3));
		
	}
}
