package com.hashcap.mmsgenerator;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MmsGeneratorService extends Service {
	private static final String TAG = "MMS Generator/MmsGeneratorService";
	private static final boolean DEBUG = true;

	private boolean mBound;
	private Status mStatus;

	private MmsGenerator mMmsGenerator;

	class ServiceBinder extends Binder {
		public MmsGeneratorService getService() {
			return MmsGeneratorService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceBinder();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	public boolean startGenerator(MmsGenerator generator) {
		if (mStatus == Status.IDEL) {
			mMmsGenerator = generator;
			notifyDataChanged();
		}
		return false;
	}

	private void notifyDataChanged() {
		startGenerator();

	}

	private void startGenerator() {
		if (mMmsGenerator != null) {
			if (mMmsGenerator.isValidMmsdData()) {

			} else {
				log("Cann't MmsGenerator process! because you have not select a valid mms data.");
				log("Openning Settings activity! to get valid data.");
				Toast.makeText(this, "Please sellect valid MMS data.",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(this,
						SettingsPreferenceActivity.class);
				startActivity(intent);
			}
		} else {
			log("Generator cann't start ! mMmsGenerator not found : mMmsGenerator = "
					+ mMmsGenerator);
		}

	}

	private void log(String msg) {
		if (DEBUG) {
			Log.d(TAG, msg);
		}
	}

	enum Status {
		IDEL, RUNNING;
	}

	interface OnMmsGeneratorStatusChangedListener {
		void OnMmsGeneratorStatusChanged(Status status);
	}

}
