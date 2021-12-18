package com.crio.codingame.commands;

import static org.mockito.ArgumentMatchers.anyList;

import java.util.ArrayList;

import com.crio.codingame.exceptions.NoSuchCommandException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@DisplayName("CommandInvokerTest")
@ExtendWith(MockitoExtension.class)
public class CommandInvokerTest {
    private CommandInvoker commandInvoker;

    @Mock
    CreateQuestionCommand createQuestionCommand;

    @Mock
    ListQuestionCommand listQuestionCommand;

    @Mock
    CreateUserCommand createUserCommand;

    @Mock
    CreateContestCommand createContestCommand;

    @Mock
    ListContestCommand listContestCommand;

    @Mock
    AttendContestCommand attendContestCommand;

    @Mock
    WithdrawContestCommand withdrawContestCommand;

    @Mock
    LeaderBoardCommand leaderBoardCommand;

    @Mock
    RunContestCommand runContestCommand;

    @BeforeEach
    void setup(){
        commandInvoker = new CommandInvoker();
        commandInvoker.register("CREATE_USER",createUserCommand);
        commandInvoker.register("CREATE_QUESTION",createQuestionCommand);
        commandInvoker.register("LIST_QUESTION",listQuestionCommand);
        commandInvoker.register("CREATE_CONTEST",createContestCommand);
        commandInvoker.register("LIST_CONTEST",listContestCommand);
        commandInvoker.register("ATTEND_CONTEST",attendContestCommand);
        commandInvoker.register("RUN_CONTEST",runContestCommand);
        commandInvoker.register("LEADERBOARD",leaderBoardCommand);
        commandInvoker.register("WITHDRAW_CONTEST",withdrawContestCommand);
    }

    @Test
    @DisplayName("executeCommand method Should Execute Command Given CommandName and List of tokens")
    public void executeCommand_GivenNameAndTokens_ShouldExecuteCommand() {
        commandInvoker.executeCommand("CREATE_USER",anyList());
        commandInvoker.executeCommand("CREATE_QUESTION",anyList());
        commandInvoker.executeCommand("LIST_QUESTION",anyList());
        commandInvoker.executeCommand("CREATE_CONTEST",anyList());
        commandInvoker.executeCommand("LIST_CONTEST",anyList());
        commandInvoker.executeCommand("ATTEND_CONTEST",anyList());
        commandInvoker.executeCommand("RUN_CONTEST",anyList());
        commandInvoker.executeCommand("LEADERBOARD",anyList());
        commandInvoker.executeCommand("WITHDRAW_CONTEST",anyList());
    }

    @Test
    @DisplayName("executeCommand method Should Throw Exception Given Incorrect CommandName and List of tokens")
    public void executeCommand_GivenIncorrectNameAndTokens_ShouldThrowException() {
        //Act and Assert
        Assertions.assertThrows(NoSuchCommandException.class,() -> commandInvoker.executeCommand("RANDOM-COMMAND",new ArrayList<String>()));

    }


}
