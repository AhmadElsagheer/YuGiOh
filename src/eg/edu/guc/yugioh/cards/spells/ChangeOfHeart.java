package eg.edu.guc.yugioh.cards.spells;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;


public class ChangeOfHeart extends SpellCard {

	public ChangeOfHeart(String name, String description)
	{
		super(name, description);
	}
	
	public void action(MonsterCard monster)
	{
		if(getBoard().getActivePlayer().getField().getMonstersArea().size()==5)
			return;
		getBoard().getOpponentPlayer().getField().getMonstersArea().remove(monster);
		getBoard().getActivePlayer().getField().addMonsterToField(monster, monster.getMode(), monster.isHidden());
	}
}
