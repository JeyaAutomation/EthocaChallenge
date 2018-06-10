package enums;

public enum Item {
	MAGIC_MOUSE("Magic Mouse", "product_view_40", 150.00), 
	APPLE_TV("Apple TV", "product_view_89", 80.00);

	public String itemName;
	public String itemReference;
	double itemPrice;

	private Item(String itemName, String itemReference, double itemPrice) {
		this.itemName = itemName;
		this.itemReference = itemReference;
		this.itemPrice = itemPrice;
	}

	public String getItemName() {
		return this.itemName;
	}

	public String getItemReference() {
		return this.itemReference;
	}

	public double getItemPrice() {
		return this.itemPrice;
	}

	public static double getItemPriceByName(String itemName) throws Exception {
		for (Item i : Item.values()) {
			if (i.itemName.equals(itemName)) {
				return i.itemPrice;
			}
		}
		throw new Exception("Item not found");
	}
}