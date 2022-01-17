package com.crio.codingame.entities;

import java.util.ArrayList;
import java.util.List;

import com.crio.codingame.exceptions.InvalidContestException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ContestTest")
public class ContestTest {


   // TODO: WARNING!!!
   //  DO NOT MODIFY ANY FILES IN THE TESTS/ ASSESSMENTS UNLESS ASKED TO.
   //  Any modifications in this file may result in Assessment failure!



   @Test
   @DisplayName("#1 Contest should throw InvalidContestException if any Question Level in the List is not equal to Contest Level")
   public void contest_ShouldThrowInvalidContestException_GivenInvalidQuestionList(){
       //Arrange
       String name = "Crio.Do PhonePe TechScholars Assessment #1";
       List<Question> questions =  new ArrayList<Question>(){
           {
           add(new Question("Question1",Level.LOW,10));
           add(new Question("Question2",Level.LOW,20));
           add(new Question("Question3",Level.HIGH,30));
           }
       };
       Level level = Level.LOW;
       User creator = new User("Yakshit",0);
       ContestStatus contestStatus = ContestStatus.IN_PROGRESS;

       //Act and Assert
       Assertions.assertThrows(InvalidContestException.class,() -> new Contest(name, questions, level, creator, contestStatus));
   }


    @Test
    @DisplayName("endContest method Should End Contest")
    public void endContest_ShouldEndContest(){
        //Arrange
        String id = "1";
        String name = "Crio.Do PhonePe TechScholars Assessment #1";
        List<Question> questions =  new ArrayList<Question>(){
            {
            add(new Question("1", "Question1",Level.LOW,10));
            add(new Question("1", "Question2",Level.LOW,20));
            add(new Question("1", "Question3",Level.LOW,30));
            }
        };
        Level level = Level.LOW;
        User creator = new User("1","Yakshit",0);
        ContestStatus contestStatus = ContestStatus.IN_PROGRESS;
        Contest contest = new Contest(id, name, questions, level, creator, contestStatus);

        //Act
        contest.endContest();

        //Act and Assert
        Assertions.assertEquals(contest.getContestStatus(),ContestStatus.ENDED);
    }


}

