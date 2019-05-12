import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/** Class SplayTree **/
public class SplayT  implements Tree
{
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


    class SplayNode
    {
        SplayNode left, right, parent;
        String element;

        public SplayNode()
        {
            this("", null, null, null);
        }
        public SplayNode(String ele)
        {
            this(ele, null, null, null);
        }
        public SplayNode(String ele, SplayNode left, SplayNode right, SplayNode parent)
        {
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.element = ele;
        }

    }

    private SplayNode root;
    private int count = 0;

    /** Constructor **/
    public SplayT()
    {
        root = null;
    }


    /** function to insert element */
    public void insert(String ele)
    {
        SplayNode z = root;
        SplayNode p = null;
        while (z != null)
        {
            p = z;
            if (ele.compareTo(p.element) > 0)
                z = z.right;
            else
                z = z.left;
        }
        z = new SplayNode();
        z.element = ele;
        z.parent = p;
        if (p == null)
            root = z;
        else if (ele.compareTo(p.element) > 0)
            p.right = z;
        else
            p.left = z;
        Splay(z);
        count++;
    }
    /** rotate **/
    public void makeLeftChildParent(SplayNode c, SplayNode p)
    {
        if ((c == null) || (p == null) || (p.left != c) || (c.parent != p))
            throw new RuntimeException("WRONG");

        if (p.parent != null)
        {
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
        }
        if (c.right != null)
            c.right.parent = p;

        c.parent = p.parent;
        p.parent = c;
        p.left = c.right;
        c.right = p;
    }

    /** rotate **/
    public void makeRightChildParent(SplayNode c, SplayNode p)
    {
        if ((c == null) || (p == null) || (p.right != c) || (c.parent != p))
            throw new RuntimeException("WRONG");
        if (p.parent != null)
        {
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
        }
        if (c.left != null)
            c.left.parent = p;
        c.parent = p.parent;
        p.parent = c;
        p.right = c.left;
        c.left = p;
    }

    /** function splay **/
    private void Splay(SplayNode x)
    {
        while (x.parent != null)
        {
            SplayNode Parent = x.parent;
            SplayNode GrandParent = Parent.parent;
            if (GrandParent == null)
            {
                if (x == Parent.left)
                    makeLeftChildParent(x, Parent);
                else
                    makeRightChildParent(x, Parent);
            }
            else
            {
                if (x == Parent.left)
                {
                    if (Parent == GrandParent.left)
                    {
                        makeLeftChildParent(Parent, GrandParent);
                        makeLeftChildParent(x, Parent);
                    }
                    else
                    {
                        makeLeftChildParent(x, x.parent);
                        makeRightChildParent(x, x.parent);
                    }
                }
                else
                {
                    if (Parent == GrandParent.left)
                    {
                        makeRightChildParent(x, x.parent);
                        makeLeftChildParent(x, x.parent);
                    }
                    else
                    {
                        makeRightChildParent(Parent, GrandParent);
                        makeRightChildParent(x, Parent);
                    }
                }
            }
        }
        root = x;
    }

@Override

    public void delete(String ele)
    {
        SplayNode node = findNode(ele);
        remove(node);
    }

    /** function to remove node **/
    private void remove(SplayNode node)
    {
        if (node == null)
            return;

        Splay(node);
        if( (node.left != null) && (node.right !=null))
        {
            SplayNode min = node.left;
            while(min.right!=null)
                min = min.right;

            min.right = node.right;
            node.right.parent = min;
            node.left.parent = null;
            root = node.left;
        }
        else if (node.right != null)
        {
            node.right.parent = null;
            root = node.right;
        }
        else if( node.left !=null)
        {
            node.left.parent = null;
            root = node.left;
        }
        else
        {
            root = null;
        }
        node.parent = null;
        node.left = null;
        node.right = null;
        node = null;
        count--;
    }

    /** Functions to count number of nodes **/
    public int countNodes()
    {
        return count;
    }

    /** Functions to search for an element **/
    public boolean search(String val)
    {
        return findNode(val) != null;
    }

    private SplayNode findNode(String ele)
    {
        SplayNode PrevNode = null;
        SplayNode z = root;
        while (z != null)
        {
            PrevNode = z;
            if (ele.compareTo(z.element) > 0)
                z = z.right;
            else if (ele.compareTo(z.element) < 0)
                z = z.left;
            else if(ele.compareTo( z.element) ==0) {
                Splay(z);
                return z;
            }

        }
        if(PrevNode != null)
        {
            Splay(PrevNode);
            return null;
        }
        return null;
    }

@Override
public void inOrder()
    {
        inorder(root);
    }
    private void inorder(SplayNode r)
    {
        if (r != null)
        {
            inorder(r.left);
            System.out.print(r.element +" ");
            inorder(r.right);
        }
    }


}