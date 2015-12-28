package eg.edu.guc.yugioh.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Phase;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.ChangeOfHeart;
import eg.edu.guc.yugioh.cards.spells.MagePower;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.DefenseMonsterAttackException;
import eg.edu.guc.yugioh.exceptions.MonsterMultipleAttackException;
import eg.edu.guc.yugioh.exceptions.MultipleMonsterAdditionException;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;
import eg.edu.guc.yugioh.gui.GUI;
import eg.edu.guc.yugioh.gui.buttons.ActionButton;
import eg.edu.guc.yugioh.gui.buttons.CardButton;
import eg.edu.guc.yugioh.gui.buttons.HandButton;
import eg.edu.guc.yugioh.gui.buttons.MonsterButton;
import eg.edu.guc.yugioh.gui.buttons.SpellButton;
import eg.edu.guc.yugioh.gui.panels.BoardPanel;
import eg.edu.guc.yugioh.gui.panels.FieldPanel;
import eg.edu.guc.yugioh.gui.panels.SidePanel;

public class Controller implements ActionListener, MouseListener {
	
	private Board board;
	private GUI gui;
	private Player p1;
	private Player p2;
	private boolean isSacrificing;
	private ArrayList<MonsterCard> sacrifices;
	private ActionButton lastActionButton;
	private boolean isActivating;
	private boolean isAttacking;
	private boolean mouseClicked;

	
	public Controller(Board board, GUI gui) throws IOException, UnexpectedFormatException
	{
		p1 = new Player(JOptionPane.showInputDialog("Enter the first player name"));
		p2 = new Player(JOptionPane.showInputDialog("Enter the second player name"));
		this.board=board;
		this.gui=gui;
		board.startGame(p1, p2);
		addActionListeners();
		gui.getSidePanel().getLowerPlayer().getPlayerName().setText(p1.getName());
		gui.getSidePanel().getUpperPlayer().getPlayerName().setText(p2.getName());
		updateAll();

	}
	
	public void addActionListeners() //ONLY BUTTONS
	{
		SidePanel sidePanel = gui.getSidePanel();
		sidePanel.getLowerPlayer().getEndPhase().addActionListener(this);
		sidePanel.getLowerPlayer().getEndTurn().addActionListener(this);
		sidePanel.getUpperPlayer().getEndPhase().addActionListener(this);
		sidePanel.getUpperPlayer().getEndTurn().addActionListener(this);
		sidePanel.getLowerPlayer().getEndPhase().addMouseListener(this);
		sidePanel.getLowerPlayer().getEndTurn().addMouseListener(this);
		sidePanel.getUpperPlayer().getEndPhase().addMouseListener(this);
		sidePanel.getUpperPlayer().getEndTurn().addMouseListener(this);
		sidePanel.getCardPanel().getSetMonster().addActionListener(this);
		sidePanel.getCardPanel().getSummonMonster().addActionListener(this);
		sidePanel.getCardPanel().getActivateSpell().addActionListener(this);
		sidePanel.getCardPanel().getSetSpell().addActionListener(this);
		sidePanel.getCardPanel().getCancel().addActionListener(this);
		sidePanel.getCardPanel().getSacrifice().addActionListener(this);
		sidePanel.getCardPanel().getAttack().addActionListener(this);
		sidePanel.getCardPanel().getChangeMode().addActionListener(this);
		BoardPanel boardPanel = gui.getBoardPanel();
		addActionListeners(boardPanel.getLowerFieldPanel().getSpellsPanel().getSpellButtons());
		addActionListeners(boardPanel.getUpperFieldPanel().getSpellsPanel().getSpellButtons());
		addActionListeners(boardPanel.getLowerFieldPanel().getMonstersPanel().getMonsterButtons());
		addActionListeners(boardPanel.getUpperFieldPanel().getMonstersPanel().getMonsterButtons());
		addActionListeners(boardPanel.getLowerFieldPanel().getHandPanel().getHandButtons());
		addActionListeners(boardPanel.getUpperFieldPanel().getHandPanel().getHandButtons());
		
		
	}
	
	public void addActionListeners(ArrayList<CardButton> buttons)
	{
		for(CardButton button : buttons)
		{
			button.addActionListener(this);
			button.addMouseListener(this);
		}
	}

