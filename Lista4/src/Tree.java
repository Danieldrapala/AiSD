import java.io.File;
import java.util.ArrayList;

public interface Tree {

     void insert(String key);
     boolean search(String val);
     void load(ArrayList<String> s );
     void inOrder();
     void delete(String key);
     boolean isEmpty();
     void loadSearch(ArrayList<String> s);
     void loadDelete(ArrayList<String> s);

}
