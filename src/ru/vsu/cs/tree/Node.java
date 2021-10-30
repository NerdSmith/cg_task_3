package ru.vsu.cs.tree;


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

    private void compute(Map<String, Integer> values) {

    }
}
