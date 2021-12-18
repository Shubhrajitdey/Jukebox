package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.services.IUserService;

public class AttendContestCommand implements ICommand{

    private final IUserService userService;
    
    public AttendContestCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        String contestId = tokens.get(1);
        String userName = tokens.get(2);
        UserRegistrationDto userRegistrationDto = null;
        try{
        userRegistrationDto = userService.attendContest(contestId, userName);
        }catch(ContestNotFoundException e){
            System.out.println("Contest for given ID:" + contestId + " Not Found");
        }catch(UserNotFoundException e){
            System.out.println("User for given Name:" + userName + " Not Found");
        }
        System.out.println(userRegistrationDto);    
    }
    
}
