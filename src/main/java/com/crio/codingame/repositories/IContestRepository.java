package com.crio.codingame.repositories;

import java.util.List;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;

public interface IContestRepository extends CRUDRepository<Contest,String> {
    public List<Contest> findAllContestLevelWise(Level level);
}
