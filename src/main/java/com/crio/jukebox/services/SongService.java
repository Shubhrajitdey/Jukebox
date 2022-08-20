package com.crio.jukebox.services;

import com.crio.jukebox.entites.Album;
import com.crio.jukebox.entites.Song;
import com.crio.jukebox.repositories.IAlbumRepository;
import com.crio.jukebox.repositories.ISongRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongService implements ISongService{
    private final ISongRepository iSongRepository;
    private final IAlbumRepository iAlbumRepository;

    public SongService(ISongRepository iSongRepository, IAlbumRepository iAlbumRepository) {
        this.iSongRepository = iSongRepository;
        this.iAlbumRepository = iAlbumRepository;
    }
    @Override
    public void loadSong(String inputFile) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            Song song;
            Album album;
            while (line != null) {
                List<String> splitByComma = Arrays.asList(line.split(","));
                String songId=splitByComma.get(0);
                String songName=splitByComma.get(1);
                String genre=splitByComma.get(2);
                String albumName=splitByComma.get(3);
                String albumOwner=splitByComma.get(4);
                List<String> listOfAlbumArtist=Arrays.asList(splitByComma.get(5).split("#"));
                song=new Song(songId,songName,genre,listOfAlbumArtist);
                // read next line
                Song savedSong=iSongRepository.save(song);
                album=iAlbumRepository.findAlbumByAlbumName(albumName);
                if(album==null){
                    album=new Album(null,albumName,new ArrayList<Song>(Arrays.asList(savedSong)),albumOwner);
                }else{
                    album.getSongList().add(savedSong);
                }
                Album savedAlbum=iAlbumRepository.save(album);
                line = reader.readLine();

            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
