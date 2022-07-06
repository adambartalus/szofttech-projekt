package tower;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BasicTower;
import model.Game;
import model.Position;
import model.StrongUnit;
import model.Unit;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class TowerTest {
    
    @Test
    public void towerShot() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new StrongUnit(new Position(3,3), game);
        try {
            game.addUnit(testunit);
        } catch (Exception ex) {
            
        }
        testunit.owner = game.getPlayer(0);
        BasicTower testTower = new BasicTower(new Position (4,4),game.getPlayer(1));
        testTower.turn(game);
        assertEquals(testunit.getHp(), 430);
        
        testTower.upgrade();
        testTower.turn(game);
        assertEquals(testunit.getHp(), 350);
    }
    @Test
    public void towerRange() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new StrongUnit(new Position(3,3), game);
        try {
            game.addUnit(testunit);
        } catch (Exception ex) {
            
        }
        testunit.owner = game.getPlayer(0);
        
        BasicTower testTower = new BasicTower(new Position (6,6),game.getPlayer(1));
        game.addTower(testTower);
        testTower.turn(game);
        assertEquals(testunit.getHp(), 500); // out of range
        
        testTower.upgrade();
        testTower.turn(game);
        assertEquals(testunit.getHp(), 420);
    }
}
