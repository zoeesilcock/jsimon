package simon;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JComponent;

public class SimonButton extends JComponent implements MouseListener{
	private static final long serialVersionUID = -2195448772023999082L;
	private String color;
	private Point position;
	private BufferedImage onImage;
	private BufferedImage offImage;
	private boolean buttonOn;
	private SimonWindow window;
	private Clip sound;
	
	public SimonButton(String c, Point p, String s, SimonWindow w){
		this.color = c;
		this.position = p;
		this.window = w;
		
		File file = new File(System.getProperty("java.class.path"));
		String path = file.getParent();
		path += "/";
		
		try {
		    onImage = ImageIO.read(new File(path + "data/images/" + this.color + "_on.png"));
		    offImage = ImageIO.read(new File(path + "data/images/" + this.color + "_off.png"));
		} catch (IOException e) {
		}
		
		this.setBounds(position.x, position.y, onImage.getWidth(), onImage.getHeight());
		this.addMouseListener(this);
		
        try {
        	AudioInputStream ain = AudioSystem.getAudioInputStream(new File(path + "data/sounds/" + s));
            DataLine.Info info =  new DataLine.Info(Clip.class,ain.getFormat());
            sound = (Clip) AudioSystem.getLine(info);
            sound.open(ain);
        }catch (Exception e) {
        	
		}
	}
	
	private void playSound(){
		if(window.isSoundEnabled()){
			sound.start();
		}
	}
	
	private void stopSound(){
		if(window.isSoundEnabled()){
			sound.stop();
			sound.setMicrosecondPosition(0);
		}
	}
	
	public void paintComponent(Graphics g){
		if(buttonOn){
			g.drawImage(onImage, 0, 0, null);
		}else{
			g.drawImage(offImage, 0, 0, null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	public boolean contains(int x, int y){
		boolean inside = false;
		
		if(this.isEnabled()){
			PixelGrabber pg = new PixelGrabber(offImage, x, y, 1, 1, false);
			try {
				pg.grabPixels();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int[] pixels = (int[]) pg.getPixels();
			
			
			return (pixels != null && pixels[0] != 0);
		}
		return inside;
	}
	
	public boolean isButtonOn() {
		return buttonOn;
	}

	public void setButtonOn(boolean buttonOn) {
		this.buttonOn = buttonOn;
		
		if(buttonOn){
			playSound();
		}else{
			stopSound();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		setButtonOn(true);
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		setButtonOn(false);
		this.repaint();
		window.buttonPressed(this);
	}
}
