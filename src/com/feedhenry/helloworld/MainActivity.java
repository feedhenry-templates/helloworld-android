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

public class MainActivity extends Activity implements OnClickListener {

	private boolean initialised = false;
	private Button cloudButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialize button
		cloudButton = (Button) findViewById(R.id.button);
		cloudButton.setOnClickListener(this);
		cloudButton.setEnabled(false);
		
		initFH();
	}

	private void initFH() {

		if (!initialised) {
			FH.init(getApplicationContext(), new FHActCallback() {
				@Override
				public void success(FHResponse resp) {
					initialised = true;
					Toast.makeText(getApplicationContext(), "Init Success",
							Toast.LENGTH_SHORT).show();
					 cloudButton.setEnabled(true);
				}

				@Override
				public void fail(FHResponse response) {
					Log.i("fh",
							"Init failed with FH Cloud"
									+ response.getRawResponse());
					Toast.makeText(getApplicationContext(), "Init Fail",
							Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		if (initialised) {
			callCloud();
		} else {
			Toast.makeText(getApplicationContext(), "App not Initialized",
					Toast.LENGTH_SHORT).show();
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
				Toast.makeText(getApplicationContext(),
						"Cloud Call failed + " + fhResponse.getRawResponse(),
						Toast.LENGTH_SHORT).show();
				Log.i("fh",
						"Cloud Call failed + " + fhResponse.getRawResponse());
			}
		});
	}

	@Override
	protected void onStop() {
		FH.stop();
		super.onStop();
	}

	@Override
	protected void onPause() {
		FH.stop();
		super.onPause();
	}
}
