package dynaLoad;

import java.lang.reflect.InvocationTargetException;

// TODO: HashTable overwrite
// the overwrite fails if data already exists -- TD
// deal with name / class in driver

// tested if !HT! not there
// tested if file does not exist
// tested if wrong format of file (at beginning)
// tested if part of file is wrong format

public class test {

	public static void main(String[] args) {

		//driver d = new driver( "dynaLoad.ArrayListStruct" );
		driver d = new driver( "dynaLoad.HashTableStruct" );
		
		try {
			// setStore
			d.setStore("newStore10.txt");
			
			//String s1 = d.checkStorageEngine("l.txt");
			
			for( int i = 0; i < 3; i++ ) {
				// has to start with 0 for ArrayList ~~~ fixed TODO test with 1 now
				String s = "List Struct";
				s = Integer.toString(i) + s;
				
				// add
				d.add(i, s);
			}
			
			// test padding
			
			d.add(25, "last");
			
			// test intermediary insert
			d.add(11, "middle11");
			
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
			
			
			// test save
			System.out.println( d.commit());
		}
		catch( dynaLoad.ItemErrorException y ){ // this is never trapped, need the next one
			System.out.println( "Main2: " + y.getMessage());
		}
		catch( InvocationTargetException x ){
			System.out.println( "Main3: " + x.getTargetException().getMessage());
		}
		catch( Exception x ){
			System.out.println( "Main: " + x.getMessage());
		}		
	}
}
