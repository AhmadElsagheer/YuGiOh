package eg.edu.guc.yugioh.exceptions;

public class MonsterMultipleAttackException extends RuntimeException
{
	public MonsterMultipleAttackException()
	{
		super("You can't attack with a monster in the same turn more than once!");
	}
	
	public MonsterMultipleAttackException(String m)
	{
		super(m);
	}
	
}
