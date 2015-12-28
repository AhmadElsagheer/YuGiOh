package eg.edu.guc.yugioh.cards.spells;
import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;

public class MonsterReborn extends SpellCard
{

	public MonsterReborn(String name, String description)
	{
		super(name, description);
	}
	
	public void action(MonsterCard monster)
	{
		if(getBoard().getActivePlayer().getField().getMonstersArea().size()==5)
			throw new NoMonsterSpaceException();
		boolean addedMonster = getBoard().getActivePlayer().getField().getAddedMonster();
		getBoard().getActivePlayer().getField().resetAddedMonster();
		MonsterCard first = getStrongest(getBoard().getActivePlayer().getField().getGraveyard(),null,0);
		MonsterCard second = getStrongest(getBoard().getOpponentPlayer().getField().getGraveyard(),null,0);
		if(first == null && second == null)
			return;
		if(first==null)
		{
			getBoard().getOpponentPlayer().getField().getGraveyard().remove(second);
			getBoard().getActivePlayer().getField().addMonsterToField(second, Mode.ATTACK, false);
			return;
		}
		if(second == null)
		{
			getBoard().getActivePlayer().getField().getGraveyard().remove(first);
			getBoard().getActivePlayer().getField().addMonsterToField(first, Mode.ATTACK, false);
		}
		if(first.getAttackPoints()>second.getAttackPoints())
		{
			getBoard().getActivePlayer().getField().getGraveyard().remove(first);
			getBoard().getActivePlayer().getField().addMonsterToField(first, Mode.ATTACK, false);
		}
		else
		{
			getBoard().getOpponentPlayer().getField().getGraveyard().remove(second);
			getBoard().getActivePlayer().getField().addMonsterToField(second, Mode.ATTACK, false);
		}
		if(!addedMonster)
			getBoard().getActivePlayer().getField().resetAddedMonster();
	}

    
	public static MonsterCard getStrongest(ArrayList<Card> graveyard, MonsterCard strongestMonster ,int i)
	{
		if(i==graveyard.size())
			return strongestMonster;
		Card card = graveyard.get(i);
		if(card instanceof MonsterCard && (strongestMonster==null || ((MonsterCard) card).getAttackPoints()>strongestMonster.getAttackPoints()))
			strongestMonster=(MonsterCard)card;
		return getStrongest(graveyard, strongestMonster,i+1);	
	}
}
