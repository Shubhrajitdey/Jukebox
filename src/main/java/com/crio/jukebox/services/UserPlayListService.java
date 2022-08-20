package com.crio.jukebox.services;

import com.crio.jukebox.entites.*;
import com.crio.jukebox.exceptions.InvalidOperationException;
import com.crio.jukebox.repositories.IUserPlayListRepository;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.dtos.UserPlayedSongDto;
import com.crio.jukebox.exceptions.PlayListNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.repositories.ISongRepository;

import java.util.*;

public class UserPlayListService implements IUserPlayListService{
    private IUserRepository iUserRepository;
    private ISongRepository iSongRepository;

    private IUserPlayListRepository userPlayListRepository;

    private LinkedList<Song> currSongPlaylistQueue;

    private ListIterator<Song> songIterator;

    public UserPlayListService(IUserRepository iUserRepository, ISongRepository iSongRepository, IUserPlayListRepository userPlayListRepository) {
        this.iUserRepository = iUserRepository;
        this.iSongRepository = iSongRepository;
        this.userPlayListRepository = userPlayListRepository;
        this.currSongPlaylistQueue = new LinkedList<Song>();
    }

    @Override
    public UserPlayedSongDto playSongById(String userId, String songId) throws UserNotFoundException, SongNotFoundException {
        return null;
    }

    @Override
    public UserPlayedSongDto playSongByOrder(String userId, SongPlayingOrder playingOrder) throws UserNotFoundException {
        User currUser=iUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User No Found"));
        if(playingOrder==SongPlayingOrder.NEXT){
            if(songIterator.hasNext()){
                Song currentPlayingSong=songIterator.next();
                UserPlayedSongDto userPlayedSongDto=new UserPlayedSongDto(currUser.getName(),currentPlayingSong.getName(),currentPlayingSong.getAlbumName(),String.join(", ", currentPlayingSong.getArtis().toString()));
                return userPlayedSongDto;
            }else{
                songIterator=currSongPlaylistQueue.listIterator();
                Song currentPlayingSong=songIterator.next();
                UserPlayedSongDto userPlayedSongDto=new UserPlayedSongDto(currUser.getName(),currentPlayingSong.getName(),currentPlayingSong.getAlbumName(),String.join(", ", currentPlayingSong.getArtis().toString()));
                return userPlayedSongDto;
            }
        }else if(playingOrder==SongPlayingOrder.BACK){
            if(songIterator.hasPrevious()){
                Song currentPlayingSong=songIterator.previous();
                UserPlayedSongDto userPlayedSongDto=new UserPlayedSongDto(currUser.getName(),currentPlayingSong.getName(),currentPlayingSong.getAlbumName(),String.join(", ", currentPlayingSong.getArtis().toString()));
                return userPlayedSongDto;
            }else{
                songIterator=currSongPlaylistQueue.listIterator();
                Song currentPlayingSong=songIterator.previous();
                UserPlayedSongDto userPlayedSongDto=new UserPlayedSongDto(currUser.getName(),currentPlayingSong.getName(),currentPlayingSong.getAlbumName(),String.join(", ", currentPlayingSong.getArtis().toString()));
                return userPlayedSongDto;
            }
        }
        return null;
    }

    @Override
    public String createPlayList(String userId, String PlayListName, List<String> songs) throws UserNotFoundException,SongNotFoundException {
        User currUser=iUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User No Found"));
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
        if(listOfPlayList!=null){
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
    public PlayList addSongToPlayList(String userId, String playListId, List<String> songs) throws UserNotFoundException, PlayListNotFoundException, SongNotFoundException ,InvalidOperationException{
        User currUser=iUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User No Found"));
        List<Song> listOfAllSongs=new ArrayList<Song>();
        for(String songId:songs){
            Song song=iSongRepository.findSongById(songId);
            if(song!=null)
                listOfAllSongs.add(song);
            else throw new SongNotFoundException("Songs Not Found");
        }
        PlayList playList=userPlayListRepository.addListOfSongsToUserPlayList(userId,playListId,listOfAllSongs);
        if(playList==null) throw new InvalidOperationException("No PlayList Found!!!");
        return playList;
    }

    @Override
    public PlayList deleteSongFromPlayList(String userId, String playListId, List<String> songs) throws UserNotFoundException, PlayListNotFoundException, SongNotFoundException {
        User currUser=iUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User No Found"));
        List<Song> listOfAllSongs=new ArrayList<Song>();
        for(String songId:songs){
            Song song=iSongRepository.findSongById(songId);
            if(song!=null)
                listOfAllSongs.add(song);
            else throw new SongNotFoundException("Songs Not Found");
        }
        PlayList playList=userPlayListRepository.removeListOfSongsFromUserPlayList(userId,playListId,listOfAllSongs);
        if(playList==null) throw new InvalidOperationException("Song ID is No Found !!!");
        return playList;
    }

    @Override
    public UserPlayedSongDto setCurrentPlayList(String userId, String playListId) throws UserNotFoundException, PlayListNotFoundException {
        User currUser=iUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User No Found"));
        if(!userPlayListRepository.isPlayListExistByPlayListId(userId,playListId)) throw new PlayListNotFoundException("PlayList is Not Found!!!");
        List<PlayList> listOfPlayList=userPlayListRepository.findAllPlayListByUserId(currUser.getId());
        PlayList currentPlayList=listOfPlayList.stream().filter(p->p.getId().equals(playListId)).findFirst().get();
        currentPlayList.getSongPlayingStatus(SongPlayingStatus.PLAYING);
        currSongPlaylistQueue.clear();
        for(Song song:currentPlayList.getSongs()){
            currSongPlaylistQueue.add(song);
        }
        songIterator=currSongPlaylistQueue.listIterator();
        Song currSong=currSongPlaylistQueue.getFirst();
        UserPlayedSongDto userPlayedSongDto=new UserPlayedSongDto(currUser.getName(),currSong.getName(),currSong.getAlbumName(),currSong.getArtis().toString());
        songIterator.next();
        return userPlayedSongDto;
    }
}
