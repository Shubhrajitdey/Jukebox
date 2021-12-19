package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;
import com.crio.codingame.services.IQuestionService;

public class ListQuestionCommand implements ICommand{

    private final IQuestionService questionService;
    
    public ListQuestionCommand(IQuestionService questionService) {
        this.questionService = questionService;
    }


    @Override
    public void execute(List<String> tokens) {
    }
    
}
