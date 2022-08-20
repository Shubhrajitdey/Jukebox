package com.crio.jukebox.repositories;

import com.crio.jukebox.entites.PlayList;
import com.crio.jukebox.entites.UserPlayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserPlayListRepository implements IUserPlayListRepository{
    private final Map<String,UserPlayList> userPlayListMap=new HashMap<String,UserPlayList>();

    private Integer autoIncrement=0;

    @Override
    public UserPlayList save(UserPlayList entity) {
        if(entity.getId()==null) {
                autoIncrement++;
                UserPlayList userPlayList = new UserPlayList(Integer.toString(autoIncrement), entity.getUser(), entity.getPlayLists());
                userPlayListMap.put(userPlayList.getUser().getId(), userPlayList);
                return userPlayList;
        }
        userPlayListMap.put(entity.getUser().getId(), entity);
        return entity;
    }

    @Override
    public List<UserPlayList> findAll() {
        return null;
    }

    @Override
    public Optional<UserPlayList> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public void delete(UserPlayList entity) {

    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<PlayList> findAllPlayListByUserId(String userId) {
        if(userPlayListMap.containsKey(userId))
            return userPlayListMap.get(userId).getPlayLists();
        return null;
    }
}
