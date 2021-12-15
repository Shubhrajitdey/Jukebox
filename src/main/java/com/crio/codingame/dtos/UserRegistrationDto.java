package com.crio.codingame.dtos;

import com.crio.codingame.entities.RegisterationStatus;

public class UserRegistrationDto {
    private final String contestName;
    private final String userName;
    private final RegisterationStatus registerationStatus;
    public UserRegistrationDto(String contestName, String userName, RegisterationStatus registerationStatus) {
        this.contestName = contestName;
        this.userName = userName;
        this.registerationStatus = registerationStatus;
    }
    public String getContestName() {
        return contestName;
    }
    public String getUserName() {
        return userName;
    }
    
    public RegisterationStatus getRegisterationStatus() {
        return registerationStatus;
    }

    @Override
    public String toString() {
        return "RegisterContestDto [contestName=" + contestName + ", registerationStatus=" + registerationStatus
                + ", userName=" + userName + "]";
    }

        
}
