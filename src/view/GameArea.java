package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

import model.ActiveSpell;
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
    private Image mountain_tile;
    private Image unit_basic_red;
    private Image unit_strong_red;
    private Image unit_fast_red;
    private Image unit_flying_red;
    private Image castle_red;
    private Image tower_short_red;
    private Image tower_basic_red;
    private Image tower_long_red;
    private Image mine_red;
    private Image unit_basic_blue;
    private Image unit_strong_blue;
    private Image unit_fast_blue;
    private Image unit_flying_blue;
    private Image castle_blue;
    private Image tower_short_blue;
    private Image tower_basic_blue;
    private Image tower_long_blue;
    private Image mine_blue;
    private Image unit_strong_black;
    private Image meteor;
    private Image freeze;
    private Image heal;
    private Position pointedCell;
    private Position selectedBuildingPos;
   
    public GameArea() {
        try {
            grass_tile = ResourceLoader.loadImage("res/grass_tile.png");
            mountain_tile = ResourceLoader.loadImage("res/mountain.png");
            unit_basic_red = ResourceLoader.loadImage("res/redbasic.png");
            unit_strong_red = ResourceLoader.loadImage("res/redstrong.png");
            unit_fast_red = ResourceLoader.loadImage("res/redknight.png");
            unit_flying_red = ResourceLoader.loadImage("res/reddragon.png");
            castle_red = ResourceLoader.loadImage("res/castlered.png");
            mine_red = ResourceLoader.loadImage("res/minered.png");
            tower_short_red = ResourceLoader.loadImage("res/towershortred.png");
            tower_basic_red = ResourceLoader.loadImage("res/towerbasicred.png");
            tower_long_red = ResourceLoader.loadImage("res/towerlongred.png");
            unit_basic_blue = ResourceLoader.loadImage("res/bluebasic.png");
            unit_strong_blue = ResourceLoader.loadImage("res/bluestrong.png");
            unit_fast_blue = ResourceLoader.loadImage("res/blueknight.png");
            unit_flying_blue = ResourceLoader.loadImage("res/bluedragon.png");
            castle_blue = ResourceLoader.loadImage("res/castleblue.png");
            mine_blue = ResourceLoader.loadImage("res/mineblue.png");
            tower_short_blue = ResourceLoader.loadImage("res/towershortblue.png");
            tower_basic_blue = ResourceLoader.loadImage("res/towerbasicblue.png");
            tower_long_blue = ResourceLoader.loadImage("res/towerlongblue.png");
            unit_strong_black = ResourceLoader.loadImage("res/neutralstrong.png");
            meteor = ResourceLoader.loadImage("res/explosion.png");
            freeze = ResourceLoader.loadImage("res/ice.png");
            heal = ResourceLoader.loadImage("res/heal.png");
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
        g2.drawImage(
                castle_blue,
                pos.getX() * Game.cellSize,
                pos.getY() * Game.cellSize,
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
                    p.getX() *Game.cellSize,
                    p.getY() *Game.cellSize,
                    Game.cellSize,
                    Game.cellSize,
                    null
                );
            if(game.getTowerAtPos(p).freeze)
	            g2.drawImage(
	                    freeze,
	                    p.getX() *Game.cellSize,
	                    p.getY() *Game.cellSize,
	                    Game.cellSize,
	                    Game.cellSize,
	                    null
	                );
        }
        //second player's castle
        pos = game.getPlayer(1).getCastlePosition();
        g2.drawImage(
                castle_red,
                pos.getX() *Game.cellSize,
                pos.getY() *Game.cellSize,
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
                    p.getX() *Game.cellSize,
                    p.getY() *Game.cellSize,
                    Game.cellSize,
                    Game.cellSize,
                    null
                );
            if(game.getTowerAtPos(p).freeze)
	            g2.drawImage(
	                    freeze,
	                    p.getX() *Game.cellSize,
	                    p.getY() *Game.cellSize,
	                    Game.cellSize,
	                    Game.cellSize,
	                    null
	                );
        }
        //obstacles
        for(Position p : game.getObstaclePositions()) {
        	g2.drawImage(
                    mountain_tile,
                    p.getX() * Game.cellSize,
                    p.getY() * Game.cellSize,
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
            		toDraw = unit_flying_blue;
            	else if(u.type == 's')
            		toDraw = unit_strong_blue;
            	else if(u.type == 'f')
            		toDraw = unit_fast_blue;
            	else
            		toDraw = unit_basic_blue;
            }
            else if(u.owner == game.getPlayer(1)) {
            	if(u.type == 'o')
            		toDraw = unit_flying_red;
            	else if(u.type == 's')
            		toDraw = unit_strong_red;
            	else if(u.type == 'f')
            		toDraw = unit_fast_red;
            	else
            		toDraw = unit_basic_red;
            }
            	
        	g2.drawImage(
                    toDraw,
                    u.getPosition().getX() *Game.cellSize,
                    u.getPosition().getY() *Game.cellSize,
                    Game.cellSize,
                    Game.cellSize,
                    null
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
                    p.getX() * Game.cellSize,
                    p.getY() * Game.cellSize,
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
	                    as.pos.getX() * Game.cellSize,
	                    as.pos.getY() * Game.cellSize,
	                    Game.cellSize,
	                    Game.cellSize,
	                    null
	                );
        	else if(as.type == 'h')
	        	g2.drawImage(
	                    heal,
	                    as.pos.getX() * Game.cellSize,
	                    as.pos.getY() * Game.cellSize,
	                    Game.cellSize,
	                    Game.cellSize,
	                    null
	                );
        	else if(as.type == 'm')
	        	g2.drawImage(
	                    meteor,
	                    as.pos.getX() * Game.cellSize,
	                    as.pos.getY() * Game.cellSize,
	                    Game.cellSize*3,
	                    Game.cellSize*3,
	                    null
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
