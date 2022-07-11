package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JPanel;

import model.spell.ActiveSpell;
import model.Game;
import model.Position;
import model.TowerShot;
import model.unit.Unit;
import res.ResourceLoader;

/**
 * Class displaying the game area
 */
public class GameArea extends JPanel{

    public Game game;
    
    public static Image grass_tile;
    public static Image mountain_tile;
    public static Image unit_basic_red;
    public static Image unit_strong_red;
    public static Image red_dragon;
    public static Image red_knight;
    public static Image castle_red;
    public static Image tower_short_red;
    public static Image tower_basic_red;
    public static Image tower_long_red;
    public static Image mine_red;
    public static Image unit_basic_blue;
    public static Image unit_strong_blue;
    public static Image blue_knight;
    public static Image blue_dragon;
    public static Image castle_blue;
    public static Image tower_short_blue;
    public static Image tower_basic_blue;
    public static Image tower_long_blue;
    public static Image mine_blue;
    public static Image unit_strong_black;
    public static Image meteor;
    public static Image freeze;
    public static Image heal;
    public static Image upgrade;
    public static Image demolish;
    
    private Position pointedCell;
    private Position selectedBuildingPos;
    
    private Icon chosenBuildingImage;
    private Point chosenBuildingImagePos;
   
    static {
        try {
            unit_strong_red = ResourceLoader.loadImage("res/redstrong.png");
            red_dragon = ResourceLoader.loadImage("res/redknight.png");
            red_knight = ResourceLoader.loadImage("res/reddragon.png");
            grass_tile = ResourceLoader.loadImage("res/grass_tile.png");
            mountain_tile = ResourceLoader.loadImage("res/mountain.png");
            unit_basic_red = ResourceLoader.loadImage("res/redbasic.png");
            castle_red = ResourceLoader.loadImage("res/castlered.png");
            mine_red = ResourceLoader.loadImage("res/minered.png");
            tower_short_red = ResourceLoader.loadImage("res/towershortred.png");
            tower_basic_red = ResourceLoader.loadImage("res/towerbasicred.png");
            tower_long_red = ResourceLoader.loadImage("res/towerlongred.png");
            unit_basic_blue = ResourceLoader.loadImage("res/bluebasic.png");
            unit_strong_blue = ResourceLoader.loadImage("res/bluestrong.png");
            blue_knight = ResourceLoader.loadImage("res/blueknight.png");
            blue_dragon = ResourceLoader.loadImage("res/bluedragon.png");
            castle_blue = ResourceLoader.loadImage("res/castleblue.png");
            mine_blue = ResourceLoader.loadImage("res/mineblue.png");
            tower_short_blue = ResourceLoader.loadImage("res/towershortblue.png");
            tower_basic_blue = ResourceLoader.loadImage("res/towerbasicblue.png");
            tower_long_blue = ResourceLoader.loadImage("res/towerlongblue.png");
            unit_strong_black = ResourceLoader.loadImage("res/neutralstrong.png");
            meteor = ResourceLoader.loadImage("res/explosion.png");
            freeze = ResourceLoader.loadImage("res/ice.png");
            heal = ResourceLoader.loadImage("res/heal.png");
            
            upgrade = ResourceLoader.loadImage("res/upgrade.png");
            demolish = ResourceLoader.loadImage("res/demolish.png");
        } catch (IOException ex) {
            Logger.getLogger(GameArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public GameArea() {
        
    }
    /**
     * @param game the object storing the game parameters
     */
    public GameArea(Game game) {
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
                (int)d.getHeight()* Game.cellSize,
                (int)d.getWidth() * Game.cellSize)
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
                    j*Game.cellSize,
                    i*Game.cellSize,
                    Game.cellSize,
                    Game.cellSize,
                    null
                );
            }
        }
        if(null != pointedCell) {
            g2.setColor(new Color(200, 200, 50, 120));
            g2.fillRect(
                    pointedCell.getY() * Game.cellSize,
                    pointedCell.getX() * Game.cellSize,
                    Game.cellSize,
                    Game.cellSize
            );
        }
        
        //drawing buildings
        Position pos;
        g2.setFont(new Font("", Font.PLAIN, 25));
        
      //first player's castle
        pos = game.getPlayer(0).getCastlePosition();
        g2.drawImage(
                castle_blue,
                pos.getY() * Game.cellSize,
                pos.getX() * Game.cellSize,
                Game.cellSize,
                Game.cellSize,
                null
            );

