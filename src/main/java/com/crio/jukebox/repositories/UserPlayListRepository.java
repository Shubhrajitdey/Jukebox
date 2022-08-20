package com.crio.jukebox.repositories;

import com.crio.jukebox.entites.PlayList;
import com.crio.jukebox.entites.Song;
import com.crio.jukebox.entites.UserPlayList;

import java.util.*;
import java.util.stream.Collectors;

public class UserPlayListRepository implements IUserPlayListRepository{
    private final Map<String,UserPlayList> userPlayListMap=new HashMap<String,UserPlayList>();

    private Integer autoIncrement=0;

    private Integer autoIncrementForPlayListId=0;

    @Override
    public UserPlayList save(UserPlayList entity) {
        if(entity.getId()==null) {
                autoIncrement++;
                UserPlayList userPlayList = new UserPlayList(Integer.toString(autoIncrement), entity.getUser(), entity.getPlayLists());
                userPlayListMap.put(userPlayList.getUser().getId(), userPlayList);
                return userPlayList;
        }
        userPlayListMap.put(entity.getUser().getId(), entity);
        return entity;
    }

    @Override
    public List<UserPlayList> findAll() {
        return new ArrayList<>(userPlayListMap.values());
    }

    @Override
    public Optional<UserPlayList> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public void delete(UserPlayList entity) {

    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<PlayList> findAllPlayListByUserId(String userId) {
        if(userPlayListMap.containsKey(userId))
            return userPlayListMap.get(userId).getPlayLists();
        return null;
    }

    @Override
    public void delelePlayListByUserIdAndPlayListId(String userId, String playListId) {
        if(userPlayListMap.containsKey(userId)){
            List<PlayList> listOfPlayList=userPlayListMap.get(userId).getPlayLists();
            if(listOfPlayList!=null || !listOfPlayList.isEmpty()){
                listOfPlayList.removeIf(playList -> playList.getId().equals(playListId));
            }
        }
    }

    @Override
    public PlayList createPlayList(PlayList entity) {
        if(entity.getId()==null){
            autoIncrementForPlayListId++;
            PlayList p=new PlayList(Integer.toString(autoIncrementForPlayListId),entity.getName(),entity.getSongs());
            return p;
        }
        return entity;
    }

    @Override
    public boolean isPlayListExistByPlayListId(String userId,String playListId) {
        List<PlayList> listOfPlayList=userPlayListMap.get(userId).getPlayLists();
        if(listOfPlayList==null || listOfPlayList.isEmpty())return false;
        return listOfPlayList.stream().filter(p -> p.getId().equals(playListId)).count()>0;
    }

    @Override
    public PlayList addListOfSongsToUserPlayList(String userId, String playListId, List<Song> songs) {
        if(userPlayListMap.containsKey(userId)){
            List<PlayList> listOfPlayList=userPlayListMap.get(userId).getPlayLists();
            if(listOfPlayList==null || listOfPlayList.isEmpty()) return null;
            PlayList currPlayList=listOfPlayList.stream().filter(lp->lp.getId().equals(playListId)).findFirst().get();
            if(currPlayList!=null){
                List<Song> currentSongs=currPlayList.getSongs();
                Set<String> currentSongList=new HashSet<String>();
                for(Song song:currentSongs)
                    currentSongList.add(song.getId());
                for(Song song:songs){
                    if(!currentSongList.contains(song.getId()))
                        currentSongs.add(song);
                }
            }
            listOfPlayList.set(listOfPlayList.indexOf(currPlayList),currPlayList);
            return currPlayList;
        }
        return null;
    }

    @Override
    public PlayList removeListOfSongsFromUserPlayList(String userId, String playListId, List<Song> songs) {
        if(userPlayListMap.containsKey(userId)){
            List<PlayList> listOfPlayList=userPlayListMap.get(userId).getPlayLists();
            if(listOfPlayList==null || listOfPlayList.isEmpty()) return null;
            PlayList currPlayList=listOfPlayList.stream().filter(lp->lp.getId().equals(playListId)).findFirst().get();
            if(currPlayList!=null){
                List<Song> currentSongs=currPlayList.getSongs();
                Set<String> currentSongList=new HashSet<String>();
                for(Song song:currentSongs)
                    currentSongList.add(song.getId());
                for(Song song:songs){
                    if(currentSongList.contains(song.getId()))
                        currentSongs.remove(song);
                    else return  null;
                }
            }
            listOfPlayList.set(listOfPlayList.indexOf(currPlayList),currPlayList);
            return currPlayList;
        }
        return null;
    }
}
