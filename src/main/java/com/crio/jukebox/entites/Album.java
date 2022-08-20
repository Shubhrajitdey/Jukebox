package com.crio.jukebox.entites;

import java.util.ArrayList;
import java.util.List;

public class Album extends BaseEntity{
    private final String name;
    private final List<Song> songList;
    private final ArtistGroup ownerArtistGroup;
    private final List<Artist> ownerArtists;
    public Album(String id,String name, List<Song> songList, List<Artist> ownerArtists) {
        this.id = id;
        this.name = name;
        this.songList = songList;
        this.ownerArtists = ownerArtists;
        this.ownerArtistGroup = new ArtistGroup();
    }
    public Album(String id,String name, List<Song> songList, ArtistGroup ownerArtistGroup) {
        this.id = id;
        this.name = name;
        this.songList = songList;
        this.ownerArtistGroup = ownerArtistGroup;
        this.ownerArtists = new ArrayList<Artist>();
    }
    public String getName() {
        return name;
    }
    public List<Song> getSongList() {
        return songList;
    }
    public ArtistGroup getOwnerArtistGroup() {
        return ownerArtistGroup;
    }
    public List<Artist> getOwnerArtists() {
        return ownerArtists;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((ownerArtistGroup == null) ? 0 : ownerArtistGroup.hashCode());
        result = prime * result + ((ownerArtists == null) ? 0 : ownerArtists.hashCode());
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
        if (ownerArtistGroup == null) {
            if (other.ownerArtistGroup != null)
                return false;
        } else if (!ownerArtistGroup.equals(other.ownerArtistGroup))
            return false;
        if (ownerArtists == null) {
            if (other.ownerArtists != null)
                return false;
        } else if (!ownerArtists.equals(other.ownerArtists))
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
        if(ownerArtistGroup!=null){
            return "Album [id=" + id +"name=" + name + ", owner=" + ownerArtistGroup + ", songList=" + songList + "]";
        }
        return "Album [id=" + id +"name=" + name + ", owner="
                + ownerArtists + ", songList=" + songList + "]";
    } 

    
    
}
