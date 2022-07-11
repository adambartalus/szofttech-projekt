package model.spell;

import model.Position;

public class ActiveSpell {
	public char type;
	public Position pos;
	public ActiveSpell(char t, Position p) {
		type = t;
		pos = p;
	}
	
}
