package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Game;
import model.Position;
import model.StrongUnit;


public class GameView {
    
    private final JFrame frame;
    private final GameArea gameArea;
    private final JPanel controlPanel;
    private final ArrayList<JButton> buttons;
    
    private Game game;

    public GameView() {
        
        game = new Game(new Dimension(18, 10));
        
        frame = new JFrame();
        frame.setLayout(new BorderLayout(0, 5));
        gameArea = new GameArea(game);
        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(gameArea.getPreferredSize().width,100));
        
        this.buttons = new ArrayList<>();
        
        JButton b = new JButton("Spawn strongunit");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameView.this.game.addUnit(new StrongUnit(new Position(1, 1)));
            }
        });
        b.setPreferredSize(new Dimension(160,30));
        
        controlPanel.add(b);
        
        frame.add(gameArea, BorderLayout.NORTH);
        frame.add(controlPanel, BorderLayout.SOUTH);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
    
}
