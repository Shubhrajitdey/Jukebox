package com.crio.codingame.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.services.IUserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("LeaderBoardCommandTest")
@ExtendWith(MockitoExtension.class)
public class LeaderBoardCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @Mock
    IUserService userServiceMock;

    @InjectMocks
    LeaderBoardCommand leaderboardCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of LeaderBoardCommand Should Print List of Users Given ScoreOrder To Console")
    public void execute_ShouldPrintUserList_GivenScoreOrder() {
        //Arrange
        String expectedOutput = "[User [id=1, contests=[], name=name1, score=30], User [id=3, contests=[], name=name3, score=40], User [id=2, contests=[], name=name2, score=50]]";
        User user1 = new User("1","name1",30);
        User user2 = new User("3","name3",40);
        User user3 = new User("2","name2",50);
        List<User> userList = List.of(user1,user2,user3);
        when(userServiceMock.getAllUserScoreOrderWise(any(ScoreOrder.class))).thenReturn(userList);

        //Act
        leaderboardCommand.execute(List.of("LEADERBOARD","ASC"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).getAllUserScoreOrderWise(any(ScoreOrder.class));
    }


    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
 
}
