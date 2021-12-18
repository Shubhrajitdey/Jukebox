package com.crio.codingame.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;
import com.crio.codingame.services.IQuestionService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ListQuestionCommandTest")
@ExtendWith(MockitoExtension.class)
public class ListQuestionCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @Mock
    IQuestionService questionServiceMock;

    @InjectMocks
    ListQuestionCommand listQuestionCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of ListQuestionCommand Should List of Question for Given Level To Console")
    public void execute_ShouldPrintQuestionList_GivenLevel() {
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.LOW, 200));
                add(new Question("3", "title3", Level.LOW, 300));
            }
        };
        String expectedOutput = "[Question [id=1, level=LOW, score=100, title=title1], Question [id=2, level=LOW, score=200, title=title2], Question [id=3, level=LOW, score=300, title=title3]]";
        
        when(questionServiceMock.getAllQuestionLevelWise(any(Level.class))).thenReturn(questionList);

        //Act
        listQuestionCommand.execute(List.of("LIST-QUESTION","MEDIUM"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(questionServiceMock,times(1)).getAllQuestionLevelWise(any(Level.class));
    }

    @Test
    @DisplayName("execute method of ListQuestionCommand Should List of All Question To Console")
    public void execute_ShouldPrintQuestionList() {
        //Arrange
        List<Question> questionList = new ArrayList<Question>(){
            {
                add(new Question("1", "title1", Level.LOW, 100));
                add(new Question("2", "title2", Level.MEDIUM, 200));
                add(new Question("3", "title3", Level.HIGH, 300));
            }
        };
        String expectedOutput = "[Question [id=1, level=LOW, score=100, title=title1], Question [id=2, level=MEDIUM, score=200, title=title2], Question [id=3, level=HIGH, score=300, title=title3]]";
        
        when(questionServiceMock.getAllQuestionLevelWise(null)).thenReturn(questionList);

        //Act
        listQuestionCommand.execute(List.of("LIST-QUESTION"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(questionServiceMock,times(1)).getAllQuestionLevelWise(null);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
 
}
