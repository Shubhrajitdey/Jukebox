package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.dtos.ContestSummaryDto;
import com.crio.codingame.services.IContestService;

public class RunContestCommand implements ICommand {

    private final IContestService contestService;
    
    public RunContestCommand(IContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void execute(List<String> tokens) {
        String contestId = tokens.get(1);
        String contestCreator = tokens.get(2);
        ContestSummaryDto contestSummaryDto = contestService.runContest(contestId, contestCreator);
        System.out.println(contestSummaryDto);
        
    }
    
}
