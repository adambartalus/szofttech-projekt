package model;

import model.tower.Tower;
import model.unit.Unit;

/**
 * Class for storing a tower shot as a (tower, unit) pair
 */
public class TowerShot {
    private final Tower tower;
    private final Unit unit;

    public TowerShot(Tower tower, Unit unit) {
        this.tower = tower;
        this.unit = unit;
    }

    public Tower getTower() {
        return tower;
    }

    public Unit getUnit() {
        return unit;
    }
    
}
