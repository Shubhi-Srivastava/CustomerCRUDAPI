package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.UUID;
import org.json.JSONObject;
import org.json.JSONArray;

public class CustomerTestApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/customers";
    private static HttpClient client = HttpClient.newHttpClient();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("=== Customer Management CLI ===");
        boolean exit = false;

        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Create Customer");
            System.out.println("2. Get All Customers");
            System.out.println("3. Get Customer by ID");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> createCustomer();
                case 2 -> getAllCustomers();
                case 3 -> {
                    System.out.print("Enter Customer ID to retrieve: ");
                    UUID id = UUID.fromString(scanner.nextLine());
                    getCustomerById(id);
                }
                case 4 -> {
                    System.out.print("Enter Customer ID to update: ");
                    UUID id = UUID.fromString(scanner.nextLine());
                    updateCustomer(id);
                }
                case 5 -> {
                    System.out.print("Enter Customer ID to delete: ");
                    UUID id = UUID.fromString(scanner.nextLine());
                    deleteCustomer(id);
                }
                case 6 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Exiting application.");

    }

    private static void createCustomer() throws Exception {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Middle Name (optional): ");
        String middleName = scanner.nextLine();
        System.out.print("Enter Email Address: ");
        String emailAddress = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();

        JSONObject json = new JSONObject();
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("middleName", middleName.isEmpty() ? JSONObject.NULL : middleName);
        json.put("emailAddress", emailAddress);
        json.put("phoneNumber", phoneNumber);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        handleResponse("Create Customer", response);
    }

    private static void getAllCustomers() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONArray prettyJsonArray = new JSONArray(response.body());
        System.out.println("All Customers:\n" + prettyJsonArray.toString(4));
    }

    private static void getCustomerById(UUID id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        handleResponse("Customer by ID", response);
    }

    private static void updateCustomer(UUID id) throws Exception {
        System.out.print("Enter Updated First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Updated Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Updated Middle Name (optional): ");
        String middleName = scanner.nextLine();
        System.out.print("Enter Updated Email Address: ");
        String emailAddress = scanner.nextLine();
        System.out.print("Enter Updated Phone Number: ");
        String phoneNumber = scanner.nextLine();

        JSONObject json = new JSONObject();
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("middleName", middleName.isEmpty() ? JSONObject.NULL : middleName);
        json.put("emailAddress", emailAddress);
        json.put("phoneNumber", phoneNumber);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        handleResponse("Update Customer", response);
    }

    private static void deleteCustomer(UUID id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        handleResponse("Delete Customer", response);
    }


    private static void handleResponse(String operation, HttpResponse<String> response) {
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            System.out.println(operation + " Response:\n" + response.body());
        } else {
            System.out.println(operation + " failed. Status code: " + response.statusCode());
            System.out.println("Error message: " + response.body());
        }
    }
}
