package trees;

import java.util.LinkedList;
import java.util.Queue;

public class Node {
    Node left;
    Node right;
    int value;

    public Node(int a)
    {
        value = a;
        left = null;
        right = null;
    }

    public static void insert(Node root , Node a)
    {
        Node temp = root;

        if(a.value<root.value && root.left == null)
        {
            root.left = a;
        }
        if(a.value>root.value && root.right == null)
        {
            root.right = a;
        }

        if(a.value<temp.value && temp.left != null)
        {
            temp = temp.left;
            insert(temp , a);
        }
        if(a.value>temp.value && temp.right != null)
        {
            temp = temp.right;
            insert(temp , a);
        }
    }

    public static void BFS(Node root)
    {
        Queue<Node> q = new LinkedList<>();
        q.offer(root);

        while(!(q.isEmpty()))
        {
            Node temp = q.poll();
            System.out.println(temp.value);
            if(temp.left != null)
                q.offer(temp.left);
            if(temp.right != null)
                q.offer(temp.right);
        }
    }

    public static void preOrder(Node root)
    {
        if(root != null)
        {
            System.out.println(root.value);
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    public static void inOrder(Node root)
    {
        if(root != null)
        {
            inOrder(root.left);
            System.out.println(root.value);
            inOrder(root.right);
        }
    }

    public static void postOrder(Node root)
    {
        if(root != null)
        {
            postOrder(root.left);
            postOrder(root.right);
            System.out.println(root.value);
        }
    }

    public static boolean search(Node root , int a)
    {
        boolean temp = false;
        while(temp == false)
        {
            if(root.value == a)
            {
                temp = true;
                return true;
            }
            else
            {
                if(a<root.value && root.left != null)
                    root = root.left;
                if(a<root.value && root.left == null)
                    return false;

                if(a>root.value && root.right != null)
                    root = root.right;
                if(a>root.value && root.right == null)
                    return false;
            }
        }
        return false;
    }

    public static int height(Node root)
    {
        if(root == null)
            return 0;
        return(1 + Math.max(height(root.left) , height(root.right)));
    }

    public static int numLeaves(Node root)
    {
        if(root == null)
            return 0;
        if(root.left == null && root.right == null)
            return 1;
        return 1 + numLeaves(root.left) + numLeaves(root.right);
    }

    public static boolean isComplete(Node root)
    {
        return (numLeaves(root) == (Math.pow(2, height(root)-1)-1));
    }

    public static boolean stBinary(Node root)
    {
        if(root == null)
            return true;
        if(root.left == null)
            return false;
        if(root.right == null)
            return false;
        return stBinary(root.left) && stBinary(root.right);
    }

    public static int numNodes(Node root)
    {
        if(root == null)
            return 0;
        return 1 + numNodes(root.left) + numNodes(root.right);
    }

    public static boolean isAVLTree(Node root)
    {
        if(root == null)
            return true;

        int leftheight = height(root.left);
        int rightheight = height(root.right);

        if(Math.abs(leftheight - rightheight) <= 1)
        {
            return isAVLTree(root.left) && isAVLTree(root.right);
        }

        return false;
    }
}
