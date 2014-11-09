package com.amgems.consort.consort;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amgems.consort.model.GameState;
import com.amgems.consort.model.GraphMappings;
import com.amgems.consort.model.Node;
import com.amgems.consort.serve.QueryService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GameSessionActivity extends ActionBarActivity {

    public static final String EXTRAS_SESSION_ID = "mSessionId";
    public static final String EXTRAS_USER = "mUsername";

    private GameSurfaceView mSurfaceView;
    private EditText mGuessEditText;

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

        QueryService service = new QueryService();
        service.getGraph(mUsername, mSessionId, new Callback<GraphMappings>() {
            @Override
            public void success(final GraphMappings graphMappings, Response response) {
                graphMappings.graph.initialize();
                mGuessEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                        if (actionId == 23) {
                            Toast.makeText(GameSessionActivity.this, "You made a guess!", Toast.LENGTH_SHORT).show();
                            String guess = mGuessEditText.getText().toString();
                            if (graphMappings.graph.containsString(guess) &&
                                !graphMappings.graph.fromString(guess).isVisible()) {
                                Node node = graphMappings.graph.fromString(guess);
                                node.setVisible(true);
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

    @Override
    protected void onPause() {
        super.onPause();

        mSurfaceView.stop();
    }
}
