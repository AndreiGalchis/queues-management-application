package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JFrame {
    private final JTextArea logArea;
    private final JScrollPane scrollPane;

    public SimulationFrame() {
        setTitle("Simulation Logs");
        setSize(800, 600);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void appendMessage(String message) {
            logArea.append(message);
            logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}