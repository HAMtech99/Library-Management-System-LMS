public class Patron {

    private int patronId;
    private String name;
    private String address;
    private double overdueFine;

    public Patron(int patronId, String name, String address, double overdueFine) {
        this.patronId = patronId;
        this.name = name;
        this.address = address;
        this.overdueFine = overdueFine;
    }

    public int getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getOverdueFine() {
        return overdueFine;
    }

    public void setOverdueFine(double overdueFine) {
        this.overdueFine = overdueFine;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Address: %s | Fine: $%.2f",
                patronId, name, address, overdueFine);
    }
}