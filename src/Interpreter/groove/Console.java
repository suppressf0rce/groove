package Interpreter.groove;

import java.util.Scanner;

public class Console {


    private static Scanner scanner = new Scanner(System.in);


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

    public static void print(char line) {
        System.out.println(line);
    }


    //STD Err functions
    //------------------------------------------------------------------------------------------------------------------
    public static void errprint(String line) {
        System.err.println(line);
    }

    public static void errprint(double line) {
        System.err.println(line);
    }

    public static void errprint(long line) {
        System.err.println(line);
    }

    public static void errprint(int line) {
        System.err.println(line);
    }

    public static void errprint(float line) {
        System.err.println(line);
    }

    public static void errprint(boolean line) {
        System.err.println(line);
    }

    public static void errprint(char line) {
        System.err.println(line);
    }


    //STD In functions
    //------------------------------------------------------------------------------------------------------------------
    public static String scanNextLine() {
        return scanner.nextLine();
    }

    public static boolean scanNextBoolean() {
        return scanner.nextBoolean();
    }

    public static int scanNextInt() {
        return scanner.nextInt();
    }

    public static long scanNextLong() {
        return scanner.nextLong();
    }

    public static float scanNextFloat() {
        return scanner.nextFloat();
    }

    public static double scanNextDouble() {
        return scanner.nextDouble();
    }

    //STD in functions That tells whether there is next scan
    public static boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public static boolean hasNextBoolean() {
        return scanner.hasNextBoolean();
    }

    public static boolean hasNextInt() {
        return scanner.hasNextInt();
    }

    public static boolean hasNextLong() {
        return scanner.hasNextLong();
    }

    public static boolean hasNextFloat() {
        return scanner.hasNextFloat();
    }

    public static boolean hasNextDouble() {
        return scanner.hasNextDouble();
    }
}
