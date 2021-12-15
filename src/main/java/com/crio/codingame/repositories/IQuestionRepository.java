package com.crio.codingame.repositories;

import java.util.List;

import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;

public interface IQuestionRepository extends CRUDRepository<Question,String> {
    public List<Question> findAllQuestionLevelWise(Level level);
}
