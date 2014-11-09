package com.amgems.consort.model;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/8/14.
 */
public class User {
    private String user;
    private int id;

    public User(String user) {
        this.user = user;
    }

    public User(String user, int id) {
        this.user = user;
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
