package com.crio.jukebox.commands;

import com.crio.jukebox.entites.User;
import com.crio.jukebox.services.IuserService;

import java.util.List;

public class CreateUserCommand implements ICommand{
    private final IuserService userService;

    public CreateUserCommand(IuserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        try{
            User user=userService.create(tokens.get(0));
            System.out.println(user);
        }catch (Exception e){
            System.out.println("Something Go Wrong While Creating User");
        }
    }
}
