package eg.edu.guc.yugioh.gui.panels;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import eg.edu.guc.yugioh.gui.buttons.CardButton;
import eg.edu.guc.yugioh.gui.buttons.MonsterButton;

public class MonstersPanel extends JPanel {
	
	private ArrayList<CardButton> monsterButtons;
	private JLabel graveyardImage = new JLabel(new ImageIcon("resources/images/graveyard.png"));
	private JLabel graveyardLabel = new JLabel("Graveyard",JLabel.CENTER);
	
	public MonstersPanel(boolean isLower)
	{
		super(new GridLayout(1,7,5,1));
		this.setPreferredSize(new Dimension(1050,100));
		this.setOpaque(false);
		graveyardImage.setLayout(new BorderLayout());
		graveyardImage.add(graveyardLabel);
		graveyardLabel.setForeground(Color.white);
		JLabel leftLabel = isLower?new JLabel():graveyardImage;
		JLabel rightLabel = !isLower?new JLabel():graveyardImage; 
		leftLabel.setPreferredSize(new Dimension(125,100));
		rightLabel.setPreferredSize(new Dimension(125,100));
		
		this.add(leftLabel);
		monsterButtons = new ArrayList<CardButton>();
		for(int i =1; i<=5;i++)
		{
			MonsterButton button = new MonsterButton("");
			if(isLower)
				button.setActionCommand("Player1");
			else
				button.setActionCommand("Player2");
			monsterButtons.add(button);
			this.add(button);
		}
		this.add(rightLabel);
	}
	public ArrayList<CardButton> getMonsterButtons() {
		return monsterButtons;
	}
	public JLabel getGraveyardLabel() {
		return graveyardLabel;
	}
	
	
	
	

}
