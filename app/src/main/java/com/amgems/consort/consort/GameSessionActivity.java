package com.amgems.consort.consort;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


public class GameSessionActivity extends ActionBarActivity {

    private GameSurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSurfaceView = new GameSurfaceView(this);
        setContentView(mSurfaceView);

        mSurfaceView.startRenderer(new Renderer());
    }

}
