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

@DisplayName("WithdrawContestCommandTest")
@ExtendWith(MockitoExtension.class)
public class WithdrawContestCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @Mock
    IUserService userServiceMock;

    @InjectMocks
    WithdrawContestCommand withdrawContestCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of WithdrawContestCommand Should Print Error Message To Console If Contest Not Found")
    public void execute_ShouldPrintErrorMessage_GivenContestNotFound() {
        //Arrange
        String contestId = "1";
        String expectedOutput = "Cannot Withdraw Contest. Contest for given id:"+contestId+" not found!";
        doThrow(new ContestNotFoundException(expectedOutput)).when(userServiceMock).withdrawContest(contestId,"Joey");

        //Act
        withdrawContestCommand.execute(List.of("WITHDRAW-CONTEST",contestId,"Joey"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).withdrawContest(contestId,"Joey");
    }

    @Test
    @DisplayName("execute method of WithdrawContestCommand Should Print Error Message To Console If User Not Found")
    public void execute_ShouldPrintErrorMessage_GivenUserNotFound() {
        //Arrange
        String userName = "Joey";
        String contestId = "1";
        String expectedOutput = "Cannot Withdraw Contest. User for given name:"+ userName+" not found!";
        doThrow(new UserNotFoundException(expectedOutput)).when(userServiceMock).withdrawContest(contestId,userName);

        //Act
        withdrawContestCommand.execute(List.of("WITHDRAW-CONTEST",contestId,userName));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).withdrawContest(contestId,userName);
    }

    @Test
    @DisplayName("execute method of WithdrawContestCommand Should Print Error Message To Console If Contest is in progress")
    public void execute_ShouldPrintErrorMessage_GivenContestInProgress() {
        //Arrange
        String contestId = "1";
        String expectedOutput = "Cannot Withdraw Contest. Contest for given id:"+contestId+" is in progress!";
        doThrow(new InvalidOperationException(expectedOutput)).when(userServiceMock).withdrawContest(contestId,"Joey");

        //Act
        withdrawContestCommand.execute(List.of("WITHDRAW-CONTEST",contestId,"Joey"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).withdrawContest(contestId,"Joey");
    }

    @Test
    @DisplayName("execute method of WithdrawContestCommand Should Print Error Message To Console If Contest is ended")
    public void execute_ShouldPrintErrorMessage_GivenContestEnded() {
        //Arrange
        String contestId = "1";
        String expectedOutput = "Cannot Withdraw Contest. Contest for given id:"+contestId+" is ended!";
        doThrow(new InvalidOperationException(expectedOutput)).when(userServiceMock).withdrawContest(contestId,"Joey");

        //Act
        withdrawContestCommand.execute(List.of("WITHDRAW-CONTEST",contestId,"Joey"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).withdrawContest(contestId,"Joey");
    }

    @Test
    @DisplayName("execute method of WithdrawContestCommand Should Print Error Message To Console If User Not Registered for Contest")
    public void execute_ShouldPrintErrorMessage_UserNotRegisteredGivenContest() {
        //Arrange
        String contestId = "1";
        String expectedOutput = "Cannot Withdraw Contest. User for given contest id:"+contestId+" is not registered!";
        doThrow(new InvalidOperationException(expectedOutput)).when(userServiceMock).withdrawContest(contestId,"Joey");

        //Act
        withdrawContestCommand.execute(List.of("WITHDRAW-CONTEST",contestId,"Joey"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).withdrawContest(contestId,"Joey");
    }

    @Test
    @DisplayName("execute method of WithdrawContestCommand Should Print Error Message To Console If User is Contest Creator")
    public void execute_ShouldPrintErrorMessage_GivenContestCreator() {
        //Arrange
        String userName = "Joey";
        String contestId = "1";
        String expectedOutput = "Cannot Withdraw Contest. Contest Creator:"+userName+ "not allowed to withdraw contest!";
        doThrow(new InvalidOperationException(expectedOutput)).when(userServiceMock).withdrawContest(contestId,userName);

        //Act
        withdrawContestCommand.execute(List.of("WITHDRAW-CONTEST",contestId,userName));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).withdrawContest(contestId,userName);
    }

    @Test
    @DisplayName("execute method of WithdrawContestCommand Should Print User Registration DTO")
    public void execute_ShouldPrintUserRegisteration() {
        //Arrange
        String userName = "Joey";
        String contestId = "1";
        String expectedOutput = "RegisterContestDto [contestName=Contest#1, registerationStatus=NOT_REGISTERED, userName=Joey]";
        when(userServiceMock.withdrawContest(contestId, userName)).thenReturn(new UserRegistrationDto("Contest#1", userName, RegisterationStatus.NOT_REGISTERED));

        //Act
        withdrawContestCommand.execute(List.of("WITHDRAW-CONTEST",contestId,userName));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(userServiceMock,times(1)).withdrawContest(contestId,userName);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
 
}
