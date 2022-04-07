package view;

import javax.swing.JButton;
import model.Tower;


public class TowerControlButton extends JButton {
    private Tower tower;
    
    public TowerControlButton() {
        super();
    }
    public TowerControlButton(String text) {
        super(text);
    }
    public Tower getTower() {
        return tower;
    }
    public void setTower(Tower t) {
        tower = t;
    }
}
