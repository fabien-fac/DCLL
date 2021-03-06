/*
 * Copyright 2013 Tsaap Development Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dcll_moustaki.parser.questions.impl;

import java.util.ArrayList;
import java.util.List;

import dcll_moustaki.parser.questions.Question;
import dcll_moustaki.parser.questions.Quiz;

/**
 * @author franck Silvestre
 */
public class DefaultQuiz implements Quiz {

    private List<Question> questionList = new ArrayList<Question>();


    /**
     * Add a question to the quiz
     * @param question the question to add
     */
    public void addQuestion(Question question) {
        questionList.add(question);
    }

    /**
     * Get the question list of the quiz
     *
     * @return the question list
     */
    public List<Question> getQuestionList() {
        return questionList;
    }
}
