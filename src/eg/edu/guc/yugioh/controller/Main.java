package eg.edu.guc.yugioh.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.gui.GUI;

public class Main extends JFrame implements MouseListener {
	
	private Clip clip;
	public Main() throws IOException, UnexpectedFormatException
	{
		super();
		this.setSize(new Dimension(1000,406));
		this.setContentPane(new JLabel(new ImageIcon("resources/images/Start Game.jpg")));
		this.setLocation(150,150);Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor((new ImageIcon("resources/others/normalCursor.PNG")).getImage(),new Point(), "Normal Cursor");
		this.setCursor(cursor);
		this.setLayout(new FlowLayout(FlowLayout.LEFT,700,150));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JLabel startGame = new JLabel("Start Game",JLabel.CENTER);
		startGame.setForeground(Color.white);
		Font customFont;
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/others/prince.ttf")).deriveFont(40f);
			startGame.setFont(customFont);
		} catch (FontFormatException e) {
			
		} catch (IOException e) {
			
		}
		File soundFile = new File("resources/others/YugiohIntro.wav");
		try {
			AudioInputStream x = AudioSystem.getAudioInputStream(soundFile);
			clip = AudioSystem.getClip();
			 clip.open(x);
				clip.loop(10000);
		} catch (UnsupportedAudioFileException | IOException e) {
		}
	     catch (LineUnavailableException e) {
		}
		this.add(startGame);
		startGame.addMouseListener(this);
		this.setVisible(true);
		
	}
	
	public void startGame() throws IOException, UnexpectedFormatException
	{
		GUI gui = new GUI();
		Board board = new Board();
		Controller controller = new Controller(board,gui);
	}
	
	public static void main(String[] args) throws IOException, UnexpectedFormatException {
	
		Main m = new Main();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.setVisible(false);
		clip.stop();
		try {
			startGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnexpectedFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		e.getComponent().setForeground(Color.green);
		GUI.playSound("x");
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		e.getComponent().setForeground(Color.WHITE);
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
