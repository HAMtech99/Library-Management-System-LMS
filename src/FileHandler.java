import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

    // Reads the file at filePath and loads valid patrons into the system
    // Returns true if file was read successfully, false if file not found
    public static boolean loadFromFile(String filePath, LibrarySystem system) {
        int added = 0;
        int skipped = 0;
        int lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip blank lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Attempt to parse the line into a Patron
                Patron patron = parsePatronLine(line, lineNumber, system);

                if (patron != null) {
                    system.addPatron(patron);
                    System.out.println("Added: " + patron.getName() + " (ID: " + patron.getPatronId() + ")");
                    added++;
                } else {
                    skipped++;
                }
            }

        } catch (IOException e) {
            System.out.println("Error: File not found or could not be read - " + filePath);
            return false;
        }

        System.out.println("\nFile load complete. Added: " + added + " | Skipped: " + skipped);
        return true;
    }

    // Parses a single line into a Patron object, returns null if invalid
    public static Patron parsePatronLine(String line, int lineNumber, LibrarySystem system) {

        // Split by dash, limit 4 keeps address intact even if it had commas
        String[] tokens = line.split("-", 4);

        // Must have exactly 4 fields
        if (tokens.length != 4) {
            System.out.println("Line " + lineNumber + " skipped: incorrect number of fields.");
            return null;
        }

        // Parse and validate ID
        int patronId;
        try {
            patronId = Integer.parseInt(tokens[0].trim());
        } catch (NumberFormatException e) {
            System.out.println("Line " + lineNumber + " skipped: invalid ID format - " + tokens[0].trim());
            return null;
        }

        if (!system.isValidId(patronId)) {
            System.out.println("Line " + lineNumber + " skipped: ID out of range - " + patronId);
            return null;
        }

        if (!system.isIdUnique(patronId)) {
            System.out.println("Line " + lineNumber + " skipped: duplicate ID - " + patronId);
            return null;
        }

        // Validate name
        String name = tokens[1].trim();
        if (name.isEmpty()) {
            System.out.println("Line " + lineNumber + " skipped: name is empty.");
            return null;
        }

        // Validate address
        String address = tokens[2].trim();
        if (address.isEmpty()) {
            System.out.println("Line " + lineNumber + " skipped: address is empty.");
            return null;
        }

        // Parse and validate fine
        double fine;
        try {
            fine = Double.parseDouble(tokens[3].trim());
        } catch (NumberFormatException e) {
            System.out.println("Line " + lineNumber + " skipped: invalid fine format - " + tokens[3].trim());
            return null;
        }

        if (!system.isValidFine(fine)) {
            System.out.println("Line " + lineNumber + " skipped: fine out of range - " + fine);
            return null;
        }

        // All checks passed, return the new Patron
        return new Patron(patronId, name, address, fine);
    }
}