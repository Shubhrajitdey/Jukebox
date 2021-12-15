package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;
import com.crio.codingame.services.IContestService;

public class ListContestCommand implements ICommand{

    private final IContestService contestService;
    
    public ListContestCommand(IContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void execute(List<String> tokens) {
        if(tokens.size() == 1){
            List<Contest> qList = contestService.getAllContestLevelWise(null);
            System.out.println(qList);
            return;
        }
        String level = tokens.get(1);
        List<Contest> qList = contestService.getAllContestLevelWise(Level.valueOf(level));
        System.out.println(qList);   
    }
    
}
