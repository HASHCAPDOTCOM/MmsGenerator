package com.hashcap.mmsgenerator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG = "MMS Generator/MainActivity";
	private static final boolean DEBUG = true;
	private Button mButtonSettings;
	private Button mButtonStartStop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mButtonSettings = (Button) findViewById(R.id.buttonSettings);
		mButtonStartStop = (Button) findViewById(R.id.buttonStartStop);

		mButtonSettings.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonSettings: {
			log("buttonSettings : Clicked.");
			Intent intent = new Intent(this, SettingsPreferenceActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.buttonStartStop: {
			log("buttonStartStop : Clicked.");
			
			break;
		}

		default:
			break;
		}

	}

	private void log(String msg) {
		if (DEBUG) {
			Log.d(TAG, msg);
		}
	}
}
