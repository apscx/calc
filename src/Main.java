import java.util.Scanner;

public class Main {

    public static boolean isRomanNumber(String s) {
        return s.matches("^(?i)(M{0,3})(D?C{0,3}|C[DM])(L?X{0,3}|X[LC])(V?I{0,3}|I[VX])$");
    }

    public static boolean isNumber(String s) {
        return s.matches("\\d*");
    }

    public static String calc(String expr) {
        String[] lexems = expr.split(" ");

        if (lexems.length != 3) {
            throw new IllegalArgumentException("the string is not a mathematical operation or does not satisfy the format");
        }

        String sOperand1 = lexems[0];
        String sOperator = lexems[1];
        String sOperand2 = lexems[2];

        int value1, value2;

        boolean isRoman = isRomanNumber(sOperand1) && isRomanNumber(sOperand2);

        if (isRoman) {

            value1 = roman2arabic(sOperand1);
            value2 = roman2arabic(sOperand2);

        } else {

            if (isNumber(sOperand1) && isNumber(sOperand2)) {
                value1 = Integer.parseInt(sOperand1);
                value2 = Integer.parseInt(sOperand2);
            } else throw new IllegalArgumentException("different number systems are used simultaneously");
        }

        if (value1<1 || value1>10 || value2<1 || value2>10 )
            throw new IllegalArgumentException("operands must be numbers from 1 to 10 inclusive");

        int result = switch (sOperator) {
            case "+" -> value1 + value2;
            case "-" -> value1 - value2;
            case "*" -> value1 * value2;
            case "/" -> value1 / value2;
            default -> throw new IllegalArgumentException("the string is not a mathematical operation or does not satisfy the format");
        };

        if (isRoman) {
            if (result > 0)
                return arabic2roman(result);
            else throw new IllegalStateException("There are no negative numbers or zero in the Roman number system");
        } else
            return String.valueOf(result);

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String expr = sc.nextLine().trim();
        sc.close();

        System.out.println(calc(expr));

    }

    final static int[] arabicset = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    final static String[] romanset = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    private static int roman2arabic(String roman) {
        int pos = 0, res = 0;
        for (int i = 0; i < romanset.length; i++) {

            String r = romanset[i];
            int rlen = r.length();

            while (pos + rlen <= roman.length()) {
                if (roman.substring(pos, pos + rlen).equals(r)) {
                    res += arabicset[i];
                    pos += rlen;
                } else
                    break;
            }
        }
        return res;
    }

    private static String arabic2roman(int val) {

        String res = "";

        for (int i = 0; i < romanset.length; i++) {
            int a = arabicset[i];
            String r = romanset[i];
            while (val > 0) {
                if (val >= a) {
                    val -= a;
                    res += r;
                } else break;
            }
        }

        return res;
    }

}