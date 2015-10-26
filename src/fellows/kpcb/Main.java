package fellows.kpcb;

import java.io.Console;

public class Main {
    public static void main(String[] args) {
	    // write your code here
        Console console = System.console();
        boolean IDEEnvironment = false;
        if (console == null) {
            IDEEnvironment = true;
            System.out.println("No console: non-interactive mode!");
            System.exit(0);
        }
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
            if (IDEEnvironment) {
                System.out.println("Please run this in a native command line environment! You are probably trying" +
                        "to run this from an IDE . Navigate to the folder containing the jar and run" +
                        "\n\'java -jar HashMap.jar\'");
                return;
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
