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
		
		f = new File( sStore );
		if( f.exists())
		{
			System.out.println( "Deleting default store..." );
			f.delete();
		}
	}

	@After
	public void tearDown() throws Exception {
		
		if( f.exists())
			f.delete();
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
		
		di = new dataItem( "new One", size0 + 1 );
		
		try
		{
			als.addItem(di);
			assertEquals( size0 + 1, als.getSize());
		}
		catch( ItemErrorException x )
		{
			fail("Could not add item " + Integer.toString( di.getKey()) + "...\n" + x.getMessage());
		}
		
		
		fail("Not yet implemented");
	}

	@Test
	public void testGetItem() throws ItemErrorException
	{
		
		di = new dataItem( "blob", als.getSize() + 1 );
		als.addItem( di );
		assertEquals( "blob" , als.getItem(di.getKey()));
		
	}

	@Test
	public void testDelItem() throws ItemErrorException
	{
		di = new dataItem( "blob2", als.getSize() + 1 );
		als.addItem(di);
		als.delItem(di.getKey());
		
		try
		{
			als.getItem(di.getKey());
			fail( "Item Not Deleted" );
		}
		catch( ItemErrorException x )
		{
			assertTrue(true);
		}
		
	}


	@Test
	public void testSerialize() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetStore() {
		
	}

	@Test
	public void testGetEngine() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSize() {
		fail("Not yet implemented");
	}

}
