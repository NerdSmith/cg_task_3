package ru.vsu.cs.function_interpreter.util;

public class StringUtils {
    public static String removeWhitespaces(String str) {
        return str.replaceAll("\\s+","");
    }

    public static boolean isBracketsBalanced(String expression) {
        char[] chars = expression.toCharArray();
        int counter = 0;
        boolean isRequiringChar = false;
        for (char ch: chars) {
            if (ch == '(') {
                counter++;
                isRequiringChar = true;
            }
            else if (ch == ')') {
                if (isRequiringChar) {
                    return false;
                }
                else {
                    counter--;
                    if (counter < 0) {
                        return false;
                    }
                }
            }
            else {
                isRequiringChar = false;
            }
        }
        return counter == 0;
    }
}
