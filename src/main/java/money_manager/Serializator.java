package money_manager;

import java.io.*;

/**
 * Class containing methods for serialization of the
 * Manager class instance. It can save and load Manager instance
 * from a file and handles all the exceptions that can arise.
 *
 * @see Manager
 */
public class Serializator {
    /**
     * Method used for saving the Manager class instance.
     * It makes sure that the Money_Manager file exists, and
     * creates it if necessary.
     * @param manager an instance of the Manager class
     * @see Manager
     */
    public static void save(Manager manager){
        try{
            File file = new File("Money_Manager");
            file.delete();
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream("Money_Manager");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(manager);
            objectOutputStream.flush();
            objectOutputStream.close();
        }catch (IOException e){
            System.out.println("Input/Output Exception");
            return;
        }catch (SecurityException e){
            System.out.println("Security exception, no access to the save file");
            return;
        }
    }

    /**
     * Method used for loading previously saved Manager object.
     * It preserves the current Manager object and restores it
     * in case of any errors or exceptions.
     * @param old_manager the current Manager object, used in case the new one cannot be loaded
     * @return Manager object, either loaded if method worked properly or the previous one otherwise
     * @see Manager
     */
    public static Manager load(Manager old_manager){
        Manager manager;
        try {
            FileInputStream fileInputStream = new FileInputStream("Money_Manager");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            manager = (Manager) objectInputStream.readObject();
            objectInputStream.close();
        }catch (IOException e){
            System.out.println("Input/Output Exception, restoring previous data");
            e.printStackTrace();
            return old_manager;
        } catch (ClassNotFoundException e) {
            System.out.println("Save data not found, restoring previous data");
            return old_manager;
        }
        return manager;
    }
}
