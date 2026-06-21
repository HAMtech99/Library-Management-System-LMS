public class Patron {

    // Private fields - only accessible through getters and setters
    private int patronId;   // Unique 7-digit identifier for this patron
    private String name;    // Full name of the patron
    private String address; // Mailing address of the patron
    private double overdueFine; // Current overdue fine balance in dollars

    // Constructor - assigns all four fields when a new Patron object is created
    public Patron(int patronId, String name, String address, double overdueFine) {
        this.patronId = patronId;
        this.name = name;
        this.address = address;
        this.overdueFine = overdueFine;
    }

    // Returns the patron's unique 7-digit ID
    public int getPatronId() {
        return patronId;
    }

    // Returns the patron's full name
    public String getName() {
        return name;
    }

    // Returns the patron's mailing address
    public String getAddress() {
        return address;
    }

    // Returns the patron's current overdue fine amount
    public double getOverdueFine() {
        return overdueFine;
    }

    // Updates the patron's overdue fine - the only field that can change after creation
    public void setOverdueFine(double overdueFine) {
        this.overdueFine = overdueFine;
    }

    // Returns a formatted string of all four patron fields
    // Fine is formatted to 2 decimal places with a dollar sign
    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Address: %s | Fine: $%.2f",
                patronId, name, address, overdueFine);
    }
}