	public void actionPerformed(ActionEvent e) 
	{
		
		JButton button = (JButton)e.getSource();
		if(button.getText().equals(""))
			{mouseClicked=false;
			return;}
		Player p = e.getActionCommand().equals("Player1")? p1:p2;
		FieldPanel fieldPanel = e.getActionCommand().equals("Player1")?gui.getBoardPanel().getLowerFieldPanel():gui.getBoardPanel().getUpperFieldPanel();
		if(isSacrificing)
		{
			if(button.getText().equals("Sacrifice"))
			{
				if(lastActionButton.getText().equals("Summon Monster"))
					try {
						board.getActivePlayer().summonMonster((MonsterCard) lastActionButton.getCard(), sacrifices);
					} catch (RuntimeException e1) {
						JOptionPane.showMessageDialog(gui, e1.getMessage());
					}
				else
					try {
						board.getActivePlayer().setMonster((MonsterCard) lastActionButton.getCard(), sacrifices);
					} catch (RuntimeException e1) {
						JOptionPane.showMessageDialog(gui, e1.getMessage());
					}
				updateHand(board.getActivePlayer());
				updateGraveyard(board.getActivePlayer());
				
			}
			if(button.getText().equals("Cancel") || button.getText().equals("Sacrifice"))
			{
				isSacrificing = false;
				sacrifices=null;
				lastActionButton = null;
				gui.getSidePanel().getCardPanel().getCancel().setVisible(false);
				gui.getSidePanel().getCardPanel().getSacrifice().setVisible(false);
				updateMonsterArea(board.getActivePlayer());
				mouseClicked=false;
			}
			if(button instanceof MonsterButton && p==board.getActivePlayer())
			{
				MonsterCard monster = board.getActivePlayer().getField().getMonstersArea().get(fieldPanel.getMonstersPanel().getMonsterButtons().indexOf(button));
				MonsterButton monsterButton = (MonsterButton)button;
				if(!(sacrifices.contains(monster)))
				{
					sacrifices.add(monster);
					monsterButton.getMonsterName().setForeground(Color.RED);
				}
				else
				{
					sacrifices.remove(monster);
					monsterButton.getMonsterName().setForeground(Color.white);
				}
			}
			mouseClicked=false;
			
			return;
		}
		if(isActivating)
		{
			if(button.getText().equals("Cancel"))
			{
				isActivating = false;
				lastActionButton = null;
				gui.getSidePanel().getCardPanel().getCancel().setVisible(false);
				mouseClicked=false;
				
			}
			if(button instanceof MonsterButton)
			{
				if(lastActionButton.getCard() instanceof ChangeOfHeart && p!=board.getActivePlayer() ||  lastActionButton.getCard() instanceof MagePower && p==board.getActivePlayer() )
				{
					SpellCard spell = (SpellCard) lastActionButton.getCard();
					MonsterCard monster = p.getField().getMonstersArea().get(fieldPanel.getMonstersPanel().getMonsterButtons().indexOf(button));
					try {
						board.getActivePlayer().activateSpell(spell, monster);
					} catch (RuntimeException e1) {
						JOptionPane.showMessageDialog(gui, e1.getMessage());
					}
					gui.getSidePanel().getCardPanel().getCancel().setVisible(false);
					updateAll();
					isActivating = false;
					lastActionButton = null;
				}
					
			}
			mouseClicked=false;
			checkWinner();
			return;
		}
		if(isAttacking)
		{
			if(button.getText().equals("Cancel"))
			{
				isAttacking=false;
				lastActionButton=null;
				gui.getSidePanel().getCardPanel().getCancel().setVisible(false);
				mouseClicked=false;
			}
			if(button instanceof MonsterButton && p == board.getOpponentPlayer())
			{
				MonsterCard monster = board.getOpponentPlayer().getField().getMonstersArea().get(fieldPanel.getMonstersPanel().getMonsterButtons().indexOf(button));
				try {
					board.getActivePlayer().declareAttack((MonsterCard) lastActionButton.getCard(), monster);
				} catch (RuntimeException e1) {
					JOptionPane.showMessageDialog(gui, e1.getMessage());
				}
				updateMonsterArea();
				updateGraveyard();
				updateLifePoints();
				isAttacking=false;
				lastActionButton=null;
				gui.getSidePanel().getCardPanel().getCancel().setVisible(false);
			}
			mouseClicked=false;
			checkWinner();
			return;
		}
		if(button.getText().equals("Change Mode"))
		{
			gui.getSidePanel().getCardPanel().hideButtons();
			ActionButton actionButton = (ActionButton) button;
			try {
				board.getActivePlayer().switchMonsterMode((MonsterCard) actionButton.getCard());
			} catch (RuntimeException e1) {
				JOptionPane.showMessageDialog(gui, e1.getMessage());
			}
			updateMonsterArea(board.getActivePlayer());
			mouseClicked=false;
		}
		if(button.getText().equals("Attack"))
		{
			gui.getSidePanel().getCardPanel().hideButtons();
			ActionButton actionButton = (ActionButton) button;
			if(board.getOpponentPlayer().getField().getMonstersArea().isEmpty())
			{
				try {
					board.getActivePlayer().declareAttack((MonsterCard) actionButton.getCard());
				} catch (RuntimeException e1) {
					JOptionPane.showMessageDialog(gui, e1.getMessage());
				}
				updateLifePoints(board.getOpponentPlayer());
			}
			else
			{
				JOptionPane.showMessageDialog(gui, "Choose a monster to attack");
				isAttacking=true;
				lastActionButton=actionButton;
				gui.getSidePanel().getCardPanel().getCancel().setVisible(true);
			}
			mouseClicked=false;
			
		}
		if(button.getText().equals("Activate Spell"))
		{
	
			gui.getSidePanel().getCardPanel().getActivateSpell().setVisible(false);
			gui.getSidePanel().getCardPanel().getSetSpell().setVisible(false);
			ActionButton actionButton = (ActionButton) button;
			SpellCard spell = (SpellCard) actionButton.getCard();
			if(!(spell instanceof ChangeOfHeart || spell instanceof MagePower))
			{
				try {
					board.getActivePlayer().activateSpell(spell, null);
				} catch (RuntimeException e1) {
					JOptionPane.showMessageDialog(gui, e1.getMessage());
				}
				updateAll();
			}
			else
			{
				if(spell instanceof ChangeOfHeart)
					JOptionPane.showMessageDialog(gui, "Choose one monster from your opponent's monsters area");
				else
					JOptionPane.showMessageDialog(gui, "Choose one monster from your monsters Area");
				isActivating = true;
				lastActionButton=actionButton;
				gui.getSidePanel().getCardPanel().getCancel().setVisible(true);
			}
			try {
				board.getActivePlayer().setSpell((SpellCard) actionButton.getCard());
			} catch (RuntimeException e1) {
				JOptionPane.showMessageDialog(gui, e1.getMessage());
			}
			updateSpellArea(board.getActivePlayer());
			updateHand(board.getActivePlayer());
			mouseClicked=false;
		}
		
		if(button.getText().equals("Set Spell"))
		{
			gui.getSidePanel().getCardPanel().getActivateSpell().setVisible(false);
			gui.getSidePanel().getCardPanel().getSetSpell().setVisible(false);
			ActionButton actionButton = (ActionButton) button;
			try {
				board.getActivePlayer().setSpell((SpellCard) actionButton.getCard());
			} catch (RuntimeException e1) {
				JOptionPane.showMessageDialog(gui, e1.getMessage());
			}
			updateSpellArea(board.getActivePlayer());
			updateHand(board.getActivePlayer());
			mouseClicked=false;
		}
		if(button.getText().equals("Set Monster")||button.getText().equals("Summon Monster"))
		{
			gui.getSidePanel().getCardPanel().getSetMonster().setVisible(false);
			gui.getSidePanel().getCardPanel().getSummonMonster().setVisible(false);
			ActionButton actionButton = (ActionButton) button;
			MonsterCard monster = (MonsterCard) actionButton.getCard();
			int level = monster.getLevel();
			if(level <=4)
			{
				if(button.getText().equals("Set Monster"))
					try {
						board.getActivePlayer().setMonster(monster);
					} catch (RuntimeException e1) {
						JOptionPane.showMessageDialog(gui, e1.getMessage());
					}
				else
					try {
						board.getActivePlayer().summonMonster(monster);
					} catch (RuntimeException e1) {
						JOptionPane.showMessageDialog(gui, e1.getMessage());
					}
				updateMonsterArea(board.getActivePlayer());
				updateHand(board.getActivePlayer());
			}
			else
			{
				int noOfMonsters = level/7 +1;
				JOptionPane.showMessageDialog(gui,"Choose " + noOfMonsters + " monster(s) to sacrifice");
				gui.getSidePanel().getCardPanel().getCancel().setVisible(true);
				gui.getSidePanel().getCardPanel().getSacrifice().setVisible(true);
				isSacrificing=true;
				sacrifices = new ArrayList<MonsterCard>();
				lastActionButton = actionButton;
				
			}
			mouseClicked=false;
		}
		
		if(button.getText().equals("End Turn"))
		{
			p.endTurn();
			setPlayersColors();
			setPhaseColors();
			updateHand();
			updateSpellArea();
			updateMonsterArea();
			updateDeck();
			mouseClicked=false;
			gui.getSidePanel().getCardPanel().getCardView().setIcon(new ImageIcon(""));
			gui.getSidePanel().getCardPanel().getPower().setText("");
		}
		if(button.getText().equals("End Phase"))
		{
			p.endPhase();
			setPlayersColors();
			setPhaseColors();
			updateHand();
			updateSpellArea();
			updateMonsterArea();
			updateDeck();
			
			gui.getSidePanel().getCardPanel().getCardView().setIcon(new ImageIcon(""));
			gui.getSidePanel().getCardPanel().getPower().setText("");
			mouseClicked=false;
		}
		
		if(button instanceof SpellButton && p==board.getActivePlayer())
		{
			gui.getSidePanel().getCardPanel().hideButtons();
			if(button.getText().equals(""))
				return;
			SpellCard card = p.getField().getSpellArea().get(fieldPanel.getSpellsPanel().getSpellButtons().indexOf(button));
			gui.getSidePanel().getCardPanel().getActivateSpell().setVisible(true);
			gui.getSidePanel().getCardPanel().getActivateSpell().setCard(card);
			mouseClicked=true;
			return;
			
		}
		
		if(button instanceof MonsterButton && p==board.getActivePlayer())
		{
			gui.getSidePanel().getCardPanel().hideButtons();
			if(button.getText().equals(""))
				return;
			MonsterCard card = p.getField().getMonstersArea().get(fieldPanel.getMonstersPanel().getMonsterButtons().indexOf(button));
			gui.getSidePanel().getCardPanel().getAttack().setVisible(true);
			gui.getSidePanel().getCardPanel().getAttack().setCard(card);
			gui.getSidePanel().getCardPanel().getChangeMode().setVisible(true);
			gui.getSidePanel().getCardPanel().getChangeMode().setCard(card);
			mouseClicked=true;
			return;
			
		}
		
		if(button instanceof HandButton && p == board.getActivePlayer())
		{
			gui.getSidePanel().getCardPanel().hideButtons();
			Card card = p.getField().getHand().get(fieldPanel.getHandPanel().getHandButtons().indexOf(button));
			if(card instanceof MonsterCard)
			{
				gui.getSidePanel().getCardPanel().getSetMonster().setVisible(true);
				gui.getSidePanel().getCardPanel().getSummonMonster().setVisible(true);
				gui.getSidePanel().getCardPanel().getSetMonster().setCard(card);
				gui.getSidePanel().getCardPanel().getSummonMonster().setCard(card);
				
			}
			else
			{
				gui.getSidePanel().getCardPanel().getActivateSpell().setVisible(true);
				gui.getSidePanel().getCardPanel().getSetSpell().setVisible(true);
				gui.getSidePanel().getCardPanel().getActivateSpell().setCard(card);
				gui.getSidePanel().getCardPanel().getSetSpell().setCard(card);
			}
			
			mouseClicked=true;
			return;
		}
		checkWinner();
		
		mouseClicked=false;
		
	}

		
	public void setPlayersColors()
	{
		if(p1 == board.getActivePlayer())
		{
			gui.getSidePanel().getLowerPlayer().setPlayerMode("active");
			gui.getSidePanel().getUpperPlayer().setPlayerMode("opponent");
		}
		else
		{
			gui.getSidePanel().getLowerPlayer().setPlayerMode("opponent");
			gui.getSidePanel().getUpperPlayer().setPlayerMode("active");
		}
		
		gui.getSidePanel().getLowerPlayer().repaint();
		gui.getSidePanel().getUpperPlayer().repaint();
	}
	
