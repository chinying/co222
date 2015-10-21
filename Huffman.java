import java.io.*;
import java.util.*;

public class Huffman{
    private int N;
    private PriorityQueue<Node> pq;
    private Node root;

    public Huffman(String[] s, int[] freq){
        N = s.length;
        pq = new PriorityQueue<Node>(N, this.sortBy());

        for (int i=0; i<N; i++){
            Node tmp = new Node(s[i], freq[i]);
            pq.add(tmp);
        }

        //build the tree
        while (pq.size() > 1){
            Node right = pq.poll();
            Node left = pq.poll();
            int sum = right.getN() + left.getN();
            String comb = right.getC() + "*" + left.getC();

            Node merged = new Node(comb, sum);

            System.out.printf("merging %s and %s to %d \n", right.getC(), left.getC(), sum);

            merged.setLeft(left);
            merged.setRight(right);
            right.setParent(merged, true);
            left.setParent(merged, false);

            pq.add(merged);
        }

        root = pq.peek();
        //System.out.println(root);
        System.out.println("ROOT IS " + root.getN());
        printTree(root);

    }

    private class Node{
        private String c;
        private int n;

        //0 (false) for left, 1 (true) for right
        private boolean fork;
        private Node leftChild;
        private Node rightChild;
        private Node parent;

        public Node(String c, int n){
            this.c = c;
            this.n = n;
        }
        public String getC(){return c;}
        public int getN(){return n;}

        public boolean isLeaf(){
            return leftChild == null && rightChild == null;
        }

        public void setLeft(Node node){ leftChild = node;}
        public void setRight(Node node){ rightChild = node;}

        public void setParent(Node node, boolean left){
            parent = node;
            fork = left;
        }

        public Node left(){return leftChild;}
        public Node right(){return rightChild;}
        public Node retParent(){return parent;}

        public String route(){
            return fork ? "1" : "0";
        }

    }

    public Comparator<Node> sortBy(){
        return new Comparator<Node>(){
            public int compare(Node p, Node q){
                if (p.getN() > q.getN()) return 1;
                if (p.getN() < q.getN()) return -1;
                return 0;
            }
        };
    };

    public void printTree(Node node){
        //System.out.print(node.route());
        if (node.isLeaf()){
            //System.out.print(" -> ");
            tracePath(node);
            System.out.printf("(%d) : %s \n", node.getN(), node.getC());
            return;
        }
        printTree(node.left());
        printTree(node.right());
    }

    public void tracePath(Node node){
        if (node.getN() == root.getN()) return;
        System.out.print(node.route());
        tracePath(node.retParent());
    }

    public static void main(String[] args) throws Exception{
        //Huffman hm = new Huffman("huffman coding test data example");
        String INFILE = "input.txt";
        BufferedReader in = new BufferedReader(new FileReader(INFILE));
        int n = Integer.parseInt(in.readLine());
        int [] freq = new int[n];
        String [] values = new String[n];
        for (int i=0; i<n; i++){
            String line = in.readLine();
            StringTokenizer st = new StringTokenizer(line, " ");
            freq[i] = Integer.parseInt(st.nextToken());
            values[i] = st.nextToken();
        }

        Huffman hm = new Huffman(values, freq);
    }

}
