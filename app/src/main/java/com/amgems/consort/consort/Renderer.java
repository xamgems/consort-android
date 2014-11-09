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

            float xScale = 2.0f * width / game.getMapping().width;
            float yScale = 4.0f * height / game.getMapping().height;

            float x = game.getShiftX();
            float y = game.getShiftY();

            canvas.drawRGB(66, 66, 66);

            for (Node node : game.getMapping().graph) {
                if (node.isVisible()) {
                    Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    textPaint.setColor(Color.rgb(255, 255, 255));
                    textPaint.setTextSize(40);
                    textPaint.setColor(Color.rgb(0, 0, 0));
                    circlePaint.setColor(Color.rgb(255, 152, 07));

                    String nodeName;

                    if (node.isDiscovered()) {
                        nodeName = node.getData();
                    } else {
                        nodeName = node.getData().replaceAll("[a-zA-Z]", "*");
                    }

                    for (Integer neighbour : node.getNeighbors()) {
                        // What the Jesus...
                        Node neighbourNode = game.getMapping().graph.fromString(game.getMapping().mappings.get(neighbour));
                        if (neighbourNode.isVisible()) {
                            canvas.drawLine(node.getX(xScale) - x, node.getY(yScale) - y, neighbourNode.getX(xScale) - x, neighbourNode.getY(yScale) - y, textPaint);
                        }
                    }

                    float length = nodeName.length() * 15;
                    canvas.drawRect(node.getX(xScale) - length - x,
                            node.getY(yScale) - 40 - y,
                            node.getX(xScale) + length * 2 - x,
                            node.getY(yScale) + 10 - y,
                            circlePaint
                    );
                    canvas.drawText(nodeName, node.getX(xScale) - x, node.getY(yScale) - y, textPaint);
                }
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

}
