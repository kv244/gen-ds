package dynaLoad;

import java.lang.reflect.InvocationTargetException;

// deal with name / class in driver

// tested if !HT! not there
// tested if file does not exist
// tested if wrong format of file (at beginning)
// tested if part of file is wrong format

public class test {

	public static void main(String[] args) {

		//driver d = new driver( "dynaLoad.ArrayListStruct" ); 
		driver d = new driver("dynaLoad.HashTableStruct");
		
		try {
			// setStore
			d.setStore(d.getEngine() + "BigStore10.txt");
			
			//String s1 = d.checkStorageEngine("l.txt");
			
			for( int i = 1; i < 1000000; i++ ) {
				// has to start with 0 for ArrayList ~~~ will pad with NULL if not: that is ok 
				String s = "List Struct";
				s = Integer.toString(i) + s;
				
				// add
				d.add(i, s);
			}
			
			// test padding for AL
			// this will fail in HS
			
			//d.add(25, "last");
			
			// test intermediary insert
			//d.add(11, "middle11");
			
			/*
			for( int i = 0; i < d.getSize(); i ++ ) 
				// this fails for hash, as items are not sequential
				// it can be improved but need to catch the core error
			{
				System.out.println( "__item " + Integer.toString( i ) + " = " + d.get( i ) );
			}
			*/
			
			// iterator testing
			dataItem di = null; // has to be initialized!
			d.iterReset();
			do {
				di = d.iter();
				if(di != null) {
					System.out.println(Integer.toString(di.getKey()) + "-->" + di.getItem());
				}
			} while (di != null);
			
			System.out.println("saving...");		
			
			// test save
			System.out.println(d.commit());
		}
		catch( dynaLoad.ItemErrorException y ){ // this is never trapped, need the next one
			System.out.println("ERRMain2: " + y.getMessage());
		}
		catch( InvocationTargetException x ){
			System.out.println("ERRMain3: " + x.getTargetException().getMessage());
		}
		catch( Exception x ){
			System.out.println("ERRMain: " + x.getMessage());
		}		
	}
}
