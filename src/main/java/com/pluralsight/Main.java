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

            char choice = sc.next().charAt(0);
            sc.nextLine();

            switch (choice) {
                case 'D': {
                    String filePath = "transaction.csv";

                    Scanner input = new Scanner(System.in);

                    System.out.println("===ADD DEPOSIT===");

                    System.out.println(" ");
                    System.out.println("Enter Date (yyyy-dd-mm): ");
                    String depositDate = input.nextLine();
                    System.out.println("Enter time (hh:mm:ss):  ");
                    String depositTime = input.nextLine();
                    System.out.println("Enter description of product or service: ");
                    String depositDescription = input.nextLine();
                    System.out.println("Enter vendor name: ");
                    String depositVendor = input.nextLine();
                    System.out.println("Enter amount:  ");
                    String depositAmountString = input.nextLine();

                    double depositAmount = Double.parseDouble(depositAmountString);
                    Transactions newDeposit = new Transactions(depositDate, depositTime, depositDescription, depositVendor, depositAmount );
                    inventoryList.add(newDeposit);

                    try (java.io.FileWriter fw = new java.io.FileWriter("transactions.csv", true);
                        java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)){

                        String line = depositDate + "|" + depositTime + "|" +depositDescription + "|" + depositVendor + "|" + depositAmountString;

                        bw.write(line);
                        bw.newLine();

                        System.out.println("Deposit entry was saved !");

                    }
                    catch (IOException e){
                        System.out.println("Could not save the file. Please try again.");
                    }
                }
                break;

                case 'P':

                    Scanner input2 = new Scanner(System.in);
                    System.out.println("---MAKE PAYMENT---");

                    System.out.println(" ");
                    System.out.println("Enter Date (yyyy-dd-mm): ");
                    String paymentDate = input2.nextLine();
                    System.out.println("Enter time (hh:mm:ss):  ");
                    String paymentTime = input2.nextLine();
                    System.out.println("Enter description of product or service: ");
                    String paymentDescription = input2.nextLine();
                    System.out.println("Enter vendor name: ");
                    String paymentVendor = input2.nextLine();
                    System.out.println("Enter amount:  ");
                    String paymentAmountString = input2.nextLine();

                    double paymentAmount = Double.parseDouble(paymentAmountString);
                    Transactions newpaymentAmount = new Transactions(paymentDate, paymentTime, paymentDescription, paymentVendor, paymentAmount );
                    inventoryList.add(newpaymentAmount);


                    try (java.io.FileWriter fw = new java.io.FileWriter("transactions.csv", true);
                         java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)){

                        String line = paymentDate + "|" + paymentTime + "|" + paymentDescription + "|" + paymentVendor + "|" + "-$" + paymentAmountString;

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
                    LocalDate date = LocalDate.parse(parts[0].trim());
                    LocalTime time = LocalTime.parse(parts[1].trim());
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
