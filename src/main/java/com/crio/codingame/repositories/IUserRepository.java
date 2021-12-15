package com.crio.codingame.repositories;

import java.util.Optional;

import com.crio.codingame.entities.User;

public interface IUserRepository extends CRUDRepository<User,String> {
    public Optional<User> findByName(String name); 
}
