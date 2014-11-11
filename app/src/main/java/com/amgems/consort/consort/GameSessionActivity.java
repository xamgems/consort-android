package com.amgems.consort.consort;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amgems.consort.model.GameState;
import com.amgems.consort.model.Graph;
import com.amgems.consort.model.GraphMappings;
import com.amgems.consort.model.Node;
import com.amgems.consort.serve.GcmManager;
import com.amgems.consort.serve.QueryService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GameSessionActivity extends ActionBarActivity {

    public static final String EXTRAS_SESSION_ID = "mSessionId";
    public static final String EXTRAS_USER = "mUsername";

    private GameSurfaceView mSurfaceView;
    private EditText mGuessEditText;
    private Graph mGraph;

    private String mUsername;
    private int mSessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_session);

        mSurfaceView = (GameSurfaceView) findViewById(R.id.surface_view);
        mGuessEditText = (EditText) findViewById(R.id.guess_edittext);

        mUsername = getIntent().getStringExtra(EXTRAS_USER);
        mSessionId = getIntent().getIntExtra(EXTRAS_SESSION_ID, -1);

        if (mSessionId < 0) {
            throw new IllegalArgumentException("EXTRAS_SESSION_ID must be specified");
        }

        mSurfaceView.setRenderer(new Renderer());
        GcmManager gcmManager = new GcmManager(this);
        gcmManager.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getExtras().getString("data");
                mGraph.check(data);
            }
        });

        final QueryService service = new QueryService();
        service.getGraph(mUsername, mSessionId, new Callback<GraphMappings>() {
            @Override
            public void success(final GraphMappings graphMappings, Response response) {
                graphMappings.graph.initialize();

                for (Node n : graphMappings.graph) {
                    if (n.isDiscovered()) {
                        n.setVisible(true);
                        for (Integer neigh : n.getNeighbors()) {
                            Node neighNode = graphMappings.graph.fromString(graphMappings.mappings.get(neigh));
                            neighNode.setVisible(true);
                        }
                    }
                }

                mGraph = graphMappings.graph;
                mGuessEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                        if (actionId == 23) {
                            Toast.makeText(GameSessionActivity.this, "You made a guess!", Toast.LENGTH_SHORT).show();
                            String guess = mGuessEditText.getText().toString();
                            if (graphMappings.graph.containsString(guess) &&
                                    !graphMappings.graph.fromString(guess).isDiscovered() &&
                                    graphMappings.graph.fromString(guess).isVisible()) {

                                Node node = graphMappings.graph.fromString(guess);
                                node.setDiscovered(true);

                                animateTo(getResources().getColor(R.color.light_green));

                                for (Integer neigh : node.getNeighbors()) {
                                    Node neighNode = graphMappings.graph.fromString(graphMappings.mappings.get(neigh));
                                    neighNode.setVisible(true);
                                }
                                service.updateState(mUsername, guess, new Callback<String>() {
                                    @Override
                                    public void success(String s, Response response) {
                                        Log.d(getClass().getSimpleName(), "Success");
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.d(getClass().getSimpleName(), "Failure: " + error.getMessage());
                                    }
                                });
                            } else {
                                animateTo(getResources().getColor(R.color.red));
                            }
                        }
                        return false;
                    }
                });
                mSurfaceView.start(new GameState(graphMappings));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(GameSessionActivity.this, "Someone fucked up: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void animateTo(int endColor) {
        ValueAnimator animator = ValueAnimator.ofInt(getResources().getColor(R.color.offwhite), endColor);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(1);
        animator.setDuration(600);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newColor = (Integer) valueAnimator.getAnimatedValue();
                mGuessEditText.setBackgroundColor(newColor);
            }
        });
        animator.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSurfaceView.stop();
    }
}
