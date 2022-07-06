package view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
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
        rowField.setSize(new Dimension(30, 25));
        rowField.setLocation(250, 150);
        colField = new JTextField();
        colField.setSize(new Dimension(30, 25));
        colField.setLocation(250, 180);
        
        JLabel rowLabel = new JLabel("Rows: ");
        rowLabel.setSize(new Dimension(50, 25));
        rowLabel.setLocation(205, 150);
        
        JLabel colLabel = new JLabel("Cols: ");
        colLabel.setSize(new Dimension(50, 25));
        colLabel.setLocation(210, 180);
        
        player1NameField = new JTextField("Player1");
        player1NameField.setSize(new Dimension(100, 25));
        player1NameField.setLocation(250, 210);
        player2NameField = new JTextField("Player2");
        player2NameField.setSize(new Dimension(100, 25));
        player2NameField.setLocation(250, 240);
        
        JLabel p1 = new JLabel("Player 1: ");
        p1.setSize(new Dimension(60, 25));
        p1.setLocation(190, 210);
        JLabel p2 = new JLabel("Player 2: ");
        p2.setSize(new Dimension(60, 25));
        p2.setLocation(190, 240);
        
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener((ActionEvent e) -> {
            gv.startNewGame();
        });
        startGameButton.setSize(new Dimension(100, 25));
        startGameButton.setMargin(new Insets(0, 0, 0, 0));
        startGameButton.setLocation(210, 270);
        
        setLayout(null);
        add(rowLabel);
        add(rowField);
        add(colLabel);
        add(colField);
        add(p1);
        add(player1NameField);
        add(p2);
        add(player2NameField);
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