        //first player's towers
        for(Position p : game.getPlayer(0).getTowerPositions()) {
            Image toDraw = tower_basic_blue;
            if(game.getTowerAtPos(p).type=='s')
            	toDraw = tower_short_blue;
            else if(game.getTowerAtPos(p).type=='l')
            	toDraw = tower_long_blue;
            g2.drawImage(
                toDraw,
                p.getY() *Game.cellSize,
                p.getX() *Game.cellSize,
                Game.cellSize,
                Game.cellSize,
                null
            );
            if(game.getTowerAtPos(p).isFrozen())
                g2.drawImage(
                    freeze,
                    p.getY() *Game.cellSize,
                    p.getX() *Game.cellSize,
                    Game.cellSize,
                    Game.cellSize,
                    null
                );
        }
        //second player's castle
        pos = game.getPlayer(1).getCastlePosition();
        g2.drawImage(
                castle_red,
                pos.getY() *Game.cellSize,
                pos.getX() *Game.cellSize,
                Game.cellSize,
                Game.cellSize,
                null
            );
        //second player's towers
        for(Position p : game.getPlayer(1).getTowerPositions()) {
        	Image toDraw = tower_basic_red;
            if(game.getTowerAtPos(p).type=='s')
            	toDraw = tower_short_red;
            else if(game.getTowerAtPos(p).type=='l')
            	toDraw = tower_long_red;
            g2.drawImage(
                    toDraw,
                    p.getY() *Game.cellSize,
                    p.getX() *Game.cellSize,
                    Game.cellSize,
                    Game.cellSize,
                    null
                );
            if(game.getTowerAtPos(p).isFrozen())
                g2.drawImage(
                    freeze,
                    p.getY() *Game.cellSize,
                    p.getX() *Game.cellSize,
                    Game.cellSize,
                    Game.cellSize,
                    null
                );
        }
        //obstacles
        for(Position p : game.getObstaclePositions()) {
        	g2.drawImage(
                    mountain_tile,
                    p.getY() * Game.cellSize,
                    p.getX() * Game.cellSize,
                    Game.cellSize,
                    Game.cellSize,
                    null
                );
        }
        //drawing units
        for(Unit u : this.game.getUnits()) {
        	Image toDraw = unit_strong_black;
            if(u.owner == game.getPlayer(0)) {
            	if(u.type == 'o')
            		toDraw = blue_dragon;
            	else if(u.type == 's')
            		toDraw = unit_strong_blue;
            	else if(u.type == 'f')
            		toDraw = blue_knight;
            	else
            		toDraw = unit_basic_blue;
            }
            else if(u.owner == game.getPlayer(1)) {
            	if(u.type == 'o')
            		toDraw = red_knight;
            	else if(u.type == 's')
            		toDraw = unit_strong_red;
            	else if(u.type == 'f')
            		toDraw = red_dragon;
            	else
            		toDraw = unit_basic_red;
            }
        	g2.drawImage(
                    toDraw,
                    u.getPosition().getY() * Game.cellSize,
                    u.getPosition().getX() * Game.cellSize,
                    Game.cellSize,
                    Game.cellSize,
                    null
                );
                g2.setColor(Color.red);
                g2.fillRect(
                    u.getPosition().getY() * Game.cellSize,
                    u.getPosition().getX() * Game.cellSize - 10,
                    (int) (Game.cellSize * ((double)u.getHp() / u.getMaxHp())),
                    5
                );
                g2.setColor(Color.black);
                g2.drawRect(
                    u.getPosition().getY() * Game.cellSize,
                    u.getPosition().getX() * Game.cellSize - 10,
                    Game.cellSize,
                    5
                );
        }
        //goldmines
        for(Position p : game.getGoldminePositions()) {
            Image toDraw;
            if(game.getGoldmineAtPos(p).getOwner()== game.getPlayer(0))
                toDraw = mine_blue;
            else
                toDraw = mine_red;
            g2.drawImage(
                    toDraw,
                    p.getY() * Game.cellSize,
                    p.getX() * Game.cellSize,
                    Game.cellSize,
                    Game.cellSize,
                    null
                );
        }
        //Spell effects
        for(ActiveSpell as : game.activeSpells) {
            if(as.type == 'f')
                g2.drawImage(
                    freeze,
                    as.pos.getY() * Game.cellSize,
                    as.pos.getX() * Game.cellSize,
                    Game.cellSize,
                    Game.cellSize,
                    null
                );
        	else if(as.type == 'h')
                    g2.drawImage(
                        heal,
                        as.pos.getY() * Game.cellSize,
                        as.pos.getX() * Game.cellSize,
                        Game.cellSize,
                        Game.cellSize,
                        null
                    );
        	else if(as.type == 'm')
                    g2.drawImage(
                        meteor,
                        as.pos.getY() * Game.cellSize,
                        as.pos.getX() * Game.cellSize,
                        Game.cellSize*3,
                        Game.cellSize*3,
                        null
                    );
        }
        
        //outline for selected cell
        if(null != selectedBuildingPos) {
            g2.setColor(Color.YELLOW);
            g2.drawRect(
                selectedBuildingPos.getY() * Game.cellSize,
                selectedBuildingPos.getX() * Game.cellSize,
                Game.cellSize,
                Game.cellSize
            );
        }
        //tower shot lines
        g2.setColor(Color.BLACK);
        for(TowerShot ts : game.getTowerShots()) {
            if(ts.getUnit().getHp() <= 0) continue;
            g2.drawLine(
                ts.getTower().getPosition().getY() * Game.cellSize + Game.cellSize / 2,
                ts.getTower().getPosition().getX() * Game.cellSize + Game.cellSize / 2,
                ts.getUnit().getPosition().getY() * Game.cellSize + Game.cellSize / 2,
                ts.getUnit().getPosition().getX() * Game.cellSize + Game.cellSize / 2
            );
        }
        if(null != chosenBuildingImage) {
            chosenBuildingImage.paintIcon(this, g2, chosenBuildingImagePos.x - 25, chosenBuildingImagePos.y - 25);
        }
    }

    void setChosenBuildingImagePos(Point point) {
        chosenBuildingImagePos = point;
    }

    void setChosenBuildingImage(Icon chosenBuildingImage) {
        this.chosenBuildingImage = chosenBuildingImage;
    }
}
