package dynaLoad;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.*;
import java.io.IOException;

// TODO provide iterators in engines
// to populate tree
// ? how are classes identified from JARs √
// TODO check any static references as these will load the class
// TODO logger + exception handling...

public class driver {
	itemOp _engine;
	Object ptrEngine;
	Method method;
	
	@SuppressWarnings("rawtypes")
	Class ptrClass;
	
	final static String version = "1.0.2";
	
	// engine identifiers - magic and class
	final static String[][] engines = {{ "!AL!", "dynaLoad.ArrayListStruct" }, 
		{ "!HT!", "dynaLoad.HashTableStruct" }};
	private final static int _MAGIC = 0;
	private final static int _CLASS = 1;
	
	/*
	 * Methods:
	 * 
	 * setEngine - will load class dynamically 
	 * 	(private, called through ctor of driver; one driver only has one engine)
	 * getEngine
	 * setStore
	 * put i, S
	 * get
	 * delete i
	 * commit
	 */
	
	// constructor
	// all this does is set/instantiate the engine
	public driver(String engine) {
		this.setEngine(engine);
	}
	
	// setEngine
	// typical name: dynaLoad.HashTableStruct
	private void setEngine(String engine){
		
		try {		
			ptrClass = Class.forName(engine);
			ptrEngine = ptrClass.newInstance();
			/* TODO is there a better way to do this */
		} catch(ClassNotFoundException x) {
				System.out.println( "Driver::ClassNotFound: " + x.getMessage() );
		} catch(Exception x){
			System.out.println( "Driver::Error: " + x.getMessage() );
		}
	}
	
	// used for compatibility etc
	public static final String getVersion() {
		return version;
	}
	
	// checks the magic and returns the engine version
	// or null if file is not a valid storage
	public static final String checkStorageEngine(String fileName) {
		for(int i = 0; i < engines.length; i++){
			String s = engines[i][_MAGIC];
			if(checkMagic(fileName, s)) {
				return engines[i][_CLASS];
			}
		}
		return null;
	}
	
	private static final boolean checkMagic( String fileName, String magic ) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String readMagic = in.readLine();
			in.close();
			return (readMagic.equals(magic));
		} catch(Exception x) {
			// TODO log exception
			return false;
		}			
	}
	
	// getEngine
	public String getEngine() throws
		dynaLoad.ItemErrorException,
		NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException{
		// return _engine.getEngine();
		
		String res = "";
		Class noParams[] = {};
		this.method = this.ptrClass.getDeclaredMethod( "getEngine", noParams );
		res = this.method.invoke( ptrEngine, (Object[]) null ).toString();
		
		return res;
	}
	
	// getSize
	public int getSize() throws
		dynaLoad.ItemErrorException,
		NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException{
		int res = -1;
		// return _engine.getSize();
		
		Class noParams[] = {};
		this.method = this.ptrClass.getDeclaredMethod("getSize", noParams);
		res = (Integer)this.method.invoke(ptrEngine, (Object[]) null);
		
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public void setStore(String store) throws 
		dynaLoad.ItemErrorException,
		NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException{
		
		Class paramString[] = new Class[1];
		paramString[0] = String.class;
		
		this.method = this.ptrClass.getDeclaredMethod( "setStore", paramString );
		this.method.invoke( ptrEngine, new String( store ));
		
		// _engine.setStore( store );
	}
	
	// iterators TODO review
	@SuppressWarnings("rawtypes")
	public void iterReset() throws ItemErrorException,
		NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException {
		
		Class noParams[] = {};
		this.method = this.ptrClass.getDeclaredMethod( "iterReset", noParams );
		this.method.invoke(ptrEngine, (Object[]) null);
	}
	
	@SuppressWarnings("rawtypes")
	public dataItem iter() throws ItemErrorException,
		NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException {
	
		Class noParams[] = {};
		dataItem ret = null;
		
		this.method = this.ptrClass.getDeclaredMethod("next", noParams);
		ret = (dataItem)this.method.invoke(ptrEngine, (Object[]) null);
	
		return ret;
	}
	
	// TODO end iter -- move to interface
	
	@SuppressWarnings("rawtypes")
	public void add(int i, String v) throws
		dynaLoad.ItemErrorException,
		NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException{
		Class[] paramDi = new Class[1];
		paramDi[0] = dataItem.class;
		
		this.method = this.ptrClass.getDeclaredMethod( "addItem", paramDi );
		// Class[] e = this.method.getExceptionTypes();
		this.method.invoke( ptrEngine, new dataItem( v, i ) );
		// _engine.addItem(di);
	}
	
	// get element
	public String get(int i) throws ItemErrorException,
		NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException {
		
		// return _engine.getItem( i );
		String res = "";
		Class paramInt[] = new Class[1];
		paramInt[0] = Integer.TYPE;
		
		this.method = this.ptrClass.getDeclaredMethod( "getItem", paramInt );
		res = this.method.invoke(ptrEngine, i).toString();
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public void del(int i) throws ItemErrorException, NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException{
		
		Class[] paramInt = new Class[1];
		paramInt[0] = Integer.TYPE;
		
		this.method = this.ptrClass.getDeclaredMethod( "del", paramInt );
		this.method.invoke( ptrEngine, i );
		
		// _engine.delItem( i );
	}
	
	@SuppressWarnings("rawtypes")
	public String commit() throws 
		IOException, FileNotFoundException, NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException{
		
		String res = "";
		Class noParams[] = {};
		this.method = this.ptrClass.getDeclaredMethod( "serialize", noParams );
		res = this.method.invoke( ptrEngine, (Object[]) null ).toString();
		
		return res;
		// _engine.serialize();
	}
}
