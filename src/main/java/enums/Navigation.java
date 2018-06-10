package enums;

public enum Navigation {
	 HOME("Home", new String[] { "menu-item-15"}),
	 PRODUCT_ACCESSORIES("Product Category > Accessories", new String[] { "menu-item-33", "menu-item-34" }),
	 PRODUCT_IMACS("Product Category > iMacs", new String[] { "menu-item-33", "menu-item-35" }),
	 PRODUCT_IPADS("Product Category > iPads", new String[] { "menu-item-33", "menu-item-36" }),
	 PRODUCT_IPHONE("Product Category > iPhones", new String[] {"menu-item-33","menu-item-37"}),
	 PRODUCT_IPODS("Product Category > iPhones", new String[] {"menu-item-33","menu-item-38"}),
	 PRODUCT_MACBOOKS("Product Category > Macbooks", new String[] {"menu-item-33","menu-item-39"}),
	 ALL_PRODUCTS("All Products", new String[] {"menu-item-72"});


	private final String title;
	private final String[] IDs;

	private Navigation(String title, String[] IDs) {
		this.title = title;
		this.IDs = IDs;
	}

	public String[] getClickableIDs() {
		return this.IDs;
	}

	public String getTitle() {
		return this.title;
	}
	}
