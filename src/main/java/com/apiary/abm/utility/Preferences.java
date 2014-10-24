package com.apiary.abm.utility;


import com.apiary.abm.ui.ABMToolWindow;


public class Preferences
{
	private java.util.prefs.Preferences mPreferences;

	public static final String PREF_PLUGIN_INITIALIZED = "pref_plugin_initialized";
	public static final String PREF_APIARY_BLUEPRINT_RAW = "pref_apiary_blueprint_raw";
	public static final String PREF_APIARY_BLUEPRINT_JSON = "pref_apiary_blueprint_json";


	public Preferences()
	{
		mPreferences = java.util.prefs.Preferences.userNodeForPackage(ABMToolWindow.class);
	}


	// GETTERS
	public boolean getPluginInitialized()
	{
		return mPreferences.getBoolean(PREF_PLUGIN_INITIALIZED, false);
	}


	public String getApiaryBlueprintRaw()
	{
		return new String(mPreferences.getByteArray(PREF_APIARY_BLUEPRINT_RAW, null));
	}


	public String getApiaryBlueprintJson()
	{
		return new String(mPreferences.getByteArray(PREF_APIARY_BLUEPRINT_JSON, null));
	}


	// SETTERS
	public void setPluginInitialized(boolean input)
	{
		mPreferences.putBoolean(PREF_PLUGIN_INITIALIZED, input);
	}


	public void setApiaryBlueprintRaw(String input)
	{
		mPreferences.putByteArray(PREF_APIARY_BLUEPRINT_RAW, input.getBytes());
	}


	public void setApiaryBlueprintJson(String input)
	{
		mPreferences.putByteArray(PREF_APIARY_BLUEPRINT_JSON, input.getBytes());
	}
}
