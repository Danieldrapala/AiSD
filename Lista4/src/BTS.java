public class BTS {


    private class BTSNode
    {
        BTSNode  parent;
        BTSNode  left;
        BTSNode  right;
        int key;
        String value;
        BTSNode(String s)
        {
            this.value=s;
        }
    }
    private BTSNode root = null;      // korzeń naszego drzewa
    private class TreeException extends Throwable {
        TreeException() {}
        TreeException(String msg) { super(msg); }
    }
    /* Dodawanie elementów */
    public void insert(String val) {
        if(root == null)
            root = new BTSNode(val);
        else {
            BTSNode actual = root;
            BTSNode parent = null;
            while(actual != null) {
                parent = actual;
                actual = (actual.value.compareTo(val) > 0) ? actual.left : actual.right;
            }
            if(actual.value.compareTo(val) > 0) {
                parent.left = new BTSNode(val);
                parent.left.parent = parent;
            }
            else {
                parent.right = new BTSNode(val);
                parent.right.parent = parent;
            }
        }
    }
    /**********************     end BSTInsert       *******************************/

    /* Wyszukiwanie elementu */
    public BTSNode search(String val )   throws TreeException{
        BTSNode actual = root;
        while(actual != null && actual.value != val)
            actual = (actual.value.compareTo(val) >0) ? actual.left : actual.right;
        if(actual == null)
            throw new TreeException("Not Found Key");
        return actual;
    }
    /* Usuwanie elementu */
    public BTSNode remove(String val ) throws TreeException {
        BTSNode node = this.search(val);
        BTSNode parent = node.parent;
        BTSNode tmp;
        if(node.left != null && node.right != null) {
            tmp = this.remove(this.successor(val).value);
            tmp.left = node.left;
            if(tmp.left != null)
                tmp.left.parent = tmp;
            tmp.right = node.right;
            if(tmp.right != null)
                tmp.right.parent = tmp;
        }
        else
            tmp = (node.left != null) ? node.left : node.right;
        if(tmp != null)
        // tmp.parent = parent;
            node.parent = parent;
        if(parent == null)
            root = tmp;
        else if(parent.left == node)
            parent.left = tmp;
        else
            parent.right = tmp;
        return node;
    }
    /*************************      end BSTRemove       ***************************/

    /*  InOrder */
    public void inOrder(BTSNode node) {
        if(node != null) {
            inOrder(node.left);
            System.out.print(node.value + ", ");
            inOrder(node.right);
        }
    }
/*************************      end InOrder         ***************************/
/*  Znajdowanie następnika  */
private BTSNode successor(String val) throws TreeException {
    BTSNode node = this.search(val);
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
        throw new TreeException("Not Found Successor");
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
