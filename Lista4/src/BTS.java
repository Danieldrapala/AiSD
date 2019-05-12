import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class BTS implements Tree{

    public long  count =0;

    @Override
    public void load(File p) {
        String data = "";

        try {
            data = new String(Files.readAllBytes(Paths.get(p.toURI())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s: data.split("[\\p{P} \\s]")){
            if (s.equals("")) continue;
            insert(s);
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
        }
    }
    private BTSNode root = null;      // korzeń naszego drzewa

    /* Dodawanie elementów */
    @Override

    public void insert(String val) {
        while(!Pattern.matches("[a-zA-Z]",String.valueOf(val.charAt(0)))||!Pattern.matches("[a-zA-Z]",String.valueOf(val.charAt(val.length()-1)))) {
            if (!Pattern.matches("[a-zA-Z]", String.valueOf(val.charAt(0))))
                val = val.substring(1);
            if (!Pattern.matches("[a-zA-Z]", String.valueOf(val.charAt(val.length() - 1)))) {
                val = val.substring(0, val.length() - 1);
            }
        }
            if (root == null)
                root = new BTSNode(val);
            else {
                BTSNode actual = root;
                BTSNode parent = null;
                while (actual != null) {
                    parent = actual;
                    actual = (actual.value.compareTo(val) > 0) ? actual.left : actual.right;
                }
                if (parent.value.compareTo(val) > 0) { // actual zmiana
                    parent.left = new BTSNode(val);
                    parent.left.parent = parent;
                } else {
                    parent.right = new BTSNode(val);
                    parent.right.parent = parent;
                }
            }
            count++;
        }
    /**********************     end BSTInsert       *******************************/

    /* Wyszukiwanie elementu */
    @Override


    public boolean search(String val)
    {
        return find(val) != null;
    }

    public BTSNode find(String val ){
        BTSNode actual = root;
        while(actual != null && actual.value.equals(val))
            actual = (actual.value.compareTo(val) >0) ? actual.left : actual.right;
        if(actual == null)
            return null;
        return actual;
    }
    /* Usuwanie elementu */
    @Override
    public void delete(String val ) {
        BTSNode node = this.find(val);
        if(node!=null&& root!=null)
            remove(node);

    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }


    public BTSNode remove(BTSNode node )  {

        BTSNode parent = node.parent;
        BTSNode tmp;
        if(node.left != null && node.right != null) {
            tmp = this.remove(this.successor(node.value));
            tmp.left = node.left;
            if(tmp.left != null)
                tmp.left.parent = tmp;
            tmp.right = node.right;
            if(tmp.right != null)
                tmp.right.parent = tmp;
        }
        else  tmp = (node.left != null) ? node.left : node.right;

        if(tmp != null) tmp.parent = parent;

        if(parent == null) root = tmp;
        else if(parent.left == node)  parent.left = tmp;
        else parent.right = tmp;

        return node;
    }
    /*************************      end BSTRemove       ***************************/
    @Override

    public void inOrder() {
            printInOrderAllNodes(root);
    }



    /*  InOrder */
    public void printInOrderAllNodes(BTSNode node) {
        if(node != null) {
            printInOrderAllNodes(node.left);
            System.out.print(node.value + ", ");
            printInOrderAllNodes(node.right);
        }
    }
/*************************      end InOrder         ***************************/
/*  Znajdowanie następnika  */
private BTSNode successor(String val)   {
    BTSNode node = this.find(val);
// Szukanie następnika gdy węzeł ma prawego potomka
    if(node.right != null) {
        node = node.right;
        while(node.left != null)
            node = node.left;
        return node;
    }
// Szukanie następnika gdy węzeł nie ma prawgo potomka
    else if(node.right == null && node != root && node != this.max(root)) {
        BTSNode parent = node.parent;
        while(parent != root && parent.value.compareTo(node.value)<0)
            parent = parent.parent;
        return parent;
    }
    else
    return null;
}
/*********************      end BST Successor       ***************************/

// Znajdowanie minimalnego klucza
private BTSNode max(BTSNode node) {
    while(node.right != null)
        node = node.right;
    return node;
}
/**********************     end BST MAX     ***********************************/

}
