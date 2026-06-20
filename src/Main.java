public class Main {
    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();
        FileHandler.loadFromFile("patrons.txt", system);
        system.displayAllPatrons();
    }
}