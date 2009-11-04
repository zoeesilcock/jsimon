package simon;

import java.io.Serializable;

import javax.swing.JOptionPane;

public class Highscore implements Serializable, Comparable<Highscore>{
	private static final long serialVersionUID = 5336970952126746391L;
	private String name = "";
	private int score;
	private float secondsPerMove;
	
	public Highscore(int s, float seconds){
		this.score = s;
		this.secondsPerMove = seconds;
		this.name = (String)JOptionPane.showInputDialog(null, "New Highscore!");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public float getSecondsPerMove() {
		return secondsPerMove;
	}

	@Override
	public int compareTo(Highscore o) {
		int v = o.getScore() - this.getScore();
		
		if(v == 0){
			int thisTime = (int) (this.getSecondsPerMove() * 1000);
			int thatTime = (int) (o.getSecondsPerMove() * 1000);
			
			v = thisTime - thatTime;
		}
		
		return v;
	}
}