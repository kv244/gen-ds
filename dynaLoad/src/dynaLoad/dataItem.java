package dynaLoad;

public class dataItem {

	private String item;
	private int key;
	
	public dataItem(String sItem, int iKey) {
		this.item = sItem;
		this.key = iKey;
	}
	
	public String getItem() {
		return this.item;
	}
	
	public int getKey() {
		return this.key;
	}
	
	@Override
	public String toString() {
		return Integer.toString(key) + "; " + item;
	}
	
	// opposite of the above, returns null if illegal
	public static final dataItem toDi(String text) {
		dataItem diRet = null;
		int split = text.indexOf("; ");
		try {
			int key = Integer.parseInt(text.substring(0, split));
			String item = text.substring(split+2);
			diRet = new dataItem(item, key);
		} catch(Exception x) { }
		
		return diRet;
	}
}
