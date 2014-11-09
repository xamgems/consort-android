package com.amgems.consort.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/8/14.
 */
public class Graph implements Iterable<Node> {
    @Expose
    @SerializedName("Nodes")
    private Set<Node> nodes;

    private Map<String, Node> stringNodeMap;

    public Graph() {
    }

    public Graph(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public void initialize() {
        stringNodeMap = new HashMap<String, Node>();
        for (Node n : nodes) {
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

    public Node fromString(String s) {
        return stringNodeMap.get(s);
    }

    public int size() {
        return this.nodes.size();
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }


}
