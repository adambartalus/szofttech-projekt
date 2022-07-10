package view;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Game;
import model.Tower;


public class TowerPanel extends JPanel {
    
    private final TowerStatPanel towerStatPanel;
    private final TowerControlPanel towerControlPanel;
    
    public TowerPanel(GameView gv) {
        towerStatPanel = new TowerStatPanel();
        towerControlPanel = new TowerControlPanel(gv);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(towerStatPanel);
        add(towerControlPanel);
        
        setVisible(false);
    }
    public void initGame(Game game) {
        towerControlPanel.initGame(game);
    }
    public void update(Tower tower) {
        towerStatPanel.updateLabels(tower);
        towerControlPanel.update(tower);
    }
    
}
