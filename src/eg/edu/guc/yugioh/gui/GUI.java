package eg.edu.guc.yugioh.gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import eg.edu.guc.yugioh.gui.*;
import eg.edu.guc.yugioh.gui.panels.*;

public class GUI extends JFrame {
	
	private BoardPanel boardPanel = new BoardPanel();
	private SidePanel sidePanel = new SidePanel();
	
	public GUI()
	{
		super("Yu-Gi-Oh");
		this.setContentPane(new JLabel(new ImageIcon("resources/images/background.jpg")));
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor((new ImageIcon("resources/others/normalCursor.PNG")).getImage(),new Point(), "Normal Cursor");
		this.setCursor(cursor);
		this.setSize(1366,725);
		this.setLayout(new BorderLayout());
		this.add(sidePanel,BorderLayout.WEST);
		this.add(boardPanel, BorderLayout.EAST);
		this.setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		playSound("YugiohGame");
		
		
	}
	
	public static void playSound(String name)
	{
		File soundFile = new File("resources/others/"+name+".wav");
		try {
			AudioInputStream x = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(x);
			if(name.equals("YugiohGame"))
				clip.loop(1000);
			else
				clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
		}
		 catch (LineUnavailableException e) {
		}
		
	}
	public BoardPanel getBoardPanel() {
		return boardPanel;
	}

	public SidePanel getSidePanel() {
		return sidePanel;
	}
	
	

}
