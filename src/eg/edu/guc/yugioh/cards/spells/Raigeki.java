package eg.edu.guc.yugioh.cards.spells;


import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class Raigeki extends SpellCard{

	public Raigeki(String name, String description)
	{
		super(name, description);
	}
	
	public void action(MonsterCard monster)
	{
		Field curField = getBoard().getOpponentPlayer().getField();
		curField.removeMonsterToGraveyard(getBoard().getOpponentPlayer().getField().getMonstersArea());
	}
}
