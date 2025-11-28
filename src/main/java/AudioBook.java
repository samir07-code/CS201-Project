import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class AudioBook extends Book {
    private double length; // in minutes
    
    // custom function to organize database code & confirm user input before adding to database
    public void addToDatabase(){
        // add to file
        try {
            List<HashMap<String, Object>> books = BookInterface.parseBooksJSON();
            
            HashMap<String, Object> hashableObj = new HashMap<>();
            hashableObj.put("title", this.getTitle());
            hashableObj.put("author", this.getAuthor());
            hashableObj.put("genre", this.getGenre());
            hashableObj.put("length", this.getLength());
            hashableObj.put("cost", this.getCost());
            books.add(hashableObj);
            
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("books.json");
            
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, books);
        } catch (java.io.IOException e){
            System.out.println("Unexpected error!");
        }
    }
    
    public AudioBook(String title, String author, String genre, double length){
        super(title, author, genre, length * 5);
        this.length = length;
    }
    
    public double getLength(){
        return length;
    }
    
    public double getCost(){
        return length * 5;
    }
    
    public static double getAverageLength(){
        List<HashMap<String, Object>> books = BookInterface.parseBooksJSON();
        
        double totalLength = 0;
        int audioCount = 0;
        
        for (int i = 0; i < books.size(); i++) {
            if (!books.get(i).containsKey("length")) continue;
            totalLength += (double) books.get(i).get("length");
            audioCount += 1;
        }
        
        if (audioCount == 0) return 0;
        return totalLength / audioCount;
    }
    
    public static String getLastThreeAudioBooks(){
        List<HashMap<String, Object>> books = BookInterface.parseBooksJSON();
        
        int count = 0;
        StringBuilder result = new StringBuilder();
        
        for (int i = books.size() - 1; i >= 0; i--) {
            if (!books.get(i).containsKey("length")) continue;
            HashMap<String, Object> book = books.get(i);
            
            count += 1;
            AudioBook temp = new AudioBook((String)book.get("title"), (String) book.get("author"),
                    (String) book.get("genre"), (double) book.get("length"));
            
            result.append(temp);
            result.append("\n");
            
            /*
            result.append(String.format(
                    ">> Audio Book <<\nTitle: %s\nAuthor: %s\nGenre: %s\nCost: $%.2f\nLength: %.2f minutes\n",
                    book.get("title"), book.get("author"), book.get("genre"), (double) book.get("cost"), (double) book.get("length")
            ));
            */
            
            if (count == 3) break;
        }
        
        return result.toString();
    }
    
    public String toString(){
        return String.format(
                ">> Audio Book <<\nTitle: %s\nAuthor: %s\nGenre: %s\nCost: $%.2f\nLength: %.2f minutes\n",
                getTitle(), getAuthor(), getGenre(), getCost(), getLength()
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
