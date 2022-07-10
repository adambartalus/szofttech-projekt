package model;

public abstract class Spell implements Buyable {

    public abstract void Effect(Position pos, Game game, Player player);
    
}
