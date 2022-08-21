package com.crio.jukebox;

import com.crio.jukebox.entites.SongPlayingOrder;
import com.crio.jukebox.repositories.*;
import com.crio.jukebox.services.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

//        // To Be Delted
//        ISongRepository iSongRepository=new SongRepository();
//        IAlbumRepository iAlbumRepository=new AlbumRepository();
//        ISongService iSongService=new SongService(iSongRepository,iAlbumRepository);
//        iSongService.loadSong("songs.txt");
////      Testing Adding Songs
//
//        IUserPlayListRepository userPlayListRepository=new UserPlayListRepository();
//        IUserRepository iUserRepository=new UserRepository(userPlayListRepository);
//        IuserService iuserService=new UserService(iUserRepository);
//        IUserPlayListService iUserPlayListService=new UserPlayListService(iUserRepository,iSongRepository,userPlayListRepository);
//
//        iuserService.create("Sanket");
//        System.out.println(iUserRepository.findAll());
//        iUserPlayListService.createPlayList("1","My Pop Songs",Arrays.asList("1"));
////        System.out.println("--------------------------------------------");
////        System.out.println(userPlayListRepository.findAll());
//        iUserPlayListService.addSongToPlayList("1","1",Arrays.asList("1","7","8","10","12","15"));
////        System.out.println("--------------------------------------------");
////        System.out.println(userPlayListRepository.findAll());
////        iUserPlayListService.deleteSongFromPlayList("1","1",Arrays.asList("1"));
//        System.out.println("--------------------------------------------");
//        System.out.println(userPlayListRepository.findAll());
//        System.out.println("--------------------------------------------");
//        System.out.println(iUserPlayListService.setCurrentPlayList("1","1"));
//        System.out.println("--------------------------------------------");
//        System.out.println("Back 1 -"+iUserPlayListService.playSongByOrder("1", SongPlayingOrder.BACK));
//        System.out.println("--------------------------------------------");
//        System.out.println("Back 2- "+iUserPlayListService.playSongByOrder("1", SongPlayingOrder.BACK));
//        System.out.println("--------------------------------------------");
//        System.out.println("Back 3- "+iUserPlayListService.playSongByOrder("1", SongPlayingOrder.BACK));
//        System.out.println("--------------------------------------------");
//        //System.out.println(iUserPlayListService.playSongByOrder("1", SongPlayingOrder.BACK));
//        System.out.println("NEXT 3- "+iUserPlayListService.playSongByOrder("1", SongPlayingOrder.NEXT));
//        System.out.println("--------------------------------------------");
//        System.out.println("Change Current 10 -"+iUserPlayListService.playSongById("1","10"));
//        System.out.println("--------------------------------------------");
//        System.out.println("Change Current 100 -"+iUserPlayListService.playSongById("1","22"));

	}

    public static void run(List<String> commandLineArgs) {
    // Complete the final logic to run the complete program.

    }
}
