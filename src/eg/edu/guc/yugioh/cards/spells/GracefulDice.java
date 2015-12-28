package eg.edu.guc.yugioh.cards.spells;
import java.util.Random;
import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class GracefulDice extends SpellCard {

	public GracefulDice(String name, String description)
	{
		super(name, description);
	}
	
	public void action(MonsterCard monster)
	{
		int x = ((new Random()).nextInt(10) + 1)*100;
		Field curField = getBoard().getActivePlayer().getField();
		for(int i = 0; i < curField.getMonstersArea().size(); i++)
		{
			MonsterCard curMonster = curField.getMonstersArea().get(i);
			curMonster.setAttackPoints(curMonster.getAttackPoints()+x);
			curMonster.setDefensePoints(curMonster.getDefensePoints()+x);
		}
	}
}
