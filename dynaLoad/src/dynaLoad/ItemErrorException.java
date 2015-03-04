package dynaLoad;

public class ItemErrorException extends Exception {
	static final long serialVersionUID = 0;

	private String message;
	
	public ItemErrorException()
	{
		this.message = "";
	}
	
	public ItemErrorException( String sMess )
	{
		this.message = sMess;
	}
	
	@Override
	public String getMessage()
	{
		return this.message;
	}
}
