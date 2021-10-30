package ru.vsu.cs.action;

public class Sum extends Action {
    @Override
    public Double compute(Double firstOperand, Double secondOperand) {
        return firstOperand + secondOperand;
    }
}
