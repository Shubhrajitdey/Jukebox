package com.crio.codingame.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.crio.codingame.entities.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    void setup(){
        final Map<String,User> userMap = new HashMap<String,User>(){
            {
                put("1",new User("1", "user1", 10));
                put("2",new User("2", "user2", 20));
                put("3",new User("3", "user3", 10));
            }
        };
        userRepository = new UserRepository(userMap);
    }

    @Test
    @DisplayName("save method should create and return new User")
    public void saveUser(){
        //Arrange
        final User question4 = new User("user4", 30);
        User expectedUser = new User("4", "user4",30);
        //Act
        User actualUser = userRepository.save(question4);
        //Assert
        Assertions.assertEquals(expectedUser,actualUser);
    }

    @Test
    @DisplayName("findAll method should return All User")
    public void findAllUser(){
        //Arrange
        int expectedCount = 3;
        //Act
        List<User> actualUsers = userRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualUsers.size());
    }

    @Test
    @DisplayName("findAll method should return Empty List if no Users Found")
    public void findAllUser_ShouldReturnEmptyList(){
        //Arrange
        int expectedCount = 0;
        UserRepository emptyUserRepository = new UserRepository();
        //Act
        List<User> actualUsers = emptyUserRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualUsers.size());
    }

    @Test
    @DisplayName("findById method should return User Given Id")
    public void findById_ShouldReturnUser_GivenUserId(){
        //Arrange
        String expectedUserId = "3";
        //Act
        Optional<User> actualUser = userRepository.findById(expectedUserId);
        //Assert
        Assertions.assertEquals(expectedUserId,actualUser.get().getId());
    }

    @Test
    @DisplayName("findById method should return empty if User Not Found")
    public void findById_ShouldReturnEmptyIfUserNotFound(){
        //Arrange
        Optional<User> expected = Optional.empty();
        //Act
        Optional<User> actual = userRepository.findById("5");
        //Assert
        Assertions.assertEquals(expected,actual);
    }

    @Test
    @DisplayName("findByName method should return User Given Name")
    public void findByName_ShouldReturnUser_GivenUserId(){
        //Arrange
        String expectedUserName = "user1";
        //Act
        Optional<User> actualUser = userRepository.findByName(expectedUserName);
        //Assert
        Assertions.assertEquals(expectedUserName,actualUser.get().getName());
    }

    @Test
    @DisplayName("findByName method should return empty if User Not Found")
    public void findByName_ShouldReturnEmptyIfUserNotFound(){
        //Arrange
        Optional<User> expected = Optional.empty();
        //Act
        Optional<User> actual = userRepository.findByName("user5");
        //Assert
        Assertions.assertEquals(expected,actual);
    }

}
