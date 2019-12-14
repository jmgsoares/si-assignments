package pt.onept.mei.is1920.assignment.kafka.cli.CliHandler;

import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import pt.onept.mei.is1920.assignment.kafka.cli.util.ConnectionUtility;

import java.util.*;

public class CountryOperationsHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static void listCountries() {
        int indexKey = 1; // starting at one
        Map<Integer, String> countriesMap = new HashMap<>();

        List<String> countries = Unirest.get(ConnectionUtility.PAGE_URL + "country")
                .asObject(new GenericType<List<String>>() {})
                .getBody();

        if(!countries.isEmpty()) {
            for (String c: countries) {
                countriesMap.put(indexKey++, c);
            }
        }

        if(countriesMap.isEmpty()) {
            // If there are no countries to list, ask to add a new one.
            System.out.println("No countries available. Add new country? (y/n)");
            if(scanner.nextLine().equals("y")) {
                addCountry();
            }

        } else {
            // Else, allow the user to select one of the countries in order to edit it.
            countriesMap.forEach((k, v) -> System.out.println(k + " " + v));

            System.out.println("Edit a country? (y/n)");
            if(scanner.nextLine().equals("y")) {
                System.out.print("Select one country to edit (numbers only): ");
                int option = scanner.nextInt();
                scanner.nextLine(); // clean buffer
                if(option > countriesMap.size() || option < 1) {
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
        // Send new name
        HttpResponse<String> response = Unirest.post(ConnectionUtility.PAGE_URL + "country")
                .queryString("name", newCountryName)
                .asString();
    }

    private static void editCountry(String selectedCountry) {
        System.out.print("New name of the country: ");
        String newCountryName = scanner.nextLine();
        // Send old and new name
        HttpResponse<String> response = Unirest.put(ConnectionUtility.PAGE_URL + "country")
                .queryString("oldName", selectedCountry)
                .queryString("newName", newCountryName)
                .asString();
    }
}
