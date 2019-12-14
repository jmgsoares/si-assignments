package pt.onept.mei.is1920.assignment.kafka.cli;

import pt.onept.mei.is1920.assignment.kafka.cli.CliHandler.CliOperationsHandler;
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
			System.out.println("3 - Money menu");

			int option = scanner.nextInt();
			switch (option) {
				case 1:
					CountryOperationsHandler.countriesMenu();
					continue;
				case 2:
					ItemsOperationsHandler.itemsMenu();
					continue;
				case 3:
					CliOperationsHandler.cliOperationsMenu();
					continue;
				default:
					return;
			}
		}
	}
}
