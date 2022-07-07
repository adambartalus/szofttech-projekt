package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.FastUnit;
import model.Game;
import model.NotEnoughGoldException;
import model.ObstacleUnit;
import model.StrongUnit;

/**
 *
 * @author Adam
 */
public class CastlePanel extends JPanel {
    private final GameView gameView;
    
    private final JPanel unitSpawnPanel;
    private final JButton sUnit;
    private final JButton fUnit;
    private final JButton oUnit;
    
    public JPanel healthBar;
    private final JLabel hpLabel;
    
    
    public CastlePanel(GameView gv) {
        gameView = gv;
        healthBar = new JPanel();
        FlowLayout l = (FlowLayout) healthBar.getLayout();
        l.setAlignment(FlowLayout.LEFT);
        l.setVgap(0);
        l.setHgap(0);
        unitSpawnPanel = new JPanel();
        
        hpLabel = new JLabel("valami");
        hpLabel.setBackground(Color.red);
        hpLabel.setOpaque(true);
        healthBar.add(hpLabel);
        healthBar.setBorder(BorderFactory.createLineBorder(Color.black));
                
        /**
         * Button for spawning strong unit
         */
        sUnit = new JButton(
            "(" + StrongUnit.COST + "g)",
            new ImageIcon(GameArea.unit_strong_red.getScaledInstance(50, 50, 0))    
        );
        sUnit.setMargin(new Insets(0, 0, 0, 0));
        sUnit.setVerticalTextPosition(SwingConstants.BOTTOM);
        sUnit.setHorizontalTextPosition(SwingConstants.CENTER);
        /**
         * Button for spawning fast unit
         */
        fUnit = new JButton(
            "(" + FastUnit.COST + "g)",
            new ImageIcon(GameArea.unit_fast_red.getScaledInstance(50, 50, 0)) 
        );
        fUnit.setMargin(new Insets(0, 0, 0, 0));
        fUnit.setVerticalTextPosition(SwingConstants.BOTTOM);
        fUnit.setHorizontalTextPosition(SwingConstants.CENTER);
        
        /**
         * Button for spawning obstacle unit
         */
        oUnit = new JButton(
                "(" + ObstacleUnit.COST + "g)",
                new ImageIcon(GameArea.unit_flying_red.getScaledInstance(50, 50, 0))
        );
        oUnit.setMargin(new Insets(0, 0, 0, 0));
        oUnit.setVerticalTextPosition(SwingConstants.BOTTOM);
        oUnit.setHorizontalTextPosition(SwingConstants.CENTER);
        
        unitSpawnPanel.add(sUnit);
        unitSpawnPanel.add(fUnit);
        unitSpawnPanel.add(oUnit);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(healthBar);
        add(unitSpawnPanel);
    }
    public void setUpGame(Game game) {
        sUnit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                   game.addUnit(new StrongUnit(
                           game.getActivePlayer().getCastlePosition(),
                           game.getOpponent().getCastlePosition()
                        )
                   ); 
                } catch(NotEnoughGoldException exc) {
                    gameView.displayErrorMessage("You don't have enough gold!");
                } catch(Exception exc) {
                    gameView.displayErrorMessage(exc.getMessage());
                }
            }
        });
        oUnit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.addUnit(new ObstacleUnit(
                            game.getActivePlayer().getCastlePosition(),
                            game.getOpponent().getCastlePosition()
                        )
                    );
                } catch(NotEnoughGoldException exc) {
                    gameView.displayErrorMessage("You don't have enough gold!");
                } catch(Exception exc) {
                    gameView.displayErrorMessage("Error");
                }
            }
        });
        fUnit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    game.addUnit(new FastUnit(
                            game.getActivePlayer().getCastlePosition(),
                            game.getOpponent().getCastlePosition()
                        )
                    );
                } catch(NotEnoughGoldException exc) {
                    gameView.displayErrorMessage("You don't have enough gold!");
                } catch(Exception exc) {
                    gameView.displayErrorMessage("Error");
                }
            }
        });
    }
    public void updateHealthBar(int hp, int maxHP) {
        int perc = hp * 100 / maxHP;
        hpLabel.setText(String.valueOf(perc));
        hpLabel.setPreferredSize(new Dimension(
                healthBar.getSize().width * perc / 100,
                20
        ));
    }
}
