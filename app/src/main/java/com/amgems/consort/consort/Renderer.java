package com.amgems.consort.consort;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.amgems.consort.model.GameState;
import com.amgems.consort.model.Node;

import java.util.Random;

public class Renderer {

    private final Random mRandom;

    public Renderer() {
        mRandom = new Random();
    }

    public void onDrawFrame(SurfaceHolder holder, GameState game) {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();

            float xScale = 1.0f * width / game.getMapping().width;
            float yScale = 1.0f * height / game.getMapping().height;

            float x = game.getShiftX();
            float y = game.getShiftY();

            canvas.drawRGB(0, 0, 0);

            for (Node node : game.getMapping().graph) {
                Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                textPaint.setColor(Color.rgb(255, 255, 255));
                textPaint.setTextSize(40);
                circlePaint.setColor(Color.rgb(255, 152, 07));

                String nodeName;

                if (node.isVisible()) {
                    nodeName = node.getData();
                } else {
                    nodeName = node.getData().replaceAll("[a-zA-Z]", "*");
                }

                float length = nodeName.length() * 15;
                canvas.drawRect(node.getX(xScale) - length - x,
                        node.getY(yScale) - 40 - y,
                        node.getX(xScale) + length * 2 - x,
                        node.getY(yScale) + 10 - y,
                        circlePaint
                );
                canvas.drawText(nodeName, node.getX(xScale) - x, node.getY(yScale) - y, textPaint);
                for (Integer neighbour : node.getNeighbors()) {
                    // What the Jesus...
                    Node neighbourNode = game.getMapping().graph.fromString(game.getMapping().mappings.get(neighbour));
                    canvas.drawLine(node.getX(xScale) - x, node.getY(yScale) - y,
                            neighbourNode.getX(xScale) - x, neighbourNode.getY(yScale) - y,
                            circlePaint);
                }
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

}
