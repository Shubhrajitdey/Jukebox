package com.crio.jukebox.repositories;

import com.crio.jukebox.entites.Song;

public interface ISongRepository extends CRUDRepository<Song,String>{
    public Song findSongById(String songId);
}
