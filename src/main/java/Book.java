import java.util.HashMap;
import java.util.List;

public abstract class Book implements BookInterface {
    private String title, author, genre;
    private double cost;
    
    // custom function to organize database code & confirm user input before adding to database
    public abstract void addToDatabase();
    
    public Book(String title, String author, String genre, double cost){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.cost = cost;
    }
    
    @Override
    public double getTotalCost() { // can't be static b/c it needs to override the abstract method in BookInterface
        List<HashMap<String, Object>> books = BookInterface.parseBooksJSON();
        
        double total = 0;
        
        for (int i = 0; i < books.size(); i++) {
            total += (double) books.get(i).get("cost");
        }
        
        return total;
    }
    
    public String getTitle() {
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getGenre() {
        return genre;
    }
    
    public HashMap<String, Integer> getNumBooksByGenre(){ // can't be static b/c it needs to override the abstract method in BI
        List<HashMap<String, Object>> books = BookInterface.parseBooksJSON();
        
        HashMap<String, Integer> genreCounts = new HashMap<>();
        
        for (int i = 0; i < books.size(); i++) {
            String genre = (String) books.get(i).get("genre");
            
            genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
        }
        
        return genreCounts;
    }
    public abstract double getCost();
}
