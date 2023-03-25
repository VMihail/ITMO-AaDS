/** Допишу как-нибудь хороший шаблон с ДО */

import java.util.*;
import java.io.*;

public class Problem {
  public static void main(final String ...args) throws IOException {
    try (final FastScanner in = new FastScanner(System.in); final PW out = new PW(System.out)) {
      new Solver(in, out).solve();
    }
  }

  private record Solver(FastScanner in, PW out) {
    public void solve() {
      int t = 1;
      // t = scanInt();
      while (t-- > 0) {
        solveOne();
      }
    }

    private void solveOne() {
      int n = scanInt(), m = scanInt();
      SegmentTree<NodeImpl> segmentTree = new SegmentTree<>(n, NodeImpl.NEUTRAL);
      for (int i = 0; i < n; i++) {
        segmentTree.set(i, new NodeImpl(scanInt()));
      }
      while (m-- > 0) {
        int a = scanInt(), b = scanInt(), c = scanInt();
        if (a == 1) {
          segmentTree.set(b, new NodeImpl(c));
        } else {
          out.println(segmentTree.sum(b, c).getValue());
        }
      }
    }

    private interface Node<T> {
      Node<T> merge(Node<T> second);

      T getValue();

      Node<T> getNeutral();
    }

    private record NodeImpl(int value) implements Node<Integer> {
      public static final NodeImpl NEUTRAL = new NodeImpl(Integer.MAX_VALUE);

      @Override
      public Node<Integer> merge(Node<Integer> second) {
        return new NodeImpl(Math.min(value, second.getValue()));
      }

      @Override
      public Integer getValue() {
        return value;
      }

      @Override
      public Node<Integer> getNeutral() {
        return NEUTRAL;
      }
    }

    private static class SegmentTree<T extends Node<?>> {
      private final T[] tree;
      private final int arraySize;
      private final T NEUTRAL;

      private SegmentTree(int n, final T NEUTRAL) {
        n = getNearestTwoDegree(n);
        final int size = (n << 1) - 1;
        this.tree = (T[]) new Object[size];
        this.NEUTRAL = NEUTRAL;
        this.arraySize = n;
        Arrays.fill(this.tree, NEUTRAL);
      }

      private int getNearestTwoDegree(final int n) {
        int result = 1;
        while (result < n) {
          result <<= 1;
        }
        return result;
      }

      public void set(int i, T value) {
        if (i < 0 || i >= arraySize) {
          throw new IndexOutOfBoundsException(String.format("Index %d for range [0, %d)", i, arraySize));
        }
        set(i, value, 0, 0, arraySize);
      }

      public T sum(int left, int right) {
        return sum(left, right, 0, 0, arraySize);
      }

      private T sum(int left, int right, int currentNode, int currentLeft, int currentRight) {
        if (currentLeft >= right || currentRight <= left) {
          return NEUTRAL;
        }
        if (currentLeft >= left && currentRight <= right) {
          return tree[currentNode];
        }
        final int middle = getMiddle(currentLeft, currentRight);
        final int leftChild = (currentNode << 1) + 1;
        return sum(left, right, leftChild, currentLeft, middle).
                merge(sum(left, right, leftChild + 1, middle, currentRight));
      }

      private void set(int i, T value, int currentNode, int left, int right) {
        if (left == right - 1) {
          tree[currentNode] = value;
          return;
        }
        final int middle = getMiddle(left, right);
        final int leftChild = (currentNode << 1) + 1;
        if (i < middle) {
          set(i, value, leftChild, left, middle);
        } else {
          set(i, value, leftChild + 1, middle, right);
        }
        tree[currentNode] = tree[leftChild].merge(tree[leftChild + 1]);
      }

      private int getMiddle(int left, int right) {
        return ((right - left) >> 1) + left;
      }

      @Override
      public String toString() {
        return Arrays.toString(tree);
      }
    }

    // InputRead
    private int scanInt() {
      return in.nextInt();
    }

    private long scanLong() {
      return in.nextLong();
    }

    private double scanDouble() {
      return in.nextDouble();
    }

    private String next() {
      return in.next();
    }

    private int[] nextIntArray(int len) {
      int[] array = new int[len];
      for (int i = 0; i < len; i++) {
        array[i] = scanInt();
      }
      return array;
    }

    private long[] nextLongArray(int len) {
      long[] array = new long[len];
      for (int i = 0; i < len; i++) {
        array[i] = scanLong();
      }
      return array;
    }
  }

  private static class FastScanner implements AutoCloseable, Iterator<String> {
    private final BufferedReader reader;
    private StringTokenizer tokenizer;

    public FastScanner(final InputStream inputStream) {
      reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public FastScanner(final File file) throws FileNotFoundException {
      reader = new BufferedReader(new FileReader(file));
    }

    public FastScanner(final String string) {
      reader = new BufferedReader(new StringReader(string));
    }

    @Override
    public void close() throws IOException {
      reader.close();
    }

    @Override
    public boolean hasNext() {
      boolean result = read();
      return result && tokenizer.hasMoreTokens();
    }

    @Override
    public String next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return tokenizer.nextToken();
    }

    public int nextInt() {
      return Integer.parseInt(next());
    }

    public long nextLong() {
      return Long.parseLong(next());
    }

    public float nextFloat() {
      return Float.parseFloat(next());
    }

    public double nextDouble() {
      return Double.parseDouble(next());
    }

    private boolean read() {
      while (tokenizer == null || !tokenizer.hasMoreTokens()) {
        try {
          String nextLine = reader.readLine();
          if (nextLine == null) {
            return false;
          }
          tokenizer = new StringTokenizer(nextLine);
        } catch (IOException e) {
          System.out.println("I/O error " + e.getMessage());
        }
      }
      return true;
    }
  }

  private static class PW extends PrintWriter {
    public PW(final OutputStream outputStream) {
      super(outputStream);
    }

    public void print(final Object ...objects) {
      for (int i = 0; i < objects.length - 1; i++) {
        print(objects[i] + " ");
      }
      print(objects[objects.length - 1]);
    }

    public void println(final Object ...objects) {
      for (int i = 0; i < objects.length - 1; i++) {
        print(objects[i] + " ");
      }
      println(objects[objects.length - 1]);
    }

    public void printArray(final int[] array) {
      for (int i = 0; i < array.length - 1; i++) {
        print(array[i] + " ");
      }
      println(array[array.length - 1]);
    }
  }
}
