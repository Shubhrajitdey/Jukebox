package com.crio.codingame.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidOperationException;
import com.crio.codingame.repositories.ContestRepository;
import com.crio.codingame.repositories.QuestionRepository;
import com.crio.codingame.repositories.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ContestServiceTest")
@ExtendWith(MockitoExtension.class)
public class ContestServiceTest {


    // TODO: WARNING!!!
    //  DO NOT MODIFY ANY FILES IN THE TESTS/ ASSESSMENTS UNLESS ASKED TO.
    //  Any modifications in this file may result in Assessment failure!

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private ContestRepository contestRepositoryMock;

    @Mock
    private QuestionRepository questionRepositoryMock;

    @InjectMocks
    private ContestService contestService;

    /*
    @Test
    @DisplayName("create method should create Contest ")
    public void create_ShouldReturnContest(){
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator = new User("1","creator",0);
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.of(contestCreator));
        when(questionRepositoryMock.findAllQuestionLevelWise(anyString())).thenReturn(questionList);

        //Act
        User actualUser = userService.create("Yakshit");

        //Assert
        Assertions.assertEquals(expectedUser,actualUser);
        verify(userRepositoryMock,times(1)).save(any(User.class));
    }
*/
    @Test
    @DisplayName("getAllContestLevelWise method should return List of Contest if input is null")
    public void getAllContestLevelWise_ShouldReturnAllContestList(){
        //Arrange
        List<Question> questionList1 = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator1= new User("1","creator1",0);
        Contest contest1 = new Contest("1","contestName1",questionList1,Level.LOW, contestCreator1,ContestStatus.NOT_STARTED);
        List<Question> questionList2 = new ArrayList<Question>(){
            {
                add(new Question("4", "title4", Level.HIGH, 1100));
                add(new Question("5", "title5", Level.HIGH, 900));
                add(new Question("6", "title6", Level.HIGH, 800));
            }
        };
        User contestCreator2 = new User("2","creator2",0);
        Contest contest2 = new Contest("1","contestName2",questionList2,Level.HIGH, contestCreator2,ContestStatus.NOT_STARTED);        
        List<Contest> expectedContestList = new ArrayList<Contest>(){
            {
                add(contest1);
                add(contest2);
            }
        };
        when(contestRepositoryMock.findAll()).thenReturn(expectedContestList);

        //Act
        List<Contest> actualContestList = contestService.getAllContestLevelWise(null);

        //Assert
        Assertions.assertTrue(
            expectedContestList.size() == actualContestList.size() &&
            expectedContestList.containsAll(actualContestList) &&
            actualContestList.containsAll(expectedContestList)
        );
        verify(contestRepositoryMock,times(1)).findAll();
        verify(contestRepositoryMock,times(0)).findAllContestLevelWise(any(Level.class));
    }

    @Test
    @DisplayName("getAllContestLevelWise method should return List of Contest if level is given")
    public void getAllContestLevelWise_ShouldReturnAllContestList_GivenLevel(){
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
        List<Contest> expectedContestList = new ArrayList<Contest>(){
            {
                add(contest1);
                add(contest2);
            }
        };
        when(contestRepositoryMock.findAllContestLevelWise(any(Level.class))).thenReturn(expectedContestList);

        //Act
        List<Contest> actualContestList = contestService.getAllContestLevelWise(Level.HIGH);

        //Assert
        Assertions.assertTrue(
            expectedContestList.size() == actualContestList.size() &&
            expectedContestList.containsAll(actualContestList) &&
            actualContestList.containsAll(expectedContestList)
        );
        verify(contestRepositoryMock,times(1)).findAllContestLevelWise(any(Level.class));
        verify(contestRepositoryMock,times(0)).findAll();
        
    }

    @Test
    @DisplayName("runContest method Should Throw NoContestFoundException If No Contest Found")
    public void runContest_ShouldThrowNoContestFoundException(){
        //Arrange
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        //Act and Assert
        Assertions.assertThrows(ContestNotFoundException.class,()-> contestService.runContest("1","contestCreator1"));
        verify(contestRepositoryMock,times(1)).findById(anyString());
    }


    @Test
    @DisplayName("runContest method Should Throw InvalidOperationException If Contest is in Progress")
    public void runContest_ShouldThrowInvalidOperationException_GivenContestInProgress(){
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator = new User("1","contestCreator1",0);
        Contest contest = new Contest("1","contestName",questionList,Level.LOW, contestCreator,ContestStatus.IN_PROGRESS);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        // Act and Assert
        Assertions.assertThrows(InvalidOperationException.class,()-> contestService.runContest("1","contestCreator1"));
        verify(contestRepositoryMock,times(1)).findById(anyString());
    }

    @Test
    @DisplayName("runContest method Should Throw InvalidOperationException If Contest has ended")
    public void runContest_ShouldThrowInvalidOperationException_GivenContestEnded(){
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator = new User("1","contestCreator1",0);
        Contest contest = new Contest("1","contestName",questionList,Level.LOW, contestCreator,ContestStatus.ENDED);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        // Act and Assert
        Assertions.assertThrows(InvalidOperationException.class,()-> contestService.runContest("1","contestCreator1"));
        verify(contestRepositoryMock,times(1)).findById(anyString());
    }

    @Test
    @DisplayName("runContest method Should Throw InvalidOperationException If Not ran by Contest Creator")
    public void runContest_ShouldThrowInvalidOperationException_GivenWrongContestCreator(){
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator = new User("1","contestCreator1",0);
        Contest contest = new Contest("1","contestName",questionList,Level.LOW, contestCreator,ContestStatus.NOT_STARTED);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        // Act and Assert
        Assertions.assertThrows(InvalidOperationException.class,()-> contestService.runContest("1","contestCreator2"));
        verify(contestRepositoryMock,times(1)).findById(anyString());
    }


    @Test
    @DisplayName("runContest method")
    public void testRunContest(){
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator = new User("1","contestCreator1",0);
        User user1 = new User("2","user1",0);
        User user2 = new User("3","user2",0);
        Contest contest = new Contest("1","contestName",questionList,Level.LOW, contestCreator,ContestStatus.NOT_STARTED);
        Contest contestEnded = new Contest("1","contestName",questionList,Level.LOW, contestCreator,ContestStatus.ENDED);
        contestCreator.addContest(contest);
        user1.addContest(contest);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        when(userRepositoryMock.findAll()).thenReturn(List.of(contestCreator,user1,user2));
        when(contestRepositoryMock.save(any(Contest.class))).thenReturn(contestEnded);

        // Act
        
        contestService.runContest("1","contestCreator1");

        //Assert
        verify(contestRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findAll();
        verify(contestRepositoryMock,times(1)).save(any(Contest.class));
        verify(userRepositoryMock,times(2)).save(any(User.class));
    }
}
