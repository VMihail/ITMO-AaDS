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

    interface Solve {
        void solve();
        int MOD = (int) pow(10, 9) + 7, INF = Integer.MAX_VALUE - 1;
    }

    static class Solver implements Solve {
        private final FastScanner in;
        private final FastPrintWriter out;

        public Solver(FastScanner in, FastPrintWriter out) {
            this.in = in;
            this.out = out;
        }

        @Override
        public void solve() {
            int n = scanInt(), k = scanInt();
            int[] cost = new int[n], dp = new int[n], way = new int[n];
            Arrays.fill(dp, -INF);
            cost[0] = cost[n - 1] = 0;
            for (int i = 1; i < n - 1; i++) {
                cost[i] = scanInt();
            }
            dp[0] = 0;
            for (int i = 1; i < n; i++) {
                for (int j = 1; j <= k; j++) {
                    if (i - j < 0) {
                        break;
                    }
                    if (dp[i - j] > dp[i]) {
                        dp[i] = dp[i - j];
                        way[i] = i - j;
                    }
                }
                dp[i] += cost[i];
            }
            out.println(dp[n - 1]);
            List<Integer> result = new ArrayList<>();
            result.add(n - 1);
            int next = way[n - 1];
            while (next > 0) {
                result.add(next);
                next = way[next];
            }
            result.add(0);
            out.println(result.size() - 1);
            for (int i = result.size() - 1; i >= 0; i--) {
                out.print(result.get(i) + 1, "");
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

    static class FastScanner implements Closeable {
        private final BufferedReader reader;
        private StringTokenizer st;

        public FastScanner(InputStream inputStream) {
            reader = new BufferedReader(new InputStreamReader(inputStream));
        }

        @Override
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
