package com.hashcap.mmsgenerator;

import com.hashcap.mmsgenerator.MmsGeneratorService.ServiceBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG = "MMS Generator/MainActivity";
	private static final boolean DEBUG = true;
	private static final String START = "start";
	private static final String STOP = "stop";
	private Button mButtonSettings;
	private Button mButtonStartStop;

	private EditText mEditTextConversations;
	private EditText mEditTextConversationMessages;
	private EditText mEditTextInbox;
	private EditText mEditTextOutbox;
	private EditText mEditTextDraft;
	private EditText mEditTextFailed;
	private EditText mEditTextNotificationInd;

	private boolean mBound = false;
	private MmsGeneratorService mMmsGeneratorService;
	private MmsGenerator mPendingMmsGenerator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mButtonSettings = (Button) findViewById(R.id.buttonSettings);
		mButtonStartStop = (Button) findViewById(R.id.buttonStartStop);

		mButtonSettings.setOnClickListener(this);

		mEditTextConversations = (EditText) findViewById(R.id.editText_conversation);
		mEditTextConversationMessages = (EditText) findViewById(R.id.editText_conversation_message);
		mEditTextInbox = (EditText) findViewById(R.id.editTextInbox);
		mEditTextOutbox = (EditText) findViewById(R.id.editTextOutbox);
		mEditTextDraft = (EditText) findViewById(R.id.editTextDraft);
		mEditTextFailed = (EditText) findViewById(R.id.editTextFailed);
		mEditTextNotificationInd = (EditText) findViewById(R.id.editTextNotificationInd);

		InputFilter[] inputFilters = new InputFilter[] { new InputFilterMinMax(
				this, "0", "500") };
		mEditTextConversations.setFilters(inputFilters);
		mEditTextConversationMessages.setFilters(inputFilters);
		mEditTextInbox.setFilters(inputFilters);
		mEditTextOutbox.setFilters(inputFilters);
		mEditTextDraft.setFilters(inputFilters);
		mEditTextFailed.setFilters(inputFilters);
		mEditTextNotificationInd.setFilters(inputFilters);

		Intent intentService = new Intent(this, MmsGeneratorService.class);
		startService(intentService);

	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent intentService = new Intent(this, MmsGeneratorService.class);
		bindService(intentService, mServiceConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mBound) {
			unbindService(mServiceConnection);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
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
			if (mButtonStartStop.getText().equals(START)) {
				mPendingMmsGenerator = getMmsGenerator();
				if (mPendingMmsGenerator.isValid()) {
					if (mBound) {
						if (mMmsGeneratorService
								.startGenerator(mPendingMmsGenerator)) {
							mPendingMmsGenerator = null;
							log("Generator Start. and reset mPendingMmsGenerator : mPendingMmsGenerator = "
									+ mPendingMmsGenerator);
						}
					} else {
						log("MmsGeneratorService has not yed bound. Generator process can't start now!");
					}
				} else {
					log("Invalid Data ! STOP : mPendingMmsGenerator "
							+ mPendingMmsGenerator);
					Toast.makeText(MainActivity.this,
							"Put valid data and start again!",
							Toast.LENGTH_LONG).show();
				}
			} else {

			}

			break;
		}

		default:
			break;
		}

	}

	private MmsGenerator getMmsGenerator() {
		MmsGenerator mmsGenerator = new MmsGenerator(this);
		int conversations = Integer.parseInt((TextUtils
				.isEmpty(mEditTextConversations.getText().toString()) ? "0"
				: mEditTextConversations.getText().toString()));

		int conversationMessages = Integer
				.parseInt((TextUtils.isEmpty(mEditTextConversationMessages
						.getText().toString()) ? "0" : mEditTextConversations
						.getText().toString()));

		int inbox = Integer.parseInt((TextUtils.isEmpty(mEditTextInbox
				.getText().toString()) ? "0" : mEditTextInbox.getText()
				.toString()));

		int outbox = Integer.parseInt((TextUtils.isEmpty(mEditTextOutbox
				.getText().toString()) ? "0" : mEditTextOutbox.getText()
				.toString()));
		int draft = Integer.parseInt((TextUtils.isEmpty(mEditTextDraft
				.getText().toString()) ? "0" : mEditTextDraft.getText()
				.toString()));

		int failed = Integer.parseInt((TextUtils.isEmpty(mEditTextFailed
				.getText().toString()) ? "0" : mEditTextFailed.getText()
				.toString()));

		int notificationInd = Integer.parseInt((TextUtils
				.isEmpty(mEditTextNotificationInd.getText().toString()) ? "0"
				: mEditTextNotificationInd.getText().toString()));

		mmsGenerator.setConversations(conversations);
		mmsGenerator.setConversations(conversationMessages);
		mmsGenerator.setConversations(inbox);
		mmsGenerator.setConversations(outbox);
		mmsGenerator.setConversations(draft);
		mmsGenerator.setConversations(failed);
		mmsGenerator.setConversations(notificationInd);

		return mmsGenerator;
	}

	private void log(String msg) {
		if (DEBUG) {
			Log.d(TAG, msg);
		}
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBound = false;
			log("MmsGeneratorService unbound sucessfully");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			log("MmsGeneratorService bound sucessfully.");
			mBound = true;
			mMmsGeneratorService = ((ServiceBinder) service).getService();
			if (mPendingMmsGenerator != null) {
				log("Found mPendingGenerator ! Will start soon...");
				if (mPendingMmsGenerator.isValid()) {
					if (mMmsGeneratorService
							.startGenerator(mPendingMmsGenerator)) {
						mPendingMmsGenerator = null;
						log("Generator Start. and reset mPendingMmsGenerator : mPendingMmsGenerator = "
								+ mPendingMmsGenerator);
					}
				} else {
					log("Invalid Data ! STOP : mPendingMmsGenerator "
							+ mPendingMmsGenerator);
					Toast.makeText(MainActivity.this,
							"Put valid data and start again!",
							Toast.LENGTH_LONG).show();
				}

			}

		}
	};
}
