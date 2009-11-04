package simon;

import java.util.ArrayList;
import java.util.Collections;

public class HighscoreList extends MySerializer<ArrayList<Highscore>>{
	private static final long serialVersionUID = 3310334028264250451L;
	private ArrayList<Highscore> highscoreList = new ArrayList<Highscore>();
	private int maxHighscores;
	
	public HighscoreList(String fileName, int max){
		super(fileName);
		this.maxHighscores = max;
		
		highscoreList = loadObject();
		if(highscoreList == null){
			highscoreList = new ArrayList<Highscore>();
		}else{
			Collections.sort(highscoreList);
		}
	}
	
	public int size(){
		return highscoreList.size();
	}
	
	public void add(Highscore score){
		highscoreList.add(score);
		
		Collections.sort(highscoreList);
		if(highscoreList.size() > maxHighscores){
			highscoreList.remove(highscoreList.size()-1);
		}
		
		saveObject(highscoreList);
	}

	public ArrayList<Highscore> getList() {
		return highscoreList;
	}
}
