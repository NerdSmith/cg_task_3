package ru.vsu.cs.function_interpreter.tree;

import java.util.*;

import static ru.vsu.cs.function_interpreter.util.StringUtils.isBracketsBalanced;
import static ru.vsu.cs.function_interpreter.util.StringUtils.removeWhitespaces;

public class ExpressionTree {
    private final List<Character> SUM_SIGNS = Arrays.asList('+', '-');
    private final List<Character> MULTIPLY_SIGNS = Arrays.asList('*', '/');
    private final List<Character> EXPONENTIATION_SIGNS = Arrays.asList('^', '$');

    private Node node;

    public ExpressionTree(String expression) throws Exception {
        expression = removeWhitespaces(expression);
        boolean isValidBrackets = isBracketsBalanced(expression);
        if (!isValidBrackets) {
            throw new Exception("Brackets is invalid");
        }
        this.node = sum(expression);
    }

    public double compute() throws Exception {
        return node.compute(new HashMap<>());
    }

    public double compute(Map<Character, Double> vars) throws Exception {
        return node.compute(vars);
    }

    private Node sum(String expression) throws Exception {
        Node node;
        int idx = findNextOperation(expression, SUM_SIGNS);
        if (idx != -1) {
            Node leftNode = multiply(expression.substring(0, idx));
            Node rightNode = sum(expression.substring(idx + 1));
            String value = Character.toString(expression.charAt(idx));

            node = new Node(NodeType.OPERATION, leftNode, rightNode, value);
        }
        else {
            node = multiply(expression);
        }
        return node;
    }

    private Node multiply(String expression) throws Exception {
        Node node;
        int idx = findNextOperation(expression, MULTIPLY_SIGNS);
        if (idx != -1) {
            Node leftNode = element(expression.substring(0, idx));
            Node rightNode = multiply(expression.substring(idx + 1));
            String value = Character.toString(expression.charAt(idx));

            node = new Node(NodeType.OPERATION, leftNode, rightNode, value);
        }
        else {
            node = element(expression);
        }
        return node;
    }

    private Node element(String expression) throws Exception {
        // exponentiation block
        Node node;
        int idx = findNextOperation(expression, EXPONENTIATION_SIGNS);
        if (idx != -1) {
            Node leftNode = sum(expression.substring(0, idx));
            Node rightNode = sum(expression.substring(idx + 1));
            String value = Character.toString(expression.charAt(idx));

            node = new Node(NodeType.OPERATION, leftNode, rightNode, value);
        }
        // brackets block
        else if (expression.charAt(0) == '(' && expression.charAt(expression.length() - 1) == ')') {
            String newExpression = expression.substring(1, expression.length() - 1);
            node = sum(newExpression);
        }
        // parsing numeric element
        else if (isNumeric(expression)) {
            node = new Node(NodeType.NUMBER, null, null, expression);
        }
        // parsing variable and constant element
        else if (Character.isLetter(expression.charAt(0))) {
            // 4 constant
            if (Character.isUpperCase(expression.charAt(0))) {
                node = new Node(NodeType.CONSTANT, null, null, expression);
            }
            // 4 var
            else {
                node = new Node(NodeType.VARIABLE, null, null, expression);
            }
        }
        // error case
        else {
            throw new Exception("Can't parse element expression");
        }
        return node;
    }

    private int findNextOperation(String expression, List<Character> operations) {
        int skipCount = 0;
        int foundIdx = -1;
        char currChar;
        for (int i = 0; i < expression.length(); i++) {
            currChar = expression.charAt(i);
            // evade brackets
            if (currChar == '(') {
                skipCount++;
            }
            else if (currChar == ')') {
                skipCount--;
            }
            // check curr idx
            if (
                    (skipCount == 0) &&
                    (operations.contains(currChar)) &&
                    (i - 1 >= 0) &&
                    (
                            Character.isDigit(expression.charAt(i - 1)) ||
                            Character.isLetter(expression.charAt(i - 1)) ||
                            expression.charAt(i - 1) == '(' ||
                            expression.charAt(i - 1) == ')'
                    )
            ) {
                return i;
            }
        }
        return foundIdx;
    }

    private boolean isNumeric(String expression) {
        try {
            double d = Double.parseDouble(expression);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        String s = "1/(x+1/3)";
        ExpressionTree n = new ExpressionTree(s);
        System.out.println(n.compute(Map.of('x', -33.0/100)));

        // System.out.println(Double.isFinite(n.compute()));

    }
}
