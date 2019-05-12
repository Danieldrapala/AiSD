import java.io.File;

public interface Tree {

     void insert(String key);
     boolean search(String val);
     void load(File p );
     void inOrder();
     void delete(String key);
     boolean isEmpty();

}
