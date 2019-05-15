import java.io.File;
import java.util.ArrayList;

public interface Tree {
     void printCounters();
      void zerocounters();
     void printOperations();

     void insert(String key   );
     boolean search(String val);
     void load(ArrayList<String> s ,int mod);
     void inOrder();
     void delete(String key);
     boolean isEmpty();
     void loadSearch(ArrayList<String> s,int mod);
     void loadDelete(ArrayList<String> s,int mod);

}
