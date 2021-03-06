/**
 * Copyright 2015 Red Hat, Inc., and individual contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feedhenry.helloworld;

import com.feedhenry.helloworld_android.R;
import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

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


    @Override
    protected void onStop() {
      FH.stop();
      super.onStop();
    }
    
    
}
