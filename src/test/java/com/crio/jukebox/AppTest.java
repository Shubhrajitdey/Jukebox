
package com.crio.jukebox;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AppTest")
public class AppTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Integration Test #1")
    void runTest1(){

        //Arrange
        List<String> arguments= new ArrayList<>(List.of("INPUT_FILE=jukebox-input.txt"));

		String expectedOutput = "Songs Loaded successfully\n"+
        "1 Kiran\n"+
        "Playlist ID - 1\n"+
        "Playlist ID - 2\n"+
        "Delete Successful\n"+
        "Current Song Playing\n"+
        "Song - South of the Border\n"+
        "Album - No.6 Collaborations Project\n"+
        "Artists - Ed Sheeran,Cardi.B,Camilla Cabello\n"+
        "Playlist ID - 1\n"+
        "Playlist Name - MY_PLAYLIST_1\n"+
        "Song IDs - 1 4 5 6 7\n"+
        "Playlist ID - 1\n"+
        "Playlist Name - MY_PLAYLIST_1\n"+
        "Song IDs - 1 4 5 6\n"+
        "Current Song Playing\n"+
        "Song - Cross Me\n"+
        "Album - No.6 Collaborations Project\n"+
        "Artists - Ed Sheeran,Chance The Rapper,PnB Rock\n"+
        "Current Song Playing\n"+
        "Song - Give Life Back To Music\n"+
        "Album - Random Access Memories\n"+
        "Artists - Daft Punk,Nile Rodgers\n"+
        "Current Song Playing\n"+
        "Song - South of the Border\n"+
        "Album - No.6 Collaborations Project\n"+
        "Artists - Ed Sheeran,Cardi.B,Camilla Cabello\n"+
        "Current Song Playing\n"+
        "Song - Give Life Back To Music\n"+
        "Album - Random Access Memories\n"+
        "Artists - Daft Punk,Nile Rodgers\n"+
        "Current Song Playing\n"+
        "Song - Cross Me\n"+
        "Album - No.6 Collaborations Project\n"+
        "Artists - Ed Sheeran,Chance The Rapper,PnB Rock\n"+
        "Given song id is not a part of the active playlist";
        //Act
        App.run(arguments);

        //Assert
        Assertions.assertEquals(expectedOutput,outputStreamCaptor.toString().trim());
 
	}

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

}
