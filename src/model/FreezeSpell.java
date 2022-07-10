package model;

public class FreezeSpell extends Spell{
    public static int COST = 100;
    public void Effect(Position pos, Game game, Player player) {
            if(null != game.getTowerAtPos(pos) && game.getTowerAtPos(pos).getOwner() != player)
            {
                game.getTowerAtPos(pos).setFrozen(true);
            }
            game.activeSpells.add(new ActiveSpell('f',pos));
    }
    public int getCost() {
        return COST;
    }
}
