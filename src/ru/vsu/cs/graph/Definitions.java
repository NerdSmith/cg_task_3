package ru.vsu.cs.graph;

import java.util.Arrays;
import java.util.List;

public class Definitions {
    public static final List<List<String>> SIGN_PRIORITY = Arrays.asList(
            Arrays.asList("^", "%"),
            Arrays.asList("*", "/"),
            Arrays.asList("+", "-")
    );
}
