package dcll_moustaki.parser.question.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dcll_moustaki.parser.questions.impl.DefaultAnswer;
import dcll_moustaki.parser.questions.impl.DefaultAnswerBlock;

public class DefaultAnswerBlockTest extends TestCase {

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultAnswerBlockTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultAnswerBlockTest.class);
	}
	
	/**
	 * Test create
	 */
	public void test_create() {	
		DefaultAnswerBlock block = new DefaultAnswerBlock();
		assertNotNull(block);
		assertEquals(block.getAnswerList().size(), 0);
	}
	
	/**
	 * Test add answer
	 */
	public void test_add_answer() {	
		DefaultAnswerBlock block = new DefaultAnswerBlock();
		String identifier = "identifier";
		DefaultAnswer answer = new DefaultAnswer();
		answer.setIdentifier(identifier);
		
		block.addAnswer(answer);
		
		assertEquals(block.getAnswerList().size(), 1);
	}
	
	/**
	 * Test get answer
	 */
	public void test_get_answer() {	
		DefaultAnswerBlock block = new DefaultAnswerBlock();
		String identifier = "identifier";
		DefaultAnswer answer = new DefaultAnswer();
		answer.setIdentifier(identifier);
		
		block.addAnswer(answer);
		
		assertEquals(block.getAnswerList().get(0), answer);
	}
}
