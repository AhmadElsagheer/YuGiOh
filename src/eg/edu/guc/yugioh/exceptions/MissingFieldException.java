package eg.edu.guc.yugioh.exceptions;

public class MissingFieldException extends UnexpectedFormatException {

	
	public MissingFieldException()
	{
		super();
	}
	
	public MissingFieldException(String m)
	{
		super(m);
	}
	
	public MissingFieldException(String sourceFile, int sourceLine)
	{
		super(sourceFile, sourceLine);
	}
}
