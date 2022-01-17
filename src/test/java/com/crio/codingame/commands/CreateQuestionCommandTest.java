package com.crio.codingame.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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

@DisplayName("CreateQuestionCommandTest")
@ExtendWith(MockitoExtension.class)
public class CreateQuestionCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @Mock
    IQuestionService questionServiceMock;

    @InjectMocks
    CreateQuestionCommand createQuestionCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of CreateQuestionCommand Should Print newly Created Question To Console")
    public void execute_ShouldPrintQuestion() {
        //Arrange
        String expectedOutput = "Question [id=1, level=MEDIUM, score=50, title=Question 1]";
        Question question = new Question("1","Question 1",Level.MEDIUM,50);
        when(questionServiceMock.create(anyString(),any(Level.class),anyInt())).thenReturn(question);

        //Act
        createQuestionCommand.execute(List.of("CREATE-QUESTION","Question 1","MEDIUM","50"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(questionServiceMock,times(1)).create(anyString(),any(Level.class),anyInt());
    }


    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
 
}
