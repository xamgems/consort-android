package com.amgems.consort.model;

public class GameState {

    private final GraphMappings mGraphMappings;
    private int mShiftX, mShiftY;
    private float prevX, prevY;
    private boolean isShifting;

    public GameState(GraphMappings mappings) {
        mGraphMappings = mappings;
    }

    public GraphMappings getMapping() {
        return mGraphMappings;
    }

    public synchronized void startShiftViewport(float x, float y) {
        prevX = x;
        prevY = y;
        isShifting = true;
    }

    public float getShiftX() {
        return mShiftX;
    }

    public float getShiftY() {
        return mShiftY;
    }

    // Should be coordinates of NEW finger position, not delta
    public synchronized void shiftViewport(float newX, float newY) {
        if (isShifting) {
            mShiftX -= (newX - prevX);
            mShiftY -= (newY - prevY);

            prevX = newX;
            prevY = newY;
        }
    }

    public synchronized void endShiftViewport() {
        isShifting = false;
    }

}
