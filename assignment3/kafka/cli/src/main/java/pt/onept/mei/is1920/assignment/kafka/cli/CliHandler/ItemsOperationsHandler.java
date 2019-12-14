package pt.onept.mei.is1920.assignment.kafka.cli.CliHandler;

import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import pt.onept.mei.is1920.assignment.kafka.cli.util.ConnectionUtility;
import pt.onept.mei.is1920.assignment.kafka.cli.util.ResponseMessages;
import pt.onept.mei.is1920.assignment.kafka.common.type.Item;

import java.util.*;

public class ItemsOperationsHandler {
    private static Scanner scanner = new Scanner(System.in);
    private static Gson gson = new Gson();

    public static void listItems() {
        int indexKey = 1; // starting at one
        Map<Integer, String> itemsMap = new HashMap<>();

        JSONArray response = Unirest.get(ConnectionUtility.PAGE_URL_ITEM)
                .asJson()
                .getBody()
                .getArray();

        if (!response.isEmpty()) {
            for (Object o : response.toList()) {
                itemsMap.put(indexKey++, gson.fromJson(o.toString(), Item.class).getName());
            }
        }

        if (itemsMap.isEmpty()) {
            // If there are no items to list, ask to add a new one.
            System.out.println("No items available. Add new item? (y/n)");
            if (scanner.nextLine().equals("y")) {
                addItem();
            }

        } else {
            // Else, allow the user to select one of the items in order to edit it.
            itemsMap.forEach((k, v) -> System.out.println(k + " - " + v));

            System.out.println("Add new item? (y/n)");
            if (scanner.nextLine().equals("y")) {
                addItem();
                return;
            }

            System.out.println("Edit an item? (y/n)");
            if (scanner.nextLine().equals("y")) {
                System.out.print("Select one item to edit (numbers only): ");
                int option = scanner.nextInt();
                scanner.nextLine(); // clean buffer
                if (option > itemsMap.size() || option < 1) {
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

        if (newItemName.isEmpty()) {
            System.out.println("Invalid name.");
            return;
        }

        // Send new name
        HttpResponse<String> response = Unirest.post(ConnectionUtility.PAGE_URL_ITEM)
                .queryString("name", newItemName)
                .asString();

        ResponseMessages.responseMessages(response.getStatus());
    }

    private static void editItem(String selectedItem) {
        System.out.print("New name of the item: ");
        String newItemName = scanner.nextLine();

        if (newItemName.isEmpty()) {
            System.out.println("Invalid name.");
            return;
        }

        // Send old and new name
        HttpResponse<String> response = Unirest.put(ConnectionUtility.PAGE_URL_ITEM + "/{oldName}")
                .routeParam("oldName", selectedItem)
                .queryString("name", newItemName)
                .asString();

        ResponseMessages.responseMessages(response.getStatus());
    }
}
