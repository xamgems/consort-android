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

    @Expose
    @SerializedName("x")
    private int x;

    @Expose
    @SerializedName("x")
    private int y;

    private boolean visible;

    public Node() {}

    public Node(String data) {
        this(data, 0, 0, 0);
    }

    public Node(String data, int id) {
        this(data, id, 0, 0);
    }

    public Node(String data, int id, int x, int y) {
        this.data = data;
        this.id = id;
        this.x = x;
        this.y = y;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Node: { id: " + id + " data: " + data + " vis: " + visible +
                "\n\tpos: (" + x + ", " + y + ") }";
    }

    @Override
    public int hashCode() {
        return (data + this.id).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Node && this.id == ((Node)o).id;
    }
}
