package model;

import java.util.ArrayList;

public class HealSpell implements Spell {
	public static int cost = 100;
	public void Effect(Position pos, Game game, Player player) {
		for(int j = 0; j < game.getUnitsAtPos(pos).size(); j++)
			if(game.getUnitsAtPos(pos).get(j).owner == player)
				game.getUnitsAtPos(pos).get(j).heal(500);
		game.activeSpells.add(new ActiveSpell('h',pos));
	}
}
