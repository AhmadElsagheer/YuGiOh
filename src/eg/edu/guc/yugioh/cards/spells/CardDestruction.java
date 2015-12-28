package eg.edu.guc.yugioh.cards.spells;
import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class CardDestruction extends SpellCard {
	
	public CardDestruction(String name, String description)
	{
		super(name, description);
	}
	
	public void action(MonsterCard monster)
	{
		Field curField = getBoard().getActivePlayer().getField();
		int size = curField.removeFromHand();
		curField.addNCardsToHand(size);
		
		curField = getBoard().getOpponentPlayer().getField();
		size = curField.removeFromHand();
		curField.addNCardsToHand(size);	
	}
}
