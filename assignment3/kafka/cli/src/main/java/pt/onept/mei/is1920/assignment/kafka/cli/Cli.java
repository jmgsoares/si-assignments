package pt.onept.mei.is1920.assignment.kafka.cli;

import pt.onept.mei.is1920.assignment.kafka.cli.CliHandler.CliOperationsHandler;

import java.util.Scanner;

public class Cli {

	public static void main(String[] args) {
		while(true) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("1.Add countries to the database.");
			System.out.println("2.Edit countries on the database.");
			System.out.println("3.List countries from the database.");
			System.out.println("4.Add items to the database.");
			System.out.println("5.Edit items on the database.");
			System.out.println("6.List items from the database.");
			System.out.println("7.Get the revenue per item.");
			System.out.println("8.Get the expenses per item.");
			System.out.println("9.Get the profit per item.");
			System.out.println("10.Get the total revenues.");
			System.out.println("11.Get the total expenses.");
			System.out.println("12.Get the total profit.");
			System.out.println("13.Get the average amount spent in each purchase (separated by item).");
			System.out.println("14.Get the average amount spent in each purchase (aggregated for all items).");
			System.out.println("15.Get the item with the highest profit of all.");
			System.out.println("16.Get the total revenue in the last hour.");
			System.out.println("17.Get the total expenses in the last hour.");
			System.out.println("18.Get the total profits in the last hour.");
			System.out.println("19.Get the name of the country with the highest sales per item.");

			int option = scanner.nextInt();
			switch (option) {
				case 0:
					return;
				case 1:
					CliOperationsHandler.addCountry();
					continue;
				case 2:
					CliOperationsHandler.editCountry();
					continue;
				case 3:
					CliOperationsHandler.listCountries();
					continue;
				case 4:
					CliOperationsHandler.addItem();
					continue;
				case 5:
					CliOperationsHandler.editItem();
					continue;
				case 6:
					CliOperationsHandler.listItems();
					continue;
				case 7:
					CliOperationsHandler.getRevenuePerItem();
					continue;
				case 8:
					CliOperationsHandler.getExpensePerItem();
					continue;
				case 9:
					CliOperationsHandler.getProfitPerItem();
					continue;
				case 10:
					CliOperationsHandler.getTotalRevenue();
					continue;
				case 11:
					CliOperationsHandler.getTotalExpense();
					continue;
				case 12:
					CliOperationsHandler.getTotalProfit();
					continue;
				case 13:
					CliOperationsHandler.getAverageSpentByItem();
					continue;
				case 14:
					CliOperationsHandler.getTotalAverageSpent();
					continue;
				case 15:
					CliOperationsHandler.getItemHighestProfit();
					continue;
				case 16:
					CliOperationsHandler.getTotalRevenueLastHour();
					continue;
				case 17:
					CliOperationsHandler.getTotalExpenseLastHour();
					continue;
				case 18:
					CliOperationsHandler.getTotalProfitLastHour();
					continue;
				case 19:
					CliOperationsHandler.getCountryHighestSalesPerItem();
			}
		}
	}
}