	public void setPhaseColors()
	{
		Phase phase = board.getActivePlayer().getField().getPhase();
		JLabel main1 = gui.getBoardPanel().getPhasePanel().getMain1();
		JLabel main2 = gui.getBoardPanel().getPhasePanel().getMain2();
		JLabel battle = gui.getBoardPanel().getPhasePanel().getBattle();
		main1.setForeground(Color.white);
		main2.setForeground(Color.white);
		battle.setForeground(Color.white);
		if(phase==Phase.MAIN1)
			main1.setForeground(Color.GREEN);
		else
			if(phase==Phase.MAIN2)
				main2.setForeground(Color.GREEN);
			else
				battle.setForeground(Color.green);
			
		
	}
		

	public void updateMonsterArea(Player p)
	{
		ArrayList<MonsterCard> monsters = p.getField().getMonstersArea();
		ArrayList<CardButton> monsterButtons; 
		if(p == p1)
			monsterButtons = gui.getBoardPanel().getLowerFieldPanel().getMonstersPanel().getMonsterButtons();
		else
			monsterButtons = gui.getBoardPanel().getUpperFieldPanel().getMonstersPanel().getMonsterButtons();
		for(int i = 0; i < monsters.size(); i++)
		{
			MonsterCard monster = monsters.get(i);
			MonsterButton button = (MonsterButton) monsterButtons.get(i);
			button.getMonsterMode().setText(monster.getMode()==Mode.ATTACK?"Attack":"Defense");
			button.setText(monster.getName());
			if(p==board.getActivePlayer() || monster.getMode()==Mode.ATTACK)
			{
				button.getMonsterName().setText(monster.getName());
				button.getPower().setText("ATK/"+monster.getAttackPoints()+" DEF/"+monster.getDefensePoints());
			}
			else
			{
				button.getMonsterName().setText("Monster");
				button.getPower().setText("");
				
			}
			button.getMonsterName().setForeground(Color.WHITE);
			
		
		}
		for(int i = monsters.size(); i < 5; i++)
		{
			MonsterButton button = (MonsterButton) monsterButtons.get(i);
			button.setText("");
			button.getMonsterName().setText("");
			button.getPower().setText("");
			button.getMonsterMode().setText("");
			
		}			
	}
	
