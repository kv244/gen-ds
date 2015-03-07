package dynaLoad;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArrayListStructTest {
	
	ArrayListStruct als;
	dataItem di;
	File f;
	String sStore;

	@Before
	public void setUp() throws Exception {
		als = new ArrayListStruct();
		sStore = itemOp.storeDef;
		// delete the default store def, start with empty
		
		delFile( sStore );
		
	}
	
	private void delFile( String sFileName ){
		f = new File( sFileName );
		if( f.exists()){
			System.out.println( "Deleting file: " + sFileName ); 
			f.delete();
		}
	}

	@After
	public void tearDown() throws Exception {
		
		if( f.exists())
			f.delete();
		f = null;
		als = null;
		di = null;
	}

	@Test
	public void testArrayListStruct() {

		// this will use the default store
		// default store is empty now, so test size is zero
		
		assertNotNull( als ); 
		assertEquals( 0, als.getSize() );
		
	}

	@Test
	public void testAddItem() {
		
		int size0 = als.getSize();
		String added = "archaeopteryx";
		
		di = new dataItem( added, size0 + 1 );
		
		try{
			als.addItem(di);
			assertEquals( als.getItem( size0 + 1 ), added );
			als.delItem( size0 + 1 );
		}
		catch( ItemErrorException x ){
			fail("Could not add item " + Integer.toString( di.getKey()) + "...\n" + x.getMessage());
		}
		
	}

	@Test
	public void testGetItem() throws ItemErrorException{
		di = new dataItem( "blob", als.getSize() + 1 );
		als.addItem( di );
		assertEquals( "blob" , als.getItem(di.getKey()));
		
	}

	@Test
	public void testDelItem() throws ItemErrorException{
		di = new dataItem( "blob2", als.getSize() + 1 );
		als.addItem(di);
		als.delItem(di.getKey());
		
		try{
			String res = als.getItem(di.getKey());
			if( !res.equals( als._null )) fail( "Item Not Deleted" );
		}
		catch( ItemErrorException x ){
			assertTrue(true);
		}
	}

	@Test
	public void testSerialize() throws Exception {
		// this should test
		// identity between saved file and expectation?
		// currently only testing output
		
		ArrayListStruct save = new ArrayListStruct();
		dataItem di2 = new dataItem( "nothing", 0 );
		String tFile = "boo";
		String res = "";
		delFile( tFile );
		save.setStore( tFile );
		save.addItem( di2 );
		res = save.serialize();
		
		String exp = "Written to " + tFile + " records 1";
		save = null; di2 = null; delFile( tFile );
		assertEquals( res, exp );
		
	}

	@Test
	public void testSetStore()  {
		
		String demoStore = "";
		
		for( int i = 0; i < 10; i++ ){
			demoStore += Integer.toString((int)Math.random() * i );
		}
		
		delFile( demoStore );
		als.setStore( demoStore );
		assertEquals( als.getSize(), 0 );
		
	}

	@Test
	public void testGetEngine() {
		assertEquals( als.getEngine(), "dynaLoad.ArrayListStruct" );
	}

}
