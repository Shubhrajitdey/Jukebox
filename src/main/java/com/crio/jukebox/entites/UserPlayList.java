package com.crio.jukebox.entites;

import java.util.List;

public class UserPlayList extends BaseEntity{
    private final User user;
    private final List<PlayList> playLists;
    public UserPlayList(String id,User user, List<PlayList> playLists) {
        this.id=id;
        this.user = user;
        this.playLists = playLists;
    }
    public User getUser() {
        return user;
    }
    public List<PlayList> getPlayLists() {
        return playLists;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((playLists == null) ? 0 : playLists.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserPlayList other = (UserPlayList) obj;
        if (playLists == null) {
            if (other.playLists != null)
                return false;
        } else if (!playLists.equals(other.playLists))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "UserPlayList [id=" + id+" playLists=" + playLists + ", user=" + user + "]";
    }
    
    
}
