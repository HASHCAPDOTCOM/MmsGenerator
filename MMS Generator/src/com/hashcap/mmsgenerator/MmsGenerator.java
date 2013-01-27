package com.hashcap.mmsgenerator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MmsGenerator {
	private int mConversations;
	private int mConversationMessages;
	private int mInbox;
	private int mOutbox;
	private int mDraft;
	private int mFailed;
	private int mNotificationInd;

	private SharedPreferences mPreferences;
	private Context mContext;

	private boolean mCcRecipient;
	private boolean mBccRecipient;;
	private boolean mEmailRecipient;
	private boolean mSubject;
	private boolean mMultiRecipient;
	private String mAttachmentType;
	private String mAttachmentData;

	public MmsGenerator(Context context) {
		mContext = context;
		this.mConversations = 0;
		this.mConversationMessages = 0;
		this.mInbox = 0;
		this.mOutbox = 0;
		this.mDraft = 0;
		this.mFailed = 0;
		this.mNotificationInd = 0;

		mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		mCcRecipient = mPreferences.getBoolean(
				SettingsPreferenceActivity.CC_RECIPIENT, false);
		mCcRecipient = mPreferences.getBoolean(
				SettingsPreferenceActivity.BCC_RECIPIENT, false);
		mEmailRecipient = mPreferences.getBoolean(
				SettingsPreferenceActivity.EMAIL_RECIPIENT, false);
		mSubject = mPreferences.getBoolean(SettingsPreferenceActivity.SUBJECT,
				false);
		mMultiRecipient = mPreferences.getBoolean(
				SettingsPreferenceActivity.MULTIPLE_RECIPIENT, false);
		mAttachmentType = mPreferences.getString(
				SettingsPreferenceActivity.ATTACHMENT_TYPE_LIST, null);
		mAttachmentData = mPreferences.getString(
				SettingsPreferenceActivity.ATTACHMENT_DATA_LIST, null);

	}

	public int getConversations() {
		return mConversations;
	}

	public void setConversations(int conversations) {
		mConversations = conversations;
	}

	public int getConversationMessages() {
		return mConversationMessages;
	}

	public void setConversationMessages(int conversationMessages) {
		mConversationMessages = conversationMessages;
	}

	public int getInbox() {
		return mInbox;
	}

	public void setInbox(int inbox) {
		mInbox = inbox;
	}

	public int getOutbox() {
		return mOutbox;
	}

	public void setOutbox(int outbox) {
		mOutbox = outbox;
	}

	public int getDraft() {
		return mDraft;
	}

	public void setDraft(int draft) {
		mDraft = draft;
	}

	public int getFailed() {
		return mFailed;
	}

	public void setFailed(int failed) {
		mFailed = failed;
	}

	public int getNotificationInd() {
		return mNotificationInd;
	}

	public void setNotificationInd(int notificationInd) {
		mNotificationInd = notificationInd;
	}

	@Override
	public String toString() {
		return "MmsGenerator [mConversations=" + mConversations
				+ ", mConversationMessages=" + mConversationMessages
				+ ", mInbox=" + mInbox + ", mOutbox=" + mOutbox + ", mDraft="
				+ mDraft + ", mFailed=" + mFailed + ", mNotificationInd="
				+ mNotificationInd + "]";
	}

	public boolean isValid() {
		if (mConversations > 0 && mConversationMessages > 0) {
			return true;
		}
		if (mInbox > 0 || mOutbox > 0 || mDraft > 0 || mFailed > 0
				|| mNotificationInd > 0) {
			return true;
		}
		return false;

	}

	public boolean isValidMmsdData() {
		if (mSubject || (mAttachmentType != null && mAttachmentData != null)) {
			return true;
		}
		return false;
	}
}
