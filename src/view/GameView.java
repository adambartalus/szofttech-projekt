package view;

import javax.swing.JFrame;
import model.Game;


public class GameView {
    
    private final JFrame frame;
    private final GameArea gameArea;
    
    private Game game;

    public GameView() {
        
        game = new Game();
        
        frame = new JFrame();
        gameArea = new GameArea(game);
        
        frame.add(gameArea);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
}
