package money_manager;

import java.io.Serializable;

/**
 * Represents a single transaction. Implements Serializable
 * interface, so it can be saved and loaded.
 *
 * @see Serializator
 * @see Serializable
 */
public class Transaction implements Serializable {
    /**
     * Represents id of the transaction, used for transaction removal.
     */
    int id;
    /**
     * Represents value of the transaction.
     */
    int val;
    /**
     * Represents transaction's title, given by user for his own convenience.
     */
    String title;
    /**
     * Represents transaction's category, used to display transactions by category.
     */
    String category;
    /**
     * Represents the date on which the transaction occurred.
     */
    String date;
    /**
     * Reference to the paired transfer, used only if created transaction is a transfer between accounts.
     */
    Transaction paired_transfer = null;
    /**
     * Represents exchange rate between currencies of different accounts. Used only if created transaction is
     * a transfer between accounts with two different currencies.
     */
    float exchange_rate = 1;
    /**
     * Reference to the account to which the paired transfer is assigned. Used only if created transaction
     * is a transfer between accounts and only during removal operation.
     */
    Account paired_account = null;
    /**
     * Only constructor of the Transaction class.
     * @param id_temp transaction id, one if this is the first transaction, otherwise id of the previous transaction plus one.
     * @param val_temp value of the transaction provided by the user.
     * @param title title of the transaction provided by the user.
     * @param date_temp date of the transaction provided by the user.
     * @param cat category of the transaction provided by the user.
     */
    Transaction(int id_temp, int val_temp, String title, String date_temp, String cat){
        id = id_temp;
        val = val_temp;
        this.title = title;
        category = cat;
        date = date_temp;
    }

    /**
     * Method that assigns properties of a transfer to Transaction.
     *
     * @param paired_transfer Transfer object assigned to the other of the two accounts in a transfer operation.
     * @param exch_rate exchange rate submitted by the user.
     * @param paired_account The other of the two accounts in a transfer operation.
     * @see Account
     */
    public void create_transfer(Transaction paired_transfer, float exch_rate, Account paired_account){
        this.paired_transfer = paired_transfer;
        exchange_rate = exch_rate;
        this.paired_account = paired_account;
    }
}