package pt.onept.mei.is1920.assignment.kafka.cli;

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
					continue;
				case 2:
					continue;
				case 3:
					continue;
				case 4:
					continue;
				case 5:
					continue;
				case 6:
					continue;
				case 7:
					continue;
				case 8:
					continue;
				case 9:
					continue;
				case 10:
					continue;
				case 11:
					continue;
				case 12:
					continue;
				case 13:
					continue;
				case 14:
					continue;
				case 15:
					continue;
				case 16:
					continue;
				case 17:
					continue;
				case 18:
					continue;
				case 19:
					continue;
			}
		}
	}
}
