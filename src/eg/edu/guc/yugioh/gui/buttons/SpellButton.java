package eg.edu.guc.yugioh.gui.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import eg.edu.guc.yugioh.board.player.Player;

public class SpellButton extends CardButton {
	
	private JLabel spellName;
	public SpellButton()
	{
		super();
		this.setPreferredSize(new Dimension(150, 100));
		spellName=new JLabel(this.getText(),JLabel.CENTER);
		spellName.setForeground(Color.white);
		this.add(spellName);
		
	}
	
	public SpellButton(String m)
	{
		super(m);
		this.setPreferredSize(new Dimension(150, 100));
		spellName=new JLabel(this.getText(),JLabel.CENTER);
		spellName.setForeground(Color.white);
		this.add(spellName);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		try {
			g.drawImage(ImageIO.read(new File("resources/images/SpellCard.jpg")), 0, 0, null);
		} catch (IOException e) {
		}
	}

	public JLabel getSpellName() {
		return spellName;
	}

}
