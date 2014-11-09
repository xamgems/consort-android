package com.amgems.consort.consort;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.amgems.consort.model.GameState;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    GameLoop mGameLoop;

    public GameSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public void startRenderer(Renderer renderer) {
        mGameLoop = new GameLoop(getHolder(), renderer, new GameState());
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mGameLoop.setRunning(true);
        mGameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
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
