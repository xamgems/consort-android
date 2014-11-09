package com.amgems.consort.consort;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.amgems.consort.model.GameState;

import java.util.Random;

public class Renderer {

    private final Random mRandom;

    public Renderer() {
        mRandom = new Random();
    }

    public void onDrawFrame(SurfaceHolder holder, GameState game) {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawRGB(mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
            holder.unlockCanvasAndPost(canvas);
        }
    }

}
