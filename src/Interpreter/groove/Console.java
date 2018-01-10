package Interpreter.groove;

import java.util.Scanner;

public class Console {

    //STD Out functions
    //------------------------------------------------------------------------------------------------------------------
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

    public static void print(boolean line) {
        System.out.println(line);
    }


    //STD Err functions
    //------------------------------------------------------------------------------------------------------------------
    public static void eprint(String line) {
        System.err.println(line);
    }

    public static void eprint(double line) {
        System.err.println(line);
    }

    public static void eprint(long line) {
        System.err.println(line);
    }

    public static void eprint(int line) {
        System.err.println(line);
    }

    public static void eprint(float line) {
        System.err.println(line);
    }

    public static void eprint(boolean line) {
        System.err.println(line);
    }


    //STD In functions
    //------------------------------------------------------------------------------------------------------------------
    public static String scanNextLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int scanNextInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static long scanNextLong() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLong();
    }

    public static float scanNextFloat() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextFloat();
    }

    public static double scanNextDouble() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }
}
