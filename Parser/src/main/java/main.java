import dcll_moustaki.parser.questions.Quiz;
import dcll_moustaki.parser.questions.impl.DefaultQuestion;
import dcll_moustaki.parser.questions.impl.DefaultQuiz;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DefaultQuiz dq = new DefaultQuiz();
		DefaultQuestion quest = new DefaultQuestion();
		quest.setTitle("babar");
		dq.addQuestion(quest);
		System.out.println(dq.getQuestionList().get(0).getTitle());
	}

}
