package com.crio.jukebox.repositories;

import com.crio.jukebox.entites.Album;

public interface IAlbumRepository extends CRUDRepository<Album,String> {
    public Album findAlbumById(String albumId);

    public  Album findAlbumByAlbumName(String albumName);

}
