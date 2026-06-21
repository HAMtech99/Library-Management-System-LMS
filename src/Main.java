public class Main {

    // Application entry point - launches the Library Management System
    public static void main(String[] args) {

        // Print welcome message to greet the user on startup
        System.out.println("Welcome to the Library Management System!");

        // Create the LibrarySystem to hold all patron data in memory
        LibrarySystem system = new LibrarySystem();

        // Create the MenuHandler and pass the system to it
        // MenuHandler owns all user interaction from this point forward
        MenuHandler menuHandler = new MenuHandler(system);

        // Start the menu loop - program runs until user selects Exit
        menuHandler.run();
    }
}