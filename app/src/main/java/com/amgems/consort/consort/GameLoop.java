package com.amgems.consort.consort;

import android.view.SurfaceHolder;

import com.amgems.consort.model.GameState;

public class GameLoop extends Thread {

    private volatile boolean mIsRunning;

    private final SurfaceHolder mHolder;
    private final Renderer mRenderer;
    private GameState mGameState;

    public GameLoop(SurfaceHolder holder, Renderer renderer) {
        mIsRunning = false;

        mHolder = holder;
        mRenderer = renderer;
    }

    public void setGameState(GameState game) {
        mGameState = game;
    }

    @Override
    public void run() {
        while (mIsRunning) {
            // Updates to game state are made
            mGameState.mShiftX += 1;
            mGameState.mShiftY += 1;

            mRenderer.onDrawFrame(mHolder, mGameState);
        }
    }

    public void setRunning(boolean isRunning) {
        mIsRunning = isRunning;
    }

}
