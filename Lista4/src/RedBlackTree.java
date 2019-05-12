
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
  enum RBT_COLORS {
    RED, BLACK
}

 class RedBlackTreeNode {

    private RedBlackTreeNode parent;
    private RedBlackTreeNode leftChild;
    private RedBlackTreeNode rightChild;
    private RBT_COLORS color;
    private String key;

     RedBlackTreeNode(){ this.color = RBT_COLORS.BLACK; }

    RedBlackTreeNode(String key) {
        this.key = key;
        this.color = RBT_COLORS.BLACK;
    }
    RedBlackTreeNode getParent() { return parent; }

    void setParent(RedBlackTreeNode parent) { this.parent = parent;}

     RedBlackTreeNode getLeftChild() { return leftChild;
    }

     void setLeftChild(RedBlackTreeNode childLeft) { this.leftChild = childLeft; }

     RedBlackTreeNode getRightChild() { return rightChild; }

     void setRightChild(RedBlackTreeNode childRight) { this.rightChild = childRight; }

     RBT_COLORS getColor() { return color; }

     void setColor(RBT_COLORS color) { this.color = color; }

     String getKey() { return key; }

    public void setKey(String key) { this.key = key; }
}
public class RedBlackTree  implements Tree{

    private RedBlackTreeNode nil;
    private RedBlackTreeNode root;



     RedBlackTree() {
        this.nil = new RedBlackTreeNode();
        this.root = new RedBlackTreeNode();
        root.setLeftChild(nil);
        root.setRightChild(nil);
        root.setParent(nil);
        root.setColor(RBT_COLORS.BLACK);
    }

    void setRootNode(RedBlackTreeNode root) {
        this.root = root;
    }


    RedBlackTreeNode getRootNode() {
        return root;
    }

    RedBlackTreeNode getNilNode() {
        return nil;
    }

    public boolean search(String val)
    {
        return find(val) != null;
    }

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
    @Override
    public boolean isEmpty() {
        return root==null;
    }



    public RedBlackTreeNode find(String key) {
        RedBlackTreeNode x = root;
        while (!x.equals(nil)) {
            if (x.getKey().equals(key)) {
                return x;
            } else if (x.getKey().compareTo(key) > 0) {
                x = x.getLeftChild();
            } else {
                x = x.getRightChild();
            }
        }
        return null;
    }


    public void insert(String key) {
        insert(new RedBlackTreeNode(key));
    }

    private void insert(RedBlackTreeNode node) {
        RedBlackTreeNode x = root;
        RedBlackTreeNode y = nil;
        while (!x.equals(nil)) {
            y = x;
            if (node.getKey().compareTo(x.getKey()) >= 0) {
                x = x.getRightChild();
            } else if (node.getKey().compareTo(x.getKey()) < 0) {
                x = x.getLeftChild();
            }
        }
        if (y.getKey().compareTo(node.getKey()) > 0) {
            y.setLeftChild(node);
        } else {
            y.setRightChild(node);
        }
        node.setParent(y);
        node.setLeftChild(nil);
        node.setRightChild(nil);
        node.setColor(RBT_COLORS.RED);
        rbtInsertFixup(this, node);
    }


    public RedBlackTreeNode getMinNodeStartingFrom(RedBlackTreeNode startingNode) {
        RedBlackTreeNode x = startingNode;
        RedBlackTreeNode y = nil;
        while (!x.equals(nil)) {
            y = x;
            x = x.getLeftChild();
        }
        if (y != nil) {
            return y;
        } else {
            return null;
        }
    }
    @Override
    public void delete(String val ) {
        RedBlackTreeNode node = this.find(val);
        if(node!=null&& root!=null)
            remove(node);

    }

