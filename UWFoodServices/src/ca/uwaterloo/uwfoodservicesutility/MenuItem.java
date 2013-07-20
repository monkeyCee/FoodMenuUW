package ca.uwaterloo.uwfoodservicesutility;

public class MenuItem {
	
	String product_name;
	int product_id;
	String diet_type;
	
	public MenuItem(String product_name, int product_id, String diet_type) {
		this.product_name = product_name;
		this.product_id = product_id;
		this.diet_type = diet_type;
	}
	
	public String getProductName() {
		return product_name;
	}
	
	public int getProductID() {
		return product_id;
	}
	
	public String getDietType() {
		return diet_type;
	}
	
	public void setProductName(String product_name) {
		this.product_name = product_name;
	}
	
	public void setProductID(int product_id) {
		this.product_id = product_id;
	}
	
	public void setDietType(String diet_type) {
		this.diet_type = diet_type;
	}
}