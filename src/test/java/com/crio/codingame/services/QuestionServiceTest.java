package com.crio.codingame.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;
import com.crio.codingame.repositories.IQuestionRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("QuestionServiceTest")
@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {


    // TODO: WARNING!!!
    //  DO NOT MODIFY ANY FILES IN THE TESTS/ ASSESSMENTS UNLESS ASKED TO.
    //  Any modifications in this file may result in Assessment failure!
    
    @Mock
    private IQuestionRepository qRepositoryMock;

    @InjectMocks
    private QuestionService questionService;

    @Test
    @DisplayName("create method should create Question")
    public void create_ShouldReturnQuestion(){
        //Arrange
        Question expectedQuestion = new Question("1", "title", Level.LOW, 10);
        when(qRepositoryMock.save(any(Question.class))).thenReturn(expectedQuestion);

        //Act
        Question actualQuestion = questionService.create("title",Level.LOW,10);

        //Assert
        Assertions.assertEquals(expectedQuestion,actualQuestion);
        verify(qRepositoryMock,times(1)).save(any(Question.class));
    }
    
    @Test
    @DisplayName("getAllQuestionLevelWise method should return List of Question if input is null")
    public void getAllQuestionLevelWise_ShouldReturnAllQuestionList(){
        //Arrange
        List<Question> expectedQuestionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.MEDIUM, 200));
                add(new Question("3", "title3", Level.HIGH, 300));
            }
        };
        when(qRepositoryMock.findAll()).thenReturn(expectedQuestionList);

        //Act
        List<Question> actualQuestionList = questionService.getAllQuestionLevelWise(null);

        //Assert
        Assertions.assertTrue(
            expectedQuestionList.size() == actualQuestionList.size() &&
            expectedQuestionList.containsAll(actualQuestionList) &&
            actualQuestionList.containsAll(expectedQuestionList)
        );
        verify(qRepositoryMock,times(1)).findAll();
        verify(qRepositoryMock,times(0)).findAllQuestionLevelWise(any(Level.class));
    }

    @Test
    @DisplayName("getAllQuestionLevelWise method should return List of Question if level is given")
    public void getAllQuestionLevelWise_ShouldReturnAllQuestionList_GivenLevel(){
        //Arrange
        List<Question> expectedQuestionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 90));
                add(new Question("3", "title3", Level.LOW, 80));
            }
        };
        when(qRepositoryMock.findAllQuestionLevelWise(any(Level.class))).thenReturn(expectedQuestionList);

        //Act
        List<Question> actualQuestionList = questionService.getAllQuestionLevelWise(Level.LOW);

        //Assert
        Assertions.assertTrue(
            expectedQuestionList.size() == actualQuestionList.size() &&
            expectedQuestionList.containsAll(actualQuestionList) &&
            actualQuestionList.containsAll(expectedQuestionList)
        );
        verify(qRepositoryMock,times(1)).findAllQuestionLevelWise(any(Level.class));
        verify(qRepositoryMock,times(0)).findAll();
    }
}
