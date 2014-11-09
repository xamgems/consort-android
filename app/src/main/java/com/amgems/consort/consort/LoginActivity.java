package com.amgems.consort.consort;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amgems.consort.serve.QueryService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends Activity {

    private EditText mUserEditText;
    private Button mUserSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        setUpViews();

        QueryService.init();

        mUserSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QueryService.connectSession(mUserEditText.getText().toString(), new Callback<List<Integer>>() {
                    @Override
                    public void success(List<Integer> integers, Response response) {
                        Toast.makeText(LoginActivity.this, "Sogui lah " + integers.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Toast.makeText(LoginActivity.this, "Someone fucked up: " + retrofitError, Toast.LENGTH_LONG).show();
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
