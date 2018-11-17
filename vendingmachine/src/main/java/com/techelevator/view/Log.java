package com.techelevator.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Log {

	File logFile;
	File salesReport;

	public Log() throws IOException {
		logFile = new File("/tmp/logFile.txt");
		logFile.createNewFile();
		salesReport = new File("/tmp/salesReport.txt");
		salesReport.createNewFile();
	}

	public Log(String logPath, String reportPath) throws IOException {

		logFile = new File(logPath);
		logFile.createNewFile();
		salesReport = new File(reportPath);
		salesReport.createNewFile();
	}

	public void addToLog(String type, BigDecimal amount, BigDecimal transactionBalance) throws IOException {

		final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		try (FileWriter fw = new FileWriter(logFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter writer = new PrintWriter(bw);) {

			writer.println(String.format("%-26s", timestamp) + " " + String.format("%-20s", type) + " "
					+ String.format("%-8s", amount) + " " + transactionBalance);

		} catch (IOException ex) {

		}
	}

	public void salesReport(String data, BigDecimal totalSales) throws IOException {

		try (FileWriter fw = new FileWriter(salesReport, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter writer = new PrintWriter(bw);) {
			writer.println("SALES REPORT");

			writer.println(data);

			writer.println("** TOTAL SALES ** $" + totalSales);

		} catch (IOException ex) {

		}

	}

}
