package simon;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;

public class SimonWindow extends JFrame implements ActionListener, ItemListener, KeyListener{
	private static final long serialVersionUID = 1847004660115073987L;

	private ArrayList<SimonButton> buttons = new ArrayList<SimonButton>();
	private SimonGame theGame;
	private JLabel centerLabel1;
	private JLabel centerLabel2;
	
	private JMenu gameMenu;
	private JMenuItem newGameItem;
	private JMenuItem highscoresItem;
	private JCheckBoxMenuItem soundEnabledItem;
	private JMenuItem quitItem;
	
	private JMenu dificultyMenu;
	private JRadioButtonMenuItem easyItem;
	private JRadioButtonMenuItem hardItem;
	
	final String[] colors = {"red", "green", "blue", "yellow"};
	final Point[] positions = {new Point(110, 20), new Point(313, 80), new Point(110, 283), new Point(50, 80)};
	final String[] sounds = {"red.wav", "green.wav", "blue.wav", "yellow.wav"};
	
	public SimonWindow(SimonGame game){
		super("Simon");
		this.theGame = game;
		this.setSize(500, 500);
		
		this.getContentPane().setLayout(null);
		
		for(int i = 0; i < 4; i++){
			SimonButton b = new SimonButton(colors[i], positions[i], sounds[i], this);
			buttons.add(b);
			this.getContentPane().add(b);
			b.setEnabled(false);
			b.setFocusable(false);
		}
		
		centerLabel1 = new JLabel("", SwingConstants.CENTER);
		centerLabel1.setBounds(200, 200, 100, 20);
		this.getContentPane().add(centerLabel1);
		
		centerLabel2 = new JLabel("", SwingConstants.CENTER);
		centerLabel2.setBounds(200, 225, 100, 20);
		this.getContentPane().add(centerLabel2);
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		newGameItem = new JMenuItem("New game");
		newGameItem.addActionListener(this);
		highscoresItem = new JMenuItem("Highscores");
		highscoresItem.addActionListener(this);
		soundEnabledItem = new JCheckBoxMenuItem("Sound", theGame.getSettings().isSoundEnabled());
		soundEnabledItem.addItemListener(this);
		quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(this);
		
		gameMenu = new JMenu("Game");
		gameMenu.add(newGameItem);
		gameMenu.add(highscoresItem);
		gameMenu.add(soundEnabledItem);
		gameMenu.addSeparator();
		gameMenu.add(quitItem);
		
		easyItem = new JRadioButtonMenuItem("Easy", (theGame.getSettings().getDificulty() == 0));
		easyItem.addActionListener(this);
		hardItem = new JRadioButtonMenuItem("Hard", (theGame.getSettings().getDificulty() == 1));
		hardItem.addActionListener(this);
		dificultyMenu = new JMenu("Dificulty");
		dificultyMenu.add(easyItem);
		dificultyMenu.add(hardItem);
		
		menuBar.add(gameMenu);
		menuBar.add(dificultyMenu);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				theGame.quit();
			}
		});
		
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(this);
		
		this.setVisible(true);
	}
	
	public void setCenterLabel(String text){
		centerLabel1.setText(text);
	}
	
	public void setCenterLabel2(String text){
		centerLabel2.setText(text);
	}
	
	public void setButtonsEnabled(boolean enabled){
		for(SimonButton button : buttons){
			button.setEnabled(enabled);
			button.setButtonOn(false);
		}
	}
	
	public void gameOver(){
		centerLabel2.setText("Game over!");
	}
	
	public void setButtonHighlight(SimonButton b, boolean on){
		b.setButtonOn(on);
		b.repaint();
	}

	public void buttonPressed(SimonButton b){
		theGame.receiveButtonPress(b);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == newGameItem){
			centerLabel1.setText("");
			centerLabel2.setText("");
			theGame.startNewGame();
		}else if(e.getSource() == highscoresItem){
			theGame.showHighscoreList();
		}else if(e.getSource() == quitItem){
			theGame.quit();
		}else if(e.getSource() == easyItem){
			theGame.getSettings().setDificulty(0);
			hardItem.setSelected(false);
		}else if(e.getSource() == hardItem){
			theGame.getSettings().setDificulty(1);
			easyItem.setSelected(false);
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e){
		if(e.getSource() == soundEnabledItem){
			theGame.getSettings().setSoundEnabled(soundEnabledItem.getState());
		}
	}

	public SimonButton getButton(int index) {
		return buttons.get(index);
	}

	public boolean isSoundEnabled() {
		return theGame.getSettings().isSoundEnabled();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		SimonButton button = null;
		
		if(key == KeyEvent.VK_UP){
			button = buttons.get(0);
		}else if(key == KeyEvent.VK_RIGHT){
			button = buttons.get(1);
		}else if(key == KeyEvent.VK_DOWN){
			button = buttons.get(2);
		}else if(key == KeyEvent.VK_LEFT){
			button = buttons.get(3);
		}
		
		if(button != null){
			button.setButtonOn(true);
			button.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		SimonButton button = null;
		
		if(key == KeyEvent.VK_UP){
			button = buttons.get(0);
		}else if(key == KeyEvent.VK_RIGHT){
			button = buttons.get(1);
		}else if(key == KeyEvent.VK_DOWN){
			button = buttons.get(2);
		}else if(key == KeyEvent.VK_LEFT){
			button = buttons.get(3);
		}
		
		if(button != null){
			button.setButtonOn(false);
			button.repaint();
			this.buttonPressed(button);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
