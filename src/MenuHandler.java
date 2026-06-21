import java.util.Scanner;

public class MenuHandler {

    private Scanner scanner;
    private LibrarySystem system;

    // Constructor - receives the LibrarySystem, creates the Scanner
    public MenuHandler(LibrarySystem system) {
        this.system = system;
        this.scanner = new Scanner(System.in);
    }

    // Main loop - runs until user selects 6
    public void run() {
        while (true) {
            displayMenu();
            int choice = getMenuChoice();
            handleMenuChoice(choice);
        }
    }

    // Prints the menu to the console
    public void displayMenu() {
        System.out.println("\n===========================================");
        System.out.println("        Library Management System");
        System.out.println("===========================================");
        System.out.println("1. Add Patron from File");
        System.out.println("2. Add Patron Manually");
        System.out.println("3. Remove Patron");
        System.out.println("4. Display All Patrons");
        System.out.println("5. Exit");
        System.out.println("6. Clear All Patrons");
        System.out.println("7. Update Patron Fine");
        System.out.println("===========================================");
        System.out.print("Enter your choice: ");
    }

    // Reads and validates the menu choice
    private int getMenuChoice() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 7) {
                    return choice;
                } else {
                    System.out.print("Invalid option. Please enter a number between 1 and 7: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid option. Please enter a number between 1 and 7: ");
            }
        }
    }

    // Routes the choice to the correct method
    public void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                promptAddPatronFromFile();
                break;
            case 2:
                promptAddPatronManual();
                break;
            case 3:
                promptRemovePatron();
                break;
            case 4:
                // Option 4 - display all patrons
                system.displayAllPatrons();
                break;
            case 5:
                // Option 5 - exit the program
                System.out.println("Exiting LMS. Goodbye.");
                System.exit(0);
                break;
            case 6:
                promptClearAllPatrons();
                break;
            case 7:
                promptUpdatePatronFine();
                break;
            default:
                System.out.println("Invalid option. Please enter a number between 1 and 7.");
        }
    }

    // Option 1 - load from file
    public void promptAddPatronFromFile() {
        System.out.print("Enter the path to your patron data file (or press Enter to cancel): ");
        String path = scanner.nextLine().trim();
        if (path.isEmpty()) {
            System.out.println("No path entered. Returning to menu.");
            return;
        }
        boolean success = FileHandler.loadFromFile(path, system);
        if (success) {
            System.out.println("\nUpdated patron list:");
            system.displayAllPatrons();
        }
    }

    // Option 2 - add manually
    public void promptAddPatronManual() {
        System.out.println("Press Enter at any time to cancel and return to the menu.");

        // Get and validate ID
        int patronId = 0;
        while (true) {
            System.out.print("Enter 7-digit Patron ID: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Add patron cancelled. Returning to menu.");
                return;
            }

            try {
                patronId = Integer.parseInt(input);
                if (!system.isValidId(patronId)) {
                    System.out.println("Invalid ID. Must be a 7-digit number (1000000 - 9999999).");
                } else if (!system.isIdUnique(patronId)) {
                    System.out.println("ID " + patronId + " already exists. Please enter a different ID.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric 7-digit ID.");
            }
        }

        // Get and validate name
        String name = "";
        while (true) {
            System.out.print("Enter Patron Name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Add patron cancelled. Returning to menu.");
                return;
            } else {
                break;
            }
        }

        // Get and validate address
        String address = "";
        while (true) {
            System.out.print("Enter Patron Address: ");
            address = scanner.nextLine().trim();
            if (address.isEmpty()) {
                System.out.println("Add patron cancelled. Returning to menu.");
                return;
            } else {
                break;
            }
        }

        // Get and validate fine
        double fine = 0.0;
        while (true) {
            System.out.print("Enter Overdue Fine Amount ($0.00 - $250.00): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Add patron cancelled. Returning to menu.");
                return;
            }
            try {
                fine = Double.parseDouble(input);
                if (!system.isValidFine(fine)) {
                    System.out.println("Invalid fine. Must be between $0.00 and $250.00.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric fine amount.");
            }
        }

        // All fields valid - create and add patron
        Patron patron = new Patron(patronId, name, address, fine);
        system.addPatron(patron);
        System.out.println("Patron " + name + " (ID: " + patronId + ") added successfully.");

        // Display updated list
        System.out.println("\nUpdated patron list:");
        system.displayAllPatrons();
    }

    // Option 3 - remove patron
    public void promptRemovePatron() {
        int patronId = 0;
        while (true) {
            System.out.print("Enter the 7-digit Patron ID to remove (or press Enter to cancel): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Remove cancelled. Returning to menu.");
                return;
            }

            try {
                patronId = Integer.parseInt(input);
                if (!system.isValidId(patronId)) {
                    System.out.println("Invalid ID. Must be a 7-digit number (1000000 - 9999999).");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric 7-digit ID.");
            }
        }

        Patron patron = system.getPatronById(patronId);

        if (patron == null) {
            System.out.println("No patron with ID " + patronId + " was found.");
            System.out.println("\nUpdated patron list:");
            system.displayAllPatrons();
            return;
        }

        // Confirm before removing
        System.out.print("Are you sure you want to remove " + patron.getName() + " (ID: " + patronId + ")? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes")) {
            system.removePatron(patronId);
            System.out.println("Patron " + patron.getName() + " (ID: " + patronId + ") has been removed.");
        } else {
            System.out.println("Remove cancelled.");
        }

        // Display updated list
        System.out.println("\nUpdated patron list:");
        system.displayAllPatrons();
    }

    // Option 6 - clear all patrons from the system
    public void promptClearAllPatrons() {
        System.out.print("Are you sure you want to clear all " + system.getPatronCount() + " patrons from the system? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("yes")) {
            system.clearAllPatrons();
            system.displayAllPatrons();
        } else {
            System.out.println("Clear cancelled.");
        }
    }

    // Option 7 - update patron fine
    public void promptUpdatePatronFine() {
        if (system.getPatronCount() == 0) {
            System.out.println("No patrons are currently enrolled in the system.");
            return;
        }
        System.out.print("Enter the 7-digit Patron ID (or press Enter to cancel): ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("Update cancelled. Returning to menu.");
            return;
        }

        int patronId;
        try {
            patronId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric 7-digit ID.");
            return;
        }

        Patron patron = system.getPatronById(patronId);
        if (patron == null) {
            System.out.println("No patron with ID " + patronId + " was found.");
            return;
        }

        System.out.println("Current fine for " + patron.getName() + ": $" + String.format("%.2f", patron.getOverdueFine()));
        System.out.print("Enter new fine amount ($0.00 - $250.00) (or press Enter to cancel): ");
        String fineInput = scanner.nextLine().trim();

        if (fineInput.isEmpty()) {
            System.out.println("Update cancelled. Returning to menu.");
            return;
        }

        double fine;
        try {
            fine = Double.parseDouble(fineInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric fine amount.");
            return;
        }

        if (!system.isValidFine(fine)) {
            System.out.println("Invalid fine. Must be between $0.00 and $250.00.");
            return;
        }

        patron.setOverdueFine(fine);
        System.out.println("Fine updated for " + patron.getName() + " (ID: " + patronId + "). New fine: $" + String.format("%.2f", fine));

        // Display updated list
        System.out.println("\nUpdated patron list:");
        system.displayAllPatrons();
    }
}