package Interpreter.groove;

public class Console {

    public static void print(String line) {
        System.out.println(line);
    }


    public static void print(double line) {
        System.out.println(line);
    }

    public static void print(long line) {
        System.out.println(line);
    }

    public static void print(int line) {
        System.out.println(line);
    }

    public static void print(float line) {
        System.out.println(line);
    }

    public static void eprint(String line) {
        System.err.println(line);
        System.err.flush();
    }


    public static void eprint(double line) {
        System.err.println(line);
        System.err.flush();
    }

    public static void eprint(long line) {
        System.err.println(line);
        System.err.flush();
    }

    public static void eprint(int line) {
        System.err.println(line);
        System.err.println(line);
    }

    public static void eprint(float line) {
        System.err.println(line);
        System.err.flush();
    }

}
