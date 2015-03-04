package dynaLoad;

// TODO finalize methods: getters in HashTable driver
// Add logger - each engine will have access to it
// Add tests
// to fix sizing related to array
// how to deal with structs too large to fit in memory?
// to fix GitHub (use share project under Team) 
// also source specification doc
// add seek times to method
// the main will be replaced by shell interpret
// the overwrite fails if data already exists, get better error reporting FIX1
//	how to subclass exception to get the inner message FIX1

public class test {

	public static void main(String[] args) {

		driver d = new driver( "dynaLoad.HashTableStruct" );
		try
		{
			d.setStore("local2.txt");
			
			for( int i = 1; i < 5000; i++ )
			{
				d.add( i, "ZOSO" + Integer.toString( i ));
			}
			
			d.commit();
		}
		catch( ItemErrorException y ) //TODO replace FIX1. this is not caught
		{
			System.out.println( "Main__ " + y.getMessage());
		}
		catch( Exception x )
		{
			System.out.println( "Main__ " + x.getMessage());
		}
		
	}

}
