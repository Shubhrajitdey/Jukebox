package com.crio.jukebox.exceptions;

public class SongNotFoundException extends RuntimeException{
    public SongNotFoundException() {
        super();
    }
    public SongNotFoundException(String msg) {
        super(msg);
    }
}
