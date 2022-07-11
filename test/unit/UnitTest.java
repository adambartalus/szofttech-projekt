package unit;

import java.awt.Dimension;
import org.junit.Test;
import model.Game;
import model.unit.Dragon;
import model.Position;
import model.unit.StrongUnit;
import model.unit.Unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnitTest {
    
    @Test
    public void unitMovement() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new Dragon(new Position(3,3), new Position(3,8));
        try {
            game.addUnit(testunit);
        } catch (Exception ex) {
            
        }
        testunit.owner = game.getPlayer(0);
        testunit.step(new int[10][15]);
        assertTrue(testunit.getPosition().equals(new Position(3,4)));
    }
    @Test
    public void unitDamage() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new StrongUnit(new Position(3,3), null);
        testunit.owner = game.getPlayer(0);
        testunit.takeDamage(100);
        assertEquals(400, testunit.getHp());
    }
    @Test
    public void deadUnit() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new StrongUnit(new Position(3,3), null);
        try {
            game.addUnit(testunit);
        } catch (Exception ex) {
            
        }
        testunit.owner = game.getPlayer(0);
        
        assertTrue(game.getUnits().contains(testunit));
        testunit.takeDamage(600);
        assertTrue(testunit.isDead());
    }
}
