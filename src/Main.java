public class Main {
    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();

        Patron p1 = new Patron(1234567, "Sarah Jones", "2222 Pine St. Orlando, FL 33826", 50.10);
        Patron p2 = new Patron(2345678, "John Doe", "101 Lake Ave. Winter Haven, FL 33880", 0.00);

        system.addPatron(p1);
        system.addPatron(p2);

        system.displayAllPatrons();

        System.out.println("Removing Sarah...");
        system.removePatron(1234567);

        system.displayAllPatrons();
    }
}