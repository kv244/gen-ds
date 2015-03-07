package dynaLoad;

import java.util.Enumeration;
import java.util.Hashtable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

public class HashTableStruct implements itemOp {
	
	private Hashtable<Integer, String> _struct;
	private String _store;
	static final String _engine = "dynaLoad.HashTableStruct";
	static final String _magic = "!HT!";
	
	// Ctor determines loading of store
	public HashTableStruct()
	{
		_struct = new Hashtable<Integer, String>();
		_store = itemOp.storeDef; // default value
		loadStore();
	}
	
	// Attempt to populate in memory structure with disk data
	// Silent error if it does not exist, throw out if it exists 
	// and wrong marker or IO error
	// Called at init and on switch store
	// It wipes out the existing in-memory structure 
	// if it reads the data successfully
	
	@SuppressWarnings("static-access")
	private void loadStore()
	{
		Hashtable<Integer, String> loaded;
		BufferedReader in = null; 
		
		try
		{
			in = new BufferedReader(new FileReader( this._store ));
			String magic = in.readLine();
			
			if( !magic.equals( this._magic ))
				return;
			// if file exists and not the same type (!= magic)
			// same as if file did not exist; no data to load
						
			// file exists and same magic, attempt to load data
			// parse chars are { : }
			// anything that fails is ignored (eg int that does not convert)
				
			String line;
			loaded = new Hashtable<Integer, String>();
			
			while(( line = in.readLine()) != null ){
				
				if( lineFits( line )){
					String s1, s2;
					int separatorPos = line.indexOf(':');
					int iKey;
					
					s1 = line.substring( 1, separatorPos ); // first segment
					s2 = line.substring( separatorPos + 1, line.length() - 1 ); // second segment
				
					try{
						iKey = Integer.parseInt( s1 );
						loaded.put( iKey, s2 );		
					}
					catch( NumberFormatException x ){
						// just ignore line
					}
					
				} // end if; if line does not follow format, throw error
				// which will fail silently below; so if any line cannot be read
				// the whole is disposed of as the structure does not get populated
				else
				{
					throw new IOException();
				}
				
			} // end while (reading file)
			
			if( loaded.size() > 0 )
				this._struct = loaded;		
		}
		catch( FileNotFoundException x) // file missing, silent fail 
		{
			
		}
		catch( IOException x) // can't read from file, silent fail
		{
			
		}
		finally
		{
			try{ in.close(); }catch( Exception x ){}
		}
			
		return;
	}
	
	// refactor: determine if line read in is a valid line
	private boolean lineFits( String line )
	{
		return line.startsWith("{") && line.endsWith("}") && line.contains(":");
	}
	
	@SuppressWarnings("static-access")
	public String getEngine()
	{
		return this._engine;
	}
	
	// addItem
	public void addItem(dataItem di) throws ItemErrorException 
	{
		// returns item if key already exists or null if it is a new insert
		// we throw error if it already exists
		
		if( _struct.putIfAbsent( di.getKey(), di.getItem()) != null )
		{
			String errMess = "Item already exists " + Integer.toString( di.getKey());
			errMess += "\nNew data: " + di.getItem() + "\nOld data: " + _struct.get( di.getKey());
			throw new ItemErrorException( errMess );
		}	
	}

	// getItem
	public String getItem(int iKey) throws ItemErrorException 
	{
		String res;
		if(( res = this._struct.get(iKey)) != null )
			return res;
		else
			throw new ItemErrorException( "Item " + Integer.toString(iKey) + " not found to retrieve." );
	}

	// delItem
	public void delItem(int iKey) throws ItemErrorException 
	{
		String res = null;
		if(( res = this._struct.get(iKey)) != null )
		{
			this._struct.remove(iKey);
			
		}
		else
			throw new ItemErrorException( "Item " + Integer.toString(iKey) + " not found to delete." );
	}

	@SuppressWarnings("static-access")
	public String serialize() throws FileNotFoundException, IOException 
	{
		// Using file to avoid dealing with bytes and string conversion
		// Newlines separate records, to make reading in easier
		
		PrintWriter out = new PrintWriter( this._store );
		out.write( this._magic );
		out.write( "\n" );
		
		String res = "Written to " + this._store + " records:";
		int i = 0;
		
		Enumeration<Integer> keys = this._struct.keys();
		
		while( keys.hasMoreElements() )
		{
			int key = keys.nextElement();
			String val = this._struct.get(key);
			
			String line = "{" + Integer.toString(key) + ":" + val + "}\n";
			out.print( line );
			i++;
		}
		
		out.flush();
		out.close();
		
		res += Integer.toString(i);
		
		return res;
	}

	// returns size of store after setting it 
	public int setStore( String store )
	{
		this._store = store;
		this.loadStore(); 
		return this.getSize();
	}

	// getSize
	public int getSize() 
	{
		return this._struct.size();
	}
	
}
