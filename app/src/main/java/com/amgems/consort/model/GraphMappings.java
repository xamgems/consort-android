package com.amgems.consort.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/8/14.
 */
public class GraphMappings {
    @Expose
    @SerializedName("Graph")
    public Graph graph;

    @Expose
    @SerializedName("Mappings")
    public Map<Integer, String> mappings;
}
