package com.crio.codingame.repositories;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;
import com.crio.codingame.entities.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ContestRepositoryTest {
    private ContestRepository contestRepository;

    @BeforeEach
    void setup(){
        final List<Question> questionLow = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW,10));
            }
        };
        final List<Question> questionMedium = new ArrayList<Question>(){
            {
                add(new Question("2", "title2", Level.MEDIUM,20));
            }
        };
        final User user1 = new User("1","user1",0);
        final User user2 = new User("1","user2",0);
        final User user3 = new User("1","user3",0);
        final Map<String,Contest> contestMap = new HashMap<String,Contest>(){
            {
                put("1", new Contest("1","contest1",questionLow,Level.LOW, user1,ContestStatus.NOT_STARTED));
                put("2", new Contest("2","contest2",questionMedium,Level.MEDIUM, user2,ContestStatus.NOT_STARTED));
                put("3", new Contest("3","contest3",questionMedium,Level.MEDIUM, user3,ContestStatus.NOT_STARTED));
            }
        };
        contestRepository = new ContestRepository(contestMap);
    }

    @Test
    @DisplayName("save method should create and return new Contest")
    public void saveContest(){
        //Arrange
        
        final List<Question> questionLow = new ArrayList<Question>(){
            {
                add(new Question("4", "title4", Level.LOW,10));
            }
        };
        final User user4 = new User("4","user4",0);
        Contest contest4 = new Contest("contest4",questionLow,Level.LOW, user4,ContestStatus.NOT_STARTED);
        Contest expectedContest = new Contest("4","contest4",questionLow,Level.LOW, user4,ContestStatus.NOT_STARTED);
        //Act
        Contest actualContest = contestRepository.save(contest4);
        //Assert
        Assertions.assertEquals(expectedContest,actualContest);
    }

    @Test
    @DisplayName("findAll method should return All Contest")
    public void findAllContest(){
        //Arrange
        int expectedCount = 3;
        //Act
        List<Contest> actualContests = contestRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualContests.size());
    }

    @Test
    @DisplayName("findAll method should return Empty List if no Contests Found")
    public void findAllContest_ShouldReturnEmptyList(){
        //Arrange
        int expectedCount = 0;
        ContestRepository emptyContestRepository = new ContestRepository();
        //Act
        List<Contest> actualContests = emptyContestRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualContests.size());
    }

    @Test
    @DisplayName("findById method should return Contest Given Id")
    public void findById_ShouldReturnContest_GivenContestId(){
        //Arrange
        String expectedContestId = "3";
        //Act
        Optional<Contest> actualContest = contestRepository.findById(expectedContestId);
        //Assert
        Assertions.assertEquals(expectedContestId,actualContest.get().getId());
    }

    @Test
    @DisplayName("findById method should return empty if Contest Not Found")
    public void findById_ShouldReturnEmptyIfContestNotFound(){
        //Arrange
        Optional<Contest> expected = Optional.empty();
        //Act
        Optional<Contest> actual = contestRepository.findById("5");
        //Assert
        Assertions.assertEquals(expected,actual);
    }

    @Test
    @DisplayName("findAllContestLevelWise method should return List of Contest Given Level")
    public void findAllContestLevelWise_ShouldReturnContestList_GivenLevel(){
        //Arrange
        final List<Question> questionMedium = new ArrayList<Question>(){
            {
                add(new Question("2", "title2", Level.MEDIUM,20));
            }
        };
        final User user2 = new User("1","user2",0);
        List<Contest> expectedContests = new ArrayList<Contest>(){
            {
                add(new Contest("2","contest2",questionMedium,Level.MEDIUM, user2,ContestStatus.NOT_STARTED));
            }
        };
        //Act
        List<Contest> actualContests = contestRepository.findAllContestLevelWise(Level.MEDIUM);
        //Assert
        Assertions.assertTrue(
            expectedContests.size() == actualContests.size() &&
            expectedContests.containsAll(actualContests) &&
            actualContests.containsAll(expectedContests)
        );
    }

    @Test
    @DisplayName("findAllLevelWise method should return Empty List If No Contest Found Given Level")
    public void findAllContestLevelWise_ShouldReturnEmptyList_GivenLevel(){
        //Arrange
        int expectedCount = 0;
        //Act
        List<Contest> actualContests = contestRepository.findAllContestLevelWise(Level.HIGH);
        //Assert
        Assertions.assertEquals(expectedCount,actualContests.size());
    }
    
}
