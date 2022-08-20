package com.crio.jukebox.repositories;

import com.crio.jukebox.entites.PlayList;
import com.crio.jukebox.entites.UserPlayList;

import java.util.List;

public interface IUserPlayListRepository extends CRUDRepository<UserPlayList,String>{
    public List<PlayList> findAllPlayListByUserId(String userId);
}
