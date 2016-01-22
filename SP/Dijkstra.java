import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;
import java.io.FileReader;
import java.io.BufferedReader;

public class Dijkstra{

  private int INF = ~(1 << 31);

  private Integer[][] g;
  private int V;

  private int[] distances;
  private int[] prev;

  public Dijkstra(Integer[][] g) {
    this.g = g;
    V = g.length;
  }

  public void printGraph() {
    for (int i = 0; i < V; i++) {
      for (int j = 0; j < V; j++) {
        System.out.print(g[i][j] +  " ");
      }
      System.out.println();
    }
  }

  public void dijkstra(int src) {
    int[] dist = new int[V];
    int[] prev = new int[V];

    for (int i = 0; i < V; i++) dist[i] = INF;
    dist[src] = 0;

    TreeSet<Integer> pq = new TreeSet<Integer>(new Comparator<Integer>(){
      @Override
      public int compare(Integer x, Integer y) {
        if (dist[x] < dist[y]) return -1;
        else if (dist[x] > dist[y]) return 1;
        else return 0;
      }
    });

    pq.add(0);
    while (!pq.isEmpty()) {
      int u = pq.pollFirst();
      for (int i = 0; i < V; i++) {
        if (g[u][i] == null) continue;
        int alt = dist[u] + g[u][i];
        if (alt < dist[i]) {
          pq.remove(i);
          dist[i] = alt;
          pq.add(i);
          prev[i] = u;
        }
      }
    }

    distances = dist;
    this.prev = prev;
  }

  private int[] distances() {
    return distances;
  }
  public void printPath(int source, int dest) {
    int n = dest;
    Stack<Integer> path = new Stack<Integer>();
    while (true) {
      path.push(n);
      if (n == source) break;
      n = prev[n];
    }

    while (!path.empty()) {
      System.out.print(path.pop() + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) throws Exception{

    int start = 0, end = 3;

    BufferedReader in = new BufferedReader(new FileReader("input.in"));
    int sz = Integer.parseInt(in.readLine());
    Integer[][] g = new Integer[sz][sz];

    for (int i = 0; i < sz; i++){
      String line = in.readLine();
      String[] tokens = line.split(" ");
      int from = Integer.parseInt(tokens[0]);
      int neighbourCount = tokens.length >> 1;
      for (int j = 0; j < neighbourCount; j++) {
        int to = Integer.parseInt(tokens[j*2 + 1]);
        int wt = Integer.parseInt(tokens[j*2 + 2]);
        g[from][to] = wt;
        g[to][from] = wt;
      }
    }

    Dijkstra d = new Dijkstra(g);
    d.dijkstra(start);
    int[] dist = d.distances();

    if (dist[end] == (~(1 << 31))) {
      System.out.println("-1");
      return;
    }

    System.out.println(dist[end]);
    d.printPath(start, end);
  }
}
