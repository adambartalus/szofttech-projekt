package model;

public class FreezeSpell implements Spell{
	public static int cost = 100;
	public void Effect(Position pos, Game game, Player player) {
		if(null != game.getTowerAtPos(pos) && game.getTowerAtPos(pos).getOwner() != player)
		{
                    game.getTowerAtPos(pos).freeze = true;
		}
		game.activeSpells.add(new ActiveSpell('f',pos));
	}
	
}
