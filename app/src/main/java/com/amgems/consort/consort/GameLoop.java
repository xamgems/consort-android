package com.amgems.consort.consort;

import android.view.SurfaceHolder;

import com.amgems.consort.model.GameState;

public class GameLoop extends Thread {

    private volatile boolean mIsRunning;

    private final SurfaceHolder mHolder;
    private final Renderer mRenderer;
    private final GameState mGameState;

    public GameLoop(SurfaceHolder holder, Renderer renderer, GameState game) {
        mIsRunning = false;

        mHolder = holder;
        mRenderer = renderer;
        mGameState = game;
    }

    @Override
    public void run() {
        while (mIsRunning) {
            // update mGameState
            mRenderer.onDrawFrame(mHolder, mGameState);
        }
    }

    public void setRunning(boolean isRunning) {
        mIsRunning = isRunning;
    }

}
