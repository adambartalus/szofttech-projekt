package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Game;
import model.Unit;
import res.ResourceLoader;

public class GameArea extends JPanel{

    private Game game;
    
    private final int FPS = 120;
    private final Timer timer;
    
    private Image bg;
    
    public GameArea(Game game) {
        
        this.game = game;
        try {
            bg = ResourceLoader.loadImage("res/bg.jpg");
        } catch(Exception e) {
            
        }
        Dimension d = this.game.getMapDimension();
        setPreferredSize(
            new Dimension(
                (int)d.getWidth() * Game.cellSize,
                (int)d.getHeight() * Game.cellSize)
        );
        
        timer = new GameTimer(1000.0 / FPS);
        timer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = this.game.getMapDimension();
        
        g2.drawImage(bg, 0, 0, (int)d.getWidth() * Game.cellSize, (int)d.getHeight() * Game.cellSize, null);
        
        //drawing units
        for(Unit u : this.game.getUnits()) {
            g2.setColor(Color.red);
            g2.fillRect(
                u.getPosition().getX() * Game.cellSize,
                u.getPosition().getY() * Game.cellSize,
                Game.cellSize,
                Game.cellSize
            );
        } 
    }
    
    private class GameTimer extends Timer {
        public GameTimer(double ms) {
            super((int)ms, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    repaint();
                }
            });
        }
    }
}
