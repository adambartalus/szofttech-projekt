package model.spell;

import model.Buyable;
import model.Game;
import model.Player;
import model.Position;

public abstract class Spell implements Buyable {

    public abstract void Effect(Position pos, Game game, Player player);
    
}
