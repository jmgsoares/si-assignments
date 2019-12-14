package pt.onept.mei.is1920.assignment.kafka.cli.CliHandler;

import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import pt.onept.mei.is1920.assignment.kafka.cli.util.ConnectionUtility;

import java.util.Scanner;

public class AnalyticsOperationsHandler {
    public static void analyticsOperationsMenu() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type the number with the desired operation (0 to leave)");
            System.out.println(" 1 - Get the revenue per item.");
            System.out.println(" 2 - Get the expenses per item.");
            System.out.println(" 3 - Get the profit per item.");
            System.out.println(" 4 - Get the total revenues.");
            System.out.println(" 5 - Get the total expenses.");
            System.out.println(" 6 - Get the total profit.");
            System.out.println(" 7 - Get the average amount spent in each purchase (separated by item).");
            System.out.println(" 8 - Get the average amount spent in each purchase (aggregated for all items).");
            System.out.println(" 9 - Get the item with the highest profit of all.");
            System.out.println("10 - Get the total revenue in the last hour.");
            System.out.println("11 - Get the total expenses in the last hour.");
            System.out.println("12 - Get the total profits in the last hour.");
            System.out.println("13 - Get the name of the country with the highest sales per item.");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Revenue per item");
                    getTotalPerItem("rev-itm");
                    continue;
                case 2:
                    System.out.println("Expense per item");
                    getTotalPerItem("exp-itm");
                    continue;
                case 3:
                    System.out.println("Profit per item");
                    getTotalPerItem("pft-itm");
                    continue;
                case 4:
                    System.out.println("Total revenue");
                    getTotal("tot-rev");
                    continue;
                case 5:
                    System.out.println("Total expense");
                    getTotal("tot-exp");
                    continue;
                case 6:
                    System.out.println("Total profit");
                    getTotal("tot-pft");
                    continue;
                case 7:
                    System.out.println("Average amount spent in each purchase (separated by item)");
                    getTotalPerItem("avg-exp-ord-itm");
                    continue;
                case 8:
                    System.out.println("Average amount spent in each purchase (aggregated for all items)");
                    getTotal("avg-exp-ord");
                    continue;
                case 9:
                    System.out.println("Item with the highest profit of all");
                    getTotalPerItem("mst-prof-itm");
                    continue;
                case 10:
                    System.out.println("Total revenue in the last hour");
                    getTotal("lst-hour-rev");
                    continue;
                case 11:
                    System.out.println("Total expense in the last hour");
                    getTotal("lst-hour-exp");
                    continue;
                case 12:
                    System.out.println("Total profit in the last hour");
                    getTotal("lst-hour-pft");
                    continue;
                case 13:
                    getCountryHighestSalesPerItem();
                    continue;
                default:
                    return;
            }
        }
    }

    private static void getTotalPerItem(String param) {
        Scanner scanner = new Scanner(System.in);

        JSONArray response = Unirest.get(ConnectionUtility.PAGE_URL_ANALYTICS + param)
                .asJson()
                .getBody()
                .getArray();

        if (!response.isEmpty()) {
            for (Object o : response.toList()) {
                System.out.printf("Name: %-15s Value: %-15s\n", ((JSONObject) o).get("item").toString(),
                        ((JSONObject) o).get("value").toString());
            }
        }
        System.out.println("Press any key to continue...");
        scanner.nextLine();
    }

    private static void getTotal(String param) {
        Scanner scanner = new Scanner(System.in);

        JSONArray response = Unirest.get(ConnectionUtility.PAGE_URL_ANALYTICS + param)
                .asJson()
                .getBody()
                .getArray();

        for (Object o : response.toList()) {
            System.out.println("Value: " + ((JSONObject) o).get("value").toString());
        }
        System.out.println("Press any key to continue...");
        scanner.nextLine();
    }

    private static void getCountryHighestSalesPerItem() {
        Scanner scanner = new Scanner(System.in);

        JSONArray response = Unirest.get(ConnectionUtility.PAGE_URL_ANALYTICS + "cnt-hst-sale-itm")
                .asJson()
                .getBody()
                .getArray();

        System.out.println("Country with the highest sales per item");
        if (!response.isEmpty()) {
            for (Object o : response.toList()) {
                System.out.printf("Name: %-15s Country: %-15s Value: %-15s\n", ((JSONObject) o).get("item").toString(),
                        ((JSONObject) o).get("country").toString(), ((JSONObject) o).get("value").toString());
            }
        }
        System.out.println("Press any key to continue...");
        scanner.nextLine();
    }
}
