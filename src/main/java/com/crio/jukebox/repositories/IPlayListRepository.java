package com.crio.jukebox.repositories;

import java.util.List;
import com.crio.jukebox.entites.PlayList;
import com.crio.jukebox.entites.Song;

public interface IPlayListRepository extends CRUDRepository<PlayList,String>{
    public List<Song> findAllSongById(String playListId);
}
