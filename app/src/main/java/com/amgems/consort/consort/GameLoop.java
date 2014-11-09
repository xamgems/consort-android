package com.amgems.consort.consort;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.amgems.consort.model.GameState;

public class GameLoop extends Thread implements View.OnTouchListener {

    private volatile boolean mIsRunning;

    private final SurfaceHolder mHolder;
    private final Renderer mRenderer;
    private GameState mGameState;

    private final Object mGameStateMonitor = new Object();

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
            mRenderer.onDrawFrame(mHolder, mGameState);
        }
    }

    public void setRunning(boolean isRunning) {
        mIsRunning = isRunning;
    }

    @Override
    public boolean onTouch(View view, final MotionEvent motionEvent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // There's some horrendous race conditions going on here. If things start crashing I'd
                // look here first. Maybe consider adding points to a BlockingQueue that can be consumed
                // by the main loop.
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mGameState.startShiftViewport(motionEvent.getX(), motionEvent.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mGameState.shiftViewport(motionEvent.getX(), motionEvent.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        mGameState.endShiftViewport();
                        break;
                    default:
                        break;
                }
            }
        }).start();

        return true;
    }
}
