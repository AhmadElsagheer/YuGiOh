package eg.edu.guc.yugioh.board.player;

import java.io.IOException;
import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.*;


public class Player implements Duelist {

	private final String name;
	private int lifePoints;
	private Field field;
	
	
	public Player(String name) throws IOException, UnexpectedFormatException
	{
		this.name = name;
		this.lifePoints = 8000;
		this.field=new Field();
		
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getLifePoints()
	{
		return this.lifePoints;
	}
	
	public void setLifePoints(int lifePoints)
	{
		this.lifePoints = lifePoints;
	}
	
	public Field getField()
	{
		return this.field;
	}
	
	public boolean summonMonster(MonsterCard monster) throws NoMonsterSpaceException, MultipleMonsterAdditionException, WrongPhaseException
	{
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer())
			return false;
		field.addMonsterToField(monster, Mode.ATTACK, null);
		return field.isAddedSuccessfully();
	}
	
	public boolean summonMonster(MonsterCard monster, ArrayList<MonsterCard> sacrifices) throws NoMonsterSpaceException, MultipleMonsterAdditionException, WrongPhaseException
	{
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer())
			return false;
		field.addMonsterToField(monster, Mode.ATTACK, sacrifices);
		return field.isAddedSuccessfully();
	}
	
	public boolean setMonster(MonsterCard monster) throws NoMonsterSpaceException, MultipleMonsterAdditionException, WrongPhaseException
	{
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer())
			return false;
		field.addMonsterToField(monster, Mode.DEFENSE, null);
		return field.isAddedSuccessfully();
	}
	
	public boolean setMonster(MonsterCard monster, ArrayList<MonsterCard> sacrifices) throws NoMonsterSpaceException, MultipleMonsterAdditionException, WrongPhaseException
	{
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer())
			return false;
		field.addMonsterToField(monster, Mode.DEFENSE, sacrifices);
		return field.isAddedSuccessfully();
	}
	
	public boolean setSpell(SpellCard spell) throws NoSpellSpaceException, WrongPhaseException, NoMonsterSpaceException
	{
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer())
			return false;
		field.addSpellToField(spell, null, true);
		return field.isAddedSuccessfully();
	}
	
	public boolean activateSpell(SpellCard spell, MonsterCard monster) throws WrongPhaseException, NoSpellSpaceException, NoMonsterSpaceException
	{
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer())
			return false;
		field.activateSetSpell(spell, monster);
		if(field.isAddedSuccessfully())
			return true;
		
		field.addSpellToField(spell, null, false);
		return field.isAddedSuccessfully();
	}
	public boolean declareAttack(MonsterCard activeMonster, MonsterCard opponentMonster) throws DefenseMonsterAttackException, MonsterMultipleAttackException, WrongPhaseException
	{
			
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer())
			return false;
		if(this!=Card.getBoard().getActivePlayer())
			return false;
		if(activeMonster.getMode()==Mode.DEFENSE)
			throw new DefenseMonsterAttackException();
		if(activeMonster.isAttacked())
			throw new MonsterMultipleAttackException();
		if(field.getPhase()!=Phase.BATTLE)
			throw new WrongPhaseException();
		activeMonster.setAttacked(true);
		activeMonster.action(opponentMonster);
		
		return true;
	}
	public boolean declareAttack(MonsterCard activeMonster) throws DefenseMonsterAttackException, MonsterMultipleAttackException, WrongPhaseException
	{
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer())
			return false;
		if(Card.getBoard().getOpponentPlayer().getField().getMonstersArea().isEmpty())
			return declareAttack(activeMonster,null);
	    return false;
	}
	public void addCardToHand()
	{
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer())
			return;
		field.addCardToHand();
		
	}
	public void addNCardsToHand(int n)
	{
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer())
			return;
		field.addNCardsToHand(n);
		
	}
	
	public void endPhase()
	{
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer())
			return;
		if(field.getPhase()==Phase.MAIN1)
		{
			field.setPhase(Phase.BATTLE);
		}
		else
			if(field.getPhase()==Phase.BATTLE)
				field.setPhase(Phase.MAIN2);
			else
				endTurn();
	}
	public boolean endTurn()
	{
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer())
			return false;
		field.setPhase(Phase.MAIN1);
		field.resetMonsterAttack();
		field.resetMonsterMode();
		Card.getBoard().nextPlayer();
		
		return true;
	}

	public boolean switchMonsterMode(MonsterCard monster) throws WrongPhaseException
	{
		if(Card.getBoard().getWinner()!=null || this!=Card.getBoard().getActivePlayer() ||!field.getMonstersArea().contains(monster))
			return false;
		if(field.getPhase()==Phase.BATTLE)
			throw new WrongPhaseException();
		if(monster.isModeChanged())
			return false;
		monster.setModeChanged(true);
		monster.setMode(monster.getMode()==Mode.ATTACK?Mode.DEFENSE:Mode.ATTACK);
		return true;
	}
	
}
