package com.amgems.consort.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/8/14.
 */
public class User {

    @Expose
    @SerializedName("userName")
    private String userName;

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("sessionId")
    private int sessionId;
}
