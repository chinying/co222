/*
Top down method : start with an empty heap insert everything one by one
Bottom up: take unsorted, call sink
* note that the array is 1-indexed
*/

import java.lang.Comparable;

public class MinHeap<Item extends Comparable<Item>>{
  public static final int DEFAULTSIZE = 128;
  public int size;
  public int N;
  private Item[] heap;

  public MinHeap(int n){
    heap = (Item[]) new Comparable[n];
    size = n;
  }

  public MinHeap(){
    this(DEFAULTSIZE);
  }

  public void insert(Item x){
    //System.out.print("* inserting " + x + "\n");
    heap[++N] = x;
    swim(N);
    if (N >= size/2){
      resize(size*2);
    }
  }

  public Item top(){
    return heap[1];
  }

  public Item pop(){
    //System.out.print("*DELETE MIN\n");
    //swaps root with last element
    Item r = heap[1];
    swap(1, N--);
    sink(1);
    heap[N+1] = null;
    if (N > 1 && N == size/4) resize(size/2);
    return r;
  }

  private void swim(int k){ // operates by position
    while (k>1 && less(parent(k), k)){
      swap(parent(k), k);
      k >>= 1;
    }
  }

  private void sink(int k){ //sometimes aka heapify
    while (2*k <= N){
      // j is the selected child to be swapped
      int j = left(k);
      if (j < N && less(left(k), right(k))) j = right(k);
      if (!less(k, j)) break;
      swap(k, j);
      k = j;
    }
  }

  private boolean less(int x, int y){ //returns true if x <= y
    return heap[x].compareTo(heap[y]) >= 0;
  }

  private void swap(int x, int y){
    Item tmp = heap[x];
    heap[x] = heap[y];
    heap[y] = tmp;
  }

  private void resize(int capacity){
    Item[] tmp = (Item[]) new Comparable[capacity];
    for (int i=0; i<=N; i++) tmp[i] = heap[i];
    heap = tmp;
    size = capacity;
  }

  public void increaseKey(int pos, Item k) {
    if (heap[pos].compareTo(k) < 0) {
      System.out.println("Error: key cannot be less than original");
      return;
    }
    heap[pos] = k;
    swim(pos);
  }

  public void decreaseKey(int pos, Item k) {
    if (heap[pos].compareTo(k) > 0) {
      System.out.println("Error: key cannot be more than original");
      return;
    }
    heap[pos] = k;
    sink(pos);
  }

  public void topDownHeapify(Item[] arr){
    for (int i=0; i<arr.length; i++) insert(arr[i]);
  }

  public void bottomUpHeapify(Item[] arr){
    heap = (Item[]) new Comparable[arr.length+1];
    heap[0] = null;
    for (int i=0; i<arr.length; i++) heap[i+1] = arr[i];
    N = arr.length;
    for (int i = parent(N); i >= 1; i--) sink(i);
  }

  public int size() { return N;}
  public boolean empty(){
    return size() == 0;
  }

  public void printheap(){
    for (int i=1; i<=N; i++) System.out.print(heap[i]);
    System.out.println();
  }

  private int left(int k){ return k << 1;}
  private int right(int k){ return (k << 1) + 1;}
  private int parent(int k){ return k >> 1;}

  public static void main(String [] args){

  }
}
