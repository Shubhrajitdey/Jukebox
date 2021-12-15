package com.crio.codingame.services;

import java.util.List;

import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;
import com.crio.codingame.repositories.IQuestionRepository;

public class QuestionService implements IQuestionService{
    private final IQuestionRepository questionRepository;

    public QuestionService(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question create(String title, Level level, Integer difficultyScore) {
        final Question question = new Question(title,level, difficultyScore);
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestionLevelWise(Level level) {
        if(level == null){
            return questionRepository.findAll();
        }
        return questionRepository.findAllQuestionLevelWise(level);
    }
    
}
