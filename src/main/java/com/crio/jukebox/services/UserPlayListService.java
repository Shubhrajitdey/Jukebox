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

    private List<Song> currSongPlaylistQueue;

    private Integer currentSongPlayingIdx;

    //private ListIterator<Song> songIterator;

    public UserPlayListService(IUserRepository iUserRepository, ISongRepository iSongRepository, IUserPlayListRepository userPlayListRepository) {
        this.iUserRepository = iUserRepository;
        this.iSongRepository = iSongRepository;
        this.userPlayListRepository = userPlayListRepository;
        this.currSongPlaylistQueue = new LinkedList<Song>();
    }

    @Override
    public UserPlayedSongDto playSongById(String userId, String songId) throws UserNotFoundException, SongNotFoundException {
        User currUser=iUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User No Found!!!"));
        Song song=iSongRepository.findById(songId).orElseThrow(() -> new SongNotFoundException("Song No Found!!!"));
        if(currSongPlaylistQueue.get(currentSongPlayingIdx).getId().equals(songId)) {
            UserPlayedSongDto userPlayedSongDto=new UserPlayedSongDto(currUser.getName(),song.getName(),song.getAlbumName(),String.join(", ", song.getArtis().toString()));
            return userPlayedSongDto;
        }
        for(int i=0;i<currSongPlaylistQueue.size();i++){
            if(currSongPlaylistQueue.get(i).getId().equals(songId)){
                currentSongPlayingIdx=i;
                Song newSong=currSongPlaylistQueue.get(i);
                UserPlayedSongDto userPlayedSongDto=new UserPlayedSongDto(currUser.getName(),newSong.getName(),newSong.getAlbumName(),String.join(", ", newSong.getArtis().toString()));
                return userPlayedSongDto;
            }
        }
        throw new SongNotFoundException("Song is Not Present Current Playing PlayList !!!!");
    }

    @Override
    public UserPlayedSongDto playSongByOrder(String userId, SongPlayingOrder playingOrder) throws UserNotFoundException {
        User currUser=iUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User No Found"));
        if(playingOrder==SongPlayingOrder.NEXT){
            currentSongPlayingIdx=(currentSongPlayingIdx+1)%currSongPlaylistQueue.size();
            Song currentPlayingSong=currSongPlaylistQueue.get(currentSongPlayingIdx);
            UserPlayedSongDto userPlayedSongDto=new UserPlayedSongDto(currUser.getName(),currentPlayingSong.getName(),currentPlayingSong.getAlbumName(),String.join(", ", currentPlayingSong.getArtis().toString()));
            return userPlayedSongDto;
        }else if(playingOrder==SongPlayingOrder.BACK){
            currentSongPlayingIdx=(currentSongPlayingIdx-1+currSongPlaylistQueue.size())%currSongPlaylistQueue.size();
            Song currentPlayingSong=currSongPlaylistQueue.get(currentSongPlayingIdx);
            UserPlayedSongDto userPlayedSongDto=new UserPlayedSongDto(currUser.getName(),currentPlayingSong.getName(),currentPlayingSong.getAlbumName(),String.join(", ", currentPlayingSong.getArtis().toString()));
            return userPlayedSongDto;
        }
        return null;
    }

    @Override
    public PlayList createPlayList(String userId, String PlayListName, List<String> songs) throws UserNotFoundException,SongNotFoundException {
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
        return savedPlayList;
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
        for(PlayList curPlay:listOfPlayList){
            if(curPlay.getSongPlayingStatus()==SongPlayingStatus.PLAYING && !curPlay.getId().equals(playListId)){
                curPlay.setSongPlayingStatus(SongPlayingStatus.NOT_PLAYING);
            }
        }
        PlayList currentPlayList=listOfPlayList.stream().filter(p->p.getId().equals(playListId)).findFirst().get();
        currentPlayList.setSongPlayingStatus(SongPlayingStatus.PLAYING);
        currSongPlaylistQueue.clear();
        for(Song song:currentPlayList.getSongs()){
            currSongPlaylistQueue.add(song);
        }
        //songIterator=currSongPlaylistQueue.listIterator();
        currentSongPlayingIdx=0;
        Song currSong=currSongPlaylistQueue.get(currentSongPlayingIdx);
        UserPlayedSongDto userPlayedSongDto=new UserPlayedSongDto(currUser.getName(),currSong.getName(),currSong.getAlbumName(),currSong.getArtis().toString());
        //songIterator.next();
        return userPlayedSongDto;
    }
}
