package view;

import java.awt.Dimension;
import javax.swing.JPanel;
import model.Game;

public class GameArea extends JPanel{

    private Game game;
    
    public GameArea(Game game) {
        
        this.game = game;
        
        setPreferredSize(new Dimension(500, 500));
    }
    
}