    public void remove(RedBlackTreeNode node) {
        RedBlackTreeNode y = node;
        RedBlackTreeNode x = nil;
        RBT_COLORS y_original_color = y.getColor();
        if (node.getLeftChild().equals(nil)) {
            x = node.getRightChild();
            rbtTransplant(this, node, node.getRightChild());
        } else if (node.getRightChild().equals(nil)) {
            x = node.getLeftChild();
            rbtTransplant(this, node, node.getLeftChild());
        } else {
            y = getMinNodeStartingFrom(node.getRightChild());
            y_original_color = y.getColor();
            x = y.getRightChild();
            if (y.getParent().equals(node)) {
                x.setParent(y);
            } else {
                rbtTransplant(this, y, y.getRightChild());
                y.setRightChild(node.getRightChild());
                y.getRightChild().setParent(y);
            }
            rbtTransplant(this, node, y);
            y.setLeftChild(node.getLeftChild());
            y.getLeftChild().setParent(y);
            y.setColor(node.getColor());
        }
        if (y_original_color == RBT_COLORS.BLACK) {
            deleteFixup(this, x);
        }
    }

@Override
    public void inOrder() {
            printInOrderAllNodes(root, 0, "root");

    }

    private void printInOrderAllNodes(RedBlackTreeNode node, int level, String typeChild) {
        if (node != nil) {
            if (node.getLeftChild() != nil) {
                printInOrderAllNodes(node.getLeftChild(), level + 1, "left child");
            }

            if (node.getKey() != null && node.getColor() != null) {
                String output = "Node at level " + level + ". Key: " + node.getKey() + ". Color: "
                        + node.getColor().toString() + ". Type child: " + typeChild + ". Parent: "
                        + node.getParent().getKey();
                System.out.println(output);
            }

            if (node.getRightChild() != nil) {
                printInOrderAllNodes(node.getRightChild(), level + 1, "right child");
            }
        }
    }
    static void rbtTransplant(RedBlackTree tree, RedBlackTreeNode oldNode,RedBlackTreeNode newNode) {
            if (oldNode.getParent().equals(tree.getNilNode())) {
                tree.setRootNode(newNode);
            } else if (oldNode.equals(oldNode.getParent().getLeftChild())) {
                oldNode.getParent().setLeftChild(newNode);
            } else {
                oldNode.getParent().setRightChild(newNode);
            }
            newNode.setParent(oldNode.getParent());
        }

