import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Prim{

  private int INF = ~(1 << 31);
  private boolean[] mstSet;
  private ArrayList<HashMap<Integer, Integer>> g;
  private int V;

  private int[] distances;
  private int[] prev;

  public Prim(ArrayList<HashMap<Integer, Integer>> g) {
    this.g = g;
    V = g.size();
  }


  private class Vertex implements Comparable<Vertex>{
    int v, key;
    public Vertex(int v, int key){
      this.v = v;
      this.key = key;
    }

    public int v(){return v;}
    public int key(){return key;}

    @Override
    public int compareTo(Vertex cmp) {
      if (key > cmp.key) return 1;
      if (key < cmp.key) return -1;
      return 0;
    }
  }

  private void MST(int root){

    MinHeap<Vertex> pq = new MinHeap<Vertex>();
    boolean[] inPQ = new boolean[V];
    Vertex[] parent = new Vertex[V];
    Vertex[] dist = new Vertex[V];

    for (int i = 0; i<V; i++){
      inPQ[i] = true;
      if (i != root){
        dist[i] = new Vertex(i, INF);
        pq.insert(dist[i]);
        parent[i] = null;
      }
    }
    dist[root] = new Vertex(root, 0);
    pq.insert(dist[root]);

    while (!pq.empty()) {

      Vertex u = pq.pop();
      inPQ[u.v()] = false;
      // u.v(), u.key()
      Set<Integer> adj = neighbours(u.v()).keySet();

      for (Integer v : adj) {
        int wt = g.get(u.v()).get(v);
        if (inPQ[v] && wt < dist[v].key()) {
          // really stupid way of doing this because PQ isn't indexed
          // keep popping till match, update value, put the rest back in
          Vertex tmp;
          Stack<Vertex> stack = new Stack<Vertex>();
          while (!pq.empty()) {
            tmp = pq.pop();
            inPQ[tmp.v()] = false;
            if (tmp.v() == v && tmp.key() == dist[v].key()) break;
            stack.push(tmp); // this is after break, so match is not kept
          }

          dist[v] = new Vertex(v, wt);
          pq.insert(dist[v]);
          inPQ[v] = true;
          parent[v] = u;

          while (!stack.empty()) {
            tmp = stack.pop();
            pq.insert(tmp);
            inPQ[tmp.v()] = true;
          }
        }
      }

    }

    for (int i = 0; i < V; i++) {
      System.out.print(i + " -- Dist: " + dist[i].key() + ", parent: ");
      if (parent[i] == null) System.out.print("null\n");
      else System.out.print(parent[i].v() + "\n");
    }

  }

  private HashMap<Integer, Integer> neighbours(int v) {
    return g.get(v);
  }

  // mostly for debugging
  private void printGraph(){
    for (HashMap<Integer, Integer> arr : g) {
      System.out.println(arr);
    }
  }

  public static void main(String[] args) throws Exception{
    String INFILE = args[0];
    BufferedReader in = new BufferedReader(new FileReader(INFILE));
    int sz = Integer.parseInt(in.readLine());
    ArrayList<HashMap<Integer, Integer>> g = new ArrayList<HashMap<Integer, Integer>>();

    for (int i = 0; i < sz; i++) {
      g.add(new HashMap<Integer, Integer>());
    }

    for (int i = 0; i < sz; i++){
      String line = in.readLine();
      String[] tokens = line.split(" ");
      int neighbourCount = tokens.length >> 1;
      int from = i;
      for (int j = 0; j < neighbourCount; j++) {
        int to = Integer.parseInt(tokens[j*2]);
        int wt = Integer.parseInt(tokens[j*2 + 1]);
        g.get(from).put(to, wt);
        g.get(to).put(from, wt);
      }
    }

    Prim prim = new Prim(g);
    //prim.printGraph();
    prim.MST(3);
  }

}
