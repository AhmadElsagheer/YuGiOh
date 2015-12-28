package eg.edu.guc.yugioh.board.player;
import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.*;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.DefenseMonsterAttackException;
import eg.edu.guc.yugioh.exceptions.MonsterMultipleAttackException;
import eg.edu.guc.yugioh.exceptions.MultipleMonsterAdditionException;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;

public interface Duelist {

	public boolean summonMonster(MonsterCard monster);
	public boolean summonMonster(MonsterCard monster, ArrayList<MonsterCard> sacrifices);
	public boolean setMonster(MonsterCard monster) ;
	public boolean setMonster(MonsterCard monster, ArrayList<MonsterCard> sacrifices) ;
	public boolean setSpell(SpellCard spell) ;
	public boolean activateSpell(SpellCard spell, MonsterCard monster) ;
	public boolean declareAttack(MonsterCard activeMonster, MonsterCard opponentMonster);
	public boolean declareAttack(MonsterCard activeMonster) ;
	public void addCardToHand();
	public void addNCardsToHand(int n);
	public void endPhase();
	public boolean endTurn();
	public boolean switchMonsterMode(MonsterCard monster) ;
	
}
