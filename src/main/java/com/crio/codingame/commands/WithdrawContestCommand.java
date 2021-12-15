package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.services.IUserService;

public class WithdrawContestCommand implements ICommand{

    private final IUserService userService;
    
    public WithdrawContestCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        String contestId = tokens.get(1);
        String userName = tokens.get(2);
        UserRegistrationDto userRegistrationDto = userService.withdrawContest(contestId, userName);
        System.out.println(userRegistrationDto);
        
    }
    
}
