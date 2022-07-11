package view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import model.Game;
import model.tower.Tower;


public class TowerPanel extends JPanel {
    
    private final TowerStatPanel towerStatPanel;
    private final TowerControlPanel towerControlPanel;
    
    public TowerPanel(GameView gv) {
        towerStatPanel = new TowerStatPanel();
        towerControlPanel = new TowerControlPanel(gv, this);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel vmi = new JPanel();
        vmi.setLayout(new GridBagLayout());
        vmi.add(towerControlPanel);
        
        add(towerStatPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(vmi);
        
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
