package com.crio.codingame.services;

import java.util.List;
import java.util.stream.Collectors;

import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.RegisterationStatus;
import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidOperationException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.repositories.IContestRepository;
import com.crio.codingame.repositories.IUserRepository;

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IContestRepository contestRepository;

    public UserService(IUserRepository userRepository, IContestRepository contestRepository) {
        this.userRepository = userRepository;
        this.contestRepository = contestRepository;
    }

    @Override
    public User create(String name) {
        final User user = new User(name,0);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUserScoreOrderWise(ScoreOrder scoreOrder) throws InvalidOperationException {
        if(ScoreOrder.ASC.equals(scoreOrder)){
           return userRepository.findAll().stream().sorted((u1,u2) -> u1.getScore()-u2.getScore()).collect(Collectors.toList());
        }
        return userRepository.findAll().stream().sorted((u1,u2) -> u2.getScore()-u1.getScore()).collect(Collectors.toList());
    }

    @Override
    public UserRegistrationDto attendContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException {
        Contest contest = contestRepository.findById(contestId).orElseThrow(ContestNotFoundException::new);
        User user = userRepository.findByName(userName).orElseThrow(UserNotFoundException::new);
        user.addContest(contest);
        userRepository.save(user);
        return new UserRegistrationDto(contest.getName(), user.getName(),RegisterationStatus.REGISTERED);
    }


    @Override
    public UserRegistrationDto withdrawContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
        Contest contest = contestRepository.findById(contestId).orElseThrow(ContestNotFoundException::new);
        User user = userRepository.findByName(userName).orElseThrow(UserNotFoundException::new);
        if(contest.getContestStatus().equals(ContestStatus.IN_PROGRESS) || contest.getContestStatus().equals(ContestStatus.ENDED)){
            throw new InvalidOperationException();
        }
        user.deleteContest(contest);
        userRepository.save(user);
        return new UserRegistrationDto(contest.getName(), user.getName(), RegisterationStatus.NOT_REGISTERED);
    }
    
}
