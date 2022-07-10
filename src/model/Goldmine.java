package model;

public class Goldmine extends Building implements Buyable {
    public static final int COST = 1500;
    private int yield = 200;
    
    public Goldmine(Position pos, Player player) {
        super(pos, player);
    }
    public void turn() {
        this.owner.increaseGold(yield);
    }

    @Override
    public int getCost() {
        return COST;
    }
}
