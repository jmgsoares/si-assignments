package pt.onept.mei.is1920.assignment.kafka.cli;

import pt.onept.mei.is1920.assignment.kafka.cli.CliHandler.AnalyticsOperationsHandler;
import pt.onept.mei.is1920.assignment.kafka.cli.CliHandler.CountryOperationsHandler;
import pt.onept.mei.is1920.assignment.kafka.cli.CliHandler.ItemsOperationsHandler;

import java.util.Scanner;

public class Cli {

	public static void main(String[] args) {
		while(true) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Type the number with the desired operation (0 to leave)");
			System.out.println("1 - Add/Edit country menu");
			System.out.println("2 - Add/Edit item menu");
			System.out.println("3 - Analytics menu");

			int option = scanner.nextInt();
			switch (option) {
				case 1:
					CountryOperationsHandler.listCountries();
					continue;
				case 2:
					ItemsOperationsHandler.listItems();
					continue;
				case 3:
					AnalyticsOperationsHandler.analyticsOperationsMenu();
					continue;
				default:
					return;
			}
		}
	}
}
