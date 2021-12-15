package com.crio.codingame.services;

import java.util.List;

import com.crio.codingame.dtos.ContestSummaryDto;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;

public interface IContestService {
    public Contest create(String contestName, Level level, String contestCreator, Integer numQuestion);
    public List<Contest> getAllContestLevelWise(Level level);
    public ContestSummaryDto runContest(String contestId, String contestCreator);
}
