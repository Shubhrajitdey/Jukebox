package com.crio.jukebox.entites;

import java.util.List;
import java.lang.String;

public class Album extends BaseEntity{
    private final String name;
    private final List<Song> songList;
    private final String ownerName;

    public Album(String id, String name, List<Song> songList, String ownerName) {
        this.id = id;
        this.name = name;
        this.songList = songList;
        this.ownerName = ownerName;
    }
    public String getName() {
        return name;
    }
    public List<Song> getSongList() {
        return songList;
    }
    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((ownerName == null) ? 0 : ownerName.hashCode());
        result = prime * result + ((songList == null) ? 0 : songList.hashCode());
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
        Album other = (Album) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (ownerName == null) {
            if (other.ownerName != null)
                return false;
        } else if (!ownerName.equals(other.ownerName))
            return false;
        if (songList == null) {
            if (other.songList != null)
                return false;
        } else if (!songList.equals(other.songList))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Album [id=" + id +", name=" + name + ", owner="
                + ownerName + ", songList=" + songList + "]";
    } 

    
    
}
