package com.amgems.consort.consort;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.amgems.consort.model.GameState;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoop mGameLoop;
    private Renderer mRenderer;

    public GameSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public void setRenderer(Renderer renderer) {
        mRenderer = renderer;
        mGameLoop = new GameLoop(getHolder(), mRenderer);
    }

    public void start(GameState initialState) {
        mGameLoop.setGameState(initialState);
        setOnTouchListener(mGameLoop);
        mGameLoop.start();
    }

    public void stop() {
        setOnTouchListener(null);
        mGameLoop.setRunning(false);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mGameLoop.setRunning(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        while (true) {
            try {
                mGameLoop.setRunning(false);
                mGameLoop.join();
                return;
            } catch (InterruptedException e) {
            }
        }
    }

}
