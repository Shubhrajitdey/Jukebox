package com.crio.jukebox.services;

import com.crio.jukebox.entites.*;
import com.crio.jukebox.repositories.IUserPlayListRepository;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.dtos.UserPlayedSongDto;
import com.crio.jukebox.exceptions.PlayListNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.repositories.ISongRepository;

import java.util.*;

public class UserPlayListService implements IUserPlayListService{
    IUserRepository iUserRepository;
    ISongRepository iSongRepository;

    IUserPlayListRepository userPlayListRepository;

    LinkedList<Song> currSongPlaylistQueue;

    public UserPlayListService(IUserRepository iUserRepository, ISongRepository iSongRepository, IUserPlayListRepository userPlayListRepository, LinkedList<Song> currSongPlaylistQueue) {
        this.iUserRepository = iUserRepository;
        this.iSongRepository = iSongRepository;
        this.userPlayListRepository = userPlayListRepository;
        this.currSongPlaylistQueue = currSongPlaylistQueue;
    }

    @Override
    public UserPlayedSongDto playSongById(String userId, String songId) throws UserNotFoundException, SongNotFoundException {
        return null;
    }

    @Override
    public UserPlayedSongDto playSongByOrder(String userId, SongPlayingOrder playingOrder) throws UserNotFoundException {
        return null;
    }

    @Override
    public String createPlayList(String userId, String PlayListName, List<String> songs) throws UserNotFoundException,SongNotFoundException {
        final User currUser=iUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User No Found"));
        List<Song> listOfAllSongs=new LinkedList<Song>();
        for(String songId:songs){
            Song song=iSongRepository.findSongById(songId);
            if(song!=null)
                listOfAllSongs.add(song);
            else throw new SongNotFoundException("Songs Not Found");
        }
        PlayList playList=new PlayList(null,PlayListName,listOfAllSongs);
        PlayList savedPlayList=userPlayListRepository.createPlayList(playList);
        List<PlayList> listOfPlayList=userPlayListRepository.findAllPlayListByUserId(currUser.getId());
        if(listOfPlayList!=null || listOfPlayList.size()>0){
            listOfPlayList.add(savedPlayList);
            UserPlayList userPlayList=new UserPlayList(null,currUser,listOfPlayList);
            userPlayListRepository.save(userPlayList);
        }else{
            UserPlayList userPlayList=new UserPlayList(null,currUser,new ArrayList<PlayList>(Collections.singletonList(savedPlayList)));
            userPlayListRepository.save(userPlayList);
        }
        return savedPlayList.getId();
    }


    @Override
    public void deletePlayList(String userId, String PlayListId) throws UserNotFoundException, PlayListNotFoundException {
        if(userPlayListRepository.isPlayListExistByPlayListId(userId,PlayListId)){
            userPlayListRepository.delelePlayListByUserIdAndPlayListId(userId,PlayListId);
        }else{
            throw new PlayListNotFoundException("PlayList is Not Found");
        }

    }

    @Override
    public UserPlayedSongDto addSongToPlayList(String userId, String playListId, List<Song> songs) throws UserNotFoundException, PlayListNotFoundException, SongNotFoundException {
        return null;
    }

    @Override
    public UserPlayedSongDto deleteSongFromPlayList(String userId, String playListId, List<Song> songs) throws UserNotFoundException, PlayListNotFoundException, SongNotFoundException {
        return null;
    }
}
