package com.amgems.consort.consort;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.amgems.consort.model.GameState;
import com.amgems.consort.model.Node;

import java.util.Random;

public class Renderer {

    private static final int MAX_WIDTH = 100;
    private static final int MAX_HEIGHT = 100;

    private final Random mRandom;

    public Renderer() {
        mRandom = new Random();
    }

    public void onDrawFrame(SurfaceHolder holder, GameState game) {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();

            double xScale = 1.0 * width / MAX_WIDTH;
            double yScale = 1.0 * height / MAX_HEIGHT;

            int x = game.mShiftX;
            int y = game.mShiftY;

            canvas.drawRGB(255, 255, 255);

            for (Node node : game.getMapping().graph) {
                Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                textPaint.setTextSize(40);
                int r = mRandom.nextInt(255);
                int g = mRandom.nextInt(255);
                int b = mRandom.nextInt(255);
                textPaint.setColor(Color.rgb(r, g, b));

                canvas.drawText(node.getData(), node.getX(xScale) - x, node.getY(yScale) - y, textPaint);
                for (Integer neighbour : node.getNeighbors()) {
                    // What the Jesus...
                    Node neighbourNode = game.getMapping().graph.fromString(game.getMapping().mappings.get(neighbour));
                    canvas.drawLine(node.getX(xScale) - x, node.getY(yScale) - y, neighbourNode.getX(xScale) - x, neighbourNode.getY(yScale) - y, textPaint);
                }
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

}
