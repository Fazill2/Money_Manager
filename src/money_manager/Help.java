package money_manager;

/**
 * Class containing functions displaying help.
 */
public class Help {
    /**
     * Main method of displaying help, provides user with all the available
     * commands and their use.
     */
    public static void main_help(){
         System.out.println("Help for Money Manager");
         System.out.println("Available commands (case insensitive):");
         System.out.println("help - provides information about the commands, <- you are here");
         System.out.println("end - terminates the program");
         System.out.println("add account - adds account with chosen parameters");
         System.out.println("remove account - removes account of the chosen name");
         System.out.println("add transaction - adds transaction of chosen parameters to chosen account");
         System.out.println("remove transaction - removes transaction with chosen Id from chosen account");
        System.out.println("add transfer - adds transfer of chosen parameters between chosen accounts");
         System.out.println("display accounts - displays account/s in chosen way");
         System.out.println("save - saves current data");
         System.out.println("load - loads previously saved data");
    }

    /**
     * Method for displaying information in case of an incorrect input.
     * Suggest that the user should use the "help" command.
     */
    public static void incorrect_input_help(){
        System.out.println("Incorrect Input");
        System.out.println("In case of a  difficulty write \"help\" for help");
    }
}
