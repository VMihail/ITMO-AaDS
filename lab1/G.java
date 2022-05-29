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
            int n = scanInt(), x = scanInt(), y = scanInt();
            --n;
            int left = 0, right = (int) Math.pow(10, 9) + 1;
            while (right > left + 1) {
                int middleTime = (right - left) / 2 + left;
                if (goodTime(middleTime, n, x, y)) {
                    right = middleTime;
                } else {
                    left = middleTime;
                }
            }
            out.print(n + 1 != 1 ? min(x, y) + right : min(x, y));
        }

        private boolean goodTime(int time, int n, int x, int y) {
            return time / x + time / y >= n;
        }

        static class Heap {
            private int[] array;
            private int last;

            public Heap() {
                array = new int[10];
            }

            public void insert(int element) {
                if (isFill()) {
                    increase();
                }
                array[last++] = element;
                int i = last - 1;
                while (i > 0 && array[i] > array[(i - 1) / 2]) {
                    swap(i, (i - 1) / 2);
                    i = (i - 1) / 2;
                }
            }

            public int removeMax() {
                int result = array[0];
                array[0] = array[--last];
                int i = 0;
                while (2 * i + 1 < last) {
                    int indexNext = 2 * i + 1;
                    if (2 * i + 2 < last && array[indexNext + 1] > array[indexNext]) {
                        ++indexNext;
                    }
                    if (array[i] > array[indexNext]) {
                        break;
                    }
                    swap(i, indexNext);
                    i = indexNext;
                }
                return result;
            }

            private void increase() {
                int[] newArray = new int[array.length * 2];
                System.arraycopy(array, 0, newArray, 0, array.length);
                array = newArray;
            }

            private boolean isFill() {
                return last == array.length;
            }

            private void swap(int i, int j) {
                int save = array[i];
                array[i] = array[j];
                array[j] = save;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < last; i++) {
                    sb.append(array[i]).append(" ");
                }
                return sb.toString();
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
