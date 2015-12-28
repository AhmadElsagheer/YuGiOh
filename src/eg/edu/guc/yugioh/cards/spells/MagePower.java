package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.cards.MonsterCard;


public class MagePower extends SpellCard
{

	public MagePower(String name, String description)
	{
		super(name, description);
	}
	
	public void action(MonsterCard monster)
	{
		int newPoints= getBoard().getActivePlayer().getField().getSpellArea().size()*500;
		monster.setAttackPoints(monster.getAttackPoints()+newPoints);
		monster.setDefensePoints(monster.getDefensePoints()+newPoints);
	}
}
