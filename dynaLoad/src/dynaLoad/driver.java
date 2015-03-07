package dynaLoad;

import java.io.FileNotFoundException;
import java.lang.reflect.*;
import java.io.IOException;

public class driver 
{
	itemOp _engine;
	Object ptrEngine;
	Method method;
	@SuppressWarnings("rawtypes")
	Class ptrClass;
	
	final static String version = "1.0.0";
	
	/*
	 * Methods:
	 * 
	 * setEngine - will load class dynamically (private, called through ctor of driver; one driver only has one engine)
	 * getEngine
	 * setStore
	 * put i, S
	 * get
	 * delete i
	 * commit
	 */
	
	// used for compatibility etc
	public String getVersion()
	{
		return version;
	}
	
	// called by ctor
	private void setEngine( String engine )
	{
		// typical name: dynaLoad.HashTableStruct
		try
		{		
			ptrClass = Class.forName( engine );
			ptrEngine = ptrClass.newInstance();
			
			/* is there a better way to do this */
		}
		catch( ClassNotFoundException x )
		{
			System.out.println( "Driver__class__" + x.getMessage() );
		}
		catch( Exception x )
		{
			System.out.println( "Driver__" + x.getMessage() );
		}
		
	}
	
	// ctor
	public driver( String engine )
	{
		this.setEngine( engine );
	}
	
	// getEngine
	public String getEngine() throws
		dynaLoad.ItemErrorException,
		NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException
	{
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
		InvocationTargetException
	{
		int res = -1;
		// return _engine.getSize();
		
		Class noParams[] = {};
		this.method = this.ptrClass.getDeclaredMethod( "getSize", noParams );
		res = (Integer)this.method.invoke( ptrEngine, (Object[]) null );
		
		return res;
		
	}
	
	@SuppressWarnings("rawtypes")
	public void setStore( String store ) throws 
		dynaLoad.ItemErrorException,
		NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException
	{
		Class paramString[] = new Class[1];
		paramString[0] = String.class;
		
		this.method = this.ptrClass.getDeclaredMethod( "setStore", paramString );
		this.method.invoke( ptrEngine, new String( store ));
		
		// _engine.setStore( store );
	}
	
	@SuppressWarnings("rawtypes")
	public void add( int i, String v ) throws
		dynaLoad.ItemErrorException,
		NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException
	{
		Class[] paramDi = new Class[1];
		paramDi[0] = dataItem.class;
		
		this.method = this.ptrClass.getDeclaredMethod( "addItem", paramDi );
		// Class[] e = this.method.getExceptionTypes();
		this.method.invoke( ptrEngine, new dataItem( v, i ) );
		// _engine.addItem(di);
	}
	
	// get element
	public String get( int i ) throws ItemErrorException,
		NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException
	{
		// return _engine.getItem( i );
		String res = "";
		Class paramInt[] = new Class[1];
		paramInt[0] = Integer.TYPE;
		
		this.method = this.ptrClass.getDeclaredMethod( "getItem", paramInt );
		res = this.method.invoke( ptrEngine, i ).toString();
		
		return res;
		
	}
	
	@SuppressWarnings("rawtypes")
	public void del( int i ) throws ItemErrorException, NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException
	{
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
		InvocationTargetException
	{
		String res = "";
		Class noParams[] = {};
		this.method = this.ptrClass.getDeclaredMethod( "serialize", noParams );
		res = this.method.invoke( ptrEngine, (Object[]) null ).toString();
		
		return res;
		// _engine.serialize();
	}
}
