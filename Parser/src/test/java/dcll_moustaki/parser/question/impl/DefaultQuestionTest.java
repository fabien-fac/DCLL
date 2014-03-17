package dcll_moustaki.parser.question.impl;

import dcll_moustaki.parser.questions.AnswerBlock;
import dcll_moustaki.parser.questions.QuestionType;
import dcll_moustaki.parser.questions.TextBlock;
import dcll_moustaki.parser.questions.impl.DefaultAnswer;
import dcll_moustaki.parser.questions.impl.DefaultAnswerBlock;
import dcll_moustaki.parser.questions.impl.DefaultQuestion;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DefaultQuestionTest extends TestCase {

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultQuestionTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultQuestionTest.class);
	}

	/**
	 * Test create
	 */
	public void test_create() {
		DefaultQuestion question = new DefaultQuestion();
		assertNotNull(question);
	}

	/**
	 * Test set / get title
	 */
	public void test_title() {
		String title = "title";
		DefaultQuestion question = new DefaultQuestion();
		question.setTitle(title);

		assertEquals(title, question.getTitle());
	}

	/**
	 * Test set / get type
	 */
	public void test_type() {
		QuestionType type = QuestionType.ExclusiveChoice;
		DefaultQuestion question = new DefaultQuestion();
		question.setQuestionType(type);

		assertEquals(type, question.getQuestionType());
	}

	/**
	 * Test add / get answerBlock
	 */
	public void test_answerBlock() {
		DefaultQuestion question = new DefaultQuestion();

		DefaultAnswerBlock block = new DefaultAnswerBlock();
		String identifier = "identifier";
		DefaultAnswer answer = new DefaultAnswer();
		answer.setIdentifier(identifier);
		block.addAnswer(answer);

		// Test de l'insertion
		question.addAnswerBlock(block);
		assertTrue(question.getAnswerBlockList().size() == 1);

		// Test du get list
		assertEquals(block, question.getAnswerBlockList().get(0));

		// Test de l'égalité entre element inséré et récupéré
		assertEquals(answer, question.getAnswerBlockList().get(0)
				.getAnswerList().get(0));

	}

	/**
	 * Test add / get textBlock
	 */
	public void test_textBlock() {
		DefaultQuestion question = new DefaultQuestion();
		final String text = "textblock";

		TextBlock textblock = new TextBlock() {
			@Override
			public String getText() {
				// TODO Auto-generated method stub
				return text;
			}
		};

		// Test de l'insertion
		question.addTextBlock(textblock);
		assertTrue(question.getTextBlockList().size() == 1);

		// Test du get list
		assertEquals(textblock, question.getTextBlockList().get(0));

		// Test de l'égalité entre element inséré et récupéré
		assertEquals(text, question.getTextBlockList().get(0).getText());
		
		// Test de l'insertion
		assertTrue(question.getBlockList().size() == 1);
	}

}
