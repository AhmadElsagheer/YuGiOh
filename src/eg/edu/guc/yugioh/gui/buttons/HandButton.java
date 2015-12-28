package eg.edu.guc.yugioh.gui.buttons;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import eg.edu.guc.yugioh.board.player.Player;

public class HandButton extends CardButton {
	
	
	public HandButton()
	{
		super();
		this.setSize(new Dimension(70, 80));
		this.setBackground(Color.white);
	
	}
	
	public HandButton(String m)
	{
		super(m);
		this.setSize(new Dimension(70, 80));
		this.setBackground(Color.white);
	}

	

}
