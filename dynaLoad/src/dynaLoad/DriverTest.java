package dynaLoad;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

public class DriverTest {

	driver pDriver;
	String sEngine;
	String sStore;
	File f;
	
	@Before
	public void setUp() throws Exception 
	{
		
		sStore = "driverTest.txt";
		
		f = new File( sStore );
		if( f.exists())
			f.delete();
		
		sEngine = "dynaLoad.HashTableStruct";
		this.pDriver = new driver( sEngine );
		System.out.println( "CTOR" + pDriver.getEngine());
		
	}

	@After
	public void tearDown() throws Exception 
	{
		if( f.exists())
			f.delete();
		this.pDriver = null;
	}

	@Test
	public void testDriver() {
		
		assertNotNull( this.pDriver );
	}

	@Test
	public void testGetEngine() throws Exception {
		
		assertEquals( this.sEngine, pDriver.getEngine() );
	}

	@Test
	public void testSetStore() throws Exception
	{
		this.pDriver.setStore("testStore");
		
	}

	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testDel() {
		fail("Not yet implemented");
	}

	@Test
	public void testCommit() {
		fail("Not yet implemented");
	}

}
