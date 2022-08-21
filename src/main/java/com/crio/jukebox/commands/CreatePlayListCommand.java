package com.crio.jukebox.commands;

import com.crio.jukebox.entites.PlayList;
import com.crio.jukebox.entites.Song;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.services.IUserPlayListService;

import java.util.ArrayList;
import java.util.List;

public class CreatePlayListCommand implements ICommand{

    public CreatePlayListCommand(IUserPlayListService userPlayListService) {
        this.userPlayListService = userPlayListService;
    }

    private final IUserPlayListService userPlayListService;

    @Override
    public void execute(List<String> tokens) {
        try{
            List<String> csong=new ArrayList<String>();
            for(int i=3;i<tokens.size()-1;i++)
                csong.add(tokens.get(i));
            PlayList playList=userPlayListService.createPlayList(tokens.get(1),tokens.get(2),csong);
            System.out.println(playList);
        }catch (UserNotFoundException e){
            System.out.println("User is Not Present. Please try again");
        }catch (SongNotFoundException e){
            System.out.println("Some Requested Songs Not Available. Please try again.");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