	public void updateMonsterArea()
	{
		updateMonsterArea(p1);
		updateMonsterArea(p2);
	}
	
	public void updateSpellArea(Player p)
	{
		ArrayList<SpellCard> spells = p.getField().getSpellArea();
		ArrayList<CardButton> spellButtons; 
		if(p == p1)
			spellButtons = gui.getBoardPanel().getLowerFieldPanel().getSpellsPanel().getSpellButtons();
		else
			spellButtons = gui.getBoardPanel().getUpperFieldPanel().getSpellsPanel().getSpellButtons();
		for(int i = 0; i < spells.size(); i++)
		{
			SpellButton button = (SpellButton) spellButtons.get(i);
			button.setText(spells.get(i).getName());
			if(p == board.getActivePlayer())
				button.getSpellName().setText(spells.get(i).getName());
			else
				button.getSpellName().setText("Spell");
			
		}
		for(int i = spells.size(); i < 5; i++)
		{
			SpellButton button = (SpellButton) spellButtons.get(i);
			button.setText("");
			button.getSpellName().setText("");
		}			
	}
	
	public void updateSpellArea()
	{
		updateSpellArea(p1);
		updateSpellArea(p2);
	}
	
	
	
	public void updateHand(Player p)
	{
		ArrayList<Card> hand = p.getField().getHand();
		ArrayList<CardButton> handButtons;
		if(p == p1)
			handButtons = gui.getBoardPanel().getLowerFieldPanel().getHandPanel().getHandButtons();
		else
			handButtons = gui.getBoardPanel().getUpperFieldPanel().getHandPanel().getHandButtons();
		int i = 0;
		if(p == board.getActivePlayer())
			for(; i < hand.size(); i++)
			{
				Card card = hand.get(i);
				CardButton button = handButtons.get(i);
				button.setText(card.getName());
				button.setVisible(true);
				if(card instanceof MonsterCard)
				{
					MonsterCard monster=(MonsterCard)card;
					button.getPower().setText("ATK/"+monster.getAttackPoints()+" DEF/"+monster.getDefensePoints());
				}
				else
					button.getPower().setText("");
				
			}
		for(; i < 20; i++)
		{
			CardButton button = handButtons.get(i);
			button.setVisible(false);
		}			
	}
	
