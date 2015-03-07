package dynaLoad;

// TODO finalize methods: getters in HashTable driver
// Add logger - each engine will have access to it
// Add tests - the driver is testable? or each class
// the main will be replaced by shell interpret
// the overwrite fails if data already exists, get better error reporting FIX1
//	how to subclass exception to get the inner message FIX1
// how to deal with structs too large to fit in memory?
// to add Swing GUI too to use the driver
// 	open file, show in tree, switch engine
// deal with name / class in driver

// tested if !HT! not there
// tested if file does not exist
// tested if wrong format of file (at beginning)
// tested if part of file is wrong format

public class test 
{

	public static void main(String[] args) 
	{

		// driver d = new driver( "dynaLoad.ArrayListStruct" );
		driver d = new driver( "dynaLoad.HashTableStruct" );
		
		try
		{
			// setStore
			d.setStore("local12.txt");
			
			for( int i = 0; i < 3; i++ ) // has to start with 0 for ArrayList ~~~ test with 1 now TODO
			{
				String s = "List Struct";
				s = Integer.toString( i ) + s;
				
				// add
				d.add( i, s );
			}
			
			// test padding
			
			d.add( 25, "last" );
			
			// test intermediary insert
			d.add( 11, "middle11" );
			
			for( int i = 0; i < d.getSize(); i ++ )
				System.out.println( "__item " + Integer.toString( i ) + " = " + d.get( i ) );
			
			// test save
			System.out.println( d.commit());
		}
		catch( dynaLoad.ItemErrorException y ) //TODO replace FIX1. this is not caught
		{
			System.out.println( "Main2__ " + y.getMessage());
		}
		catch( Exception x )
		{
			System.out.println( "Main__ " + x.getMessage());
		}
		
	}

}
