package com.amgems.consort.consort;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amgems.consort.serve.GcmManager;
import com.amgems.consort.serve.QueryService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends ActionBarActivity  {

    private EditText mUserEditText;
    private Button mUserSubmitButton;

    private String mRegId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpViews();
        mUserSubmitButton.setClickable(false);

        GcmManager gcmManager = new GcmManager(this);
        gcmManager.registerInBackground(new GcmRegistrationReceiver() {
            @Override
            public void onReceivedRegistrationId(String id) {
                mRegId = id;
                mUserEditText.setClickable(true);
                mUserEditText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(LoginActivity.this, "Failed to register with GCM: " + errorMsg, Toast.LENGTH_SHORT);
            }
        });

        final QueryService service = new QueryService();

        mUserSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.connectSession(mUserEditText.getText().toString(),
                        mRegId, new Callback<List<Integer>>() {
                    @Override
                    public void success(List<Integer> integers, Response response) {
                        Intent activityIntent = new Intent(LoginActivity.this, MainMenuActivity.class);
                        activityIntent.putExtra(MainMenuActivity.EXTRAS_SESSION_LIST,
                                new ArrayList<Integer>(integers));
                        activityIntent.putExtra(MainMenuActivity.EXTRAS_USERNAME, mUserEditText.getText().toString());
                        startActivity(activityIntent);
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Toast.makeText(LoginActivity.this, "Error: " + retrofitError,
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    public void setUpViews() {
        mUserEditText = (EditText) findViewById(R.id.user_edittext);
        mUserSubmitButton = (Button) findViewById(R.id.user_submit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
