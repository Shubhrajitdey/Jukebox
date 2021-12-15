package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.services.IUserService;

public class LeaderBoardCommand implements ICommand{

    private final IUserService userService;
    
    public LeaderBoardCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        String order = tokens.get(1);
        List<User> uList = userService.getAllUserScoreOrderWise(ScoreOrder.valueOf(order));
        uList.forEach(u->{
            System.out.println("[Name:" + u.getName() + " Score:" +u.getScore()+"]");
        });
        
    }
    
}
