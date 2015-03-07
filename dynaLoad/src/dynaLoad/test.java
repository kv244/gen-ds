package dynaLoad;

// TODO finalize methods: getters in HashTable driver
// Add logger - each engine will have access to it
// Add tests - the driver is testable
// to fix sizing related to array
// add seek times to method?
// the main will be replaced by shell interpret
// the overwrite fails if data already exists, get better error reporting FIX1
//	how to subclass exception to get the inner message FIX1
// how to deal with structs too large to fit in memory?
// to add Swing GUI too to use the driver
// deal with name / class in driver

// to read ArrayList methods

// tested if !HT! not there
// tested if file does not exist
// tested if wrong format of file (at beginning)
// tested if part of file is wrong format

public class test 
{

	public static void main(String[] args) 
	{

		driver d = new driver( "dynaLoad.ArrayListStruct" );
		try
		{
			// setStore
			// d.setStore("local5.txt");
			
			for( int i = 0; i < 3; i++ ) // has to start with 0 for ArrayList
			{
				String s = "List Struct";
				s = Integer.toString( i ) + s;
				
				// add
				d.add( i, s );
			}
			
			d.commit();
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
