package dynaLoad;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ArrayListStruct implements itemOp 
{

	private ArrayList<String> _struct;
	static final String _engine = "dynaLoad.ArrayListStruct";
	static final String _magic = "!AL!";
	private String _store;
	
	// ctor, will set store to the default value
	// and attempt to populate structure
	public ArrayListStruct()
	{
		this._struct = new ArrayList<String>();
		this._store = itemOp.storeDef;
		loadStore();
	}
	
	@Override
	public void addItem( dataItem di ) throws ItemErrorException 
	{
	
		if( _struct.size() > 0 && di.getKey() > _struct.size())
			throw new ItemErrorException( "Out of bounds" );
		
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


	@Override
	public void serialize() throws FileNotFoundException, IOException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int setStore( String store ) 
	{
		this._store = store;
		this.loadStore(); 
		return this.getSize();
		
	}

	// populate store
	private void loadStore()
	{
		
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
