package model;

import java.util.ArrayList;

public class MeteorSpell implements Spell {
	public static int cost = 500;
	public void Effect(Position pos, Game game, Player player) {
		ArrayList<Position> positions = new ArrayList<Position>();
		positions.add(pos);
		positions.add(new Position(pos.getX()+1,pos.getY()+0));
		positions.add(new Position(pos.getX()+2,pos.getY()+0));
		positions.add(new Position(pos.getX()+0,pos.getY()+1));
		positions.add(new Position(pos.getX()+1,pos.getY()+1));
		positions.add(new Position(pos.getX()+2,pos.getY()+1));
		positions.add(new Position(pos.getX()+0,pos.getY()+2));
		positions.add(new Position(pos.getX()+1,pos.getY()+2));
		positions.add(new Position(pos.getX()+2,pos.getY()+2));
		
		for(int i = 0; i < positions.size(); i++)
			for(int j = 0; j < game.getUnitsAtPos(positions.get(i)).size(); j++)
				if(game.getUnitsAtPos(positions.get(i)).get(j).owner != player)
					game.getUnitsAtPos(positions.get(i)).get(j).takeDamage(500);
		game.activeSpells.add(new ActiveSpell('m',pos));
	}

}
