import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final int n = in.nextInt();
    private static final ArrayDeque<Integer> a = new ArrayDeque<>();
    private static final ArrayDeque<Integer> b = new ArrayDeque<>();

    public static void main(String[] args) {
        solve();
    }

    private static void solve() {
        for (int i = 0; i < n; i++) {
            String zap = in.next();

            switch (zap) {
                case "+":
                    String add = in.next();
                    b.add(Integer.parseInt(add));
                    break;
                case "-":
                    System.out.println(a.pollFirst());
                    break;
                default:
                    String addEl = in.next();
                    b.addFirst(Integer.parseInt(addEl));
                    break;
            }

            if (b.size() > a.size()) {
                int m = b.pollFirst();
                a.add(m);
            }
        }
    }
}
