import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BST implements Tree{

    public long  count =0;

    @Override
    public void load(ArrayList<String> s) {

        for (String o: s){
            insert(o);
        }
    }
    @Override
    public void loadSearch(ArrayList<String> s) {
        for (String o: s){
            search(o);
        }


    }

    @Override
    public void loadDelete(ArrayList<String> s ) {

        for (String o : s) {
            delete(o);
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
        String value = val;
        if (!value.equals("") ) {
            if (root == null)
                root = new BTSNode(value);
            else {
                BTSNode actual = root;
                BTSNode parent = null;
                while (actual != null) {
                    parent = actual;
                    actual = (actual.value.compareTo(value) > 0) ? actual.left : actual.right;
                }
                if (parent.value.compareTo(value) > 0) { // actual zmiana
                    parent.left = new BTSNode(value);
                    parent.left.parent = parent;
                } else {
                    parent.right = new BTSNode(value);
                    parent.right.parent = parent;
                }
            }
            count++;
        }
    }
    /**********************     end BSTInsert       *******************************/

    /* Wyszukiwanie elementu */
    @Override


    public boolean search(String val)
    {

        return find(root,val) != null;
    }
    private BTSNode find(BTSNode x, String value) {
        while (x != null && !(value.equals(x.value))) {
            if (value.compareTo(x.value) < 0) {
                x = x.left;
            } else x = x.right;
        }
        return x;
    }

    /* Usuwanie elementu */
    @Override
    public void delete(String val ) {
        if(!val.equals("")) {

            BTSNode node = this.find(root, val);
            if (node != null && root != null)
                remove(node);
            else
                System.out.println("Nie ma takiego ciagu znaków");

        }
    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }






    public BTSNode remove(BTSNode node )  {

        BTSNode parent = node.parent;
        BTSNode tmp;
        if (node.left != null && node.right != null) {
            tmp = this.remove(this.successor(node.value));
            tmp.left = node.left;
            if (tmp.left != null)
                tmp.left.parent = tmp;
            tmp.right = node.right;
            if (tmp.right != null)
                tmp.right.parent = tmp;
        } else tmp = (node.left != null) ? node.left : node.right;

        if (tmp != null) tmp.parent = parent;

        if (parent == null) root = tmp;
        else if (parent.left == node) parent.left = tmp;
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
    BTSNode node = this.find(root,val);
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