	public void updateHand()
	{
		updateHand(p1);
		updateHand(p2);
	}
	
	public void updateGraveyard(Player p)
	{
		ArrayList<Card> graveyard = p.getField().getGraveyard();
		JLabel graveyardLabel;
		if(p == p1)
			graveyardLabel = gui.getBoardPanel().getLowerFieldPanel().getMonstersPanel().getGraveyardLabel();
		else
			graveyardLabel = gui.getBoardPanel().getUpperFieldPanel().getMonstersPanel().getGraveyardLabel();
		if(graveyard.size()==0)
			graveyardLabel.setText("Graveyard");
		else
			graveyardLabel.setText(graveyard.get(graveyard.size()-1).getName());
	}
	
	public void updateGraveyard()
	{
		updateGraveyard(p1);
		updateGraveyard(p2);
	}
	
	public void updateDeck(Player p)
	{
		ArrayList<Card> deck = p.getField().getDeck().getDeck();
		JLabel deckLabel;
		if(p == p1)
			deckLabel = gui.getBoardPanel().getLowerFieldPanel().getSpellsPanel().getDeckLabel();
		else
			deckLabel = gui.getBoardPanel().getUpperFieldPanel().getSpellsPanel().getDeckLabel();
		
		deckLabel.setText("" +deck.size() + " Cards");
	}
	
