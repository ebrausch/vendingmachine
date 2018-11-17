package com.techelevator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.techelevator.view.Log;

public abstract class MoneyUtils {

	public static String getChange(BigDecimal amount) throws IOException {

		Log log = new Log();

		int amountDue = amount.setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue();

		int[] valueArr = { 1000, 500, 100, 25, 10, 5 };
		String[] denomArr = { "Ten Dollar Bill", "Five Dollar Bill", "One Dollar Bill", "Quarter", "Dime", "Nickel" };
		int[] count = { 0, 0, 0, 0, 0, 0 };

		String result = "Change Due: ";

		for (int i = 0; i < valueArr.length; i++) {
			while (valueArr[i] <= amountDue) {
				count[i]++;
				amountDue -= valueArr[i];

			}
		}
		for (int i = 0; i < count.length; i++) {
			if (count[i] > 0) {
				result += "\n(" + count[i] + ") " + denomArr[i];
			}
			if (count[i] > 1) {
				result += "s";
			}
		}

		log.addToLog("GIVE CHANGE:", amount, new BigDecimal(0));

		return result;
	}
}
