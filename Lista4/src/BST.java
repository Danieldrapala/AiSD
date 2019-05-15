import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BST implements Tree{

    public long  count =0;
    public long  max =0;

    public long  insertOperation=0;
    public long deleteOperation=0;
    public long searchOperation=0;
    public long counterMOD = 0;
    public long counterIF = 0;
    @Override
    public void printCounters(){
        System.out.println("counterMOD number: "+counterMOD);
        System.out.println("counterIF number: "+counterIF);

    }
    @Override

    public void zerocounters(){
        counterIF=0;
        counterMOD=0;
    }

    @Override
    public void printOperations() {
        System.out.println("insertOperations number: "+insertOperation);
        System.out.println("searchOperations number: "+searchOperation);
        System.out.println("deleteOperations number: "+deleteOperation);
        System.out.println("actual number of nodes number: "+count);
        System.out.println("max number of nodes number: "+max);
    }

    public  boolean myEquals(String nr1,String nr2){
        counterIF++;
        return nr1.equals(nr2);
    }
    public int comparingTo(String nr1,String nr2){
        counterIF++;
        return nr1.compareTo(nr2);
    }
    @Override
    public void load(ArrayList<String> s,int mod) {
        if(mod==0) {
            for (String o : s) {
                 insert(o);
            }
        }



    }
    @Override
    public void loadSearch(ArrayList<String> s,int mod) {
       if(mod==0) {
           for (String o : s) {
               search(o);
           }
       }
    }

    @Override
    public void loadDelete(ArrayList<String> s,int mod) {
        if(mod==0){
        for (String o : s) {
            delete(o);
        }
    }
    }

    private class BTSNode
    {
        BTSNode  parent;
        BTSNode  left;
        BTSNode  right;
        String value;
        BTSNode(String s)
        {
            this.value=s;
            left=null;
            right=null;
        }
    }
    private BTSNode root = null;      // korzeń naszego drzewa

    /* Dodawanie elementów */
    @Override
    public void insert(String val) {

            if (root == null)
                root = new BTSNode(val);
            else {
                BTSNode actual = root;
                BTSNode parent = null;
                while (actual != null) {
                    parent = actual;
                    actual = (comparingTo(actual.value,val) > 0) ? actual.left : actual.right;
                }

                if (comparingTo(parent.value,val) > 0) { // actual zmiana
                    parent.left = new BTSNode(val);
                    parent.left.parent = parent;
                } else {
                    parent.right = new BTSNode(val);
                    parent.right.parent = parent;
                }
            }
            count++;
            if(count>max){
                max=count;
            }
        insertOperation++;
    }

    @Override
    public boolean search(String val)
    {

        return find(root,val) != null;
    }
    private BTSNode find(BTSNode x, String value) {
        while (x != null && !(myEquals(value,x.value))) {
            if ( comparingTo(value,x.value) < 0) {
                x = x.left;
            } else x = x.right;
        }
        searchOperation++;
        return x;
    }
    @Override
    public void delete(String val ) {
        BTSNode node = this.find(root, val);
        if (node != null && root != null) {
            remove(node);
            count--;
        }
        else
            System.out.println("Nie ma takiego ciagu znaków");
    }
    @Override
    public boolean isEmpty() {
        return root==null;
    }

    public BTSNode remove(BTSNode node )  {

        BTSNode parent = node.parent;
        BTSNode tmp;
        if (node.left != null && node.right != null) {
            tmp = this.remove(this.successor(node));
            tmp.left = node.left;
            if (tmp.left != null){
                tmp.left.parent = tmp;
                counterMOD++;
            }
            tmp.right = node.right;
            if (tmp.right != null) {
                tmp.right.parent = tmp;
                counterMOD++;
            }
        } else tmp = (node.left != null) ? node.left : node.right;

        if (tmp != null) tmp.parent = parent;
        if (parent == null) root = tmp;
        else if (parent.left == node) parent.left = tmp;
        else parent.right = tmp;
        counterMOD++;
        deleteOperation++;
    return node;

    }
    @Override

    public void inOrder() {
            printInOrderAllNodes(root);
    }

    /*  InOrder */
    public void printInOrderAllNodes(BTSNode node) {
        if(node != null) {
            printInOrderAllNodes(node.left);
            System.out.println(node.value );
            printInOrderAllNodes(node.right);
        }
    }
/*  Znajdowanie następnika  */
private BTSNode successor(BTSNode node)   {
// Szukanie następnika gdy węzeł ma prawego potomka
    if(node.right != null) {
        node = node.right;
        while(node.left != null)
            node = node.left;
        return node;
    }
// Szukanie następnika gdy węzeł nie ma prawgo potomka
    else if( node != root && node != this.max(root)) {
        BTSNode parent = node.parent;
        while(parent != root &&comparingTo( parent.value,node.value)<0)
            parent = parent.parent;
        return parent;
    }
    else
    return null;
}

// Znajdowanie minimalnego klucza
private BTSNode max(BTSNode node) {
    while(node.right != null)
        node = node.right;
    return node;
}

}
