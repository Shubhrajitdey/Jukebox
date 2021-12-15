package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;
import com.crio.codingame.services.IContestService;

public class CreateContestCommand implements ICommand{

    private final IContestService contestService;

    public CreateContestCommand(IContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void execute(List<String> tokens) {
        String contestName = tokens.get(1);
        String level = tokens.get(2);
        String creatorName = tokens.get(3);
        if(tokens.size() == 4){
         Contest contest = contestService.create(contestName, Level.valueOf(level), creatorName,null);
         System.out.println(contest);
         return;
        }
        Integer numQuestion = Integer.parseInt(tokens.get(4));
        Contest contest = contestService.create(contestName,Level.valueOf(level), creatorName,numQuestion);
        System.out.println(contest);
    }
    
}
