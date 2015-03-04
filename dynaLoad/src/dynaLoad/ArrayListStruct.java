package dynaLoad;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ArrayListStruct implements itemOp 
{

	private ArrayList<String> _struct;
	static final String _engine = "AL";
	static final String _magic = "!AL!";
	
	public ArrayListStruct()
	{
		_struct = new ArrayList<String>();
	}
	
	@Override
	public void addItem(dataItem di) throws ItemErrorException {
		// TODO Auto-generated method stub

		if( _struct.size() > 0 && di.getKey() > _struct.size())
			throw new ItemErrorException("Out of bounds");
		// TODO how to deal with this - sparse array
		// autogrow?
		// if autogrow and NULL --> insert
		// also beware @ get, if returning null it means item not there
		
		else
			_struct.add( di.getKey(), di.getItem());
		
	}

	@Override
	public String getItem(int iKey) throws ItemErrorException 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delItem(int iKey) throws ItemErrorException 
	{
		// TODO Auto-generated method stub

	}

	public void serialize(String store) throws FileNotFoundException,
			IOException 
	{
		// TODO Auto-generated method stub
		
		return;
		
	}

	@Override
	public void serialize() throws FileNotFoundException, IOException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int setStore(String store) 
	{
		// TODO Auto-generated method stub
		
		return this.getSize();
		
	}

	@SuppressWarnings("static-access")
	public String getEngine() 
	{
		// TODO Auto-generated method stub
		return this._engine;
	}

	@Override
	public int getSize() {
		
		return this._struct.size();
	}

}
