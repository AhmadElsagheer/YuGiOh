package eg.edu.guc.yugioh.gui.panels;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import eg.edu.guc.yugioh.gui.buttons.CardButton;
import eg.edu.guc.yugioh.gui.buttons.SpellButton;

public class SpellsPanel extends JPanel {
	
	private ArrayList<CardButton> spellButtons;
	private JLabel deckImage = new JLabel(new ImageIcon("resources/images/deck.png"));
	private JLabel deckLabel = new JLabel("Deck",JLabel.CENTER);
	public SpellsPanel(boolean isLower)
	{
		super(new GridLayout(1,7,5,0));
		this.setPreferredSize(new Dimension(1050,100));
		this.setOpaque(false);
		deckImage.setLayout(new BorderLayout());
		deckImage.add(deckLabel);
		deckLabel.setForeground(Color.white);
		JLabel leftLabel = isLower?new JLabel():deckImage;
		JLabel rightLabel = !isLower?new JLabel():deckImage;
		leftLabel.setPreferredSize(new Dimension(125,100));
		rightLabel.setPreferredSize(new Dimension(125,100));
		this.add(leftLabel);
		spellButtons = new ArrayList<CardButton>();
		for(int i =1; i<=5;i++)
		{
			SpellButton button = new SpellButton("");
			if(isLower)
				button.setActionCommand("Player1");
			else
				button.setActionCommand("Player2");
			spellButtons.add(button);
			this.add(button);
		}
		this.add(rightLabel);
	}
	public ArrayList<CardButton> getSpellButtons() {
		return spellButtons;
	}
	public JLabel getDeckLabel() {
		return deckLabel;
	}
	
	

}
