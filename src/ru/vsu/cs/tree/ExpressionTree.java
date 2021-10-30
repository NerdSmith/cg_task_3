package ru.vsu.cs.tree;

import java.util.Arrays;
import java.util.List;

public class ExpressionTree {
    private final List<Character> SUM_SIGNS = Arrays.asList('+', '-');
    private final List<Character> MULTIPLY_SIGNS = Arrays.asList('*', '/');
    private final List<Character> EXPONENTIATION_SIGNS = Arrays.asList('^', '$');

    private Node node;

    public ExpressionTree(String expression) throws Exception {
        this.node = sum(expression);
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
        Node node;
        int idx = findNextOperation(expression, EXPONENTIATION_SIGNS);
        if (idx != -1) {
            Node leftNode = sum(expression.substring(0, idx));
            Node rightNode = sum(expression.substring(idx + 1));
            String value = Character.toString(expression.charAt(idx));

            node = new Node(NodeType.OPERATION, leftNode, rightNode, value);
        }
        else if (expression.charAt(0) == '(' && expression.charAt(expression.length() - 1) == ')') {
            String newExpression = expression.substring(1, expression.length() - 1);
            node = sum(newExpression);
        }
        else if (isNumeric(expression)) {
            node = new Node(NodeType.NUMBER, null, null, expression);
        }
        else if (Character.isLetter(expression.charAt(0))){
            node = new Node(NodeType.VARIABLE, null, null, expression);
        }
        else {
            throw new Exception("Can't parse element expression");
        }
        return node;
    }

    private boolean isNumeric(String expression) {
        try {
            double d = Double.parseDouble(expression);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
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
            if ((skipCount == 0) && (operations.contains(currChar))) {
                return i;
            }
        }
        return foundIdx;
    }

    public static void main(String[] args) throws Exception {
        String s = "X^(2+3)+2";
        ExpressionTree n = new ExpressionTree(s);
        System.out.println('a');
    }
}
