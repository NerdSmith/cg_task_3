package ru.vsu.cs;

import ru.vsu.cs.gui.MainWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
