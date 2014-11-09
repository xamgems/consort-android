package com.amgems.consort.model;

public class GameState {

    private final GraphMappings mGraphMappings;
    public int mShiftX, mShiftY;

    public GameState(GraphMappings mappings) {
        mGraphMappings = mappings;
    }

    public GraphMappings getMapping() {
        return mGraphMappings;
    }

}
