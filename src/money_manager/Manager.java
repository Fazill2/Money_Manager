package money_manager;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main class of the program, responsible for storing data
 * reading user inputs and calling other functions.
 * It handles creating new accounts, adding new transaction
 * and basically all the main operations of the program.
 * It implements the Serializable interface, so the data can be saved
 * and loaded using the Serializator class.
 *
 * @see Serializable
 * @see Serializator
 */
public class Manager implements Serializable {
    /**
     * An ArrayList of accounts, contains all accounts created by a user.
     * ArrayList is used as to provide an easy way of adding new accounts,
     * and to enable easy removal of them.
     *
     * @see Account
     * @see java.util.ArrayList
     */
    ArrayList<Account> accounts = new ArrayList<>();
    static Scanner scanner;

    /**
     * Return the index of an account with the name provided by the user.
     * In case the account does not exist an exception is thrown.
     *
     * @param account_name an account name, provided by the user
     * @return the index of an account with the chosen name
     * @throws AccountNonExistentException in case the account with the chosen name does not exist, an exception is thrown.
     * @see Account
     */
    public int find_account(String account_name) throws AccountNonExistentException {
        int i = 0;
        for (Account account: accounts){
            if (account.name.equals(account_name)){
                return i;
            }
            i++;
        }
        throw new AccountNonExistentException();
    }

