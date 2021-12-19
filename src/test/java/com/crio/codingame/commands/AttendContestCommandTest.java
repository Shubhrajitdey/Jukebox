package com.crio.codingame.commands;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.entities.RegisterationStatus;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidOperationException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.services.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AttendContestCommandTest")
@ExtendWith(MockitoExtension.class)
public class AttendContestCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @Mock
    UserService userServiceMock;

    @InjectMocks
    AttendContestCommand attendContestCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of AttendContestCommand Should Print Error Message To Console If Contest Not Found")
    public void execute_ShouldPrintErrorMessage_GivenContestNotFound() {
        //Arrange
        String contestId = "1";
        String expectedOutput = "Cannot Attend Contest. Contest for given id:"+contestId+" not found!";
        doThrow(new ContestNotFoundException(expectedOutput)).when(userServiceMock).attendContest(contestId,"Joey");

        //Act
        attendContestCommand.execute(List.of("ATTEND-CONTEST",contestId,"Joey"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).attendContest(contestId,"Joey");
    }

    @Test
    @DisplayName("execute method of AttendContestCommand Should Print Error Message To Console If User Not Found")
    public void execute_ShouldPrintErrorMessage_GivenUserNotFound() {
        //Arrange
        String userName = "Joey";
        String contestId = "1";
        String expectedOutput = "Cannot Attend Contest. User for given name:"+ userName+" not found!";
        doThrow(new UserNotFoundException(expectedOutput)).when(userServiceMock).attendContest(contestId,userName);

        //Act
        attendContestCommand.execute(List.of("ATTEND-CONTEST",contestId,userName));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).attendContest(contestId,userName);
    }

    @Test
    @DisplayName("execute method of AttendContestCommand Should Print Error Message To Console If Contest is in progress")
    public void execute_ShouldPrintErrorMessage_GivenContestInProgress() {
        //Arrange
        String contestId = "1";
        String expectedOutput = "Cannot Attend Contest. Contest for given id:"+contestId+" is in progress!";
        doThrow(new InvalidOperationException(expectedOutput)).when(userServiceMock).attendContest(contestId,"Joey");

        //Act
        attendContestCommand.execute(List.of("ATTEND-CONTEST",contestId,"Joey"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).attendContest(contestId,"Joey");
    }

    @Test
    @DisplayName("execute method of AttendContestCommand Should Print Error Message To Console If Contest is ended")
    public void execute_ShouldPrintErrorMessage_GivenContestEnded() {
        //Arrange
        String contestId = "1";
        String expectedOutput = "Cannot Attend Contest. Contest for given id:"+contestId+" is ended!";
        doThrow(new InvalidOperationException(expectedOutput)).when(userServiceMock).attendContest(contestId,"Joey");

        //Act
        attendContestCommand.execute(List.of("ATTEND-CONTEST",contestId,"Joey"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).attendContest(contestId,"Joey");
    }

    @Test
    @DisplayName("execute method of AttendContestCommand Should Print Error Message To Console If User Already Registered for Contest")
    public void execute_ShouldPrintErrorMessage_UserAlreadyRegisteredGivenContest() {
        //Arrange
        String contestId = "1";
        String expectedOutput = "Cannot Attend Contest. User for given contest id:"+contestId+" is already registered!";
        doThrow(new InvalidOperationException(expectedOutput)).when(userServiceMock).attendContest(contestId,"Joey");

        //Act
        attendContestCommand.execute(List.of("ATTEND-CONTEST",contestId,"Joey"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).attendContest(contestId,"Joey");
    }

    @Test
    @DisplayName("execute method of AttendContestCommand Should Print User Registration DTO")
    public void execute_ShouldPrintUserRegisteration() {
        //Arrange
        String userName = "Joey";
        String contestId = "1";
        String expectedOutput = "RegisterContestDto [contestName=Contest#1, registerationStatus=REGISTERED, userName=Joey]";
        when(userServiceMock.attendContest(contestId, userName)).thenReturn(new UserRegistrationDto("Contest#1", userName, RegisterationStatus.REGISTERED));

        //Act
        attendContestCommand.execute(List.of("ATTEND-CONTEST",contestId,userName));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).attendContest(contestId,userName);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
 
}
