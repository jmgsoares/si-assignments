package pt.onept.mei.is1920.assignment.kafka.cli.CliHandler;

import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import pt.onept.mei.is1920.assignment.kafka.cli.util.ConnectionUtility;

import java.util.*;

public class ItemsOperationsHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static void listItems() {
        int indexKey = 1; // starting at one
        Map<Integer, String> itemsMap = new HashMap<>();

        List<String> items = Unirest.get(ConnectionUtility.PAGE_URL + "item")
                .asObject(new GenericType<List<String>>() {})
                .getBody();

        if(!items.isEmpty()) {
            for (String c: items) {
                itemsMap.put(indexKey++, c);
            }
        }

        if(itemsMap.isEmpty()) {
            // If there are no items to list, ask to add a new one.
            System.out.println("No items available. Add new item? (y/n)");
            if(scanner.nextLine().equals("y")) {
                addItem();
            }

        } else {
            // Else, allow the user to select one of the items in order to edit it.
            itemsMap.forEach((k, v) -> System.out.println(k + " " + v));

            System.out.println("Edit an item? (y/n)");
            if(scanner.nextLine().equals("y")) {
                System.out.print("Select one item to edit (numbers only): ");
                int option = scanner.nextInt();
                scanner.nextLine(); // clean buffer
                if(option > itemsMap.size() || option < 1) {
                    System.out.println("Not a valid option.");
                } else {
                    editItem(itemsMap.get(option));
                }
            }

        }
    }

    private static void addItem() {
        System.out.print("Name of the new item: ");
        String newItemName = scanner.nextLine();
        // Send new name
        HttpResponse<String> response = Unirest.post(ConnectionUtility.PAGE_URL + "item")
                .queryString("name", newItemName)
                .asString();
    }

    private static void editItem(String selectedItem) {
        System.out.print("New name of the item: ");
        String newItemName = scanner.nextLine();
        // Send old and new name
        HttpResponse<String> response = Unirest.put(ConnectionUtility.PAGE_URL + "item")
                .queryString("oldName", selectedItem)
                .queryString("newName", newItemName)
                .asString();
    }
}
