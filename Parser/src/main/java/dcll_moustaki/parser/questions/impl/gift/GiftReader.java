package dcll_moustaki.parser.questions.impl.gift;

import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;

import dcll_moustaki.parser.questions.QuizContentHandler;
import dcll_moustaki.parser.questions.QuizReader;

/**
 * @author franck Silvestre
 */
public class GiftReader implements QuizReader {

    private static Logger logger = Logger.getLogger(GiftReader.class);

    public void parse(Reader reader) throws IOException, GiftReaderException {
        int currentChar;
        quizContentHandler.onStartQuiz();
        while ((currentChar = reader.read()) != -1) {
            checkQuestionHasStarted();
            if (currentChar == ':') {
                processColonCharacter();
            } else if (currentChar == '\\') {
                processAntiSlashCharacter();
            } else if (currentChar == '{') {
                processLeftBracketCharacter();
            } else if (currentChar == '}') {
                processRightBracketCharacter();
            } else if (currentChar == '=') {
                processEqualCharacter();
            } else if (currentChar == '~') {
                processTildeCharacter();
            } else if (currentChar == '#') {
                processSharpCharacter();
            } else if (currentChar == '%') {
                processPercentCharacter();
            } else {
                processAnyCharacter(currentChar);
            }
            logger.debug("Current char  | " + (char) currentChar);
            if (accumulator != null) {
                logger.debug("Accumulator | " + accumulator.toString());
            }
            logger.debug("control caracter accumulator | "
                                    + (char) controlCharAccumulator);
        }
        endQuiz();
        quizContentHandler.onEndQuiz();

    }

    public void checkQuestionHasStarted() {
        if (!questionHasStarted) {
            questionHasStarted = true;
            quizContentHandler.onStartQuestion();
        }
    }

    public void endQuiz()
              throws GiftReaderQuestionWithInvalidFormatException {
        if (!questionHasEnded && !answerFragmentHasEnded) {
            throw new GiftReaderQuestionWithInvalidFormatException();
        }
        if (!questionHasEnded) {
            flushAccumulator();
            questionHasEnded = true;
            quizContentHandler.onEndQuestion();
        }

    }

    public void processColonCharacter()
              throws GiftReaderNotEscapedCharacterException {

        if (escapeMode) {
            processAnyCharacter(':');
            return;
        }
        if (titleHasEnded) {
            throw new GiftReaderNotEscapedCharacterException();
        }
        if (controlCharAccumulator == -1) {
            flushAccumulator();
            controlCharAccumulator = ':';
            return;
        }
        if (controlCharAccumulator == ':') {
            if (titleHasStarted) {
                titleHasEnded = true;
                quizContentHandler.onEndTitle();
            } else {
                titleHasStarted = true;
                quizContentHandler.onStartTitle();
            }
            controlCharAccumulator = -1;
        }

    }

    public void processAntiSlashCharacter()
              throws GiftReaderNotEscapedCharacterException {
        if (escapeMode) {
            processAnyCharacter('\\');
            return;
        }
        escapeMode = true;
    }

    public void processLeftBracketCharacter()
              throws GiftReaderNotEscapedCharacterException {

        if (escapeMode) {
            processAnyCharacter('{');
            return;
        }
        if (answerFragmentHasStarted) {
            throw new GiftReaderNotEscapedCharacterException();
        }
        flushAccumulator();
        answerFragmentHasStarted = true;
        answerFragmentHasEnded = false;
        quizContentHandler.onStartAnswerBlock();

    }

    public void processRightBracketCharacter()
              throws GiftReaderNotEscapedCharacterException {

        if (escapeMode) {
            processAnyCharacter('}');
            return;
        }
        if (!answerFragmentHasStarted) {
            throw  new GiftReaderNotEscapedCharacterException();
        }
        flushAccumulator();
        answerFragmentHasEnded = true;
        answerFragmentHasStarted = false;
        if (answerHasStarted) {
            answerHasStarted = false;
            quizContentHandler.onEndAnswer();
        }
        quizContentHandler.onEndAnswerBlock();

    }

    private void processEqualCharacter() throws GiftReaderException {
        processAnswerPrefix('=');
    }

    private void processTildeCharacter() throws GiftReaderException {
        processAnswerPrefix('~');
    }

    public void processAnswerPrefix(char prefix)
              throws GiftReaderNotEscapedCharacterException {
        if (escapeMode) {
            processAnyCharacter(prefix);
            return;
        }
        if (!answerFragmentHasStarted) {
            throw new GiftReaderNotEscapedCharacterException();
        }
        flushAccumulator();
        if (answerFeedbackHasStarted) {
            answerFeedbackHasStarted = false;
            getQuizContentHandler().onEndAnswerFeedBack();
        }
     // the '=' or '~' char marks the end of the current answer
        if (answerHasStarted) {
            getQuizContentHandler().onEndAnswer();
        } else {
            answerHasStarted = true;
        }
        answerCreditHasStarted = false;
        answerCreditHasEnded = false;
        // it marks the beginning of a new one too
        getQuizContentHandler().onStartAnswer(String.valueOf(prefix));
    }

