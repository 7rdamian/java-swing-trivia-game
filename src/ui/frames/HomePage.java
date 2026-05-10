package ui.frames;

import ui.panels.MenuPanel;
import javax.swing.*;

// Main frame of the application, contains the menu panel

public class HomePage extends JFrame {

    public HomePage() {
        super("General Knowledge Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuPanel menuPanel = new MenuPanel(this);

        setContentPane(menuPanel);
        setVisible(true);
    }
}