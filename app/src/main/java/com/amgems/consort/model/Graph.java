package com.amgems.consort.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/8/14.
 */
public class Graph {
    @Expose
    @SerializedName("adj")
    private Map<Node, List<Node>> adjList;

    private Map<String, Node> stringNodeMap;

    public boolean containsString(String s) {
        return true;
    }

    public int size() {
        return this.adjList.size();
    }

}
