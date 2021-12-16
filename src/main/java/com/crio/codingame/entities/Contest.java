
package com.crio.codingame.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.crio.codingame.exceptions.InvalidContestException;

public class Contest extends BaseEntity{
    private final String name;
    private final List<Question> questions;
    private final Level level;
    private final User creator;
    private ContestStatus contestStatus;


    public Contest(String name, List<Question> questions, Level level, User creator,
            ContestStatus contestStatus) {
        this.name = name;
        this.questions = new ArrayList<>();
        validateQuestionList(questions, level);
        this.level = level;
        this.creator = creator;
        this.contestStatus = contestStatus;
    }
    // TODO: CRIO_TASK_MODULE_ENTITIES
    // Complete the validateQuestionList method to verify if all the questions have the same level and are equal to contest level.
    // Throw InValidContestException if the above condition is not true. This will stop the Object Creation.
    //  Note:
    //  1. There can be few unused imports, you will need to fix them to make the build pass.
    //  2. You can use "./gradlew build" to check if your code builds successfully.

    private void validateQuestionList(List<Question> qList, Level contestLevel) throws InvalidContestException {
    }


    
    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions.stream().collect(Collectors.toList());
    }

    public Level getLevel() {
        return level;
    }

    public User getCreator() {
        return creator;
    }

    public ContestStatus getContestStatus() {
        return contestStatus;
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
        Contest other = (Contest) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Contest [id=" + id + ", name=" + name + ", level=" + level + ", creator=" + creator.getName() + ", contestStatus=" + contestStatus + ", questions=" + questions + "]";
    }

}

