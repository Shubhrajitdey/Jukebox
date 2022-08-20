package com.crio.jukebox.repositories;

import com.crio.jukebox.entites.PlayList;
import com.crio.jukebox.entites.Song;
import com.crio.jukebox.entites.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayListRepository implements IPlayListRepository{

    private final Map<String, PlayList> listOfPlayList=new HashMap<String,PlayList>();
    private Integer autoIncrement=0;

    @Override
    public PlayList save(PlayList entity) {
        if(entity.getId()==null){
            autoIncrement++;
            PlayList p=new PlayList(Integer.toString(autoIncrement),entity.getName(),entity.getSongs());
            listOfPlayList.put(p.getId(),p);
            return p;
        }
        listOfPlayList.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public List<PlayList> findAll() {
        return listOfPlayList.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<PlayList> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public void delete(PlayList entity) {

    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<Song> findAllSongById(String playListId) {
        return null;
    }
}
