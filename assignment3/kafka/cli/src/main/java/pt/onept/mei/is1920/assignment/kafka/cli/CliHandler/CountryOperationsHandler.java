package pt.onept.mei.is1920.assignment.kafka.cli.CliHandler;

import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import pt.onept.mei.is1920.assignment.kafka.cli.util.ConnectionUtility;
import pt.onept.mei.is1920.assignment.kafka.cli.util.ResponseMessages;
import pt.onept.mei.is1920.assignment.kafka.common.type.Country;

import java.util.*;

public class CountryOperationsHandler {
    private static Scanner scanner = new Scanner(System.in);
    private static Gson gson = new Gson();

    public static void listCountries() {
        int indexKey = 1; // starting at one
        Map<Integer, String> countriesMap = new HashMap<>();

        JSONArray response = Unirest.get(ConnectionUtility.PAGE_URL + "country")
                .connectTimeout(1000)
                .asJson()
                .getBody()
                .getArray();

        if (!response.isEmpty()) {
            for (Object o : response.toList()) {
                countriesMap.put(indexKey++, gson.fromJson(o.toString(), Country.class).getName());
            }
        }

        if (countriesMap.isEmpty()) {
            // If there are no countries to list, ask to add a new one.
            System.out.println("No countries available. Add new country? (y/n)");
            if (scanner.nextLine().equals("y")) {
                addCountry();
            }
        } else {
            // Else, allow the user to select one of the countries in order to edit it.
            countriesMap.forEach((k, v) -> System.out.println(k + " - " + v));

            System.out.println("Add new country? (y/n)");
            if (scanner.nextLine().equals("y")) {
                addCountry();
                return;
            }

            System.out.println("Edit a country? (y/n)");
            if (scanner.nextLine().equals("y")) {
                System.out.print("Select one country to edit (numbers only): ");
                int option = scanner.nextInt();
                scanner.nextLine(); // clean buffer
                if (option > countriesMap.size() || option < 1) {
                    System.out.println("Not a valid option.");
                } else {
                    editCountry(countriesMap.get(option));
                }
            }
        }
    }

    private static void addCountry() {
        System.out.print("Name of the new country: ");
        String newCountryName = scanner.nextLine();

        if (newCountryName.isEmpty()) {
            System.out.println("Invalid name.");
            return;
        }

        // Send new name
        HttpResponse<String> response = Unirest.post(ConnectionUtility.PAGE_URL + "country")
                .queryString("name", newCountryName)
                .asString();

        ResponseMessages.responseMessages(response.getStatus());
    }

    private static void editCountry(String selectedCountry) {
        System.out.print("New name of the country: ");
        String newCountryName = scanner.nextLine();

        if (newCountryName.isEmpty()) {
            System.out.println("Invalid name.");
            return;
        }

        // Send old and new name
        HttpResponse<String> response = Unirest.put(ConnectionUtility.PAGE_URL + "country/{oldName}")
                .routeParam("oldName", selectedCountry)
                .queryString("name", newCountryName)
                .asString();

        ResponseMessages.responseMessages(response.getStatus());
    }
}
