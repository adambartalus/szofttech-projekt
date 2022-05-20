package spell;

import java.awt.Dimension;
import model.BasicTower;
import model.FreezeSpell;
import model.Game;
import model.HealSpell;
import model.MeteorSpell;
import model.Position;
import model.StrongUnit;
import model.Unit;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SpellTest {
    
    @Test
    public void meteorSpell() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new StrongUnit(new Position(3,3)); // 500 hp
        testunit.owner = game.getPlayer(0);
        
        MeteorSpell testspell1 = new MeteorSpell();
        testspell1.Effect(new Position(2,3), game, game.getPlayer(1));
        assertEquals(testunit.getHp(), 0);
    }
    @Test
    public void healSpell() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new StrongUnit(new Position(3,3)); // 500 hp
        testunit.owner = game.getPlayer(0);
        testunit.takeDamage(150);
        
        HealSpell testspell2 = new HealSpell();
        testspell2.Effect(new Position(3,3), game, game.getPlayer(0));
        assertEquals(testunit.getHp(), 500);
    }
    @Test
    public void freezeSpell() {
        Dimension d = new Dimension (10,15);
        Game game = new Game(d, "Player1", "Player2");
        Unit testunit = new StrongUnit(new Position(3,3)); // 500 hp
        testunit.owner = game.getPlayer(0);
        BasicTower testTower = new BasicTower(new Position (4,4),game.getPlayer(1));
        
        FreezeSpell testspell3 = new FreezeSpell();
        testspell3.Effect(new Position(4,4), game, game.getPlayer(0));
        testTower.turn(game);
        assertEquals(testunit.getHp(), 500);
    }
}
