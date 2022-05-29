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
            try {
                StringTokenizer st = new StringTokenizer(in.reader.readLine());
                Deque<Colour> balls = new ArrayDeque<>();
                balls.push(new Colour(Integer.parseInt(st.nextToken())));
                int result = 0;
                while (st.hasMoreTokens()) {
                    int nextColour = Integer.parseInt(st.nextToken());
                    assert balls.peek() != null;
                    if (nextColour == balls.peek().colour) {
                        ++balls.peek().count;
                    } else {
                        if (balls.peek().count >= 3) {
                            result += balls.pop().count;
                        }
                        assert balls.peek() != null;
                        if (nextColour == balls.peek().colour) {
                            ++balls.peek().count;
                        } else {
                            balls.push(new Colour(nextColour));
                        }
                    }
                }
                assert balls.peek() != null;
                if (balls.peek().count >= 3) {
                    result += balls.pop().count;
                }
                out.print(result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        static class Colour {
            public int colour;
            public int count;

            public Colour(int colour) {
                this.colour = colour;
                count = 1;
            }

            @Override
            public String toString() {
                return colour + " " + count;
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
