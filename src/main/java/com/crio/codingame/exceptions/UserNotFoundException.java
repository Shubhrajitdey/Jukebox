package com.crio.codingame.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException()
 {
  super();
 }
 public UserNotFoundException(String msg)
 {
  super(msg);
 }
}
