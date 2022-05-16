package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;
import model.Game;
import model.Position;
import model.TowerShot;
import model.Unit;
import res.ResourceLoader;

/**
 * Class displaying the game area
 */
public class GameArea extends JPanel{

    public Game game;
    
    private Image grass_tile;
    private Position pointedCell;
    private Position selectedBuildingPos;
   
    public GameArea() {
        try {
            grass_tile = ResourceLoader.loadImage("res/grass_tile.png");
        } catch(Exception e) {
            
        }
    }
    /**
     * @param game the object storing the game parameters
     */
    public GameArea(Game game) {
        this();
        this.game = game;
        
        adjustSize();
    }
    public void setGame(Game g) {
        game = g;
    }
    public void adjustSize() {
        Dimension d = this.game.getMapDimension();
        setPreferredSize(
            new Dimension(
                (int)d.getWidth() * Game.cellSize,
                (int)d.getHeight() * Game.cellSize)
        );
    }
    public void setPointedCell(Position p) {
        this.pointedCell = p;
    }
    public void setSelectedBuildingPos(Position p) {
        this.selectedBuildingPos = p;
    }
    /**
     * Paints the game state
     * @param g the graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = this.game.getMapDimension();

        for(int i = 0; i < (int)d.getWidth(); i++) {
            for(int j = 0; j < (int)d.getHeight(); j++) {
                g2.drawImage(
                    grass_tile,
                    i*Game.cellSize,
                    j*Game.cellSize,
                    Game.cellSize,
                    Game.cellSize,
                    null
                );
            }
        }
        if(null != pointedCell) {
            g2.setColor(new Color(200, 200, 50, 120));
            g2.fillRect(
                    pointedCell.getX() * Game.cellSize,
                    pointedCell.getY() * Game.cellSize,
                    Game.cellSize,
                    Game.cellSize
            );
        }
        
        //drawing buildings
        Position pos;
        g2.setFont(new Font("", Font.PLAIN, 25));
        
        //first player's castle
        pos = game.getPlayer(0).getCastlePosition();
        g2.setColor(Color.blue);
        g2.fillRect(pos.getX() * Game.cellSize, pos.getY() * Game.cellSize, Game.cellSize, Game.cellSize);
        g2.setColor(Color.black);
        g2.drawString("K", pos.getX() * Game.cellSize + 15, pos.getY() * Game.cellSize + 32);
        //first player's towers
        for(Position p : game.getPlayer(0).getTowerPositions()) {
            g2.setColor(Color.blue);
            g2.fillRect(
                    p.getX() * Game.cellSize,
                    p.getY() * Game.cellSize,
                    Game.cellSize,
                    Game.cellSize
            );
            g2.setColor(Color.black);
            g2.drawString("T", p.getX() * Game.cellSize + 15, p.getY() * Game.cellSize + 32);
        }
        //second player's castle
        pos = game.getPlayer(1).getCastlePosition();
        g2.setColor(Color.red);
        g2.fillRect(pos.getX() * Game.cellSize, pos.getY() * Game.cellSize, Game.cellSize, Game.cellSize);
        g2.setColor(Color.black);
        g2.drawString("K", pos.getX() * Game.cellSize + 15, pos.getY() * Game.cellSize + 32);
        //second player's towers
        for(Position p : game.getPlayer(1).getTowerPositions()) {
            g2.setColor(Color.red);
            g2.fillRect(
                    p.getX() * Game.cellSize,
                    p.getY() * Game.cellSize,
                    Game.cellSize,
                    Game.cellSize
            );
            g2.setColor(Color.black);
            g2.drawString("T", p.getX() * Game.cellSize + 15, p.getY() * Game.cellSize + 32);
        }
        //obstacles
        for(Position p : game.getObstaclePositions()) {
            g2.setColor(new Color(139, 69, 19));
            g2.fillRect(
                    p.getX() * Game.cellSize,
                    p.getY() * Game.cellSize,
                    Game.cellSize,
                    Game.cellSize
            );
        }
        //drawing units
        g2.setColor(Color.BLACK);
        for(Unit u : this.game.getUnits()) {
            g2.fillRect(
                u.getPosition().getX() * Game.cellSize,
                u.getPosition().getY() * Game.cellSize,
                Game.cellSize,
                Game.cellSize
            );
        }
        //outline for selected cell
        if(null != selectedBuildingPos) {
            g2.setColor(Color.YELLOW);
            g2.drawRect(
                    selectedBuildingPos.getX() * Game.cellSize,
                    selectedBuildingPos.getY() * Game.cellSize,
                    Game.cellSize,
                    Game.cellSize
            );
        }
        //tower shot lines
        g2.setColor(Color.BLACK);
        for(TowerShot ts : game.getTowerShots()) {
            if(ts.getUnit().getHp() <= 0) continue;
            g2.drawLine(
                ts.getTower().getPosition().getX() * Game.cellSize + Game.cellSize / 2,
                ts.getTower().getPosition().getY() * Game.cellSize + Game.cellSize / 2,
                ts.getUnit().getPosition().getX() * Game.cellSize + Game.cellSize / 2,
                ts.getUnit().getPosition().getY() * Game.cellSize + Game.cellSize / 2
            );
        }
    }
}
