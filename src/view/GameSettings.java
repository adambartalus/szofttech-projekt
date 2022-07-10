package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GameSettings extends JPanel {
    
    private final JTextField rowField;
    private final JTextField colField;
    private final JTextField player1NameField;
    private final JTextField player2NameField;
    
    public GameSettings(GameView gv) {
        
        rowField = new JTextField();
        rowField.setMaximumSize(new Dimension(50, 30));
        rowField.setBorder(null);
        colField = new JTextField();
        colField.setMaximumSize(new Dimension(50, 30));
        colField.setBorder(null);
        
        JLabel rowLabel = new JLabel("Rows: ");
        JLabel colLabel = new JLabel("Cols: ");
        
        player1NameField = new JTextField("Player1");
        player1NameField.setMaximumSize(new Dimension(300, 30));
        player1NameField.setBorder(null);
        player2NameField = new JTextField("Player2");
        player2NameField.setMaximumSize(new Dimension(300, 30));
        player2NameField.setBorder(null);
        
        JLabel p1 = new JLabel("Player 1: ");
        JLabel p2 = new JLabel("Player 2: ");
        
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        startGameButton.setBorder(null);
        startGameButton.addActionListener((ActionEvent e) -> {
            gv.startNewGame();
        });
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(140, 140, 140));
        add(rowLabel);
        add(rowField);
        add(colLabel);
        add(colField);
        add(p1);
        add(player1NameField);
        add(p2);
        add(player2NameField);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(startGameButton);
    }
    public String getRowFieldText() {
        return rowField.getText();
    }
    public String getColFieldText() {
        return colField.getText();
    }
    public String getPlayer1FieldText() {
        return player1NameField.getText();
    }
    public String getPlayer2FieldText() {
        return player2NameField.getText();
    }
}
