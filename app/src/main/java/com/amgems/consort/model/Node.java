package com.amgems.consort.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/8/14.
 */

public class Node {
    @Expose
    @SerializedName("Data")
    private String data;

    @Expose
    @SerializedName("Id")
    private int id;

    @Expose
    @SerializedName("X")
    private float x;

    @Expose
    @SerializedName("Y")
    private float y;

    @Expose
    @SerializedName("Neighbors")
    private Set<Integer> neighbors;

    @Expose
    @SerializedName("Known")
    private boolean visible;

    public Node() {}

    public Node(String data) {
        this(data, -1, -1, -1);
    }

    public Node(String data, int id) {
        this(data, id, -1, -1);
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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getX(float scalingFactor) {
        return x * scalingFactor;
    }

    public float getY(float scalingFactor) {
        return y * scalingFactor;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*
     * Dangerous!
     */
    public Set<Integer> getNeighbors() {
        return this.neighbors;
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
