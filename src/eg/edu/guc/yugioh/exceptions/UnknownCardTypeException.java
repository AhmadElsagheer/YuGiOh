package eg.edu.guc.yugioh.exceptions;

public class UnknownCardTypeException extends UnexpectedFormatException {

	private String unknownType;
	
	public UnknownCardTypeException()
	{
		super();
	}
	
	public UnknownCardTypeException(String m)
	{
		super(m);
	}
	
	public UnknownCardTypeException(String sourceFile, int sourceLine, String unknownType)
	{
		super(sourceFile, sourceLine);
		this.unknownType=unknownType;
	}

	public String getUnknownType() {
		return unknownType;
	}

	public void setUnknownType(String unknownType) {
		this.unknownType = unknownType;
	}
}
