package money_manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Class used for displaying data to the user.
 * It contains several methods for displaying the
 * accounts in a few different ways.
 */
public class Display {
    /**
     * Method for displaying all the accounts at once using display_account
     * method on all of them iteratively.
     * @param accounts an ArrayList of accounts, provided by the Manager Class.
     * @see Manager
     * @see Account
     */
    public static void basic_display(ArrayList<Account> accounts){
        if (accounts.size() != 0) {
            for (Account account : accounts) {
                display_account(account);
            }
        }
        else{
            System.out.println("No accounts to display");
        }
    }

    /**
     * Method for displaying a single instance of the account class.
     * It displays all properties of the instance in a readable way
     * and all properties of the transaction class instances in the
     * ArrayList transactions.
     * @param account a single instance of the Account class.
     * @see Account
     * @see Transaction
     */
    public static void display_account(Account account){
        System.out.println("-----------------");
        System.out.println("Name: " + account.name);
        System.out.println("Initial balance: " + account.initial_balance + " " + account.currency);
        System.out.println("Current balance: " + account.balance + " " + account.currency);
        System.out.println("Transactions:");
        System.out.println("Id | Title | Date | Value");
        for (Transaction t : account.transactions) {
            System.out.println(t.id + " | " + t.title + " | " + t.date + " | " + t.val + " " + account.currency);
        }
    }

    /**
     * Method for displaying a single instance of the account class.
     * It displays all properties of the instance in a readable way
     * and all properties of the transaction class instances in the
     * ArrayList transactions by category.
     * @param account a single instance of the Account class.
     * @see Account
     * @see Transaction
     */
    public static void display_by_category(Account account){
        System.out.println("-----------------");
        System.out.println("Name: " + account.name);
        System.out.println("Initial balance: " + account.initial_balance + " " + account.currency);
        System.out.println("Current balance: " + account.balance + " " + account.currency);
        System.out.println("Transactions:");
        System.out.println("Id | Title | Date | Value");
        int sum, expenditure, earnings;
        for (String category: account.categories){
            sum = 0;
            earnings = 0;
            expenditure = 0;
            System.out.println(category+":");
            for (Transaction t : account.transactions) {
                if (t.category.equals(category)) {
                    sum += t.val;
                    earnings += Math.max(t.val, 0);
                    expenditure += Math.min(t.val, 0);
                    System.out.println(t.id + " | " + t.title + " | " + t.date + " | " + t.val + " " + account.currency);
                }
            }
            System.out.println("Total expenditure of " + category +": " + expenditure + " " + account.currency);
            System.out.println("Total earnings of " + category +": " + earnings + " " + account.currency);
            System.out.println("Total value of " + category +": " + sum + " " + account.currency);
        }
    }

    /**
     * Method for displaying a single instance of the account class.
     * It displays all properties of the instance in a readable way
     * and all properties of the transaction class instances in the
     * ArrayList transactions by category.
     * @param account a single instance of the Account class
     * @param after_date instance of the Date class, only transactions after it wil be displayed
     * @param before_date instance of the Date class, only transactions before it wil be displayed
     * @see Account
     * @see Transaction
     */
    public static void display_by_date(Account account, Date before_date, Date after_date){
        Date transfer_date = null;
        System.out.println("-----------------");
        System.out.println("Name: " + account.name);
        System.out.println("Initial balance: " + account.initial_balance + " " + account.currency);
        System.out.println("Current balance: " + account.balance + " " + account.currency);
        System.out.println("Transactions:");
        System.out.println("Id | Title | Date | Value");
        for (Transaction t : account.transactions) {
            try {
                transfer_date = new SimpleDateFormat("yyyy-MM-dd").parse(t.date);
            }catch (ParseException ignored){
            }
            if (transfer_date == null || (transfer_date.after(after_date) && transfer_date.before(before_date))) {
                System.out.println(t.id + " | " + t.title + " | " + t.date + " | " + t.val + " " + account.currency);
            }
        }
    }
}
