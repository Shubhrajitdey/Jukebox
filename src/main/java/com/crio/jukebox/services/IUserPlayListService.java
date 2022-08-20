package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.dtos.UserPlayedSongDto;
import com.crio.jukebox.entites.Song;
import com.crio.jukebox.entites.SongPlayingOrder;
import com.crio.jukebox.exceptions.PlayListNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;

public interface IUserPlayListService {
    public UserPlayedSongDto playSongById(String userId,String songId) throws UserNotFoundException,SongNotFoundException;
    public UserPlayedSongDto playSongByOrder(String userId,SongPlayingOrder playingOrder) throws UserNotFoundException;
    public String createPlayList(String userId,String PlayListName,List<String>songs) throws UserNotFoundException,SongNotFoundException;
    public void deletePlayList(String userId,String PlayListId) throws UserNotFoundException,PlayListNotFoundException;
    public UserPlayedSongDto addSongToPlayList(String userId,String playListId,List<Song>songs) throws UserNotFoundException,PlayListNotFoundException,SongNotFoundException;
    public UserPlayedSongDto deleteSongFromPlayList(String userId,String playListId,List<Song>songs) throws UserNotFoundException,PlayListNotFoundException,SongNotFoundException;
}
