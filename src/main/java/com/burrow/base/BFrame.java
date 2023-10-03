package com.burrow.base;

import javax.swing.JFrame;

public class BFrame extends JFrame {
    public BPanel panel;

    public void maximize() {
        setExtendedState(MAXIMIZED_BOTH);
    }
    public void minimize() {
        setExtendedState(ICONIFIED);
    }
    public void restore() {
        setExtendedState(NORMAL);
    }
    public void initialize(BPanel panel) {
        this.panel = panel;
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
