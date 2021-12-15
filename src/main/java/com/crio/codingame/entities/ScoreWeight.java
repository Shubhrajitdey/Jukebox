package com.crio.codingame.entities;

public enum ScoreWeight {
    LOW(50),
    MEDIUM(30),
    HIGH(0);

    private int weight;

    ScoreWeight(int weight){
        this.weight = weight;
    }

    public int getWeight(){
        return weight;
    }
}