    /**
     * Method for displaying accounts.
     * Based on the user input it can either display all the accounts
     * or just one, as chosen by the user. It uses the Display class
     * to call the methods that actually display the data. It accounts
     * for several possible input strings.
     *
     * @see Display
     */
    public void display_accounts() {
        System.out.println("Display all accounts or one?");
        String answer = scanner.nextLine();
        switch (answer.toLowerCase()) {
            case "one", "one account", "one of them" -> {
                System.out.println("Provide account name:");
                String name = scanner.nextLine();
                Account account;
                try {
                    account = accounts.get(find_account(name));
                } catch (AccountNonExistentException e) {
                    System.out.println("No account of that name exists");
                    return;
                }
                System.out.println("Basic display, by category display or by date?");
                String second_answer = scanner.nextLine();
                switch (second_answer.toLowerCase()) {
                    case "by category", "category", "category display" -> Display.display_by_category(account);
                    case "basic", "basic display" -> Display.display_account(account);
                    case "by date", "date", "date display" ->{
                        System.out.println("Please provide date after which you want the transfers" +
                                " to be in yyyy-mm-dd format:");
                        boolean date_flag;
                        String date;
                        do {
                            date = scanner.nextLine();
                            date_flag = false;
                            Pattern pattern = Pattern.compile("^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
                            Matcher matcher = pattern.matcher(date);
                            if (!matcher.matches()) {
                                System.out.println("Incorrect date format, please use yyyy-mm-dd");
                                date_flag = true;
                            }
                        } while (date_flag);
                        Date after_date = null;
                        try {
                            after_date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                        }catch (ParseException ignored){
                        }
                        System.out.println("Please provide date before which you want the transfers to" +
                                " be in yyyy-mm-dd format:");
                        do {
                            date = scanner.nextLine();
                            date_flag = false;
                            Pattern pattern = Pattern.compile("^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
                            Matcher matcher = pattern.matcher(date);
                            if (!matcher.matches()) {
                                System.out.println("Incorrect date format, please use yyyy-mm-dd");
                                date_flag = true;
                            }
                        } while (date_flag);
                        Date before_date = null;
                        try {
                            before_date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                        }catch (ParseException ignored){
                        }
                        Display.display_by_date(account, before_date, after_date);
                    }
                    default -> System.out.println("Incorrect Input, Write \"help\" for help");
                }
            }
            case "all", "all accounts", "all of them" -> Display.basic_display(accounts);
            default -> Help.incorrect_input_help();
        }
    }

    /**
     * Method for adding a transaction to a chosen account.
     * It accepts user inputs and based on that creates
     * a transaction object and adds it to a chosen account.
     * It also makes sure the data is correct and that the
     * date is in the correct format using regex. This is
     * achieved using a method create_transaction that creates
     * a transaction with parameters chosen by the user. If all
     * the data is correct then it call the method add_transaction
     * from the Account class to add the Transaction object to the
     * chosen account.
     *
     * @see Transaction
     * @see Account
     */
    public void add_transaction() {
        System.out.println("To which account?");
        String account_name = scanner.nextLine();
        Account account;
        try {
            account = accounts.get(find_account(account_name));
        } catch (AccountNonExistentException e) {
            System.out.println("No account of that name exists");
            return;
        }
        Transaction transaction = create_transaction(account);
        account.add_transaction(transaction);
    }

    /**
     * Method for creating transactions.
     * Created transactions have parameters chosen by the user
     * and the method checks their correctness before
     * creating and returning a new Transaction.
     *
     * @param account instance of the Account class, account to which the transaction is meant to be added
     * @return instance of the Transactions class with parameters chosen by the user
     * @see Transaction
     * @see Account
     */
    public Transaction create_transaction(Account account){
        System.out.println("Provide transaction title:");
        String name = scanner.nextLine();
        System.out.println("Provide category:");
        String category = scanner.nextLine();
        System.out.println("Provide date of the transaction in yyyy-mm-dd format:");
        boolean date_flag;
        String date;
        do {
            date = scanner.nextLine();
            date_flag = false;
            Pattern pattern = Pattern.compile("^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
            Matcher matcher = pattern.matcher(date);
            if (!matcher.matches()) {
                System.out.println("Incorrect date format, please use yyyy-mm-dd");
                date_flag = true;
            }
        } while (date_flag);
        boolean int_flag;
        System.out.println("Provide transaction value (with a minus sign for expenditure):");
        int val = 0;
        do {
            try {
                int_flag = false;
                val = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please provide an Integer");
                int_flag = true;
            }
        } while (int_flag);

        return new Transaction((account.transactions.size()>0) ?
                account.transactions.get(account.transactions.size()-1).id + 1 : 1, val, name, date, category);
    }

    /**
     * Method for removing the transaction by id.
     * User provides an account name, and in case
     * that the account exists provides the id of
     * the transaction to be removed. All transactions
     * with that id are then removed from the chosen account
     * with the method remove_transaction from the Account class.
     *
     * @see Account
     */
    public void remove_transaction(){
        System.out.println("From which account?");
        String account_name = scanner.nextLine();
        int index;
        try {
            index = find_account(account_name);
        }catch (AccountNonExistentException e){
            System.out.println("Account of such name does not exist");
            return;
        }
        System.out.println("Provide id of the soon to be removed transaction:");
        boolean int_flag;
        int val = -1;
        do {
            try {
                int_flag = false;
                val = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please provide an Integer");
                int_flag = true;
            }
        } while (int_flag);
        accounts.get(index).remove_transaction(val);
    }

    /**
     * Method for adding a transfer between chosen accounts.
     * It accepts user inputs and based on that creates
     * a transaction object and adds it to a chosen account.
     * It also makes sure the data is correct and that the
     * date is in the correct format using regex. If all
     * the data is correct then it call the method add_transaction
     * from the Account class to add the Transaction object to the
     * chosen account. This is achieved using a method that creates
     * a transaction with parameters chosen by the user.
     *
     * @see Transaction
     * @see Account
     */
    public void add_transfer(){
        System.out.println("From which account?");
        String account_name_1 = scanner.nextLine();
        System.out.println("To which account?");
        String account_name_2 = scanner.nextLine();
        if (account_name_2.equals(account_name_1)){
            System.out.println("Please provide two different account names");
            return;
        }
        Account account_from;
        Account account_to;
        try {
            account_from = accounts.get(find_account(account_name_1));
            account_to = accounts.get(find_account(account_name_2));
        } catch (AccountNonExistentException e) {
            System.out.println("No account of that name exists");
            return;
        }
        Transaction transfer_from = create_transaction(account_from);
        float exchange_rate = 1;
        if (!account_from.currency.equals(account_to.currency)){
            System.out.println("Provide exchange rate:");
            boolean float_flag;
            do {
                try {
                    float_flag = false;
                    exchange_rate = Float.parseFloat(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Please provide an Integer");
                    float_flag = true;
                }
            } while (float_flag);
        }
        Transaction transfer_to = new Transaction((account_to.transactions.size()>0) ?
                account_to.transactions.get(account_to.transactions.size()-1).id + 1 : 1,
                (int) (-transfer_from.val*exchange_rate), transfer_from.title, transfer_from.date, transfer_from.category);
        transfer_to.create_transfer(transfer_from, exchange_rate, account_from);
        transfer_from.create_transfer(transfer_to, 1/exchange_rate, account_to);
        account_to.add_transaction(transfer_to);
        account_from.add_transaction(transfer_from);
    }

    /**
     * Method for adding account to the accounts field.
     * An account is added only if the provided name is unique.
     * The user provides several parameters of the account,
     * and if all are correct the account is added using the add
     * method from ArrayList.
     *
     * @see Account
     * @see java.util.ArrayList
     */
    public void add_account() {
        boolean flag;
        System.out.println("Provide account name:");
        String name = scanner.nextLine();
        try{
            find_account(name);
            System.out.println("Account of such name already exists");
            return;
        }catch (AccountNonExistentException ignored){
        }
        System.out.println("Provide currency of that account:");
        String currency = scanner.nextLine();
        System.out.println("Provide account balance:");
        Integer balance = null;
        do {
            try {
                flag = false;
                balance = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please provide an Integer");
                flag = true;
            }
        } while (flag);
        Account temp_account = new Account((accounts.size()>0) ? accounts.get(accounts.size() -1).id +1: 1,
                name, currency, balance);
        accounts.add(temp_account);
    }

    /**
     * Method for removing account with the provided name.
     * In case no account with that name exists the function just returns.
     * Otherwise, the Account object with the chosen name is found using the
     * find_account method and removed using the remove method from the
     * ArrayList class.
     *
     * @see Account
     * @see java.util.ArrayList
     */
    public void remove_account(){
        System.out.println("Provide the name of the soon to be removed account:");
        String name = scanner.nextLine();
        Account account;
        try {
            account = accounts.get(find_account(name));
        } catch (AccountNonExistentException e) {
            System.out.println("No account of that name exists");
            return;
        }
        accounts.remove(account);
    }
}
