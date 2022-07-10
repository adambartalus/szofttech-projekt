package view;

import javax.swing.Icon;
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
    public TowerControlButton(String text, Icon icon) {
        super(text, icon);
    }
    public Tower getTower() {
        return tower;
    }
    public void setTower(Tower t) {
        tower = t;
    }
}
