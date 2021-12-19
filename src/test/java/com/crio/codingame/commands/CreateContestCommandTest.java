package com.crio.codingame.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.QuestionNotFoundException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.services.IContestService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CreateContestCommandTest")
@ExtendWith(MockitoExtension.class)
public class CreateContestCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @Mock
    IContestService contestServiceMock;

    @InjectMocks
    CreateContestCommand createContestCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of CreateContestCommand Should Print newly Created Contest To Console Given NumQuestions")
    public void execute_ShouldPrintContest_GivenNumQuestions() {
        //Arrange
        String expectedOutput = "Contest [id=4, name=contest4, level=LOW, creator=user4, contestStatus=NOT_STARTED, questions=[Question [id=4, level=LOW, score=10, title=title4], Question [id=5, level=LOW, score=10, title=title5]]]";
        final List<Question> questionLow = new ArrayList<Question>(){
            {
                add(new Question("4", "title4", Level.LOW,10));
                add(new Question("5", "title5", Level.LOW,10));
            }
        };
        final User user = new User("4","user4",0);
        Contest contest = new Contest("4","contest4",questionLow,Level.LOW, user,ContestStatus.NOT_STARTED);
        when(contestServiceMock.create(anyString(),any(Level.class),anyString(),anyInt())).thenReturn(contest);

        //Act
        createContestCommand.execute(List.of("CREATE-CONTEST","name","LOW","creator","2"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock,times(1)).create(anyString(),any(Level.class),anyString(),anyInt());
    }

    @Test
    @DisplayName("execute method of CreateContestCommand Should Print newly Created Contest To Console")
    public void execute_ShouldPrintContest() {
        //Arrange
        String expectedOutput = "Contest [id=4, name=contest4, level=LOW, creator=user4, contestStatus=NOT_STARTED, questions=[Question [id=4, level=LOW, score=10, title=title4], Question [id=5, level=LOW, score=10, title=title5]]]";
        final List<Question> questionLow = new ArrayList<Question>(){
            {
                add(new Question("4", "title4", Level.LOW,10));
                add(new Question("5", "title5", Level.LOW,10));
            }
        };
        final User user = new User("4","user4",0);
        Contest contest = new Contest("4","contest4",questionLow,Level.LOW, user,ContestStatus.NOT_STARTED);
        when(contestServiceMock.create(anyString(),any(Level.class),anyString(),isNull())).thenReturn(contest);

        //Act
        createContestCommand.execute(List.of("CREATE-CONTEST","name","LOW","creator"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock,times(1)).create(anyString(),any(Level.class),anyString(),isNull());
    }

    @Test
    @DisplayName("execute method of CreateContestCommand Should Print Error Message To Console If ContestCreator Not Found")
    public void execute_ShouldPrintErrorMessage_GivenContestCreatorNotFound() {
        //Arrange
        String expectedOutput = "Contest Creator for given name: creator not found!";
        doThrow(new UserNotFoundException(expectedOutput)).when(contestServiceMock).create(anyString(),any(Level.class),anyString(),isNull());

        //Act
        createContestCommand.execute(List.of("CREATE-CONTEST","name","LOW","creator"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock,times(1)).create(anyString(),any(Level.class),anyString(),isNull());
    }

    @Test
    @DisplayName("execute method of CreateContestCommand Should Print Error Message To Console If Not Enough Questions Found")
    public void execute_ShouldPrintErrorMessage_GivenContestCreatorNotEnoughQuestionsFound() {
        //Arrange
        String expectedOutput = "Request cannot be processed as enough number of questions can not found. Please try again later!";
        doThrow(new QuestionNotFoundException(expectedOutput)).when(contestServiceMock).create(anyString(),any(Level.class),anyString(),isNull());

        //Act
        createContestCommand.execute(List.of("CREATE-CONTEST","name","LOW","creator"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock,times(1)).create(anyString(),any(Level.class),anyString(),isNull());
    }



    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
 
}
