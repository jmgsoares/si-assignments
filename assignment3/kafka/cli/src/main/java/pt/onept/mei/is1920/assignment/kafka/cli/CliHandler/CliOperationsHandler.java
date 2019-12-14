package pt.onept.mei.is1920.assignment.kafka.cli.CliHandler;

import java.util.Scanner;

public class CliOperationsHandler {
    public static void cliOperationsMenu() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type the number with the desired operation (0 to leave)");
            System.out.println("1 - Get the revenue per item.");
            System.out.println("2 - Get the expenses per item.");
            System.out.println("3 - Get the profit per item.");
            System.out.println("4 - Get the total revenues.");
            System.out.println("5 - Get the total expenses.");
            System.out.println("6 - Get the total profit.");
            System.out.println("7 - Get the average amount spent in each purchase (separated by item).");
            System.out.println("8 - Get the average amount spent in each purchase (aggregated for all items).");
            System.out.println("9 - Get the item with the highest profit of all.");
            System.out.println("10 - Get the total revenue in the last hour.");
            System.out.println("11 - Get the total expenses in the last hour.");
            System.out.println("12 - Get the total profits in the last hour.");
            System.out.println("13 - Get the name of the country with the highest sales per item.");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    getRevenuePerItem();
                    continue;
                case 2:
                    getExpensePerItem();
                    continue;
                case 3:
                    getProfitPerItem();
                    continue;
                case 4:
                    getTotalRevenue();
                    continue;
                case 5:
                    getTotalExpense();
                    continue;
                case 6:
                    getTotalProfit();
                    continue;
                case 7:
                    getAverageSpentByItem();
                    continue;
                case 8:
                    getTotalAverageSpent();
                    continue;
                case 9:
                    getItemHighestProfit();
                    continue;
                case 10:
                    getTotalRevenueLastHour();
                    continue;
                case 11:
                    getTotalExpenseLastHour();
                    continue;
                case 12:
                    getTotalProfitLastHour();
                    continue;
                case 13:
                    getCountryHighestSalesPerItem();
                    continue;
                default:
                    return;
            }
        }
    }

    private static void getRevenuePerItem() {
    }

    private static void getExpensePerItem() {
    }

    private static void getProfitPerItem() {
    }

    private static void getTotalRevenue() {
    }

    private static void getTotalExpense() {
    }

    private static void getTotalProfit() {
    }

    private static void getAverageSpentByItem() {
    }

    private static void getTotalAverageSpent() {
    }

    private static void getItemHighestProfit() {
    }

    private static void getTotalRevenueLastHour() {
    }

    private static void getTotalExpenseLastHour() {
    }

    private static void getTotalProfitLastHour() {
    }

    private static void getCountryHighestSalesPerItem() {
    }


}
