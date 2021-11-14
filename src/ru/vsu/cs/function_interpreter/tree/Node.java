package ru.vsu.cs.function_interpreter.tree;


import java.util.Map;

public class Node {
    private NodeType type;
    private Node left;
    private Node right;
    private String value;

    public Node(NodeType type, Node left, Node right, String value) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public Double compute(Map<Character, Double> vars) throws Exception {
        switch (this.type) {
            case NUMBER: {
                return Double.parseDouble(value);
            }
            case OPERATION: {
                return OperatorAction.performAnAction(value, left.compute(vars), right.compute(vars));
            }
            case VARIABLE: {
                Double varValue = vars.get(this.value.charAt(0));
                if (varValue != null) {
                    return varValue;
                }
                else {
                    throw new Exception("Variable value not specified");
                }
            }
            case CONSTANT: {
                switch (this.value) {
                    case "E": {
                        return Math.E;
                    }
                    case "Pi": {
                        return Math.PI;
                    }
                    default: {
                        throw new Exception("Unknown constant");
                    }
                }
            }
            default: {
                throw new Exception("Unknown operand");
            }
        }
    }

}
