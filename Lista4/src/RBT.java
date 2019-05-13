import java.util.ArrayList;


// true Red
// false BLACK
class Node {
    boolean color;
    String key;
    Node left, right, parent;

    Node(String key) {
        this.key = key;
    }

    Node(boolean color) {
        this.color = color;
    }
}

public class RBT implements Tree{


    private static final Node NIL = new Node(false);
    private Node root;

    void inOrderTreeWalk(Node x) {
        if (!(x == NIL)) {
            inOrderTreeWalk(x.left);
            System.out.print(x.key + ", ");
            inOrderTreeWalk(x.right);
        }
    }
    RBT(){
        root=NIL;
    }
    @Override
    public void insert(String key) {
        if(!key.equals("")) {
            Node z = new Node(key);
            insert(z);
        }

    }

    @Override

    public boolean search(String val)
    {

            return find(root, val) != null;

    }

    @Override

    public void inOrder() {
        inOrderTreeWalk(root);
    }

    @Override

    public void delete(String ele)
    {
        Node node = find(root,ele);
        remove(node);
    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }

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


    Node minimum(Node x) {
        while (!(x.left == NIL)) {
            x = x.left;
        }
        return x;
    }

    Node find(Node x, String k) {
        while (x != NIL && !(k.equals(x.key))) {
            if (k.compareTo(x.key) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return x;
    }

    void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != NIL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != NIL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    void insert(Node z) {
        Node y = NIL;
        Node x = root;

            while (!(x == NIL)) {
                y = x;
                if (z.key.compareTo(x.key) < 0) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }
            z.parent = y;
            if (y == NIL) {
                root = z;
            } else if (z.key.compareTo(y.key) < 0) {
                y.left = z;
            } else {
                y.right = z;
            }
            z.left = NIL;
            z.right = NIL;
            z.color = true;
            insertFixUP(z);

    }

    void insertFixUP(Node z) {
        Node y;
        while (z.parent.color) {
            if (z.parent == z.parent.parent.left) {
                y = z.parent.parent.right;
                if (y.color) {
                    z.parent.color = false;
                    y.color = false;
                    z.parent.parent.color = true;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = false;
                    z.parent.parent.color = true;
                    rightRotate(z.parent.parent);
                }
            } else {
                y = z.parent.parent.left;
                if (y.color) {
                    z.parent.color = false;
                    y.color = false;
                    z.parent.parent.color = true;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = false;
                    z.parent.parent.color = true;
                    leftRotate(z.parent.parent);
                }
            }
        }

        root.color = false;
    }

    void transplant(Node u, Node v) {
        if (u.parent == NIL) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    void remove(Node z) {
        Node x, y = z;
        boolean yColor = y.color;
        if (z.left == NIL) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == NIL) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (!yColor) {
            deleteFixUp(x);
        }
    }

    void deleteFixUp(Node x) {
        Node w;
        while (x != root && !x.color) {
            if (x == x.parent.left) {
                w = x.parent.right;
                //cas1 w red
                if (w.color) {
                    w.color = false;
                    x.parent.color = true;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                // childrens of w are black cas2
                if (!w.left.color && !w.right.color) {
                    w.color = true;
                    x = x.parent;
                } else if (!w.right.color) {
                    w.left.color = false;
                    w.color = true;
                    rightRotate(w);
                    w = x.parent.right;
                } else {
                    w.color = x.parent.color;
                    x.parent.color = false;
                    w.right.color = false;
                    leftRotate(x.parent);
                    x = root;
                }
            }
            else {
                w = x.parent.left;
                if (w.color) {
                    w.color = false;
                    x.parent.color = true;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (!w.left.color && !w.right.color) {
                    w.color = true;
                    x = x.parent;
                } else if (!w.left.color) {
                    w.right.color = false;
                    w.color = true;
                    leftRotate(w);
                    w = x.parent.left;
                } else {
                    w.color = x.parent.color;
                    x.parent.color = false;
                    w.left.color = false;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }

        x.color = false;
    }
}