package view;

import exception.MaxLevelReachedException;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Game;
import exception.NotEnoughGoldException;
import model.tower.Tower;


public class TowerControlPanel extends JPanel {
    
    private final TowerPanel towerPanel;
    
    private final TowerControlButton upgradeButton;
    private final TowerControlButton demolishButton;
    
    private final GameView gameView;
    
    public TowerControlPanel(GameView gv, TowerPanel tp) {
        this.towerPanel = tp;
        this.gameView = gv;
        upgradeButton = new TowerControlButton(
                "Upgrade",
                new ImageIcon(GameArea.upgrade.getScaledInstance(50, 50, 0))
        );
        upgradeButton.setMargin(new Insets(0, 0, 0, 0));
        upgradeButton.setHorizontalTextPosition(SwingConstants.CENTER);
        upgradeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        
        demolishButton = new TowerControlButton(
            "Demolish",
            new ImageIcon(GameArea.demolish.getScaledInstance(50, 50, 0))
        );
        demolishButton.setMargin(new Insets(0, 0, 0, 0));
        demolishButton.setHorizontalTextPosition(SwingConstants.CENTER);
        demolishButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        
        
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(SwingConstants.CENTER);
        
        add(upgradeButton);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(demolishButton);
    }
    public void update(Tower t) {
        upgradeButton.setTower(t);
        upgradeButton.setText(t.getUpgradeCost() + "g");
        demolishButton.setTower(t);
        demolishButton.setText(String.format("+%d g", t.getGoldReturnOnDemolish()));
    }
    public void initGame(Game game) {
        upgradeButton.addActionListener((ActionEvent e) -> {
            try {
                game.upgradeTower(upgradeButton.getTower());
                towerPanel.update(upgradeButton.getTower());
            } catch (NotEnoughGoldException ex) {
                gameView.displayErrorMessage("You don't have enough gold!");
            } catch(MaxLevelReachedException exc) {
                gameView.displayErrorMessage("The building has reached max level!");
            }
        });
        demolishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.demolishTower(demolishButton.getTower());
                towerPanel.setVisible(false);
            }
        });
    }
}
