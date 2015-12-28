package eg.edu.guc.yugioh.gui.panels;


import java.awt.*;

import javax.swing.*;

public class SidePanel extends JPanel{

	private PlayerPanel upperPlayer = new PlayerPanel("Player 2");
	private PlayerPanel lowerPlayer = new PlayerPanel("Player 1");
	private CardPanel cardPanel = new CardPanel();
	
	
	public SidePanel()
	{
		super(new FlowLayout());
		this.setPreferredSize(new Dimension(300,725));
		this.setOpaque(false);
		lowerPlayer.getEndPhase().setActionCommand("Player1");
		lowerPlayer.getEndTurn().setActionCommand("Player1");
		
		upperPlayer.getEndPhase().setActionCommand("Player2");
		upperPlayer.getEndTurn().setActionCommand("Player2");
		
	
		
		this.add(upperPlayer);
		this.add(cardPanel);	
		this.add(lowerPlayer);
		
		
	}

	public PlayerPanel getUpperPlayer() {
		return upperPlayer;
	}

	public PlayerPanel getLowerPlayer() {
		return lowerPlayer;
	}

	public CardPanel getCardPanel() {
		return cardPanel;
	}

	
	
}
