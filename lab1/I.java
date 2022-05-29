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
            int vp = scanInt(), vf = scanInt();
            double coordinate = scanDouble();
            double left = 0d, right = 1d;
            double EPS = 10e-6;
            while (right > left + EPS) {
                double middle = (left * 2d + right) / 3d;
                double t = (left + right * 2d) / 3d;
                if (time(middle, vp, vf, coordinate) < time(t, vp, vf, coordinate)) {
                    right = t;
                } else {
                    left = middle;
                }
            }
            out.print((right - left) / 2 + left);
        }

        private double time(double x, int vp, int vf, double coordinate) {
            return Math.hypot(x, 1d - coordinate) / vp + Math.hypot(1d - x, coordinate) / vf;
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
