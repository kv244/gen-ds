package dynaLoad;

import java.io.FileNotFoundException;
import java.lang.reflect.*;
import java.io.IOException;

public class driver {
	itemOp _engine;
	Object ptrEngine;
	Method method;
	@SuppressWarnings("rawtypes")
	Class ptrClass;
	
	
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
			System.out.println( "Driver__" + x.getMessage() );
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
	public String getEngine()
	{
		return _engine.getEngine();
	}
	
	@SuppressWarnings("rawtypes")
	public void setStore( String store ) throws Exception // several...
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
	
	public String get( int i ) throws ItemErrorException
	{
		return _engine.getItem( i );
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
	public void commit() throws IOException, FileNotFoundException, NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException
	{
		Class noParams[] = {};
		this.method = this.ptrClass.getDeclaredMethod( "serialize", noParams );
		this.method.invoke( ptrEngine, (Object[]) null );
		
		// _engine.serialize();
	}
}
