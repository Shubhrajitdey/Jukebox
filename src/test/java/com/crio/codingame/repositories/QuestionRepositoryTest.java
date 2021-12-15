package com.crio.codingame.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QuestionRepositoryTest {
    private QuestionRepository questionRepository;

    @BeforeEach
    void setup(){
        final Map<String,Question> questionMap = new HashMap<String,Question>(){
            {
                put("1",new Question("1", "question1", Level.LOW, 10));
                put("2",new Question("2", "question2", Level.MEDIUM, 20));
                put("3",new Question("3", "question3", Level.MEDIUM, 10));
            }
        };
        questionRepository = new QuestionRepository(questionMap);
    }

    @Test
    @DisplayName("save method should create and return new Question")
    public void saveQuestion(){
        //Arrange
        final Question question4 = new Question("title4", Level.LOW,10);
        Question expectedQuestion = new Question("4", "title4", Level.LOW,10);
        //Act
        Question actualQuestion = questionRepository.save(question4);
        //Assert
        Assertions.assertEquals(expectedQuestion,actualQuestion);
    }

    @Test
    @DisplayName("findAll method should return All Question")
    public void findAllQuestion(){
        //Arrange
        int expectedCount = 3;
        //Act
        List<Question> actualQuestions = questionRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualQuestions.size());
    }

    @Test
    @DisplayName("findAll method should return Empty List if no Questions Found")
    public void findAllQuestion_ShouldReturnEmptyList(){
        //Arrange
        int expectedCount = 0;
        QuestionRepository emptyQuestionRepository = new QuestionRepository();
        //Act
        List<Question> actualQuestions = emptyQuestionRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualQuestions.size());
    }

    @Test
    @DisplayName("findById method should return Question Given Id")
    public void findById_ShouldReturnQuestion_GivenQuestionId(){
        //Arrange
        String expectedQuestionId = "3";
        //Act
        Optional<Question> actualQuestion = questionRepository.findById(expectedQuestionId);
        //Assert
        Assertions.assertEquals(expectedQuestionId,actualQuestion.get().getId());
    }

    @Test
    @DisplayName("findById method should return empty if Question Not Found")
    public void findById_ShouldReturnEmptyIfQuestionNotFound(){
        //Arrange
        Optional<Question> expected = Optional.empty();
        //Act
        Optional<Question> actual = questionRepository.findById("5");
        //Assert
        Assertions.assertEquals(expected,actual);
    }

    @Test
    @DisplayName("findAllQuestionLevelWise method should return List of Question Given Level")
    public void findAllQuestionLevelWise_ShouldReturnQuestionList_GivenLevel(){
        //Arrange
        final List<Question> expectedQuestions = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW,20));
            }
        };
        //Act
        List<Question> actualQuestions = questionRepository.findAllQuestionLevelWise(Level.LOW);
        //Assert
        Assertions.assertTrue(
            expectedQuestions.size() == actualQuestions.size() &&
            expectedQuestions.containsAll(actualQuestions) &&
            actualQuestions.containsAll(expectedQuestions)
        );
    }

    @Test
    @DisplayName("findAllLevelWise method should return Empty List If No Question Found Given Level")
    public void findAllQuestionLevelWise_ShouldReturnEmptyList_GivenLevel(){
        //Arrange
        int expectedCount = 0;
        //Act
        List<Question> actualQuestions = questionRepository.findAllQuestionLevelWise(Level.HIGH);
        //Assert
        Assertions.assertEquals(expectedCount,actualQuestions.size());
    }
    
}
