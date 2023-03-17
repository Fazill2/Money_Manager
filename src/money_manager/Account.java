package money_manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a single account.
 * Implements Serializable interface, so it can be
 * saved and loaded.
 *
 * @see Serializable
 * @see Serializator
 */
public class Account implements Serializable {
    /**
     * Represents id of the account.
     */
    int id;
    /**
     * Represents the name of the account, provided by the user.
     */
    String name;
    /**
     * A set representing categories.
     * Set is used so that all the categories are
     * unique.
     * @see HashSet
     */
    Set<String> categories = new HashSet<>();
    /**
     * Represents the currency of the account.
     */
    String currency;
    /**
     * Represents the current balance of the account.
     * Is modified whenever a transaction is added or removed.
     */
    int balance;
    /**
     * Represent the balance of the account at the moment
     * the account was created.
     */
    int initial_balance;
    /**
     * An ArrayList of transactions, contains all transactions created by a user
     * and added to a specific account.
     * ArrayList is used as to provide an easy way of adding new transactions,
     * and to enable easy removal of them.
     */
    ArrayList<Transaction> transactions;

    /**
     * Only constructor of the Account class.
     * @param id account id, one if this is the first account, otherwise id of the previous account plus one.
     * @param name name of the account provided by the user.
     * @param curr currency of the account provided by the user.
     * @param initial_balance initial balance provided by the user.
     */
    Account(int id, String name, String curr, int initial_balance){
        this.name = name;
        this.id = id;
        balance = initial_balance;
        currency = curr;
        this.initial_balance = initial_balance;
        transactions = new ArrayList<>();
    }

    /**
     * Method for adding new transactions to the transactions field.
     * It uses the add method from the ArrayList to do so,
     * adds transaction's category to the set and modifies the balance.
     *
     * @param transaction an instance of the Transaction class.
     * @see Transaction
     * @see ArrayList
     */
    public void add_transaction(Transaction transaction){
        transactions.add(transaction);
        categories.add(transaction.category);
        balance = balance + transaction.val;
    }

    /**
     * Method for removing transaction with a certain id.
     * It iterates over all transactions and in case it's necessary
     * modifies the balance of the account. If the chosen transaction
     * is also a transfer between accounts this method removes the paired
     * transaction from the other account.
     *
     * @param id id of the transaction to be removed, provided by the user.
     * @see Transaction
     */
    public void remove_transaction(int id){
        for (Transaction transaction : transactions){
            if (transaction.id == id){
                if (transaction.paired_transfer != null){
                    transaction.paired_account.remove_transaction(transaction.paired_transfer.id);
                }
                balance -= transaction.val;
                transactions.remove(transaction);
                break;
            }
        }
    }
}
