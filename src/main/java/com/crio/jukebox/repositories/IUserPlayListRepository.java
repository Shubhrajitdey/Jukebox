package com.crio.jukebox.repositories;

import com.crio.jukebox.entites.PlayList;
import com.crio.jukebox.entites.UserPlayList;

import java.util.List;

public interface IUserPlayListRepository extends CRUDRepository<UserPlayList,String>{
    public List<PlayList> findAllPlayListByUserId(String userId);
    public void delelePlayListByUserIdAndPlayListId(String userId,String playListId);

    public PlayList createPlayList(PlayList entity);

    public boolean isPlayListExistByPlayListId(String userId,String playListId);

}
