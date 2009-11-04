package simon;

import java.io.Serializable;

public class SimonSettings extends MySerializer<SimonSettings> implements Serializable{
	private static final long serialVersionUID = -1161131003806608454L;
	private boolean soundEnabled = true;
	private int dificulty = 0;
	
	public SimonSettings(String filePath){
		super(filePath);
		
		SimonSettings temp = loadObject();
		if(temp != null){
			this.soundEnabled = temp.soundEnabled;
			this.dificulty = temp.dificulty;
		}
	}
	
	public void setSoundEnabled(boolean enabled){
		this.soundEnabled = enabled;
		saveObject(this);
	}

	public boolean isSoundEnabled(){
		return this.soundEnabled;
	}

	public void setDificulty(int dificulty) {
		this.dificulty = dificulty;
		saveObject(this);
	}

	public int getDificulty() {
		return this.dificulty;
	}	
}
