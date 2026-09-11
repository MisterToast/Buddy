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

        // Checks the command for matches
        switch (command) {
            case "logout":
                logout(subcommand);
                break;
            case "help":
                help();
                break;
            case "exit":
            case "ex":
                exit(subcommand);
                break;
            default:
                System.out.println("Sorry, but I don't know the command " + command);
                break;
        }
    }

    private static void logout(String subcommand) {
        // If User already confirmed to log out
        if (subcommand.equalsIgnoreCase("-y")) {
            System.out.println("You have successfully logged out...");
            userIsLoggedIn = false;
            login();
            return;
        }

        // If User didn't confirm yet
        System.out.println("Do you really want to logout?");
        System.out.print("> ");
        String input = scanner.nextLine();
        // Checks if input equals "yes" or "y" to log out
        if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
            System.out.println("You have successfully logged out...");
            userIsLoggedIn = false;
            login();
            return;
        }

        // If User doesn't want to log out
        System.out.println("You didn't log out. You can continue like normal...");
    }

    private static void help() {
        System.out.println("Command | Subcommand | Value | Explanation");
        System.out.println("--------|------------|-------|------------");
        System.out.println("logout  | (-y)       |       | Log out of your account");
        System.out.println("help    |            |       | Show this help table");
        System.out.println("exit    | (-y)       |       | Exit and terminate the application");
    }

    private static void exit(String subcommand) {
        // If User already confirmed to exit
        if (subcommand.equalsIgnoreCase("-y")) {
            System.out.println("Bye...");
            System.exit(0);
        }

        // If User didn't confirm yet
        System.out.println("Do you really want to exit?");
        System.out.print("> ");
        String input = scanner.nextLine();
        // Checks if input equals "yes" or "y" to exit
        if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
            System.out.println("Bye...");
            System.exit(0);
        }

        // If User doesn't want to exit
        System.out.println("You didn't exit. You can continue like normal...");
    }
}