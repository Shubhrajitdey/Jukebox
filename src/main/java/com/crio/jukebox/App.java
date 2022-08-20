package com.crio.jukebox;

import com.crio.jukebox.repositories.AlbumRepository;
import com.crio.jukebox.repositories.IAlbumRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.SongRepository;
import com.crio.jukebox.services.ISongService;
import com.crio.jukebox.services.SongService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class App {
    // To run the application  ./gradlew run --args="INPUT_FILE=jukebox-input.txt"
	public static void main(String[] args) {
		List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
        String expectedSequence = "INPUT-FILE";
        String actualSequence = commandLineArgs.stream()
                .map(a -> a.split("=")[0])
                .collect(Collectors.joining("$"));
        if(expectedSequence.equals(actualSequence)){
            run(commandLineArgs);
        }

        // To Be Delted
        ISongRepository iSongRepository=new SongRepository();
        IAlbumRepository iAlbumRepository=new AlbumRepository();
        ISongService iSongService=new SongService(iSongRepository,iAlbumRepository);
        iSongService.loadSong("songs.txt");
        System.out.println(iSongRepository.findAll());
	}

    public static void run(List<String> commandLineArgs) {
    // Complete the final logic to run the complete program.

    }
}
