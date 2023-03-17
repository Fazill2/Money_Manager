package money_manager;

import java.nio.charset.Charset;
import java.util.Scanner;


/**
 * Main Class, containing the event loop.
 */
public class Main {
    /**
     * Method with the event loop
     * @param args command line args, ignored
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, Charset.defaultCharset());
        Manager manager = new Manager();
        boolean end_flag = false;
        Manager.scanner = scanner;
        while (!end_flag){
            System.out.println("What do you want to do?");
            String answer = scanner.nextLine();
            switch (answer.toLowerCase()){
                case "help" -> Help.main_help();
                case "end" -> end_flag = true;
                case "add transaction" -> manager.add_transaction();
                case "remove transaction" -> manager.remove_transaction();
                case "add transfer" -> manager.add_transfer();
                case "display accounts" -> manager.display_accounts();
                case "add account" -> manager.add_account();
                case "remove account" -> manager.remove_account();
                case "save" -> Serializator.save(manager);
                case "load" -> manager = Serializator.load(manager);
                default -> Help.incorrect_input_help();
            }
        }
    }
}