    public void processSharpCharacter()
              throws GiftReaderNotEscapedCharacterException {

        if (escapeMode) {
            processAnyCharacter('#');
            return;
        }
        if (!answerHasStarted || answerFeedbackHasStarted) {
            throw new GiftReaderNotEscapedCharacterException();
        }
        flushAccumulator();
        answerFeedbackHasStarted = true;
     // it marks the beginning of a new one too
        getQuizContentHandler().onStartAnswerFeedBack(); 
    }


    public void processPercentCharacter()
              throws GiftReaderNotEscapedCharacterException {

        if (escapeMode) {
            processAnyCharacter('%');
            return;
        }
        if (!answerHasStarted || answerCreditHasEnded) {
            throw new GiftReaderNotEscapedCharacterException();
        }
        flushAccumulator();
        if (answerCreditHasStarted) {
            answerCreditHasStarted = false ;
            answerCreditHasEnded = true;
            getQuizContentHandler().onEndAnswerCredit();
        } else {
            answerCreditHasStarted = true ;
            getQuizContentHandler().onStartAnswerCredit();
        }

    }


    public void processAnyCharacter(int currentChar) 
    		throws GiftReaderNotEscapedCharacterException {
        if (accumulator == null) {
            accumulator = new StringBuffer();
        }
        accumulator.append((char) currentChar);
        if (controlCharAccumulator != -1){ 
        	// if a control caracter is present,
            if (controlCharAccumulator != '\\'){ 
            	// it must be a \
                throw new GiftReaderNotEscapedCharacterException();
            }
            
            controlCharAccumulator = -1;
        }
        escapeMode = false;
    }

    public void flushAccumulator() {
        if (accumulator != null) {
            quizContentHandler.onString(accumulator.toString());
            accumulator = null;
        }
    }

    public QuizContentHandler getQuizContentHandler() {
        return quizContentHandler;
    }

    public void setQuizContentHandler(
    		QuizContentHandler quizContentHandlerToSet) {
        this.quizContentHandler = quizContentHandlerToSet;
    }

    public StringBuffer getAccumulator() {
		return accumulator;
	}

	public void setAccumulator(StringBuffer accumulatorToSet) {
		this.accumulator = accumulatorToSet;
	}

	public int getControlCharAccumulator() {
		return controlCharAccumulator;
	}

	public void setControlCharAccumulator(int controlCharAccumulatorToSet) {
		this.controlCharAccumulator = controlCharAccumulatorToSet;
	}

	public boolean isEscapeMode() {
		return escapeMode;
	}

	public void setEscapeMode(boolean escapeModeToSet) {
		this.escapeMode = escapeModeToSet;
	}

	public void setQuestionHasEnded(boolean questionHasEndedToSet) {
		this.questionHasEnded = questionHasEndedToSet;
	}

	public void setTitleHasStarted(boolean titleHasStartedToSet) {
		this.titleHasStarted = titleHasStartedToSet;
	}

	public void setTitleHasEnded(boolean titleHasEndedToSet) {
		this.titleHasEnded = titleHasEndedToSet;
	}

	public void setAnswerFragmentHasStarted(
			         boolean answerFragmentHasStartedToSet) {
		this.answerFragmentHasStarted = answerFragmentHasStartedToSet;
	}

	public void setAnswerFragmentHasEnded(
			        boolean answerFragmentHasEndedToSet) {
		this.answerFragmentHasEnded = answerFragmentHasEnded;
	}

	public void setAnswerHasStarted(boolean answerHasStartedToSet) {
		this.answerHasStarted = answerHasStartedToSet;
	}

	public boolean isAnswerFeedbackHasStarted() {
		return answerFeedbackHasStarted;
	}

	public void setAnswerFeedbackHasStarted(
			          boolean answerFeedbackHasStartedToSet) {
		this.answerFeedbackHasStarted = answerFeedbackHasStartedToSet;
	}

	public boolean isAnswerCreditHasStarted() {
		return answerCreditHasStarted;
	}

	public void setAnswerCreditHasStarted(
			        boolean answerCreditHasStartedToSet) {
		this.answerCreditHasStarted = answerCreditHasStartedToSet;
	}

	public boolean isAnswerCreditHasEnded() {
		return answerCreditHasEnded;
	}

	public void setAnswerCreditHasEnded(boolean answerCreditHasEndedToSet) {
		this.answerCreditHasEnded = answerCreditHasEndedToSet;
	}



	private QuizContentHandler quizContentHandler;
    private StringBuffer accumulator;
    private int controlCharAccumulator = -1;
    private boolean escapeMode;

    private boolean questionHasStarted;
    private boolean questionHasEnded;
    private boolean titleHasStarted;
    private boolean titleHasEnded;
    private boolean answerFragmentHasStarted;
    private boolean answerFragmentHasEnded;
    private boolean answerHasStarted;
    private boolean answerFeedbackHasStarted;
    private boolean answerCreditHasStarted;
    private boolean answerCreditHasEnded;


}
