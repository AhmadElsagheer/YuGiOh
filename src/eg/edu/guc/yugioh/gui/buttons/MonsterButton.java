package eg.edu.guc.yugioh.gui.buttons;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import eg.edu.guc.yugioh.board.player.Player;

public class MonsterButton extends CardButton {
	
	private JLabel monsterName;
	private JLabel monsterMode=new JLabel("",JLabel.CENTER);
	
	public MonsterButton()
	{
		super();
		this.setPreferredSize(new Dimension(150, 100));
		this.getPower().setForeground(Color.white);
		monsterMode.setForeground(Color.white);
		monsterName=new JLabel(this.getText(),JLabel.CENTER);
		monsterName.setForeground(Color.white);
		this.add(monsterName);
		this.add(this.getPower(),BorderLayout.SOUTH);
		this.add(monsterMode,BorderLayout.NORTH);
	}
	
	public MonsterButton(String m)
	{
		super(m);
		this.setPreferredSize(new Dimension(150, 100));
		this.getPower().setForeground(Color.white);
		monsterMode.setForeground(Color.white);
		monsterName=new JLabel(this.getText(),JLabel.CENTER);
		monsterName.setForeground(Color.white);
		this.add(monsterName);
		this.add(this.getPower(),BorderLayout.SOUTH);
		this.add(monsterMode,BorderLayout.NORTH);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		try {
			g.drawImage(ImageIO.read(new File("resources/images/MonsterCard.jpg")), 0, 0, null);
		} catch (IOException e) {
		}
	}

	public JLabel getMonsterName() {
		return monsterName;
	}

	public JLabel getMonsterMode() {
		return monsterMode;
	}

	
	

}
