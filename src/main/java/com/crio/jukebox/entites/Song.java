package com.crio.jukebox.entites;

import java.util.List;


public class Song extends BaseEntity{
    private final String name;

    private final String genre;

    private String albumName;
    private final List<String> artis;
    public Song(String id, String name, String genre, List<String> artis) {
        this.name = name;
        this.genre = genre;
        this.artis = artis;
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }
    public List<String> getArtis() {
        return artis;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((artis == null) ? 0 : artis.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Song other = (Song) obj;
        if (artis == null) {
            if (other.artis != null)
                return false;
        } else if (!artis.equals(other.artis))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    @Override
    public String toString() {
        return "Song [id=" + id +", artis=" + artis + ", name=" + name + "]";
    } 
    
    
}
