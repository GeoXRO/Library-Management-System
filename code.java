import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private String id;
    private String title;
    private String author;
    private boolean isBorrowed;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void borrow() {
        isBorrowed = true;
    }

    public void returnBook() {
        isBorrowed = false;
    }

    @Override
    public String toString() {
        return "Book ID: " + id + ", Title: " + title + ", Author: " + author + ", Status: " + (isBorrowed ? "Borrowed" : "Available");
    }
}

class User {
    private String id;
    private String name;
    private List<Book> borrowedBooks;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
        book.borrow();
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
        book.returnBook();
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
}

class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerUser(User user) {
        users.add(user);
    }

    public Book findBookById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public void listBooks() {
        System.out.println("Library Books:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void listUsers() {
        System.out.println("Registered Users:");
        for (User user : users) {
            System.out.println("User ID: " + user.getId() + ", Name: " + user.getName());
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        // Add sample data
        library.addBook(new Book("B001", "1984", "George Orwell"));
        library.addBook(new Book("B002", "Brave New World", "Aldous Huxley"));
        library.registerUser(new User("U001", "Alice"));
        library.registerUser(new User("U002", "Bob"));

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. List Books");
            System.out.println("2. List Users");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    library.listBooks();
                    break;
                case 2:
                    library.listUsers();
                    break;
                case 3:
                    System.out.print("Enter User ID: ");
                    String userId = scanner.next();
                    User user = library.findUserById(userId);
                    if (user == null) {
                        System.out.println("User not found.");
                        break;
                    }
                    System.out.print("Enter Book ID: ");
                    String bookId = scanner.next();
                    Book book = library.findBookById(bookId);
                    if (book == null) {
                        System.out.println("Book not found.");
                    } else if (book.isBorrowed()) {
                        System.out.println("Book is already borrowed.");
                    } else {
                        user.borrowBook(book);
                        System.out.println("Book borrowed successfully.");
                    }
                    break;
                case 4:
                    System.out.print("Enter User ID: ");
                    userId = scanner.next();
                    user = library.findUserById(userId);
                    if (user == null) {
                        System.out.println("User not found.");
                        break;
                    }
                    System.out.print("Enter Book ID: ");
                    bookId = scanner.next();
                    book = library.findBookById(bookId);
                    if (book == null || !book.isBorrowed()) {
                        System.out.println("Book not found or not borrowed.");
                    } else {
                        user.returnBook(book);
                        System.out.println("Book returned successfully.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting system.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
