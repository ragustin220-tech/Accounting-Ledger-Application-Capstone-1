package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.ArrayList;

import static javax.print.attribute.standard.MediaSizeName.D;

public class Main {
    public static ArrayList<Transactions> inventoryList = new ArrayList<>();

    public static void main(String[] args) {

        //System.out.println("\n--- ACCOUNT LEDGER---");
        // System.out.println("--- HOME SCREEN ---");

        Scanner sc = new Scanner(System.in);

        boolean running = true;
        while (running) {

            System.out.println("""
                    --- ACCOUNT LEDGER ---
                    ---- HOME SCREEN ----
                    """


            );
            System.out.println("D) Add Deposit");
            System.out.println("P) Make payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.println("Enter your choice ( D, P, L OR X): ");

            char choice = sc.next().charAt(0);

            switch (choice) {
                case 'D': {
                    String filePath = "transaction.csv";

                    Scanner input = new Scanner(System.in);

                    System.out.println("===ADD DEPOSIT===");

                    System.out.println(" ");
                    System.out.println("Enter Date (yyyy-dd-mm): ");
                    String date = input.nextLine();
                    System.out.println("Enter time (hh:mm:ss):  ");
                    String time = input.nextLine();
                    System.out.println("Enter description of product or service: ");
                    String description = input.nextLine();
                    System.out.println("Enter vendor name: ");
                    String vendor = input.nextLine();
                    System.out.println("Enter amount:  ");
                    String amount = input.nextLine();

                }
                break;

                case 'P':
                    System.out.println("---MAKE PAYMENT---");
                    System.out.println(" ");
                    System.out.println("Enter Date (yyyy-dd-mm): ");
                    System.out.println("Enter time (hh:mm:ss):  ");
                    System.out.println("Enter description of product or service: ");
                    System.out.println("Enter vendor name: ");
                    System.out.println("Enter amount:  ");

                    break;

                case 'L': {
                    System.out.println("--- LEDGER MENU ---");
                    System.out.println("A) All (Display all entries)");
                    System.out.println("D) Deposit (Display only deposited entries)");
                    System.out.println("P) Payments (Display only negative entries");
                    System.out.println("R) Reports (run pre-defined report)");
                    System.out.println("Enter your choice ( A, D, P, R )");
                    String ledgerChoice = sc.nextLine().toUpperCase();

                    if (ledgerChoice.equals("A")){
                        System.out.println("\n--- All Transactions---");
                        for (Transactions t : inventoryList) {
                            System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + " | $" + t.getAmount());
                        }
                    }
                }
                break;
                case 'X': {
                    System.out.println("Exit the application.");
                    running = false;
                    break;
                }


                default:
                    System.out.println("Invalid choice, Please enter a capital character either D, P, L or X to exit the application. \n");

            }
        }
        sc.close();
    }
    public static void loadTransactions() {
        inventoryList.clear();

        String filePath = "src/main/resources/transactions.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String date = parts[0].trim();
                    String time = parts[1].trim();
                    String description = parts[2].trim();
                    String vendor = parts[3].trim();

                    double amount = Double.parseDouble(parts[4].trim());

                    Transactions newTransactions = new Transactions(date, time, description, vendor, amount);
                    inventoryList.add(newTransactions);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Couldn't load file" + e.getMessage());
        }
    }
}
