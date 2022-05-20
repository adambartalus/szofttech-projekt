import org.junit.Test;

import model.Unit;

import static org.junit.Assert.assertTrue;

public class BasicTest {
    
    @Test
    public void testMethod() {
        assertTrue(true);
    }
    
    Dimension d = new Dimension (10,15);
    Game game = new Game(d, "Player1", "Player2");
    Unit testunit = new Unit(new Position(3,3),1,1000);
    testunit.findpath(new Position(3,8));
    testunit.step();
    assertTrue(testunit.GetPosition().equals(new Position(3,4)));
    testunit.takeDamage(100);
    assertTrue(testunit.getHp() == 900);
}
