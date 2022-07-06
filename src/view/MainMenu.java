package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


public class MainMenu extends JPanel {
    
    private final JButton newGameButton;
    private final JButton exitButton;
    
    public MainMenu() {
        
        newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setMaximumSize(new Dimension(200, 30));
        newGameButton.setFocusPainted(false);
        
        exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(200, 30));
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(500, 500));
        
        add(Box.createRigidArea(new Dimension(0, 200)));
        add(newGameButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(exitButton);
    }
    
    public void addNewGameButtonActionListener(ActionListener al) {
        newGameButton.addActionListener(al);
    }
    public void addExitButtonActionListener(ActionListener al) {
        exitButton.addActionListener(al);
    }
}
