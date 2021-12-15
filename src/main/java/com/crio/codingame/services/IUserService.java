package com.crio.codingame.services;

import java.util.List;

import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;

public interface IUserService {
    public User create(String name);
    public List<User> getAllUserScoreOrderWise(ScoreOrder scoreOrder);
    public UserRegistrationDto attendContest(String contestId, String userName);
    public UserRegistrationDto withdrawContest(String contestId, String userName);
}
