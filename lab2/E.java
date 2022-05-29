import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final String inputLine = in.nextLine();
    private static final ArrayList<String> a = new ArrayList<>();

    public static void main(String[] args) {
        solve();
    }

    private static void solve() {
        Scanner scanner = new Scanner(inputLine);
        while (scanner.hasNext()) {
            a.add(scanner.next());
        }

        while (a.size() > 1) {
            for (int i = a.size() - 1; i > 1; i--) {
                if (isNumber(a.get(i - 1)) && isOperation(a.get(i)) && isNumber(a.get(i - 2))) {
                    String result = operation(a.get(i - 2), a.get(i - 1), a.get(i));
                    a.remove(i - 2);
                    a.remove(i - 2);
                    a.set(i - 2, result);
                }
            }
        }

        System.out.println(a.get(0));
    }

    private static boolean isNumber(String line) {
        if (line.length() > 1) {
            if (line.charAt(0) == '-' && Character.isDigit(line.charAt(1))) {
                return true;
            }
        }

        return Character.isDigit(line.charAt(0));
    }

    private static boolean isOperation(String string) {
        return string.equals("+") || string.equals("-") || string.equals("*");
    }

    private static String operation(String first, String second, String operation) {
        int x = Integer.parseInt(first);
        int y = Integer.parseInt(second);

        switch (operation) {
            case "+":
                return Integer.toString(x + y);
            case "-":
                return Integer.toString(x - y);
            default:
                return Integer.toString(x * y);
        }
    }
}
