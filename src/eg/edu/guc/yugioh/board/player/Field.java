package eg.edu.guc.yugioh.board.player;


import java.io.IOException;
import java.util.*;

import eg.edu.guc.yugioh.cards.*;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.*;

public class Field {

	private Phase phase = Phase.MAIN1;
	private ArrayList<MonsterCard> monstersArea;
	private ArrayList<SpellCard> spellArea;
	private Deck deck;
	private ArrayList<Card> hand;
	private ArrayList<Card> graveyard;
	private boolean addedMonster;
	private boolean addedSuccessfully;
	
	public Field() throws IOException, UnexpectedFormatException
	{
		monstersArea = new ArrayList<MonsterCard>(5);
		spellArea = new ArrayList<SpellCard>(5);
		deck = new Deck();
		hand = new ArrayList<Card>();
		graveyard = new ArrayList<Card>();
	}
	
	public void addMonsterToField(MonsterCard monster, Mode m, boolean isHidden) 
	{
		
		if(monstersArea.size()!=5)
		{
			monster.setMode(m);
			monster.setHidden(isHidden);
			monster.setLocation(Location.FIELD);
			hand.remove(monster);
			monstersArea.add(monster);
			
		}
		else
			throw new NoMonsterSpaceException();
	}
	
	public void addMonsterToField(MonsterCard monster, Mode m, ArrayList<MonsterCard> sacrifices) throws NoMonsterSpaceException, MultipleMonsterAdditionException, WrongPhaseException
	{
		addedSuccessfully=false;
		if(monstersArea.size()==5)
			throw new NoMonsterSpaceException();
		if(addedMonster==true)
			throw new MultipleMonsterAdditionException();
		if(!hand.contains(monster))
			return;
		if( phase==Phase.BATTLE )
			throw new WrongPhaseException();
		int n = monster.getLevel();
		if(n>=1 && n<=4 && sacrifices!=null)
			return;
		if(n>=5 && n<=6 && sacrifices.size()!=1)
			return;
		if(n>=7 && n<=8 && sacrifices.size()!=2)
			return;
		
		addedSuccessfully=true;
		this.removeMonsterToGraveyard(sacrifices);	
		this.addMonsterToField(monster, m, (m==Mode.ATTACK)?false:true);
		this.addedMonster=true;
	}
	
	public void removeMonsterToGraveyard(MonsterCard monster)
	{
		if(!(monstersArea.contains(monster)))
			return;
		monstersArea.remove(monster);
		monster.setLocation(Location.GRAVEYARD);
		monster.setAttacked(false);
		graveyard.add(monster);
	}
	
	public void removeMonsterToGraveyard(ArrayList<MonsterCard> monsters)
	{
		if(monsters==null)
			return;
		int n = monsters.size();
		for(int i =0; i<n;i++)
			this.removeMonsterToGraveyard(monsters.get(n-1-i));
	}
	
	public void addSpellToField(SpellCard action, MonsterCard monster, boolean hidden) throws WrongPhaseException, NoSpellSpaceException, NoMonsterSpaceException
	{
		addedSuccessfully=false;
		if(this.spellArea.size()==5)
			throw new NoSpellSpaceException();
		if(!hand.contains(action))
			return;
		if(phase==Phase.BATTLE)
			throw new WrongPhaseException();
		addedSuccessfully=true;
		action.setHidden(hidden);
		action.setLocation(Location.FIELD);
		hand.remove(action);
		spellArea.add(action);
		if(!hidden)
			this.activateSetSpell(action, monster);
	}
	
	public void activateSetSpell(SpellCard action, MonsterCard monster) throws WrongPhaseException, NoMonsterSpaceException
	{
		addedSuccessfully=false;
		if(action.getLocation()!=Location.FIELD)
			return;
		if(phase==Phase.BATTLE)
			throw new WrongPhaseException();
		addedSuccessfully=true;
		action.action(monster);
		this.removeSpellToGraveyard(action);
	}
	
	public void removeSpellToGraveyard(SpellCard spell)
	{
		
		if(!spellArea.contains(spell))
			return;
		spellArea.remove(spell);
		spell.setLocation(Location.GRAVEYARD);
		graveyard.add(spell);
	}
	
	public void removeSpellToGraveyard(ArrayList<SpellCard> spells)
	{
		int n = spells.size();
		for(int i =0; i<n;i++)
			this.removeSpellToGraveyard(spells.get(n-1-i));
	}
	
	public void addCardToHand()
	{
		Card card = deck.drawOneCard();
		if(card==null)
			return;
		card.setLocation(Location.HAND);
		hand.add(card);
	}
	
	public void addNCardsToHand(int n)
	{
		for(int i = 0; i<n; i++)
			addCardToHand();
	}
	
	public Phase getPhase() {
		return phase;
	}
	
	public void setPhase(Phase phase) {
		this.phase = phase;
	}
	
	public ArrayList<MonsterCard> getMonstersArea() {
		return monstersArea;
	}
	
	public ArrayList<SpellCard> getSpellArea() {
		return spellArea;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public ArrayList<Card> getGraveyard() {
		return graveyard;
	}

	public Deck getDeck() {
		return deck;
	}
	
	public boolean getAddedMonster()
	{
		return addedMonster;
	}
	
	public void resetAddedMonster() {
		this.addedMonster = false;
	}
	
	public int removeFromHand()
	{
		int size = hand.size();
		for(int i =0; i<size;i++)
		{
			Card card = hand.remove(size-i-1);
			card.setLocation(Location.GRAVEYARD);
			graveyard.add(card);
		}
		return size;
	}

	public boolean isAddedSuccessfully() {
		return addedSuccessfully;
	}
	
	public void resetMonsterAttack()
	{
		for(int i =0; i<monstersArea.size();i++)
			monstersArea.get(i).setAttacked(false);
	}
	
	public void resetMonsterMode()
	{
		for(int i =0; i<monstersArea.size();i++)
			monstersArea.get(i).setModeChanged(false);
	}

	
	
	
	
}
