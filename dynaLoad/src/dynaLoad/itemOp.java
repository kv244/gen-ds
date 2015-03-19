package dynaLoad;

// to add:
// persistence to disk
// load from disk at startup
// also to add size

import java.io.FileNotFoundException;
import java.io.IOException;

public interface itemOp {
	
	public String storeDef = "default.bin";
	
	public void addItem(dataItem di) throws ItemErrorException;
	public String getItem(int iKey) throws ItemErrorException;
	public void delItem(int iKey) throws ItemErrorException;
	public String serialize() throws FileNotFoundException, IOException; 
	public int setStore(String store);
	public String getEngine();
	public int getSize();
	public void iterReset(); // iterator reset
	public dataItem next(); // iterator 
	
}
