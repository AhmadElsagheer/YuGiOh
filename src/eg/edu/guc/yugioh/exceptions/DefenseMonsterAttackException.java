package eg.edu.guc.yugioh.exceptions;

public class DefenseMonsterAttackException extends RuntimeException
{
	public DefenseMonsterAttackException()
	{
		super("You can't attack with a monster in defense mode!");
	}
	
	public DefenseMonsterAttackException(String m)
	{
		super(m);
	}
	
}
