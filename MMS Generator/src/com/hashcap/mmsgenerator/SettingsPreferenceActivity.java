package com.hashcap.mmsgenerator;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class SettingsPreferenceActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {
	// key constants
	public static final String CC_RECIPIENT = "cc_recipients";
	public static final String BCC_RECIPIENT = "bcc_recipients";
	public static final String EMAIL_RECIPIENT = "email_recipients";
	public static final String MULTIPLE_RECIPIENT = "multiple_recipient";
	public static final String SUBJECT = "subject";
	public static final String ATTACHMENT_TYPE_LIST = "attachment_type_list";
	public static final String ATTACHMENT_DATA_LIST = "attachment_data_list";
	public static final String ATTACHMENT_TYPE_LIST_D_SUMMARY = "Choose MMS Attachment type for messages.";
	public static final String ATTACHMENT_DATA_LIST_D_SUMMARY = "Choose attachment data accordingly attachment type.";

	public static final String IMAGE = "image";
	public static final String AUDIO = "audio";
	public static final String VIDEO = "video";
	public static final String OTHER = "other";

	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		loadSavedAttachmentData();

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onStop() {
		super.onStop();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Preference preference = findPreference(key);
		if (preference instanceof ListPreference) {
			if (preference.getKey().equals(ATTACHMENT_TYPE_LIST)) {

				resetAttachmentDataList();

			} else if (preference.getKey().equals(ATTACHMENT_DATA_LIST)) {
				String value = ((ListPreference) preference).getValue();
				((ListPreference) preference).setSummary(value);

			}
		}

	}

	private void loadSavedAttachmentData() {
		ListPreference preferenceAttachmentType = (ListPreference) findPreference(ATTACHMENT_TYPE_LIST);
		ListPreference preferenceAttachmentData = (ListPreference) findPreference(ATTACHMENT_DATA_LIST);
		String value = preferenceAttachmentType.getValue();
		if (value != null) {
			if (value.equals(IMAGE)) {
				preferenceAttachmentData.setEntries(R.array.image_data);
				preferenceAttachmentData.setEntryValues(R.array.image_data_ids);
				preferenceAttachmentType.setSummary("Image");
			} else if (value.equals(AUDIO)) {
				preferenceAttachmentData.setEntries(R.array.audio_data);
				preferenceAttachmentData.setEntryValues(R.array.video_data_ids);
				preferenceAttachmentType.setSummary("Audio");
			} else if (value.equals(VIDEO)) {
				preferenceAttachmentData.setEntries(R.array.video_data);
				preferenceAttachmentData.setEntryValues(R.array.video_data_ids);
				preferenceAttachmentType.setSummary("Video");
			} else if (value.equals(OTHER)) {
				preferenceAttachmentData.setEntries(R.array.other_data);
				preferenceAttachmentData.setEntryValues(R.array.other_data_ids);
				preferenceAttachmentType.setSummary("Other");
			}
		}
		if (preferenceAttachmentData.getValue() != null) {
			preferenceAttachmentData.setSummary(preferenceAttachmentData
					.getValue());
		}

	}

	private void resetAttachmentDataList() {
		loadSavedAttachmentData();
		ListPreference preferenceAttachmentData = (ListPreference) findPreference(ATTACHMENT_DATA_LIST);
		preferenceAttachmentData.setSummary(ATTACHMENT_DATA_LIST_D_SUMMARY);

	}
}
