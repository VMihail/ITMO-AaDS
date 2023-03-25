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
      int r = scanInt(), n = scanInt(), m = scanInt();
      SegmentTree segmentTree = new SegmentTree(n, r);
      for (int i = 0; i < n; i++) {
        Matrix next = new Matrix(scanInt(), scanInt(), scanInt(), scanInt());
        segmentTree.set(i, next);
      }
      while (m-- > 0) {
        int left = scanInt() - 1, right = scanInt();
        out.println(segmentTree.min(left, right));
        out.println();
      }
    }

    private static class Matrix {
      int a, b, c, d;

      public Matrix(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
      }

      public Matrix() {
        this.a = 1;
        this.b = 0;
        this.c = 0;
        this.d = 1;
      }

      public static Matrix mull(final Matrix first, final Matrix second, final int mod) {
        return new Matrix(
         (first.a * second.a + first.b * second.c) % mod,
         (first.a * second.b + first.b * second.d) % mod,
         (first.c * second.a + first.d * second.c) % mod,
         (first.c * second.b + first.d * second.d) % mod
         );
      }

      @Override
      public String toString() {
        return a + " " + b + "\n" + c + " " + d;
      }
    }

    private static class SegmentTree {
      private final Matrix[] tree;
      private final int arraySize;
      private final int mod;

      private SegmentTree(int n, int mod) {
        n = getNearestTwoDegree(n);
        final int size = (n << 1) - 1;
        this.tree = new Matrix[size];
        Arrays.fill(tree, new Matrix());
        this.arraySize = n;
        this.mod = mod;
      }

      private int getNearestTwoDegree(final int n) {
        int result = 1;
        while (result < n) {
          result <<= 1;
        }
        return result;
      }

      public void set(int i, Matrix value) {
        if (i < 0 || i >= arraySize) {
          throw new IndexOutOfBoundsException(String.format("Index %d for range [0, %d)", i, arraySize));
        }
        set(i, value, 0, 0, arraySize);
      }

      public Matrix min(int left, int right) {
        return min(left, right, 0, 0, arraySize);
      }

      private Matrix min(int left, int right, int currentNode, int currentLeft, int currentRight) {
        if (currentLeft >= right || currentRight <= left) {
          return new Matrix();
        }
        if (currentLeft >= left && currentRight <= right) {
          return tree[currentNode];
        }
        final int middle = getMiddle(currentLeft, currentRight);
        final int leftChild = (currentNode << 1) + 1;
        return Matrix.mull(min(left, right, leftChild, currentLeft, middle),
         min(left, right, leftChild + 1, middle, currentRight), mod);
      }

      private void set(int i, Matrix value, int currentNode, int left, int right) {
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
        tree[currentNode] = Matrix.mull(tree[leftChild], tree[leftChild + 1], mod);
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
