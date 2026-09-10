import java.util.Scanner;

class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static boolean userIsLoggedIn = false;

    public static void main(String[] args) {
        // Greeting
        System.out.println("Hey, Im Buddy your small hand in the console. You need to log in before I am allowed to help you!");
        login();

        // While loop as long as the User is logged in
        while (userIsLoggedIn) {

            // General input
            System.out.print("> ");
            String input = scanner.nextLine();

            // Run the void to check whether the command is correct
            checkCommand(input);
        }
    }

    private static void login() {
        while (!userIsLoggedIn) {
            // Asks for the username
            System.out.print("Username: ");
            String usernameInput = scanner.nextLine();

            // Asks for the password
            System.out.print("Password: ");
            String passwordInput = scanner.nextLine();

            // Checks if username und password are correct
            if (usernameInput.equalsIgnoreCase("admin") && passwordInput.equalsIgnoreCase("password")) {
                // If username und password are correct -->
                userIsLoggedIn = true;
                System.out.println("You have logged in Successfully!");
                return;
            }
            // If username or password wasn't correct -->
            System.out.println("Your login wasn't successfully. Maybe the username or password wasn't correct?");
        }
    }

    private static void checkCommand(String input) {

        // Splits input into 3 parts
        String[] commandParts = input.split(" ", 3);
        String command = commandParts.length > 0 ? commandParts[0] : "";
        String subcommand = commandParts.length > 1 ? commandParts[1] : "";
        String value = commandParts.length > 2 ? commandParts[2] : "";

        switch (command) {
            case "exit":
            case "ex":
                exit();
            default:
                System.out.println("Sorry, but I don't know the command " + command);
                break;
        }
    }

    private static void exit() {
        System.out.println("Bye...");
        System.exit(0);
    }
}