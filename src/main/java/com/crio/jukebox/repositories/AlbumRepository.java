package com.crio.jukebox.repositories;

import com.crio.jukebox.entites.Album;
import com.crio.jukebox.entites.Song;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlbumRepository implements IAlbumRepository{

    private final Map<String, Album> albumList=new HashMap<String,Album>();
    private final Map<String, String> albumMappingWithNameAndId=new HashMap<String,String>();
    private Integer autoIncrement = 0;

    @Override
    public Album save(Album album) {
        if(album.getId()==null){
            autoIncrement++;
            Album a=new Album(Integer.toString(autoIncrement),album.getName(),album.getSongList(),album.getOwnerName());
            albumList.put(a.getId(),a);
            albumMappingWithNameAndId.put(a.getName(),a.getId());
            return a;
        }
        albumList.put(album.getId(),album);
        albumMappingWithNameAndId.put(album.getName(),album.getId());
        return album;
    }

    @Override
    public List<Album> findAll() {
        return albumList.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Album> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public void delete(Album entity) {

    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public long count() {
        return 0;
    }


    @Override
    public Album findAlbumById(String albumId) {
        return null;
    }

    @Override
    public Album findAlbumByAlbumName(String albumName) {
        if(albumMappingWithNameAndId.containsKey(albumName)){
            return albumList.get(albumMappingWithNameAndId.get(albumName));
        }

        return null;
    }
}
