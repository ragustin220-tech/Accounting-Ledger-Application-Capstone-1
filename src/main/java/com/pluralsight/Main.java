package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.ArrayList;

import static javax.print.attribute.standard.MediaSizeName.D;

public class Main {
    public static ArrayList<Transactions> inventoryList = new ArrayList<>();

    public static void main(String[] args) {

        //System.out.println("\n--- ACCOUNT LEDGER---");
        // System.out.println("--- HOME SCREEN ---");
        loadTransactions();
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
            String input = sc.nextLine().trim().toUpperCase();

            if(input.isEmpty()){
                System.out.println("Please enter a valid choice.");
                continue;
            }

            char choice =input.charAt(0);


            switch (choice) {
                case 'D':
                    System.out.println("\n=== ADD DEPOSIT ===");
                    System.out.print("Enter Date (yyyy-MM-dd): ");
                    String depositDate = sc.nextLine();

                    System.out.print("Enter time (hh:mm:ss): ");
                    String depositTime = sc.nextLine();

                    System.out.print("Enter description of product or service: ");
                    String depositDescription = sc.nextLine();

                    System.out.print("Enter vendor name: ");
                    String depositVendor = sc.nextLine();

                    double depositAmount = 0;
                    boolean validAmount = false;

                    while (!validAmount) {
                        System.out.print("Enter amount: ");
                        String depositAmountString = sc.nextLine().trim();

                        try {
                            if (depositAmountString.isEmpty()) {
                                System.out.println(" Amount cannot be empty! Please try again.");
                                continue;
                            }

                            depositAmount = Double.parseDouble(depositAmountString);
                            validAmount = true; // ⚠️ ONLY turns true if parsing succeeds!

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number amount.");
                        }
                    }

                    try {
                        java.time.LocalDate parsedDate = java.time.LocalDate.parse(depositDate);
                        java.time.LocalTime parsedTime = java.time.LocalTime.parse(depositTime);

                        Transactions newDeposit = new Transactions(parsedDate, parsedTime, depositDescription, depositVendor, depositAmount);
                        inventoryList.add(newDeposit);

                        try (java.io.FileWriter fw = new java.io.FileWriter("transactions.csv", true);
                             java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {

                            String line = depositDate + "|" + depositTime + "|" + depositDescription + "|" + depositVendor + "|" + depositAmount;
                            bw.write(line);
                            bw.newLine();

                            System.out.println("Deposit entry was saved successfully!");
                        }
                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println(" Date must be in yyyy-MM-dd and Time must be hh:mm:ss. Deposit cancelled.");
                    } catch (IOException e) {
                        System.out.println("Could not save the file. Please try again.");
                    }
                    break;


                case 'P':

                    Scanner input2 = new Scanner(System.in);
                    System.out.println("---MAKE PAYMENT---");

                    System.out.println(" ");
                    System.out.println("Enter Date (yyyy-dd-mm): ");
                    String paymentDate = sc.nextLine();
                    System.out.println("Enter time (hh:mm:ss):  ");
                    String paymentTime = sc.nextLine();
                    System.out.println("Enter description of product or service: ");
                    String paymentDescription = sc.nextLine();
                    System.out.println("Enter vendor name: ");
                    String paymentVendor = sc.nextLine();
                    System.out.println("Enter amount:  ");
                    String paymentAmountString = sc.nextLine();

                    double paymentAmount = Double.parseDouble(paymentAmountString);
                    LocalDate parsedPaymentDate = LocalDate.parse(paymentDate);
                    LocalTime parsedPaymentTime = LocalTime.parse(paymentTime);
                    Transactions newpaymentAmount = new Transactions(parsedPaymentDate, parsedPaymentTime, paymentDescription, paymentVendor, paymentAmount );
                    inventoryList.add(newpaymentAmount);


                    try (java.io.FileWriter fw = new java.io.FileWriter("transactions.csv", true);
                         java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)){

                        String line = paymentDate + "|" + paymentTime + "|" + paymentDescription + "|" + paymentVendor + "|" + "-$" + paymentAmount;

                        bw.write(line);
                        bw.newLine();

                        System.out.println("Payment entry was saved !");

                    }
                    catch (IOException e){
                        System.out.println("Could not save the file. Please try again.");
                    }

                    break;

                case 'L': {
                    System.out.println("--- LEDGER MENU ---");
                    System.out.println("A) All (Display all entries)");
                    System.out.println("D) Deposit (Display only deposited entries)");
                    System.out.println("P) Payments (Display only negative entries)");
                    System.out.println("R) Reports (run pre-defined report)");
                    System.out.println("Enter your choice ( A, D, P, R ):\n");
                    String ledgerChoice = sc.nextLine().trim().toUpperCase();

                    if (ledgerChoice.equals("A")) {
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

        String filePath = "transactions.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    java.time.format.DateTimeFormatter timeFormatter  = java.time.format.DateTimeFormatter.ofPattern("H:mm:ss");
                    LocalDate date = LocalDate.parse(parts[0].trim());
                    LocalTime time = LocalTime.parse(parts[1].trim(), timeFormatter);

                    String description = parts[2].trim();
                    String vendor = parts[3].trim();
                    String amountText = parts[4].trim().replace("$","");
                    double amount = Double.parseDouble(amountText);

                    Transactions newTransactions = new Transactions(date, time, description, vendor, amount);
                    inventoryList.add(newTransactions);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Couldn't load file" + e.getMessage());
        }
    }
}
