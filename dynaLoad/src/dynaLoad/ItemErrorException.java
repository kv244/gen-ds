package dynaLoad;

public class ItemErrorException extends Exception {
	static final long serialVersionUID = 0;
	
	public ItemErrorException()
	{
		super();
	}
	
	public ItemErrorException( String sMess )
	{
		super(sMess);
	}
	
	public ItemErrorException(Throwable cause) { super(cause); }
	
	public ItemErrorException(String message, Throwable cause) { super(message, cause); }
	
}
