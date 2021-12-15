package com.crio.codingame.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserContestQuestions {
    private final Map<Contest,List<Question>> contestQuestionsMap;

    public UserContestQuestions(){
        contestQuestionsMap = new HashMap<Contest,List<Question>>();
    }

    public UserContestQuestions(Map<Contest, List<Question>> contestQuestionsMap) {
        this.contestQuestionsMap = contestQuestionsMap;
    }

    public void addContestQuestion(Contest contest, List<Question> qList){
        contestQuestionsMap.putIfAbsent(contest, qList);
    }

    public List<Question> getQuestionsByContest(Contest contest){
        return contestQuestionsMap.get(contest);
    }


    @Override
    public String toString() {
        return "UserContestQuestions [contestQuestionsMap=" + contestQuestionsMap + "]";
    }
    
}
