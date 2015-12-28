package eg.edu.guc.yugioh.exceptions;

public class MultipleMonsterAdditionException extends RuntimeException
{
	public MultipleMonsterAdditionException()
	{
		super("You can't add more than 1 monster in the same turn!");
	}
	
	public MultipleMonsterAdditionException(String m)
	{
		super(m);
	}
	
}
