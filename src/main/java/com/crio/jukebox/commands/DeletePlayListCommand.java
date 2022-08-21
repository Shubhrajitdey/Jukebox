package com.crio.jukebox.commands;

import com.crio.jukebox.exceptions.PlayListNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.services.IUserPlayListService;

import java.util.List;

public class DeletePlayListCommand implements ICommand{

    public DeletePlayListCommand(IUserPlayListService userPlayListService) {
        this.userPlayListService = userPlayListService;
    }

    private final IUserPlayListService userPlayListService;

    @Override
    public void execute(List<String> tokens) {
        try{
            userPlayListService.deletePlayList(tokens.get(1),tokens.get(2));
            System.out.println("Delete Successful\n");
        }catch (UserNotFoundException e){
            System.out.println("User Not Found!!! Try Again Later\n");
        }catch (PlayListNotFoundException e){
            System.out.println("Playlist Not Found\n");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
