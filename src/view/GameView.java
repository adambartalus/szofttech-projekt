package view;

import javax.swing.JFrame;


public class GameView {
    
    private final JFrame frame;
    private final GameArea gameArea;

    public GameView() {
        frame = new JFrame();
        gameArea = new GameArea();
        
        frame.add(gameArea);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
}
