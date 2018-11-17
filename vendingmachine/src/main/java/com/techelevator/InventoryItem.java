package com.techelevator;

import java.math.BigDecimal;

public class InventoryItem {

	public void setAmountPurchased(BigDecimal amountPurchased) {
		this.amountPurchased = amountPurchased;
	}

	public void decreaseQuantity() {
		this.quantity = quantity - 1;
	}

	private String location;
	private int quantity = 5;
	private Item myItem = null;
	private BigDecimal amountPurchased;

	public InventoryItem(String location, String name, BigDecimal price, String category) {
		this.location = location;
		myItem = new Item(name, category, price);

	}

	public String getLocation() {
		return location;
	}

	public String getQuantity() {
		if (this.quantity < 1) {
			return "Sold Out";
		} else {
			return String.valueOf(quantity);
		}

	}

	public Item getMyItem() {
		return myItem;
	}

	public BigDecimal getAmountPurchased() {
		return amountPurchased;
	}

}
