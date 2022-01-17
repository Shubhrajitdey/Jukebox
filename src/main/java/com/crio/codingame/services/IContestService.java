package com.crio.codingame.services;

import java.util.List;

import com.crio.codingame.dtos.ContestSummaryDto;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidContestException;
import com.crio.codingame.exceptions.QuestionNotFoundException;
import com.crio.codingame.exceptions.UserNotFoundException;

public interface IContestService {
    public Contest create(String contestName, Level level, String contestCreator, Integer numQuestion) throws UserNotFoundException, QuestionNotFoundException;
    public List<Contest> getAllContestLevelWise(Level level);
    public ContestSummaryDto runContest(String contestId, String contestCreator) throws ContestNotFoundException, InvalidContestException;
}
