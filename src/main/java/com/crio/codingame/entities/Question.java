
package com.crio.codingame.entities;

public class Question extends BaseEntity {
    private final String title;
    private final Level level;
    private final Integer score;


    public Question(String title, Level level, Integer score) {
        this.title = title;
        this.level = level;
        this.score = score;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Question other = (Question) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public String getTitle() {
        return title;
    }


    public Level getLevel() {
        return level;
    }


    public Integer getScore() {
        return score;
    }


    @Override
    public String toString() {
        return "Question [id=" + id + ", level=" + level + ", score=" + score + ", title=" + title + "]";
    }
    
}

