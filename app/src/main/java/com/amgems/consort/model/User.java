package com.amgems.consort.model;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/8/14.
 */
public class User {
    private String user;
    private int id;
    private int session;

    public User(String user) {
        this.user = user;
    }

    public User(String user, int id, int session) {
        this.user = user;
        this.id = id;
        this.session = session;
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

    public void setSession(int session) {
        this.session = session;
    }

    public int getSession() {
        return session;
    }
}
