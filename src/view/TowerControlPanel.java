package view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.Game;
import model.NotEnoughGoldException;
import model.Tower;


public class TowerControlPanel extends JPanel {
    
    private final TowerControlButton upgradeButton;
    private final TowerControlButton demolishButton;
    private final JButton cancelButton;
    
    private final GameView gameView;
    
    public TowerControlPanel(GameView gv) {
        this.gameView = gv;
        upgradeButton = new TowerControlButton("Upgrade");
        upgradeButton.setMaximumSize(new Dimension(400, 30));
        
        demolishButton = new TowerControlButton("Demolish");
        demolishButton.setMaximumSize(new Dimension(400, 30));
        
        cancelButton = new JButton("X");
        cancelButton.setMaximumSize(new Dimension(400, 30));
        
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        add(upgradeButton);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(demolishButton);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(cancelButton);
        
        setBounds(200, 200, getPreferredSize().width, getPreferredSize().height);
        
        setVisible(false);
    }
    public void setTower(Tower t) {
        upgradeButton.setTower(t);
        demolishButton.setTower(t);
    }
    public void initGame(Game game) {
        upgradeButton.addActionListener((ActionEvent e) -> {
            try {
                game.upgradeTower(upgradeButton.getTower());
            } catch (NotEnoughGoldException ex) {
                gameView.displayErrorMessage("You don't have enough gold!");
            }
        });
        demolishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.demolishTower(demolishButton.getTower());
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TowerControlPanel.this.setVisible(false);
            }
        });
    }
}
