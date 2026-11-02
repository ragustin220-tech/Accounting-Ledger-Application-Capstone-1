package com.pluralsight;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static javax.print.attribute.standard.MediaSizeName.D;

public class Main {
    public static void main(String[] args) {

    //System.out.println("\n--- ACCOUNT LEDGER---");
        // System.out.println("--- HOME SCREEN ---");

        Scanner sc = new Scanner(System.in);


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

    switch (choice){
        case 'D':{
            System.out.println("===ADD DEPOSIT===");
            System.out.println(" ");
            System.out.println("Enter Date (yyyy-dd-mm): ");
            System.out.println("Enter time (hh:mm:ss):  ");
            System.out.println("Enter description of product or service: ");
            System.out.println("Enter vendor name: ");
            System.out.println("Enter amount:  ");
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

        case 'L':{
            System.out.println("--- LEDGER MENU ---");
            System.out.println("A) All (Display all entries)");
            System.out.println("D) Deposit (Display only deposited entries)");
            System.out.println("P) Payments (Display only negative entries");
            System.out.println("R) Reports (run pre-defined report)");
            System.out.println("Enter your choice ( A, D, P, R )");

        }

        
        default :
            System.out.println("Invalid choice, Please enter a capital character either D, P, L or X to exit the application. ");

        }





    }
    }
