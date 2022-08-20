package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.entites.PlayList;
import com.crio.jukebox.entites.User;

public interface IUserService {
    public User create(String name);
    public List<PlayList> getAllPlayList(String userId);
    
}
