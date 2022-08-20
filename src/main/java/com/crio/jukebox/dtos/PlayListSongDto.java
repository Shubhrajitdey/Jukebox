package com.crio.jukebox.dtos;

import java.util.List;
import com.crio.jukebox.entites.Song;

public class PlayListSongDto {
    private final String playListId;
    private final String playListName;
    private final List<Song> songs;
    public PlayListSongDto(String playListId, String playListName, List<Song> songs) {
        this.playListId = playListId;
        this.playListName = playListName;
        this.songs = songs;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((playListId == null) ? 0 : playListId.hashCode());
        result = prime * result + ((playListName == null) ? 0 : playListName.hashCode());
        result = prime * result + ((songs == null) ? 0 : songs.hashCode());
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
        PlayListSongDto other = (PlayListSongDto) obj;
        if (playListId == null) {
            if (other.playListId != null)
                return false;
        } else if (!playListId.equals(other.playListId))
            return false;
        if (playListName == null) {
            if (other.playListName != null)
                return false;
        } else if (!playListName.equals(other.playListName))
            return false;
        if (songs == null) {
            if (other.songs != null)
                return false;
        } else if (!songs.equals(other.songs))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "PlayListSongDto [playListId=" + playListId + ", playListName=" + playListName
                + ", songs=" + songs + "]";
    }

    
}
