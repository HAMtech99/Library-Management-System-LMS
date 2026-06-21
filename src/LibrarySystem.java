import java.util.ArrayList;

public class LibrarySystem {

    // The sole data store for the entire application
    private ArrayList<Patron> patrons;

    // Constructor - initializes the empty list
    public LibrarySystem() {
        patrons = new ArrayList<Patron>();
    }

    // Adds a patron to the list - caller must validate before calling this
    public void addPatron(Patron patron) {
        patrons.add(patron);
    }

    // Clears all patrons from the system
    public void clearAllPatrons() {
        patrons.clear();
        System.out.println("All patrons have been cleared from the system.");
    }

    // Removes a patron by ID - returns true if found and removed, false if not found
    public boolean removePatron(int patronId) {
        for (int i = 0; i < patrons.size(); i++) {
            if (patrons.get(i).getPatronId() == patronId) {
                patrons.remove(i);
                return true;
            }
        }
        return false;
    }

    // Displays all patrons - prints empty message if list is empty
    public void displayAllPatrons() {
        if (patrons.isEmpty()) {
            System.out.println("No patrons are currently enrolled in the system.");
        } else {
            for (Patron p : patrons) {
                System.out.println(p);
            }
            System.out.println("Total patrons: " + patrons.size());
        }
    }

    // Returns true if no existing patron has this ID
    public boolean isIdUnique(int patronId) {
        for (Patron p : patrons) {
            if (p.getPatronId() == patronId) {
                return false;
            }
        }
        return true;
    }

    // Returns true if ID is exactly 7 digits
    public boolean isValidId(int patronId) {
        return patronId >= 1000000 && patronId <= 9999999;
    }

    // Returns true if fine is within the valid range
    public boolean isValidFine(double fine) {
        return fine >= 0.00 && fine <= 250.00;
    }

    // Returns the Patron with the matching ID, or null if not found
    public Patron getPatronById(int patronId) {
        for (Patron p : patrons) {
            if (p.getPatronId() == patronId) {
                return p;
            }
        }
        return null;
    }

    // Returns the total number of patrons in the system
    public int getPatronCount() {
        return patrons.size();
    }
}