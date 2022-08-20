package com.crio.jukebox.repositories;

import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entites.PlayList;
import com.crio.jukebox.entites.User;

public interface IUserRepository extends CRUDRepository<User,String>{
    public List<PlayList> findAllPlayList(String name);
}