    static void deleteFixup(RedBlackTree tree, RedBlackTreeNode node) {
        RedBlackTreeNode x = tree.getNilNode();
        while (!node.equals(tree.getRootNode()) && node.getColor() == RBT_COLORS.BLACK) {
            if (node.equals(node.getParent().getLeftChild())) {
                x = node.getParent().getRightChild();
                if (x.getColor() == RBT_COLORS.RED) {
                    x.setColor(RBT_COLORS.BLACK);
                    node.getParent().setColor(RBT_COLORS.RED);
                    leftRotate(tree, node.getParent());
                    x = node.getParent().getRightChild();
                }
                if (x.getLeftChild().getColor() == RBT_COLORS.BLACK
                        && x.getRightChild().getColor() == RBT_COLORS.BLACK) {
                    x.setColor(RBT_COLORS.RED);
                    node = node.getParent();
                } else {
                    if (x.getRightChild().getColor() == RBT_COLORS.BLACK) {
                        x.getLeftChild().setColor(RBT_COLORS.BLACK);
                        x.setColor(RBT_COLORS.RED);
                        rightRotate(tree, x);
                        x = node.getParent().getRightChild();
                    }
                    x.setColor(node.getParent().getColor());
                    node.getParent().setColor(RBT_COLORS.BLACK);
                    x.getRightChild().setColor(RBT_COLORS.BLACK);
                    leftRotate(tree, node.getParent());
                    node = tree.getRootNode();
                }
            }else{
                x = node.getParent().getLeftChild();
                if (x.getColor() == RBT_COLORS.RED) {
                    x.setColor(RBT_COLORS.BLACK);
                    node.getParent().setColor(RBT_COLORS.RED);
                    rightRotate(tree, node.getParent());
                    x = node.getParent().getLeftChild();
                }
                if (x.getRightChild().getColor() == RBT_COLORS.BLACK
                        && x.getLeftChild().getColor() == RBT_COLORS.BLACK) {
                    x.setColor(RBT_COLORS.RED);
                    node = node.getParent();
                } else {
                    if (x.getLeftChild().getColor() == RBT_COLORS.BLACK) {
                        x.getRightChild().setColor(RBT_COLORS.BLACK);
                        x.setColor(RBT_COLORS.RED);
                        leftRotate(tree, x);
                        x = node.getParent().getLeftChild();
                    }
                    x.setColor(node.getParent().getColor());
                    node.getParent().setColor(RBT_COLORS.BLACK);
                    x.getLeftChild().setColor(RBT_COLORS.BLACK);
                    rightRotate(tree, node.getParent());
                    node = tree.getRootNode();
                }
            }
        }
        node.setColor(RBT_COLORS.BLACK);
    }
    static void rbtInsertFixup(RedBlackTree tree, RedBlackTreeNode node) {
        while (node.getParent().getColor().equals(RBT_COLORS.RED)) {
            if (node.getParent().equals(node.getParent().getParent().getLeftChild())) {
                RedBlackTreeNode uncle = node.getParent().getParent().getRightChild();
                if (uncle.getColor().equals(RBT_COLORS.RED)) {
                    node.getParent().setColor(RBT_COLORS.BLACK);
                    uncle.setColor(RBT_COLORS.BLACK);
                    node.getParent().getParent().setColor(RBT_COLORS.RED);
                    node = node.getParent().getParent();
                } else {
                    if (node.equals(node.getParent().getRightChild())) {
                        node = node.getParent();
                        leftRotate(tree, node);
                    }
                    node.getParent().setColor(RBT_COLORS.BLACK);
                    node.getParent().getParent().setColor(RBT_COLORS.RED);
                    rightRotate(tree, node.getParent().getParent());
                }
            } else {
                RedBlackTreeNode uncle = node.getParent().getParent().getLeftChild();
                if (uncle.getColor().equals(RBT_COLORS.RED)) {
                    node.getParent().setColor(RBT_COLORS.BLACK);
                    uncle.setColor(RBT_COLORS.BLACK);
                    node.getParent().getParent().setColor(RBT_COLORS.RED);
                    node = node.getParent().getParent();
                } else {
                    if (node.equals(node.getParent().getLeftChild())) {
                        node = node.getParent();
                        rightRotate(tree, node);
                    }
                    node.getParent().setColor(RBT_COLORS.BLACK);
                    node.getParent().getParent().setColor(RBT_COLORS.RED);
                    leftRotate(tree, node.getParent().getParent());
                }
            }
        }
        tree.getRootNode().setColor(RBT_COLORS.BLACK);
    }
   static  void rightRotate(RedBlackTree tree, RedBlackTreeNode node) {
        RedBlackTreeNode child = node.getLeftChild();
        node.setLeftChild(child.getRightChild());
        if (child.getRightChild() != tree.getNilNode()) {
            child.getRightChild().setParent(node);
        }
        child.setParent(node.getParent());
        if (node.getParent() == tree.getNilNode()) {
            tree.setRootNode(child);
        } else if (node.equals(node.getParent().getLeftChild())) {
            node.getParent().setLeftChild(child);
        } else {
            node.getParent().setRightChild(child);
        }
        child.setRightChild(node);
        node.setParent(child);
    }
 static   void leftRotate(RedBlackTree tree, RedBlackTreeNode node) {
        RedBlackTreeNode child = node.getRightChild();
        node.setRightChild(child.getLeftChild());
        if (child.getLeftChild() != tree.getNilNode()) {
            child.getLeftChild().setParent(node);
        }
        child.setParent(node.getParent());
        if (node.getParent() == tree.getNilNode()) {
            tree.setRootNode(child);
        } else if (node.equals(node.getParent().getLeftChild())) {
            node.getParent().setLeftChild(child);
        } else {
            node.getParent().setRightChild(child);
        }
        child.setLeftChild(node);
        node.setParent(child);
    }

}

