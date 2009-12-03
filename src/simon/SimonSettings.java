package simon;

import  java.util.prefs.Preferences;

public class SimonSettings{
	private static final long serialVersionUID = -1161131003806608454L;
	private static final String PREFS_DIFICULTY = "simonDificulty";
	private static final String PREFS_SOUND_ENABLED = "simonSoundEnabled";
	
	private boolean soundEnabled;
	private int dificulty;
	private Preferences prefs;
	
	public SimonSettings(){
		prefs = Preferences.userNodeForPackage(this.getClass());
		
		this.dificulty = prefs.getInt(PREFS_DIFICULTY, 0);
		this.soundEnabled = prefs.getBoolean(PREFS_SOUND_ENABLED, true);
	}
	
	public void setSoundEnabled(boolean enabled){
		this.soundEnabled = enabled;
		prefs.putBoolean(PREFS_SOUND_ENABLED, enabled);
	}

	public boolean isSoundEnabled(){
		return this.soundEnabled;
	}

	public void setDificulty(int dificulty) {
		this.dificulty = dificulty;
		prefs.putInt(PREFS_DIFICULTY, dificulty);
	}

	public int getDificulty() {
		return this.dificulty;
	}	
}
