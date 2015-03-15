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
	
}
