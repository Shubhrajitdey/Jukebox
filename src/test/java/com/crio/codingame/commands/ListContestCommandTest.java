package com.crio.codingame.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
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

@DisplayName("ListContestCommandTest")
@ExtendWith(MockitoExtension.class)
public class ListContestCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @Mock
    IContestService contestServiceMock;

    @InjectMocks
    ListContestCommand listContestCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of ListContestCommand Should List of Contests for Given Level To Console")
    public void execute_ShouldPrintContestList_GivenLevel() {
        //Arrange
        List<Question> questionList1 = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.HIGH, 100));
                add(new Question("2", "title2", Level.HIGH, 90));
                add(new Question("3", "title3", Level.HIGH, 80));
            }
        };
        User contestCreator1= new User("1","creator1",0);
        Contest contest1 = new Contest("1","contestName1",questionList1,Level.HIGH, contestCreator1,ContestStatus.NOT_STARTED);
        List<Question> questionList2 = new ArrayList<Question>(){
            {
                add(new Question("4", "title4", Level.HIGH, 1100));
                add(new Question("5", "title5", Level.HIGH, 900));
                add(new Question("6", "title6", Level.HIGH, 800));
            }
        };
        User contestCreator2 = new User("2","creator2",0);
        Contest contest2 = new Contest("1","contestName2",questionList2,Level.HIGH, contestCreator2,ContestStatus.NOT_STARTED);        
        List<Contest> contestList = new ArrayList<Contest>(){
            {
                add(contest1);
                add(contest2);
            }
        };
        String expectedOutput = "[Contest [id=1, name=contestName1, level=HIGH, creator=creator1, contestStatus=NOT_STARTED, questions=[Question [id=1, level=HIGH, score=100, title=title1], Question [id=2, level=HIGH, score=90, title=title2], Question [id=3, level=HIGH, score=80, title=title3]]], Contest [id=1, name=contestName2, level=HIGH, creator=creator2, contestStatus=NOT_STARTED, questions=[Question [id=4, level=HIGH, score=1100, title=title4], Question [id=5, level=HIGH, score=900, title=title5], Question [id=6, level=HIGH, score=800, title=title6]]]]";
        
        when(contestServiceMock.getAllContestLevelWise(any(Level.class))).thenReturn(contestList);

        //Act
        listContestCommand.execute(List.of("LIST-CONTEST","MEDIUM"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock,times(1)).getAllContestLevelWise(any(Level.class));
    }

    @Test
    @DisplayName("execute method of ListContestCommand Should List of Contests To Console")
    public void execute_ShouldPrintContestList() {
        //Arrange
        List<Question> questionList1 = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.HIGH, 100));
                add(new Question("2", "title2", Level.HIGH, 90));
                add(new Question("3", "title3", Level.HIGH, 80));
            }
        };
        User contestCreator1= new User("1","creator1",0);
        Contest contest1 = new Contest("1","contestName1",questionList1,Level.HIGH, contestCreator1,ContestStatus.NOT_STARTED);
        List<Question> questionList2 = new ArrayList<Question>(){
            {
                add(new Question("4", "title4", Level.LOW, 1100));
                add(new Question("5", "title5", Level.LOW, 900));
                add(new Question("6", "title6", Level.LOW, 800));
            }
        };
        User contestCreator2 = new User("2","creator2",0);
        Contest contest2 = new Contest("1","contestName2",questionList2,Level.LOW, contestCreator2,ContestStatus.NOT_STARTED);        
        List<Contest> contestList = new ArrayList<Contest>(){
            {
                add(contest1);
                add(contest2);
            }
        };
        String expectedOutput = "[Contest [id=1, name=contestName1, level=HIGH, creator=creator1, contestStatus=NOT_STARTED, questions=[Question [id=1, level=HIGH, score=100, title=title1], Question [id=2, level=HIGH, score=90, title=title2], Question [id=3, level=HIGH, score=80, title=title3]]], Contest [id=1, name=contestName2, level=LOW, creator=creator2, contestStatus=NOT_STARTED, questions=[Question [id=4, level=LOW, score=1100, title=title4], Question [id=5, level=LOW, score=900, title=title5], Question [id=6, level=LOW, score=800, title=title6]]]]";

        when(contestServiceMock.getAllContestLevelWise(isNull())).thenReturn(contestList);

        //Act
        listContestCommand.execute(List.of("LIST-CONTEST"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock,times(1)).getAllContestLevelWise(isNull());
    }



    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
 
}
