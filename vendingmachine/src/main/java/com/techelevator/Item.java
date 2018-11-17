package com.techelevator;

import java.math.BigDecimal;

public class Item {

	private String itemName;
	private String itemCategory;
	private BigDecimal itemPrice;

	public Item(String itemName, String itemCategory, BigDecimal itemPrice) {
		this.itemName = itemName;
		this.itemCategory = itemCategory;
		this.itemPrice = itemPrice;

	}

	public String getItemName() {
		return itemName;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public String getItemSound() {
		if (this.itemCategory.equalsIgnoreCase("Chip")) {
			return ("Crunch Crunch, Yum!");

		}
		if (this.itemCategory.equalsIgnoreCase("Candy")) {
			return ("Munch Munch,Yum!");

		}
		if (this.itemCategory.equalsIgnoreCase("Drink")) {
			return ("Glug Glug, Yum!");

		}
		if (this.itemCategory.equalsIgnoreCase("Gum")) {
			return ("Chew Chew, Yum!");
		}

		return "";
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

}
