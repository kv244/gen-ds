package dynaLoad;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

public class ArrayListStruct implements itemOp 
{

	private ArrayList<String> _struct;
	static final String _engine = "dynaLoad.ArrayListStruct";
	static final String _magic = "!AL!";
	private String _store;
	public static final String _null = "__null__";
	// for this engine, query to see if item is deleted or not
	
	
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
		// starts at 0 until size - 1
		// insert < 0 will fail
		// insert >= 0 will succeed, so we have to deal with the current
		// index
		// this is probably not the best data structure to use, 
		// it is easier to just add() without index
		
		if( di.getKey() < 0 )
			throw new ItemErrorException( "Engine does not support negative index." );
		
		int diff = di.getKey() - _struct.size(); 
		
		if( diff >= 0 ) // we must pad
		{ 
			for( int i = 0; i <= diff; i++ )
				_struct.add( _struct.size(), _null );
		}
		
		// deal with the case when this is a placeholder
		
		if( _struct.get( di.getKey()).equals( _null ))
		{
			_struct.remove( di.getKey());
		}
		
		_struct.add( di.getKey(), di.getItem());
		
	}

	@Override
	public String getItem(int iKey) throws ItemErrorException 
	{
		if( iKey >= _struct.size())
			throw new ItemErrorException( "Item past size: " + Integer.toString( iKey ) + 
					" " + Integer.toString(_struct.size()));
		
		return _struct.get( iKey );
	}

	@Override
	public void delItem(int iKey) throws ItemErrorException 
	{
		// this is just setting the item to _null, as removing it will shift the index

		if( iKey >= _struct.size()) return; // fail silently
		_struct.remove( iKey );
		_struct.add( iKey, _null );
		
	}


	@SuppressWarnings("static-access")
	@Override
	public String serialize() throws FileNotFoundException, IOException 
	{
		String res = "Written to " + _store + " records ";
		PrintWriter out = new PrintWriter( this._store );
		out.write( this._magic );
		out.write( "\n" );
		
		
		for( int i = 0; i < _struct.size(); i++ )
		{
			String val = _struct.get(i); 
			if( !val.equals( _null ))
			{
				String line = "{" + Integer.toString(i) + "__" + val + "}"; 
				out.println( line );
			}
		}
		
		out.flush();
		out.close();
		
		res += Integer.toString( _struct.size());
		
		return res;
		
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
		return this._engine;
	}

	@Override
	public int getSize() {
		
		return this._struct.size();
	}

}
