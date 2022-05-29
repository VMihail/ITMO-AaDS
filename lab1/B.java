import java.io.*;
import java.util.*;

import static java.lang.Math.*;

public class Problem {

    public static void main(final String[] args) {
        final InputStream inputStream = System.in;
        final OutputStream outputStream = System.out;
        final FastScanner in = new FastScanner(inputStream);
        final FastPrintWriter out = new FastPrintWriter(outputStream);
        final boolean multiTests = false;
        if (multiTests) {
            int t = in.nextInt();
            while (t-- > 1) {
                new Solver(in, out).solve();
            }
        }
        new Solver(in, out).solve();
        out.close();
        in.close();
    }

    static class Solver {
        private final FastScanner in;
        private final FastPrintWriter out;

        public Solver(FastScanner in, FastPrintWriter out) {
            this.in = in;
            this.out = out;
        }

        public void solve() {
            int n = scanInt();
            int[] counts = new int[101];
            for (int i = 0; i < n; i++) {
                ++counts[scanInt()];
            }
            for (int i = 0; i < 101; i++) {
                while (counts[i]-- > 0) {
                    out.print(i + " ");
                }
            }
        }

        static class MergeSort {
            public static int[] merge(int[] first, int[] second) {
                int firstPointer = 0, secondPointer = 0;
                int[] result = new int[first.length + second.length];
                while (firstPointer < first.length || secondPointer < second.length) {
                    if (secondPointer == second.length || firstPointer < first.length && first[firstPointer] < second[secondPointer]) {
                        result[firstPointer + secondPointer] = first[firstPointer++];
                    } else {
                        result[firstPointer + secondPointer] = second[secondPointer++];
                    }
                }
                return result;
            }

            public static int[] sort(int[] array) {
                if (array.length <= 1) {
                    return array;
                }
                int[] left = new int[array.length / 2], right = new int[array.length / 2 + array.length % 2];
                System.arraycopy(array, 0, left, 0, array.length / 2);
                for (int i = array.length / 2, now = 0; i < array.length; i++, now++) {
                    right[now] = array[i];
                }
                left = sort(left);
                right = sort(right);
                return merge(left, right);
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

    static class FastScanner {
        private final BufferedReader reader;
        private StringTokenizer st;

        public FastScanner(InputStream inputStream) {
            reader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public void close() {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void read() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public String next() {
            read();
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }
    }

    static class FastPrintWriter extends PrintWriter {
        public FastPrintWriter(OutputStream outputStream) {
            super(outputStream);
        }

        public void print(Object ... objects) {
            for (int i = 0; i < objects.length - 1; i++) {
                print(objects[i] + " ");
            }
            print(objects[objects.length - 1]);
        }

        public void println(Object ... objects) {
            for (int i = 0; i < objects.length - 1; i++) {
                print(objects[i] + " ");
            }
            println(objects[objects.length - 1]);
        }

        public void printArray(int[] array) {
            for (int i = 0; i < array.length - 1; i++) {
                print(array[i] + " ");
            }
            println(array[array.length - 1]);
        }
    }
}