	public void updateDeck()
	{
		updateDeck(p1);
		updateDeck(p2);
	}
	
	public void updateLifePoints(Player p)
	{
		if(p==p1)
			gui.getSidePanel().getLowerPlayer().getLifePoints().setText(""+p.getLifePoints() + " Pts");
		else
			gui.getSidePanel().getUpperPlayer().getLifePoints().setText(""+p.getLifePoints() + " Pts");
	}
	
	public void updateLifePoints()
	{
		updateLifePoints(p1);
		updateLifePoints(p2);
	}
	
	public void updateAll()
	{
		updateHand();
		updateMonsterArea();
		updateSpellArea();
		updateGraveyard();
		updateDeck();
		updateLifePoints();
		setPlayersColors();
		setPhaseColors();
	}
	
	public void checkWinner()
	{
		if(board.getWinner()==null)
			return;
		
		Object[] options = {"Yes","No"};
		int n = JOptionPane.showOptionDialog(gui,"The winner is "
		+ board.getWinner().getName() + "\nDo you want to play again? ","GAME OVER!",
		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
		if(n==0)
		{
			try {
				p1 = new Player(p1.getName());
				p2 = new Player(p2.getName());
			} catch (IOException e) {
			} catch (UnexpectedFormatException e) {
			}
			
			board.startGame(p1, p2);
			updateAll();
		}
		else
		{
			System.exit(0);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getComponent() instanceof CardButton)
			mouseClicked=true;
		JButton button = (JButton)e.getSource();
		String name=button.getText();
		if(button instanceof MonsterButton)
			name=((MonsterButton)button).getMonsterName().getText();
		if(button instanceof SpellButton)
			name=((SpellButton)button).getSpellName().getText();
		String x = "resources/images/cards/"+name+".png";
		if(button instanceof CardButton)
		{
			CardButton card = (CardButton)button;
			gui.getSidePanel().getCardPanel().getPower().setText(card.getPower().getText());
			gui.getSidePanel().getCardPanel().getCardView().setIcon(new ImageIcon(x));
			GUI.playSound("card");
		}
		else
		{
			GUI.playSound("Phase");
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		if(!mouseClicked)
		{
			JButton button = (JButton)e.getSource();
			String name=button.getText();
			if(button instanceof MonsterButton)
				name=((MonsterButton)button).getMonsterName().getText();
			if(button instanceof SpellButton)
				name=((SpellButton)button).getSpellName().getText();
			String x = "resources/images/cards/"+name+".png";
			gui.getSidePanel().getCardPanel().getCardView().setIcon(new ImageIcon(x));
			if(button instanceof CardButton)
			{
				CardButton card = (CardButton)button;
				gui.getSidePanel().getCardPanel().getCardView().setIcon(new ImageIcon(x));
				gui.getSidePanel().getCardPanel().getPower().setText(card.getPower().getText());
			}
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
