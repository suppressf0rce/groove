package Interpreter.groove;

public class String {


    public static int strLen(java.lang.String str) {
        return str.length();
    }

    public static boolean strEndsWith(java.lang.String str, java.lang.String str2) {
        return str.endsWith(str2);
    }

    public static boolean strStartsWith(java.lang.String str, java.lang.String str2) {
        return str.startsWith(str2);
    }

    public static boolean strContains(java.lang.String str, java.lang.String str2) {
        return str.contains(str2);
    }

    public static java.lang.String strToLowerCase(java.lang.String str) {
        return str.toLowerCase();
    }

    public static java.lang.String strToUpperCase(java.lang.String str) {
        return str.toUpperCase();
    }

    public static java.lang.String strSubstring(java.lang.String str, int i) {
        return str.substring(i);
    }

    public static java.lang.String strSubstring(java.lang.String str, int i, int i1) {
        return str.substring(i, i1);
    }

    public static char strCharAt(java.lang.String str, int i) {
        return str.charAt(i);
    }

    public static java.lang.String strReplace(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return str.replace(str2, str3);
    }


    public static java.lang.String strReplace(java.lang.String str, char str2, char str3) {
        return str.replace(str2, str3);
    }


    public static java.lang.String strTrim(java.lang.String str) {
        return str.trim();
    }

    public static java.lang.String strReplaceAll(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return str.replaceAll(str2, str3);
    }

    public static java.lang.String strConcat(java.lang.String str, java.lang.String str2) {
        return str.concat(str2);
    }

}
