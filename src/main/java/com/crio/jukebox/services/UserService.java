package com.crio.jukebox.services;

import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.entites.PlayList;
import com.crio.jukebox.entites.User;

import java.util.List;

public class UserService implements IuserService {

    IUserRepository iUserRepository;


    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }


    @Override
    public User create(String name) {
        return iUserRepository.save(new User(null,name));
    }

    @Override
    public List<PlayList> getAllPlayList(String userId) {
        return null;
    }
}
