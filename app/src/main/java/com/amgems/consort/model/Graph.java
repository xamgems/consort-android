package com.amgems.consort.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.Set;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/8/14.
 */
public class Graph {
    @Expose
    @SerializedName("adj")
    private Map<Node, Set<Node>> adjList;

    private Map<String, Node> stringNodeMap;

    public Graph() {
        for (Node n : adjList.keySet()) {
           stringNodeMap.put(n.getData(), n);
        }
    }

    public Node check(String s) {
       if (containsString(s)) {
           Node n = stringNodeMap.get(s);
           n.setVisible(true);
           return n;
       } else {
           return null;
       }
    }

    public boolean containsString(String s) {
        return stringNodeMap.keySet().contains(s);
    }

    public int size() {
        return this.adjList.size();
    }

}
