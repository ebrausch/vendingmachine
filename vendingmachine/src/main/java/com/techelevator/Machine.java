package com.techelevator;

//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintWriter;
import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import com.techelevator.view.Log;

public class Machine {

	BigDecimal machineBalance = new BigDecimal(0);
	private BigDecimal transactionBalance = new BigDecimal(0);

	// List to contain user's cart
	private List<Item> cart = new ArrayList<Item>();
	// List to contain total sales
	private List<Item> soldItems = new ArrayList<Item>();

	Log l = new Log();

	// Map to contain inventory objects
	private SortedMap<String, InventoryItem> stock = new TreeMap<String, InventoryItem>();
	private Log logger;

	public Machine() throws IOException {
		this.logger = new Log();
	}

	public String getSounds() {
		String result = "";
		for (Item each : cart) {
			result += each.getItemSound() + "\n";
		}

		return result;
	}

	public void generateReport() throws IOException {
		String data = "";
		int sold;

		for (String key : getStock().keySet()) {
			InventoryItem ii = getStock().get(key);
			if (ii.getQuantity().equals("Sold Out")) {
				sold = 5;
			} else {
				sold = 5 - Integer.valueOf(ii.getQuantity());
			}

			data += "\n" + String.format("%-20s", ii.getMyItem().getItemName()) + " | " + sold;
		}
		l.salesReport(data, getMachineBalance());
	}

	public BigDecimal getMachineBalance() {
		return machineBalance;
	}

	public void setMachineBalance(BigDecimal machineBalance) {
		this.machineBalance = machineBalance;
	}

	public String displayItems() {
		String result = "";
		for (String key : getStock().keySet()) {
			InventoryItem ii = getStock().get(key);
			String formattedName = String.format("%-20s", ii.getMyItem().getItemName());
			result += ii.getLocation() + " " + formattedName + " " + ii.getMyItem().getItemPrice() + "  Qty: "
					+ ii.getQuantity() + "\n";

		}
		return result;

	}

	public void addMoney(int input) throws IOException {
		setTransactionBalance((getTransactionBalance().add(new BigDecimal(input))));
		System.out.println("Transaction Balance is now : $" + getTransactionBalance().toString());
		logger.addToLog("FEED MONEY:", new BigDecimal(input), getTransactionBalance());
	}

	public String selectItem(String selectedItem) throws IOException {
		// selectedItem.toUpperCase();

		String selectedItemQuantity = getStock().get(selectedItem).getQuantity();
		BigDecimal selectedItemPrice = getStock().get(selectedItem).getMyItem().getItemPrice();

		if (!selectedItemQuantity.equals("Sold Out") && getTransactionBalance().compareTo(selectedItemPrice) >= 0) {

			cart.add(getStock().get(selectedItem).getMyItem());

			getStock().get(selectedItem).decreaseQuantity();

			setTransactionBalance(
					getTransactionBalance().subtract(getStock().get(selectedItem).getMyItem().getItemPrice()));
			machineBalance = machineBalance.add(selectedItemPrice);

			String formattedName = String.format("%-20s", getStock().get(selectedItem).getMyItem().getItemName()); ////////////////
			logger.addToLog(formattedName, getStock().get(selectedItem).getMyItem().getItemPrice(),
					getTransactionBalance());

			return selectedItem.toString() + " " + getStock().get(selectedItem).getMyItem().getItemName()
					+ " has been purchased. Your remaining balance is " + getTransactionBalance();

		} else if (getStock().containsKey(selectedItem.toUpperCase()) && selectedItemQuantity.equals("Sold Out")) {
			return "Sorry! This item is currently Sold Out!";

		} else if (getTransactionBalance().compareTo(selectedItemPrice) == -1) {
			return "\nYou have not entered enough money for this item. Please enter more money or select another item.";
		} else {

			return "This is not a valid selection";
		}
	}

	public void stock() throws FileNotFoundException {
		String importFileName = "vendingmachine.csv";
		File fileName = new File(importFileName);

		if (fileName.exists() && fileName.isFile()) {

			try (Scanner fileScanner = new Scanner(fileName)) {
				InventoryItem newInvItem = null;
				while (fileScanner.hasNextLine()) {
					String[] temp = fileScanner.nextLine().split("\\|");

					newInvItem = new InventoryItem(temp[0], temp[1], new BigDecimal(temp[2]), temp[3]);
					getStock().put(temp[0], newInvItem);

				}

			}
		} else {
			throw new FileNotFoundException("File does not exist.");
		}

	}

	public void finishTransaction() {
		for (Item each : cart) {
			soldItems.add(each);
		}
		while (cart.size() > 0) {
			cart.remove(0);
		}
	}

	public BigDecimal getTransactionBalance() {
		return transactionBalance;
	}

	public void setTransactionBalance(BigDecimal transactionBalance) {
		this.transactionBalance = transactionBalance;
	}

	public SortedMap<String, InventoryItem> getStock() {
		return stock;
	}

	public void setStock(SortedMap<String, InventoryItem> stock) {
		this.stock = stock;
	}

}
