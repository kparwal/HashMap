package fellows.kpcb;
import sun.rmi.server.InactiveGroupException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
	    // write your code here
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        HashMap<Integer> hashMap = new HashMap<>();
        System.out.println("***********************************************************************");
        System.out.println("HashMap CLI by Keshav Parwal");
        System.out.println("***********************************************************************");
        System.out.println("\nList of available HashMap commands");
        System.out.println("set\nget\ndelete\nload\nsize\nhelp");
        System.out.println("\nExample usage");
        System.out.println("set hello 73");
        System.out.println("get hello\n73\n");
        System.out.println("delete hello\nSuccessfully deleted key \'hello\'\n");

        String input = "";
        while (!input.equals("exit")) {
            System.out.print("> ");
            try {
                input = reader.readLine();
                reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String [] arguments = parseArguments(input);
            String command = arguments[0];
            try {
                if (command.equals("set")) {
                    hashMap.set(arguments[1], Integer.parseInt(arguments[2]));
                    System.out.println("Succesfully added key " + arguments[1] + " and value " + arguments[2]);
                } else if (command.equals("get")) {
                    System.out.println(hashMap.get(arguments[1]));
                } else if (command.equals(("delete"))) {
                    if (hashMap.delete(arguments[1])) {
                        System.out.println("Successfully deleted key-value pair with key \'"+arguments[1]+"\'");
                    } else {
                        System.out.println("Supplied key argument was not found.");
                    }
                } else if (command.equals("load")) {
                    System.out.println("Load Factor: " + hashMap.load());
                } else if (command.equals("size")) {
                    System.out.println("Current number of items: " + hashMap.size());
                } else if (command.equals("help")) {
                    System.out.println("\nList of available HashMap commands");
                    System.out.println("set\nget\ndelete\nload\nsize\nhelp");
                    System.out.println("\nExample usage");
                    System.out.println("set hello 73");
                    System.out.println("get hello\n73\n");
                    System.out.println("delete hello\nSuccessfully deleted key \'hello\'\n");
                } else {
                    System.out.println("Command not recognized, type \'help\' for the list of commands\n");
                }
            }
            catch (Exception e) {
                System.out.println("Invalid command or arguments - Type \'help\' to see example usage\n");
            }
        }
    }

    private static String[] parseArguments (String input) {
        return input.split("\\s+");
    }
}
