import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface BookInterface {
    // custom function to parse the JSON file the books are stored in
    public static List<HashMap<String, Object>> parseBooksJSON(){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("books.json");
        
        List<HashMap<String, Object>> books = new ArrayList<>();
        
        // file exists and isn't blank
        if (file.exists() && file.length() != 0) {
            try {
                books = mapper.readValue(
                        file,
                        new TypeReference<List<HashMap<String, Object>>>() {}
                );
            } catch (Exception e) {
                // catches if the format is wrong
                System.out.println("Invalid books.json file, possible corruption detected!");
                System.out.println("Either delete the file or properly format it!");
            }
        }
        
        return books;
    }
    
    public default void displayLastTenBooks(){
        List<HashMap<String, Object>> books = parseBooksJSON();
        
        int start = books.size() - 10;
        if (start < 0) start = 0;
        
        for (int i = start; i < books.size(); i++) {
            Book book;
            if (books.get(i).containsKey("pages")){
                book = new PrintedBook((String)books.get(i).get("title"), (String) books.get(i).get("author"),
                    (String) books.get(i).get("genre"), (int) books.get(i).get("pages"));
            } else {
                book = new AudioBook((String)books.get(i).get("title"), (String) books.get(i).get("author"),
                    (String) books.get(i).get("genre"), (double) books.get(i).get("length"));
            }
            System.out.print(book + "\n");
        }
    }
    
    public abstract HashMap<String, Integer> getNumBooksByGenre();
    public abstract double getTotalCost();
}
