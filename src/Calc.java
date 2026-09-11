public class Calc {
    public static void checkCalc(String subcommand, String value) {
        String[] valueArray = value.split(" ", 2);
        String value1 = valueArray.length > 0 ? valueArray[0] : "";
        String value2 = valueArray.length > 1 ? valueArray[1] : "";

        int value1Int = 0;
        double value1Double = 0;
        int value2Int = 0;
        try {
            value1Int = Integer.parseInt(value1);
            value1Double = Double.parseDouble(value1);

            if (!value2.isEmpty()) {
                value2Int = Integer.parseInt(value2);
            }
        }
        catch (NumberFormatException e) {
            System.out.println("ERROR: Please enter valid numbers.");
            return;
        }

        switch (subcommand) {
            case "gausssumme":
                System.out.println("The Gausssumme out of " + value1Int + " is " + calcGausssumme(value1Int));
                break;
            case "fakultaet":
                System.out.println("The Fakultaet out of " + value1Int + " is " + calcFakultaet(value1Int));
                break;
            case "potenz":
                System.out.println("The " + value2Int + ". potenz of " + value1Double + " is " + calcPotenz(value1Double, value2Int));
                break;
            case "newsystem":
                System.out.println("The number " + value1Int + " in the " + value2Int + ". system is " + calcNewSystem(value1Int, value2Int));
                break;
            case "fibonacci":
                String f = "1, 1";
                int first = 1;
                int second = 1;
                System.out.println("The first " + value1Int + " fibonacci numbers are " + calcFibonacci(f, first, second, value1Int));
                break;
            default:
                System.out.println("Sorry, but I don't know the command " + subcommand);
                break;
        }
    }

    private static int calcGausssumme(int n) { // n = number
        return (n * (n + 1)) / 2;
    }

    private static long calcFakultaet(int n) { // n = number
        if (n <= 0) return 1;
        return n * calcFakultaet(n - 1);
    }

    private static double calcPotenz(double b, int e) { // b = base, e = exponent
        if (e == 0) return 1;
        return b * calcPotenz(b, e - 1);
    }

    private static String calcNewSystem(int n, int b) { // n = number, b = base
        String a = "";
        if (b > 9) return "ERROR: only basis until 9";
        while (n > 0) {
            int r = n % b;
            a = r + a;
            n = n / b;
        }
        return a;
    }

    private static String calcFibonacci(String f, int first, int second, int n) {
        int a = 0;
        if (n > 2) {
            a = first + second;
            first = second;
            second = a;
            f = f + ", " + a;
            return calcFibonacci(f, first, second, n - 1);
        }
        return f;
    }
}
