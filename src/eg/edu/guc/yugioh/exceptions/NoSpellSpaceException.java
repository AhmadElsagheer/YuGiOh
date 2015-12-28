package eg.edu.guc.yugioh.exceptions;

public class NoSpellSpaceException extends NoSpaceException
{
	public NoSpellSpaceException()
	{
		super("There's no space in your spells area!");
	}
	
	public NoSpellSpaceException(String m)
	{
		super(m);
	}
	
}
