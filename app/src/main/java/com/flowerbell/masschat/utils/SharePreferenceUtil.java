package com.flowerbell.masschat.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	private static SharePreferenceUtil util;

	public SharePreferenceUtil(Context context, String file) {
		if (file == null || context == null)
			return;
		sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public static SharePreferenceUtil getUtil(Context context) {
		if (util == null) {
			util = new SharePreferenceUtil(context, "jcc");
		}
		return util;
	}

	public void clear() {
		editor.clear();
		editor.commit();
	}

	//
	public void putString(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	public String getString(String key, String defValue) {
		return sp.getString(key, defValue);
	}

	//
	public void putInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public int getInt(String key, int defValue) {
		return sp.getInt(key, defValue);
	}

	//
	public void putBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getBoolean(String key, boolean defValue) {
		return sp.getBoolean(key, defValue);
	}

	//
	public void putLong(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}

	public long getLong(String key, long defValue) {
		return sp.getLong(key, defValue);
	}

	//
	public void putFloat(String key, float value) {
		editor.putFloat(key, value);
		editor.commit();
	}

	public Float getFloat(String key, Float defValue) {
		return sp.getFloat(key, defValue);
	}

}
