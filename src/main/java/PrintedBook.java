import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class PrintedBook extends Book {
    private int pages;
    
    // custom function to organize database code & confirm user input before adding to database
    public void addToDatabase(){
        // add to file
        try {
            List<HashMap<String, Object>> books = BookInterface.parseBooksJSON();
            
            HashMap<String, Object> hashableObj = new HashMap<>();
            hashableObj.put("title", this.getTitle());
            hashableObj.put("author", this.getAuthor());
            hashableObj.put("genre", this.getGenre());
            hashableObj.put("pages", this.getPages());
            hashableObj.put("cost", this.getCost());
            books.add(hashableObj);
            
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("books.json");
            
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, books);
        } catch (java.io.IOException e){
            System.out.println("Unexpected error!");
        }
    }
    
    public PrintedBook(String title, String author, String genre, int pages){
        super(title, author, genre, pages * 10);
        this.pages = pages;
    }
    
    public int getPages() {
        return pages;
    }
    
    public double getCost(){
        return pages * 10;
    }
    
    public static double getAveragePages(){
        List<HashMap<String, Object>> books = BookInterface.parseBooksJSON();
        
        int totalPages = 0;
        int printedCount = 0;
        
        for (int i = 0; i < books.size(); i++) {
            if (!books.get(i).containsKey("pages")) continue;
            totalPages += (int) books.get(i).get("pages");
            printedCount += 1;
        }
        
        if (printedCount == 0) return 0;
        return (double) totalPages / printedCount;
    }
    
    public static String getLastThreePrintedBooks(){
        List<HashMap<String, Object>> books = BookInterface.parseBooksJSON();
        
        int count = 0;
        StringBuilder result = new StringBuilder();
        
        for (int i = books.size() - 1; i >= 0; i--) {
            if (!books.get(i).containsKey("pages")) continue;
            HashMap<String, Object> book = books.get(i);
            
            count += 1;
            PrintedBook temp = new PrintedBook((String)book.get("title"), (String) book.get("author"),
                    (String) book.get("genre"), (int) book.get("pages"));
            
            result.append(temp);
            result.append("\n");
            
            /*
            result.append(String.format(
                    ">> Printed Book <<\nTitle: %s\nAuthor: %s\nGenre: %s\nCost: $%.2f\nPages: %d pages\n",
                    book.get("title"), book.get("author"), book.get("genre"), (double) book.get("cost"), (int) book.get("pages")
            ));
            */
            
            if (count == 3) break;
        }
        
        return result.toString();
    }
    
    public String toString(){
        return String.format(
            ">> Printed Book <<\nTitle: %s\nAuthor: %s\nGenre: %s\nCost: $%.2f\nPages: %d pages\n",
            getTitle(), getAuthor(), getGenre(), getCost(), getPages()
        );
    }
    
    /*
    public HashMap<String, Integer> getBooksByGenre(){
        HashMap<String, Integer> genreCounts = new HashMap<>();
        
        for (String[] data : bookList){
            String genre = data[2];
            
            genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
        }
        
        return genreCounts;
    }
    */
}
