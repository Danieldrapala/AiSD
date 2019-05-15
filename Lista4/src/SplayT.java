import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


/** Class SplayTree **/
public class SplayT  implements Tree
{
    public long  count =0;
    public long  max =0;

    public long  insertOperation=0;
    public long deleteOperation=0;
    public long searchOperation=0;
    public long counterMOD = 0;
    public long counterIF = 0;
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
            for (String o : s)
                search(o);
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
        public SplayNode(String ele, SplayNode left, SplayNode right, SplayNode parent)
        {
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.element = ele;
        }

    }

    private SplayNode root;

    /** Constructor **/
    public SplayT()
    {
        root = null;
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

    @Override
    public void insert(String ele)
    {

            SplayNode z = root;
            SplayNode p = null;
            while (z != null) {
                p = z;
                if (comparingTo(ele,p.element) > 0)
                    z = z.right;
                else
                    z = z.left;
            }
            z = new SplayNode();
            z.element = ele;
            z.parent = p;
            if (p == null)
                root = z;
            else if (comparingTo(ele,p.element) > 0)
                p.right = z;
            else
                p.left = z;
            Splay(z);
            count++;
            insertOperation++;
        if(count>max){
            max=count;
        }
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
        counterMOD+=3;
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
        counterMOD+=3;

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
        deleteOperation++;

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
            counterMOD++;
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


@Override

    public boolean search(String val)
    {
        return findNode(val) != null;
    }

    private SplayNode findNode(String ele)
    {
        searchOperation++;
        SplayNode PrevNode = null;
        SplayNode z = root;
        while (z != null)
        {
            PrevNode = z;
            int condition=comparingTo(ele,z.element);
            if ( condition> 0)
                z = z.right;
            else if (condition < 0)
                z = z.left;
            else  {
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