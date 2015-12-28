package eg.edu.guc.yugioh.exceptions;

public class UnknownSpellCardException extends UnexpectedFormatException {

	private String unknownSpell;
	
	public UnknownSpellCardException()
	{
		super();
	}
	
	public UnknownSpellCardException(String m)
	{
		super(m);
	}
	
	public UnknownSpellCardException(String sourceFile, int sourceLine, String unknownSpell)
	{
		super(sourceFile, sourceLine);
		this.unknownSpell = unknownSpell;
	}

	public String getUnknownSpell() {
		return unknownSpell;
	}

	public void setUnknownSpell(String unknwonSpell) {
		this.unknownSpell = unknwonSpell;
	}
	
}
