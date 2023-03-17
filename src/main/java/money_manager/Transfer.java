package money_manager;

import java.io.Serializable;

/**
 * Represents a single transfer. Implements Serializable
 * interface, so it can be saved and loaded.
 *
 * @see Serializator
 * @see Serializable
 */
public class Transfer implements Serializable {
    /**
     * Represents id of the transfer, used for transfer removal.
     */
    int id;
    /**
     * Represents value of the transfer.
     */
    int val;
    /**
     * Represents transfer's title, given by user for his own convenience.
     */
    String title;
    /**
     * Represents transfer's category, used to display transfers by category.
     */
    String category;
    /**
     * Represents the date on which the transfer occurred.
     */
    String date;

    /**
     * Only constructor of the Transfer class.
     * @param id_temp transfer id, one if this is the first transfer, otherwise id of the previous transfer plus one.
     * @param val_temp value of the transfer provided by the user.
     * @param title title of the transfer provided by the user.
     * @param date_temp date of the transfer provided by the user.
     * @param cat category of the transfer provided by the user.
     */
    Transfer(int id_temp, int val_temp, String title, String date_temp, String cat){
        id = id_temp;
        val = val_temp;
        this.title = title;
        category = cat;
        date = date_temp;
    }
}