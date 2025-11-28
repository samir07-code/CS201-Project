import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static final Book staticAccess = new AudioBook("", "", "", 0);
    
    public static int getIntInput(){
        Scanner input = new Scanner(System.in);
        
        boolean isValid = false;
        int result = 0;
        
        while (!isValid) {
            try {
                result = input.nextInt();
            } catch (InputMismatchException e){
                System.out.print("Invalid input, try again: ");
                continue;
            }
            
            if (result > 0){
                isValid = true;
            } else {
                System.out.println("Input must be positive: ");
            }
        }
        
        return result;
    }
    
    public static double getDoubleInput(){
        Scanner input = new Scanner(System.in);
        
        boolean isValid = false;
        double result = 0;
        
        while (!isValid) {
            try {
                result = input.nextDouble();
            } catch (InputMismatchException e){
                System.out.println("Invalid input, try again: ");
                continue;
            }
            
            if (result > 0){
                isValid = true;
            } else {
                System.out.println("Input must be positive: ");
            }
        }
        
        return result;
    }
    
    public static char getCharInput(boolean isEnterValid){
        Scanner input = new Scanner(System.in);
        
        boolean isValid = false;
        char res = 'a';
        
        while (!isValid) {
            try {
                res = input.nextLine().toLowerCase().charAt(0);
            } catch (IndexOutOfBoundsException e) {
                if (isEnterValid) {
                    res = ' ';
                } else {
                    System.out.println("Invalid input, try again: ");
                    continue;
                }
            }
            
            isValid = true;
        }
        
        return res;
    }
    
    public static void awaitEnter() {
        Scanner input = new Scanner(System.in);
        input.nextLine();
    }
    
    public static void addBook(){
        Scanner userInput = new Scanner(System.in);
        
        System.out.println("Adding a book");
        System.out.println("What type of book do you want to add?");
        System.out.println("> a: Audio Book");
        System.out.println("> b: Printed Book");
        System.out.println("- Any other key will return to the main menu -");
        System.out.print("Choice: ");
        
        char choice = getCharInput(true);
        int bookType = switch (choice){
            case 'a' -> 0;
            case 'b' -> 1;
            default -> 2;
        };
        
        if (bookType == 2) {
            System.out.println("Returning to menu...");
            System.out.println();
            return;
        }
        
        System.out.println("Fill out the following entries: ");
        
        System.out.print("Title: ");
        String title = userInput.nextLine();
        
        System.out.print("Author: ");
        String author = userInput.nextLine();
        
        System.out.print("Genre: ");
        String genre = userInput.nextLine();
        
        Book book;
        
        if (bookType == 1) { // audiobook
            System.out.print("Length (in minutes): ");
            
            double length = getDoubleInput();
            
            book = new AudioBook(title, author, genre, length);
        } else { // printed book
            System.out.print("Pages: ");
            
            int pages = getIntInput();
            
            book = new PrintedBook(title, author, genre, pages);
        }
        
        System.out.println("Current book: ");
        System.out.println(book);
        System.out.print("Add to database (Y/N): ");
        
        char toBeSaved = getCharInput(false);
        if (toBeSaved == 'y'){
            System.out.print("Adding to database...");
            book.addToDatabase();
        } else {
            System.out.print("Discarding book...");
        }
        System.out.println();
    }
    
    public static void printedBookMenu(){
        System.out.println("= Printed Book Menu =");
        System.out.println("> a: Latest Three Printed Books");
        System.out.println("> b: Average Printed Book Length");
        System.out.println("- Any other key will return to the main menu -");
        System.out.print("Choice: ");
        
        char choice = getCharInput(true);
        int action = switch (choice){
            case 'a' -> 0;
            case 'b' -> 1;
            default -> 2;
        };
        
        if (action == 2) {
            System.out.println("Returning to menu...");
            System.out.println();
            return;
        }
        
        if (action == 0){
            String res = PrintedBook.getLastThreePrintedBooks();
            
            System.out.println("Last Three Printed Books: ");
            System.out.println(res);
        } else {
            double avg = PrintedBook.getAveragePages();
            System.out.println("The average length of the printed books in the database is " + avg + " pages.");
        }
        
        System.out.println("Enter to continue: ");
        awaitEnter();
    }
    
    public static void audioBookMenu(){
        System.out.println("= Audio Book Menu =");
        System.out.println("> a: Latest Three Audio Books");
        System.out.println("> b: Average Audio Book Length");
        System.out.println("- Any other key will return to the main menu -");
        System.out.print("Choice: ");
        
        char choice = getCharInput(true);
        int action = switch (choice){
            case 'a' -> 0;
            case 'b' -> 1;
            default -> 2;
        };
        
        if (action == 2) {
            System.out.println("Returning to menu...");
            System.out.println();
            return;
        }
        
        if (action == 0){
            String res = AudioBook.getLastThreeAudioBooks();
            
            System.out.println("Last Three Audio Books: ");
            System.out.println(res);
        } else {
            double avg = AudioBook.getAverageLength();
            System.out.println("The average length of the audio books in the database is " + avg + " minutes.");
        }
        
        System.out.println("Enter to continue: ");
        awaitEnter();
    }
    
    public static void displayLastTen(){
        staticAccess.displayLastTenBooks();
        
        System.out.println("Enter to continue: ");
        awaitEnter();
    }
    
    public static void displayTotal(){
        double total = staticAccess.getTotalCost();
        
        System.out.println(String.format("Total collection cost: $%.2f.", total));
        
        System.out.println("Enter to continue: ");
        awaitEnter();
    }
    
    public static void genrePage(){
        HashMap<String, Integer> genres = staticAccess.getNumBooksByGenre();
        
        System.out.println("= Genre Page =");
        genres.forEach((genre, count) ->  System.out.println(genre + ": " + count));
        
        System.out.println();
        
        System.out.println("Enter to continue: ");
        awaitEnter();
    }
    
    public static void main(String[] args) {
        System.out.println("Book Interface Project!");
        System.out.println("Starting program...");
        
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Select an action:");
            System.out.println("> 1: Add a Book");
            System.out.println("> 2: Printed Book Menu");
            System.out.println("> 3: Audio Book Menu");
            System.out.println("> 4: View Last Ten Books");
            System.out.println("> 5: Get Total Price of Collection");
            System.out.println("> 6: List Genres");
            System.out.println("> 7: Exit Program");
            System.out.print("Choice: ");
            
            int choice = getIntInput();
            switch (choice){
                case 1 -> addBook();
                case 2 -> printedBookMenu(); // Printed book menu
                case 3 -> audioBookMenu(); // Audio book menu
                case 4 -> displayLastTen(); // last ten
                case 5 -> displayTotal(); // total price
                case 6 -> genrePage(); // genre
                case 7 -> isRunning = false;
            }
        }
        
        System.out.println("Ending program...");
    }
}