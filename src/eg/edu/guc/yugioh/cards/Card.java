package eg.edu.guc.yugioh.cards;
import eg.edu.guc.yugioh.board.*;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;


abstract public class Card implements Cloneable {
	private final String name;
	private final String description;
	private boolean isHidden = true;
	private Location location=Location.DECK;
	private static Board board;
	
	public Card(String name, String description)
	{
		this.name=name;
		this.description=description;
	}
	abstract public void action(MonsterCard monster);
	
	public String getName()
	{
		return name;
	}
	public String getDescription()
	{
		return description;
	}
	public boolean isHidden()
	{
		return isHidden;
	}
	public Location getLocation()
	{
		return location;
	}
	public static Board getBoard()
	{
		return board; //STATIC
	}
	public void setHidden(boolean isHidden)
	{
		this.isHidden=isHidden;
	}
	public void setLocation(Location location)
	{
		this.location=location;
	}
	public static void setBoard(Board board)
	{
		Card.board=board; //STATIC
	}
	
	public Object clone()
	{
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	


}
