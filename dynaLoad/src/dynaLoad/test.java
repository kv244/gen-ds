package dynaLoad;

// TODO finalize methods: getters in HashTable driver
// Add logger - each engine will have access to it
// Add tests
// to fix sizing related to array
// add seek times to method?
// the main will be replaced by shell interpret
// the overwrite fails if data already exists, get better error reporting FIX1
//	how to subclass exception to get the inner message FIX1
// how to deal with structs too large to fit in memory?

// tested if !HT! not there
// tested if file does not exist
// tested if wrong format of file (at beginning)
// tested if part of file is wrong format

public class test {

	public static void main(String[] args) {

		driver d = new driver( "dynaLoad.HashTableStruct" );
		try
		{
			d.setStore("local5.txt");
			
			for( int i = 1; i < 3; i++ )
			{
				String s = "XZosoX";
				for( int j = 1; j < Math.random() * 9; j++ )
				{
					s += s;
				}
				s = Integer.toString( i ) + s;
				// random length string
				
				d.add( i, s );
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
