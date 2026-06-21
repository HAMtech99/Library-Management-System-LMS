public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Library Management System!");
        LibrarySystem system = new LibrarySystem();
        MenuHandler menuHandler = new MenuHandler(system);
        menuHandler.run();
    }
}