package com.backend;

public interface Endpoints {
    String TODOS = "/todos";
    String POSTS = "/posts";
    String COMMENTS = "/comments";
    String ALBUMS = "/albums";
    String PHOTOS = "/photos";
    String USERS = "/users";
    String USER = USERS + "/{id}";
}
