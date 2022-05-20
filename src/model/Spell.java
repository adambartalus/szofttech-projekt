package model;

public interface Spell {
	public static int cost = 100;
	public abstract void Effect(Position pos, Game game, Player player);
}
