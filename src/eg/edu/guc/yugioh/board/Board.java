package eg.edu.guc.yugioh.board;
import java.util.Random;

import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;


public class Board {

	private Player activePlayer;
	private Player opponentPlayer;
	private Player winner;
	
	public Board()
	{
		Card.setBoard(this);
	}
	public void setActivePlayer(Player p)
	{
		this.activePlayer=p;
	}
	public Player getActivePlayer()
	{
		return activePlayer;
	}
	public void setOpponentPlayer(Player p)
	{
		this.opponentPlayer=p;
	}
	public Player getOpponentPlayer()
	{
		return this.opponentPlayer;
	}
	public void setWinner(Player winner)
	{
		this.winner = winner;
	}
	
	public Player getWinner()
	{
		return winner;
	}
	
	public void whoStarts(Player p1, Player p2)
	{
		int n = ((new Random()).nextInt(2))+1;
		if(n==1)
		{
			this.setActivePlayer(p1);
			this.setOpponentPlayer(p2);
		}
		else
		{
			this.setActivePlayer(p2);
			this.setOpponentPlayer(p1);
		}
	}
	public void startGame(Player p1, Player p2)
	{
		this.whoStarts(p1, p2);
		activePlayer.getField().addNCardsToHand(6);
		opponentPlayer.getField().addNCardsToHand(5);
		winner=null;
	}
	public void nextPlayer()
	{
		Player temp = this.activePlayer;
		this.activePlayer=this.opponentPlayer;
		this.opponentPlayer=temp;
		activePlayer.getField().addCardToHand();
		this.activePlayer.getField().resetAddedMonster();
		
	}

	
	
	
	
}
