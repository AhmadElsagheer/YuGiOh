package eg.edu.guc.yugioh.exceptions;

public class NoMonsterSpaceException extends NoSpaceException
{
	public NoMonsterSpaceException()
	{
		super("There's no space in your monsters area!");
	}
	
	public NoMonsterSpaceException(String m)
	{
		super(m);
	}
	
}
