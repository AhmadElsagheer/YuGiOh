package eg.edu.guc.yugioh.exceptions;

public class WrongPhaseException extends RuntimeException
{
	public WrongPhaseException()
	{
		super("You can't perform this action in this phase!");
	}
	
	public WrongPhaseException(String m)
	{
		super(m);
	}
	
}
