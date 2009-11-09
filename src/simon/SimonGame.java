package simon;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SimonGame {
	private SimonWindow window;
	private ArrayList<SimonButton> sequence = new ArrayList<SimonButton>();
	private HighscoreList highscoreList;
	private SimonSettings settings;
	private int position;
	private Timer sequenceTimer = new Timer();
	private long startTime;
	private HighscoreWindow highscoreWin = null;
	
	final int timeOn = 500;
	final int timeOff = 250;
	final int maxHighscores = 10;
	final String highscoreFile = "data/highscores.dat";
	final String settingsFile = "data/settings.dat";
	
	public SimonGame(){		
		highscoreList = new HighscoreList(highscoreFile, maxHighscores);
		settings = new SimonSettings(settingsFile);
		window = new SimonWindow(this);
	}
	
	public void quit(){
		window.dispose();
		if(highscoreWin != null){
			highscoreWin.dispose();
		}
		System.exit(0);
	}
	
	public void startNewGame(){
		sequence.clear();
		position = 0;
		addStep();
		window.setButtonsEnabled(true);
		
		startTime = System.currentTimeMillis();
		window.setCenterLabel2("Time: ");
	}
	
	private void addStep(){
		window.setCenterLabel("Level: " + sequence.size());
		Random r = new Random();
		
		if(settings.getDificulty() == 0){
			sequence.add(window.getButton(r.nextInt(4)));
		}else if(settings.getDificulty() == 1){
			int size = sequence.size() + 1;
			sequence.clear();
			for(int i = 0; i < size; i++){
				sequence.add(window.getButton(r.nextInt(4)));
			}
		}
		startSequence();
	}
	
	public void receiveButtonPress(SimonButton b){
		updateAvarageTime();
		if(sequence.get(position) != b){
			window.gameOver();
			sequenceTimer.cancel();
			sequenceTimer = new Timer();

			window.setButtonsEnabled(false);
			
			if(highscoreList.size() < maxHighscores || (sequence.size() > highscoreList.getList().get(highscoreList.size()-1).getScore())){
				Highscore score = new Highscore(sequence.size()-1, calculateAvarageTime());
				if(score.getName() != null){
					highscoreList.add(score);
					
					if(highscoreWin != null){
						highscoreWin.setTableData(highscoreList.getList());
					}
					showHighscoreList();
				}
			}
		}else if(position == sequence.size()-1){
			position = 0;
			addStep();
		}else{
			position++;
		}
	}
	
	private void updateAvarageTime(){
		window.setCenterLabel2("Time: " + calculateAvarageTime());
	}
	
	private float calculateAvarageTime(){
		long milis = System.currentTimeMillis() - startTime;
		long reps = 0;
		
		for(int i = sequence.size(); i > 0; i--){
			reps += i;
		}
		
		milis -= (timeOn + timeOff) * reps;
		reps -= (sequence.size() - (position + 1)); 
		milis = milis / reps;
		float seconds = (float)milis/(float)1000;
		
		return seconds;
	}
	
	private void startSequence(){
		int delay = 0;
		
		window.setButtonsEnabled(false);
		
		for(int i = 0; i < sequence.size(); i++){
			sequenceTimer.schedule(new HighlightTask(sequence.get(i), true), (i + 1)*timeOn + delay);
			sequenceTimer.schedule(new HighlightTask(sequence.get(i), false), (i + 1)*timeOn + timeOn + delay);
			delay += timeOff;
		}
		sequenceTimer.schedule(new EnableButtons(), (sequence.size())*timeOn + delay + timeOff);
	}
	
	private class HighlightTask extends TimerTask{
		private SimonButton button;
		private boolean on;
		
		public HighlightTask(SimonButton b, boolean on){
			this.button = b;
			this.on = on;
		}
		
		@Override
		public void run() {
			window.setButtonHighlight(button, on);
		}		
	}
	
	private class EnableButtons extends TimerTask{		
		@Override
		public void run() {
			window.setButtonsEnabled(true);
		}
	}
	
	public void showHighscoreList(){
		if(highscoreWin == null){
			highscoreWin = new HighscoreWindow(highscoreList.getList());
		}else{
			highscoreWin.setVisible(true);
		}
	}

	public SimonSettings getSettings() {
		return settings;
	}
}
