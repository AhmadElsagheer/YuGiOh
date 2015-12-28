package eg.edu.guc.yugioh.gui.panels;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import eg.edu.guc.yugioh.gui.buttons.*;

public class HandPanel extends JPanel {
	
	private ArrayList<CardButton> handButtons = new ArrayList<CardButton> (20);
	
	public HandPanel(boolean isLower)
	{
		super(new FlowLayout());
		this.setPreferredSize(new Dimension(1050,100));
		this.setOpaque(false);
		for(int i=1;i<=20;i++)
		{
			HandButton button = new HandButton("Card " + i);
			button.setVisible(false);
			if(isLower)
				button.setActionCommand("Player1");
			else
				button.setActionCommand("Player2");
			handButtons.add(button);
			this.add(button);	
		}
		
	}

	public ArrayList<CardButton> getHandButtons() {
		return handButtons;
	}

}
