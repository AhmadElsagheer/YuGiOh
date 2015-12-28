package eg.edu.guc.yugioh.gui.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel {
	
	private JLabel name;
	private JLabel lifePoints = new JLabel("8000 pts", JLabel.CENTER);
    private JButton endPhase = new JButton("End Phase");
    private JButton endTurn = new JButton("End Turn");
    private String playerMode="opponent";
	
	public PlayerPanel(String name)
	{
		super(new FlowLayout(FlowLayout.CENTER));
	
		this.name= new JLabel(name,JLabel.CENTER);
		this.setPreferredSize(new Dimension(300,125));
		this.setOpaque(false);
		this.name.setPreferredSize(new Dimension(260,40));
		this.lifePoints.setPreferredSize(new Dimension(260,30));
		Font customFont;
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/others/prince.ttf")).deriveFont(34f);
			this.name.setFont(customFont);
			lifePoints.setFont(customFont);
		} catch (FontFormatException e) {
			
		} catch (IOException e) {
			
		}
		this.name.setForeground(Color.WHITE);
		this.lifePoints.setForeground(Color.WHITE);
		this.add(this.name);
		this.add(lifePoints);
		this.add(endPhase);
		this.add(endTurn);
		endPhase.setBackground(Color.white);
		endTurn.setBackground(Color.white);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor((new ImageIcon("resources/others/handCursor.PNG")).getImage(),new Point(), "Normal Cursor");
		endPhase.setCursor(cursor);
		endTurn.setCursor(cursor);
		
	}

	public JLabel getPlayerName() {
		return name;
	}

	public JLabel getLifePoints() {
		return lifePoints;
	}

	public JButton getEndPhase() {
		return endPhase;
	}

	public JButton getEndTurn() {
		return endTurn;
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		try {
			g.drawImage(ImageIO.read(new File("resources/images/"+playerMode+"Player.png")), 0, 0, null);
		} catch (IOException e) {
		}
	}

	public void setPlayerMode(String playerMode) {
		this.playerMode = playerMode;
	}
		
	

}
