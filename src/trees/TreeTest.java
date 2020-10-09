package trees;

public class TreeTest {
    public static void main(String args[])
    {
        Node root = new Node(100);

        Node n1 = new Node(50);
        Node n2 = new Node(20);
        Node n3 = new Node(150);
        Node n4 = new Node(70);
        Node n5 = new Node(200);
        Node n6 = new Node(90);
        Node n7 = new Node(95);
        Node n8 = new Node(120);
        Node n9 = new Node(110);
        Node n10 = new Node(10);

        root.insert(root , n1);
        root.insert(root , n2);
        root.insert(root , n3);
        root.insert(root , n4);
        root.insert(root , n5);
        root.insert(root , n6);
        root.insert(root , n7);
        root.insert(root , n8);
        root.insert(root , n9);
        root.insert(root , n10);

        root.BFS(root);

        root.preOrder(root);

        root.inOrder(root);

        root.postOrder(root);

        System.out.println(root.search(root , 70));

        System.out.println(root.height(root));

        System.out.println(root.numNodes(root));
    }
}
