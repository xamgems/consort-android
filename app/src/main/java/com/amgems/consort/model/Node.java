package com.amgems.consort.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/8/14.
 */

public class Node {
    @Expose
    @SerializedName("data")
    private String data;

    @Expose
    @SerializedName("id")
    private int id;

    private boolean visible;

    public Node(String data) {
        this.data = data;
    }

    public Node(String data, int id) {
        this.data = data;
        this.id = id;
    }

    public String getData() {
        return this.data;
    }

    public int getDataLength() {
        return getData().length();
    }

    public int getId() {
        return this.id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean v) {
        this.visible = v;
    }

}
