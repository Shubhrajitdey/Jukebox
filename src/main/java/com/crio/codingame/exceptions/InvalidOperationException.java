package com.crio.codingame.exceptions;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException()
    {
     super();
    }
    public InvalidOperationException(String msg)
    {
     super(msg);
    }
}
