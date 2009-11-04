package simon;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HighscoreWindow extends JFrame{
	private static final long serialVersionUID = -2542984839574589527L;
	private ArrayList<Highscore> highscoreList;
	private JTable table;
	private DefaultTableModel tableModel;
	
	public HighscoreWindow(ArrayList<Highscore> highscores){
		super("Highscore list");
		this.setLayout(null);
		this.setSize(300, 350);
		this.getContentPane().setBackground(Color.white);
		
		String[] columnNames = {"#", "Name", "Score", "Sec/move"};
		tableModel = new DefaultTableModel(null, columnNames);
		table = new JTable(tableModel);
		
		table.setBounds(20, 30, 240, 320);
		table.setCellSelectionEnabled(false);
		table.setColumnSelectionAllowed(false);
		table.setRowHeight(25);
		table.setEnabled(false);
		table.setShowGrid(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(2).setPreferredWidth(40);
		table.getTableHeader().setBounds(20,10,240,20);
		this.add(table.getTableHeader());
		this.add(table);
		
		setTableData(highscores);
		
		this.setVisible(true);
	}
	public void setTableData(ArrayList<Highscore> highscores){
		this.highscoreList = highscores;
		tableModel.getDataVector().removeAllElements();
		
		int i = 0;
		for(Highscore score : highscoreList){
			tableModel.insertRow(i, new Object[]{i+1, score.getName(), score.getScore(), score.getSecondsPerMove()});
			i++;
		}
	}
}
