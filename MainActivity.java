package com.feedhenry.helloworld_android.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {


    private boolean initialised = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FH.init(this, new FHActCallback() {

            @Override
            public void success(FHResponse resp) {

                initialised = true;

            }

            @Override
            public void fail(FHResponse response) {

                Log.i("fh", "Init failed with FH Cloud" + response.getRawResponse());

            }
        });

        // Initialize button
        Button cloudButton = (Button) findViewById(R.id.button);
        cloudButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

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


    @Override
    public void onClick(View v) {

        if (initialised) {
            callCloud();
        } else {
            Toast.makeText(getApplicationContext(), "App not Initialized", Toast.LENGTH_SHORT).show();
        }
    }


    private void callCloud() {
        // Use FH Agent to call the FH Cloud
        FHAgent fhAgent = new FHAgent();
        fhAgent.cloudCall(new FHActCallback() {
            @Override
            public void success(FHResponse fhResponse) {
                TextView tv = (TextView) findViewById(R.id.cloud_response);
                tv.setText(fhResponse.getJson().getString("msg"));
            }

            @Override
            public void fail(FHResponse fhResponse) {
                Toast.makeText(getApplicationContext(), "Cloud Call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
