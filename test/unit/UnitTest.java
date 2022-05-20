package unit;

import java.awt.Dimension;
import org.junit.Test;

import model.Game;
import model.Position;
import model.StrongUnit;
import model.Unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnitTest {
    
    @Test
    public void unitMovement() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new StrongUnit(new Position(3,3), game);
        game.addUnit(testunit);
        testunit.owner = game.getPlayer(0);
        testunit.findPath(new Position(3,8));
        testunit.step(game);
        assertTrue(testunit.getPosition().equals(new Position(3,4)));
    }
    @Test
    public void unitDamage() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new StrongUnit(new Position(3,3), game);
        testunit.owner = game.getPlayer(0);
        testunit.takeDamage(100);
        assertEquals(testunit.getHp(), 400);
    }
    @Test
    public void deadUnit() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new StrongUnit(new Position(3,3), game);
        game.addUnit(testunit);
        testunit.owner = game.getPlayer(0);
        
        assertTrue(game.getUnits().contains(testunit));
        testunit.takeDamage(600);
        assertFalse(game.getUnits().contains(testunit));
    }
}
