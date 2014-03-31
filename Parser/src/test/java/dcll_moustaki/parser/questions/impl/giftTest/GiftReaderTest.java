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
		
		String s = new String
				("{Question\\:12|type=\"()\"}+ 6\\%.- \\~12."
						+ "- \\#0672.- http\\:\\test.");
		Reader r = new StringReader(s);

		gr.parse(r);
	}
	
	/*
	 * Test if a simple question have only one possible choice
	 */
	public void testSimpleQuestionOnlyOneResponse() {
		String quest = "{Question |type=\"()\"}\n+ The correct answer.\n- Distractor.\n- Distractor.\n- Distractor.";
		int compteur = 0;
		
		for (int i = 0; i < quest.length(); i++) {
		    if (quest.charAt(i) == '+')             
		       compteur++;                          
		}
	
		assertEquals(compteur, 1);
	}
	
	/*
	 * Test if a '\n' char is present before each '+' or '-'
	 */
	public void testSimpleQuestionBackline() {
		String quest = "{Question |type=\"()\"}\n+ The correct answer.\n- Distractor.\n- Distractor.\n- Distractor.";
		
		for (int i = 0; i < quest.length(); i++) {
		    if (quest.charAt(i) == '+' || quest.charAt(i) == '-')             
		    	assertEquals(quest.charAt(i-1), '\n');
		}
	}
	
	/*
	 * Test if the question is a TRUE/FALSE, she contains one '+', one '-', one "TRUE" and one "FALSE"
	 */
	public void testTrueFalseQuestion() {
		String quest = "{{Bulgaria and Rumania joined the European Union in 2007.|type=\"()\"}\n+ TRUE.\n- FALSE.";
		int nbRep = 0;
		int nbMoins = 0;
		int nbPlus =0;
		
		// Verify if a Question contain TRUE, she contain FALSE too
		if (quest.contains("TRUE")) {
			assertTrue(quest.contains("FALSE"));
		}
		
		// Vérify if they are only tow response
		for (int i = 0; i < quest.length(); i++) {
		    if (quest.charAt(i) == '+') {          
		    	nbPlus++;
		    	nbRep++;
		    } else if (quest.charAt(i) == '-') {
		    	nbMoins++;
		    	nbRep++;
		    }
		}
		assertEquals(nbMoins, 1);
		assertEquals(nbPlus, 1);
		assertEquals(nbRep, 2);
	}
	
	/*
	 * Test the type of the question if it's a multiple choice question
	 */
	public void testTypeMultipleChoiceQuestion() {
		String quest = "{Question|type=\"[]\"\n+ Correct answer.\n- Incorrect answer.\n+ Correct answer.\n- Incorrect answer.";
		int nbPlus = 0;
		
		// Vérify if they are only tow response
		for (int i = 0; i < quest.length(); i++) {
			if (quest.charAt(i) == '+') {
				nbPlus++;
			}
		}
		
		if (nbPlus > 1) {
			assertTrue(quest.contains("[]"));
		}
	}
	
	
	
	/**
	 * Test flushAccumulator
	 */
	public void testFlushAccumulator()
	{
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);
		
		String s = new String ("test");
		StringBuffer acc = new StringBuffer(s);
		
		gr.setAccumulator(acc);
		assertNotNull(gr.getAccumulator());
		
		gr.flushAccumulator();
		
		assertNull(gr.getAccumulator());
	}
	
	/**
	 * Test processAnyCharacter
	 */
	public void testProcessAnyCharacter()
	{
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);
		
		int curChar = 'l';
		int ctrl = 10;
		
		gr.setControlCharAccumulator(ctrl);
		gr.setEscapeMode(true);
		
		try
		{
			gr.processAnyCharacter(curChar);
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		assertEquals(gr.getControlCharAccumulator(), 10);
		assertEquals(gr.isEscapeMode(), true);
		
		ctrl = '\\';
		
		gr.setControlCharAccumulator(ctrl);
		
		try
		{
			gr.processAnyCharacter(curChar);
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		assertEquals(gr.getControlCharAccumulator(), -1);
		assertEquals(gr.isEscapeMode(), false);
		
		ctrl = -1;
		
		gr.setControlCharAccumulator(ctrl);
		gr.setEscapeMode(true);
		
		try
		{
			gr.processAnyCharacter(curChar);
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		assertEquals(gr.getControlCharAccumulator(), -1);
		assertEquals(gr.isEscapeMode(), false);
	}
		

	/**
	 * Test processPercentCharacter
	 */
	public void testProcessPercentCharacter()
	{
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);
		
		gr.setEscapeMode(false);

		gr.setAnswerHasStarted(false);
		gr.setAnswerCreditHasEnded(false);
		
		try
		{
			gr.processPercentCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		gr.setAnswerHasStarted(true);
		gr.setAnswerCreditHasEnded(true);
		
		try
		{
			gr.processPercentCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		gr.setAnswerHasStarted(true);
		gr.setAnswerCreditHasEnded(false);
		gr.setAnswerCreditHasStarted(false);
		
		try
		{
			gr.processPercentCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		assertEquals(gr.isAnswerCreditHasStarted(), true);
		
		gr.setAnswerHasStarted(true);
		gr.setAnswerCreditHasEnded(false);
		gr.setAnswerCreditHasStarted(true);
		
		try
		{
			gr.processPercentCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(),
					GiftReaderNotEscapedCharacterException.class);
		}
		
		assertEquals(gr.isAnswerCreditHasStarted(), false);
		assertEquals(gr.isAnswerCreditHasEnded(), true);
	}
	
	/**
	 * Test processSharpCharacter
	 */
	public void testProcessSharpCharacter()
	{
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);
		
		gr.setEscapeMode(true);
		
		try
		{
			gr.processSharpCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		gr.setEscapeMode(false);
		gr.setAnswerHasStarted(false);
		gr.setAnswerFeedbackHasStarted(false);
		
		try
		{
			gr.processSharpCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		gr.setEscapeMode(false);
		gr.setAnswerHasStarted(true);
		gr.setAnswerFeedbackHasStarted(true);
		
		try
		{
			gr.processSharpCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		gr.setEscapeMode(false);
		gr.setAnswerHasStarted(true);
		gr.setAnswerFeedbackHasStarted(false);
		
		try
		{
			gr.processSharpCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		assertEquals(gr.isAnswerFeedbackHasStarted(), true);
	}
	
	/**
	 * Test processAnswerPrefix
	 */
	public void testProcessAnswerPrefix()
	{
		char prf = 'c';
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);
		
		gr.setEscapeMode(false);
		gr.setAnswerFragmentHasStarted(false);
		
		try
		{
			gr.processAnswerPrefix(prf);
		}
		catch(GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		GiftReader gr2 = new GiftReader();
		QuizContentHandler gq2 = new GiftQuizContentHandler();
		gr2.setQuizContentHandler(gq2);
		
		gr2.setEscapeMode(false);
		gr2.setAnswerFragmentHasStarted(true);
		gr2.setAnswerFeedbackHasStarted(true);
		
		try
		{
			gr2.processAnswerPrefix(prf);
		}
		catch(GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		assertEquals(gr2.isAnswerFeedbackHasStarted(), false);
		
		GiftReader gr3 = new GiftReader();
		QuizContentHandler gq3 = new GiftQuizContentHandler();
		gr3.setQuizContentHandler(gq3);
		
		String s = new String("/");
		
		gr3.getQuizContentHandler().onStartAnswer(s);

		gr3.getQuizContentHandler().onStartQuiz();
		gr3.getQuizContentHandler().onStartQuestion();
		gr3.getQuizContentHandler().onStartAnswerBlock();
		gr3.getQuizContentHandler().onStartAnswer(s);
		
		
		gr3.setEscapeMode(false);
		gr3.setAnswerFeedbackHasStarted(false);
		gr3.setAnswerFragmentHasStarted(true);
		gr3.setAnswerHasStarted(true);
		
		try
		{
			gr3.processAnswerPrefix(prf);
		}
		catch(GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
	}

	/**
	 * Test processRightBracketCharacter
	 */
	public void testProcessRightBracketCharacter()
	{
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);
		
		gr.setEscapeMode(true);
		
		try
		{
			gr.processRightBracketCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		GiftReader gr2 = new GiftReader();
		QuizContentHandler gq2 = new GiftQuizContentHandler();
		gr2.setQuizContentHandler(gq2);
		
		gr2.setEscapeMode(false);
		gr2.setAnswerFragmentHasStarted(false);
		
		try
		{
			gr2.processRightBracketCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		GiftReader gr3 = new GiftReader();
		QuizContentHandler gq3 = new GiftQuizContentHandler();
		gr3.setQuizContentHandler(gq3);
		
		String s = new String("/");
		
		gr3.getQuizContentHandler().onStartAnswer(s);

		gr3.getQuizContentHandler().onStartQuiz();
		gr3.getQuizContentHandler().onStartQuestion();
		gr3.getQuizContentHandler().onStartAnswerBlock();
		gr3.getQuizContentHandler().onStartAnswer(s);
		
		
		gr3.setEscapeMode(false);
		gr3.setAnswerFragmentHasStarted(true);
		gr3.setAnswerHasStarted(false);
		
		try
		{
			gr3.processRightBracketCharacter();
		}
		catch(GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
	}
	
	/**
	 * Test processLeftBracketCharacter
	 */
	public void testProcessLeftBracketCharacter()
	{
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);
		
		gr.setEscapeMode(true);
		
		try
		{
			gr.processLeftBracketCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
		
		GiftReader gr2 = new GiftReader();
		QuizContentHandler gq2 = new GiftQuizContentHandler();
		gr2.setQuizContentHandler(gq2);
		
		gr2.setEscapeMode(false);
		gr2.setAnswerFragmentHasStarted(true);
		
		try
		{
			gr2.processLeftBracketCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
	}
	
	/**
	 * Test processAntiSlashCharacter
	 */
	public void testProcessAntiSlashCharacter()
	{
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);
		
		gr.setEscapeMode(true);
		
		try
		{
			gr.processAntiSlashCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), 
					GiftReaderNotEscapedCharacterException.class);
		}
	}
	
	/**
	 * Test processColonCharacter
	 */
	public void testProcessColonCharacter()
	{
		GiftReader gr = new GiftReader();
		QuizContentHandler gq = new GiftQuizContentHandler();
		gr.setQuizContentHandler(gq);
		
		gr.setEscapeMode(false);
		gr.setTitleHasEnded(true);
		
		try
		{
			gr.processColonCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), GiftReaderNotEscapedCharacterException.class);
		}
		
		GiftReader gr2 = new GiftReader();
		QuizContentHandler gq2 = new GiftQuizContentHandler();
		gr2.setQuizContentHandler(gq2);
		
		gr2.setEscapeMode(false);
		gr2.setTitleHasEnded(false);
		gr2.setControlCharAccumulator(-1);
		
		try
		{
			gr2.processColonCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), GiftReaderNotEscapedCharacterException.class);
		}
		
		GiftReader gr3 = new GiftReader();
		QuizContentHandler gq3 = new GiftQuizContentHandler();
		gr3.setQuizContentHandler(gq3);
		
		gr3.getQuizContentHandler().onStartQuiz();
		gr3.getQuizContentHandler().onStartQuestion();
		gr3.getQuizContentHandler().onStartTitle();
		
		
		gr3.setEscapeMode(false);
		gr3.setTitleHasStarted(false);
		gr3.setTitleHasEnded(false);
		gr3.setControlCharAccumulator(':');
		
		try
		{
			gr3.processColonCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), GiftReaderNotEscapedCharacterException.class);
		}
		
		GiftReader gr4 = new GiftReader();
		QuizContentHandler gq4 = new GiftQuizContentHandler();
		gr4.setQuizContentHandler(gq4);
		
		gr4.getQuizContentHandler().onStartQuiz();
		gr4.getQuizContentHandler().onStartQuestion();
		gr4.getQuizContentHandler().onStartTitle();
		
		
		gr4.setEscapeMode(false);
		gr4.setTitleHasStarted(false);
		gr4.setTitleHasEnded(false);
		gr4.setControlCharAccumulator('/');
		
		try
		{
			gr4.processColonCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), GiftReaderNotEscapedCharacterException.class);
		}
		
		GiftReader gr5 = new GiftReader();
		QuizContentHandler gq5 = new GiftQuizContentHandler();
		gr5.setQuizContentHandler(gq5);
		
		gr5.getQuizContentHandler().onStartQuiz();
		gr5.getQuizContentHandler().onStartQuestion();
		gr5.getQuizContentHandler().onStartTitle();
		
		
		gr5.setEscapeMode(false);
		gr5.setTitleHasStarted(true);
		gr5.setTitleHasEnded(false);
		gr5.setControlCharAccumulator(':');
		
		try
		{
			gr5.processColonCharacter();
		}
		catch (GiftReaderNotEscapedCharacterException ex)
		{
			assertEquals(ex.getClass(), GiftReaderNotEscapedCharacterException.class);
		}
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
			assertEquals(ex.getClass(), GiftReaderQuestionWithInvalidFormatException.class);
		}
		
		gr.setQuestionHasEnded(true);
		gr.setAnswerFragmentHasEnded(true);
		
		try
		{
			gr.endQuiz();
		}
		catch (GiftReaderQuestionWithInvalidFormatException ex)
		{
			assertEquals(ex.getClass(), GiftReaderQuestionWithInvalidFormatException.class);
		}
	}
}
