package com.crio.codingame.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;
import com.crio.codingame.entities.RegisterationStatus;
import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidOperationException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.repositories.ContestRepository;
import com.crio.codingame.repositories.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UserServiceTest")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    // TODO: WARNING!!!
    //  DO NOT MODIFY ANY FILES IN THE TESTS/ ASSESSMENTS UNLESS ASKED TO.
    //  Any modifications in this file may result in Assessment failure!


    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private ContestRepository contestRepositoryMock;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("create method should create User")
    public void create_ShouldReturnUser(){
        //Arrange
        User expectedUser = new User("1", "Yakshit", 0);
        when(userRepositoryMock.save(any(User.class))).thenReturn(expectedUser);

        //Act
        User actualUser = userService.create("Yakshit");

        //Assert
        Assertions.assertEquals(expectedUser,actualUser);
        verify(userRepositoryMock,times(1)).save(any(User.class));
    }
    
    @Test
    @DisplayName("getAllUserScoreOrderWise method should return List of Users in Ascending Order")
    public void getAllUserScoreOrderWise_ShouldReturnASCUserList(){
        //Arrange
        List<User> userList = new ArrayList<User>(){
            {
                add(new User("1", "1", 1200));
                add(new User("2", "2", 1100));
                add(new User("3", "3", 1300));
            }
        };
        List<User> expectedUserList = new ArrayList<User>(){
            {
                add(new User("2", "2", 1100));
                add(new User("1", "1", 1200));
                add(new User("3", "3", 1300));
            }
        };
        when(userRepositoryMock.findAll()).thenReturn(userList);
        //Act

        List<User> actualUserList = userService.getAllUserScoreOrderWise(ScoreOrder.ASC);
        //Assert
        Assertions.assertEquals(expectedUserList, actualUserList);
        verify(userRepositoryMock,times(1)).findAll();
    }

    @Test
    @DisplayName("getAllUserScoreOrderWise method should return List of Users in Descending Order")
    public void getAllUserScoreOrderWise_ShouldReturnDESCUserList(){
        //Arrange
        List<User> userList = new ArrayList<User>(){
            {
                add(new User("1", "1", 1200));
                add(new User("2", "2", 1100));
                add(new User("3", "3", 1300));
            }
        };
        List<User> expectedUserList = new ArrayList<User>(){
            {
                add(new User("3", "3", 1300));
                add(new User("1", "1", 1200));
                add(new User("2", "2", 1100));
            }
        };
        when(userRepositoryMock.findAll()).thenReturn(userList);
        //Act

        List<User> actualUserList = userService.getAllUserScoreOrderWise(ScoreOrder.DESC);
        //Assert
        Assertions.assertEquals(expectedUserList, actualUserList);
        verify(userRepositoryMock,times(1)).findAll();
    }

    @Test
    @DisplayName("attendContest method should register user successfully")
    public void attendContest_ShouldRegisterUserSuccessfull(){
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator = new User("1","creator",0);
        Contest contest = new Contest("1","contestName",questionList,Level.LOW, contestCreator,ContestStatus.NOT_STARTED);
        User user = new User("2","userName",0);
        UserRegistrationDto expectedUserRegistrationDto = new UserRegistrationDto("contestName","userName", RegisterationStatus.REGISTERED);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.of(user));
        //Act

        UserRegistrationDto actualUserRegistrationDto = userService.attendContest("1", "userName");

        //Assert
        Assertions.assertEquals(expectedUserRegistrationDto.toString(), actualUserRegistrationDto.toString());
        verify(contestRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findByName(anyString());
        verify(userRepositoryMock,times(1)).save(any(User.class));

      }

    @Test
    @DisplayName("attendContest method should Throw ContestNotFoundException if No Contest Found")
    public void attendContest_ShouldThrowContestNotFoundException(){
        //Arrange
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        //Act and Assert

        Assertions.assertThrows(ContestNotFoundException.class,()-> userService.attendContest("1", "userName"));
        verify(contestRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(0)).findByName(anyString());
        verify(userRepositoryMock,times(0)).save(any(User.class));
    }

    @Test
    @DisplayName("attendContest method should Throw UserNotFoundException if No User Found")
    public void attendContest_ShouldThrowUserNotFoundException(){
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator = new User("1","creator",0);
        Contest contest = new Contest("1","contestName",questionList,Level.LOW, contestCreator,ContestStatus.NOT_STARTED);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.empty());
        //Act and Assert

        Assertions.assertThrows(UserNotFoundException.class,()-> userService.attendContest("1", "userName"));
        verify(contestRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findByName(anyString());
        verify(userRepositoryMock,times(0)).save(any(User.class));
    }
    
    @Test
    @DisplayName("withdrawContest method should register user successfully")
    public void withdrawContest_ShouldRegisterUserSuccessfull(){
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator = new User("1","creator",0);
        Contest contest = new Contest("1","contestName",questionList,Level.LOW, contestCreator,ContestStatus.NOT_STARTED);
        User user = new User("2","userName",0);
        UserRegistrationDto expectedUserRegistrationDto = new UserRegistrationDto("contestName","userName", RegisterationStatus.NOT_REGISTERED);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.of(user));
        //Act

        UserRegistrationDto actualUserRegistrationDto = userService.withdrawContest("1", "userName");

        //Assert
        Assertions.assertEquals(expectedUserRegistrationDto.toString(), actualUserRegistrationDto.toString());
        verify(contestRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findByName(anyString());
        verify(userRepositoryMock,times(1)).save(any(User.class));

      }

    @Test
    @DisplayName("withdrawContest method should Throw ContestNotFoundException if No Contest Found")
    public void withdrawContest_ShouldThrowContestNotFoundException(){
        //Arrange
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        //Act and Assert

        Assertions.assertThrows(ContestNotFoundException.class,()-> userService.withdrawContest("1", "userName"));
        verify(contestRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(0)).findByName(anyString());
        verify(userRepositoryMock,times(0)).save(any(User.class));
    }

    @Test
    @DisplayName("withdrawContest method should Throw UserNotFoundException if No User Found")
    public void withdrawContest_ShouldThrowUserNotFoundException(){
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator = new User("1","creator",0);
        Contest contest = new Contest("1","contestName",questionList,Level.LOW, contestCreator,ContestStatus.NOT_STARTED);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.empty());
        //Act and Assert

        Assertions.assertThrows(UserNotFoundException.class,()-> userService.withdrawContest("1", "userName"));
        verify(contestRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findByName(anyString());
        verify(userRepositoryMock,times(0)).save(any(User.class));
    } 

    @Test
    @DisplayName("withdrawContest method should Throw InvalidOperationException if Contest is in Progress")
    public void withdrawContest_ShouldThrowInvalidOperationException_GivenContestInProgress(){
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator = new User("1","creator",0);
        Contest contest = new Contest("1","contestName",questionList,Level.LOW, contestCreator,ContestStatus.IN_PROGRESS);
        User user = new User("2","userName",0);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.of(user));
        //Act and Assert

        Assertions.assertThrows(InvalidOperationException.class,()-> userService.withdrawContest("1", "userName"));
        verify(contestRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findByName(anyString());
        verify(userRepositoryMock,times(0)).save(any(User.class));
    }

    @Test
    @DisplayName("withdrawContest method should Throw InvalidOperationException if Contest has Ended")
    public void withdrawContest_ShouldThrowInvalidOperationException_GivenContestEnded(){
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator = new User("1","creator",0);
        Contest contest = new Contest("1","contestName",questionList,Level.LOW, contestCreator,ContestStatus.ENDED);
        User user = new User("2","userName",0);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.of(user));
        //Act and Assert

        Assertions.assertThrows(InvalidOperationException.class,()-> userService.withdrawContest("1", "userName"));
        verify(contestRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findByName(anyString());
        verify(userRepositoryMock,times(0)).save(any(User.class));
    }

    @Test
    @DisplayName("withdrawContest method should Throw InvalidOperationException if User is the ContestCreator")
    public void withdrawContest_ShouldThrowInvalidOperationException_GivenContestCreator(){
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        User contestCreator = new User("1","creator",0);
        Contest contest = new Contest("1","contestName",questionList,Level.LOW, contestCreator,ContestStatus.ENDED);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.of(contestCreator));
        //Act and Assert

        Assertions.assertThrows(InvalidOperationException.class,()-> userService.withdrawContest("1", "creator"));
        verify(contestRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findByName(anyString());
        verify(userRepositoryMock,times(0)).save(any(User.class));
    }

    
}
