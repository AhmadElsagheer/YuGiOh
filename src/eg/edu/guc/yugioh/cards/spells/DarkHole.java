package eg.edu.guc.yugioh.cards.spells;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class DarkHole extends Raigeki {

	public DarkHole(String name, String description)
	{
		super(name, description);
	}
	
	public void action(MonsterCard monster)
	{
		super.action(monster);
		getBoard().getActivePlayer().getField().removeMonsterToGraveyard(getBoard().getActivePlayer().getField().getMonstersArea());	
	}
}
