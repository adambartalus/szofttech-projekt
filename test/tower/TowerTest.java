package tower;

import java.awt.Dimension;
import model.tower.BasicTower;
import model.Game;
import model.Position;
import model.unit.StrongUnit;
import model.unit.Unit;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class TowerTest {
    
    @Test
    public void towerShot() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new StrongUnit(new Position(3,3), null);
        try {
            game.addUnit(testunit);
        } catch (Exception ex) {
            
        }
        testunit.owner = game.getPlayer(0);
        BasicTower testTower = new BasicTower(new Position (4,4),game.getPlayer(1));
        testTower.turn(game);
        assertEquals(430, testunit.getHp());
        
        testTower.upgradeEffect();
        testTower.turn(game);
        assertEquals(350, testunit.getHp());
    }
    @Test
    public void towerRange() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new StrongUnit(new Position(3,3), null);
        game.getUnits().add(testunit);
        testunit.owner = game.getPlayer(0);
        
        BasicTower testTower = new BasicTower(new Position (6,6),game.getPlayer(1));
        game.addTower(testTower);
        testTower.turn(game);
        assertEquals(2, testTower.getRange());
        assertEquals(500, testunit.getHp()); // out of range
        
        testTower.upgradeEffect();
        assertEquals(3, testTower.getRange());
        testTower.turn(game);
        assertEquals(420, testunit.getHp());
    }
}
