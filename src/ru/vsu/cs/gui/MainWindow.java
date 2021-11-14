package ru.vsu.cs.gui;

import ru.vsu.cs.gui.DrawingObject.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private final DrawPanel drawPanel;
    private JPanel panelMain;
    private JPanel inputPanel;
    private JLabel inputLabel;
    private JTextField inputTextField;
    private JButton removeBtn;
    private JButton submitBtn;


    public MainWindow() {
        // window settings block
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 100, 1280, 760);

        // creating a new panel 4 content pane
        this.panelMain = new JPanel();
        this.panelMain.setLayout(new BoxLayout(this.panelMain, BoxLayout.Y_AXIS));

        // creating a new drawPanel
        this.drawPanel = new DrawPanel();

        // creating a new panel 4 input
        this.inputPanel = new JPanel();
        this.inputPanel.setLayout(new BoxLayout(this.inputPanel, BoxLayout.X_AXIS));

        // creating components 4 input panel
        this.inputLabel = new JLabel();
        this.inputLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.inputLabel.setText("y=");

        this.inputTextField = new JTextField();
        this.inputTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.inputTextField.setMaximumSize(new Dimension(5000, 60));

        this.removeBtn = new JButton("Remove");
        this.removeBtn.addActionListener(new RemoveBtnListener(drawPanel));
        this.submitBtn = new JButton("Submit");
        this.submitBtn.addActionListener(new SubmitBtnListener(inputTextField, drawPanel));

        this.inputPanel.add(this.inputLabel);
        this.inputPanel.add(this.inputTextField);
        this.inputPanel.add(this.removeBtn);
        this.inputPanel.add(this.submitBtn);

        // adding components to main panel
        this.panelMain.add(this.drawPanel);
        this.panelMain.add(this.inputPanel);

        // adding panelMain as main content pane
        this.getContentPane().add(this.panelMain, BorderLayout.CENTER);

        // setting window visible
        this.setVisible(true);
    }

    private static class RemoveBtnListener implements ActionListener {
        private final DrawPanel drawPanel;

        public RemoveBtnListener(DrawPanel drawPanel) {
            this.drawPanel = drawPanel;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            this.drawPanel.removeDrawingObject();
        }
    }

    private static class SubmitBtnListener implements ActionListener {
        private final JTextField inputTextField;
        private final DrawPanel drawPanel;

        public SubmitBtnListener(JTextField inputTextField, DrawPanel drawPanel) {
            this.inputTextField = inputTextField;
            this.drawPanel = drawPanel;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String text = inputTextField.getText();
            if (!text.equals("")) {
                try {
                    drawPanel.addDrawingObject(new Graph(text));
                    drawPanel.repaint();
                } catch (Exception e) {
                    inputTextField.setText("Not correct function");
                }
            }
        }
    }
}
