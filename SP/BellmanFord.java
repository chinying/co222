import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;
import java.io.FileReader;
import java.io.BufferedReader;

public class BellmanFord{

  private int[][] g;
  private int V;
  public BellmanFord(int[][] g){
    this.g = g;
    V = g.length;
  }

  public void bellmanford(){}


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

    BellmanFord b = new BellmanFord(g);

  }
}
