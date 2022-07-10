package model;

import java.util.ArrayList;

public class MeteorSpell extends Spell {
    public static int COST = 500;
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

        for(int i = 0; i < positions.size(); i++) {
            ArrayList<Unit> unitsAtPosI = game.getUnitsAtPos(positions.get(i));
            for(int j = 0; j < unitsAtPosI.size(); j++) {
                if(unitsAtPosI.get(j).owner != player) {
                    unitsAtPosI.get(j).takeDamage(300);
                }
            }
        }
        game.activeSpells.add(new ActiveSpell('m',pos));
    }
    public int getCost() {
        return COST;
    }
}
