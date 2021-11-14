package ru.vsu.cs.function_interpreter.action;

public class Multiply extends Action {
    @Override
    public Double compute(Double firstOperand, Double secondOperand) {
        return firstOperand * secondOperand;
    }
}
