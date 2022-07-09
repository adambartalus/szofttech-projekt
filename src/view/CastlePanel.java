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
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Castle;
import model.Knight;
import model.Game;
import model.NotEnoughGoldException;
import model.Dragon;
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
    private final JLabel hpPercentageLabel;
    private final JLabel hpLabel;
    
    
    public CastlePanel(GameView gv) {
        gameView = gv;
        
        healthBar = new JPanel();
        FlowLayout l = (FlowLayout) healthBar.getLayout();
        l.setAlignment(FlowLayout.LEFT);
        l.setVgap(0);
        l.setHgap(0);
        unitSpawnPanel = new JPanel();
        unitSpawnPanel.setBorder(null);
        
        hpPercentageLabel = new JLabel("Hp");
        hpPercentageLabel.setAlignmentX(SwingConstants.LEFT);
        
        hpLabel = new JLabel("valami");
        hpLabel.setBackground(Color.red);
        hpLabel.setOpaque(true);
        
        healthBar.add(hpLabel);
        healthBar.setBorder(BorderFactory.createLineBorder(Color.black));
                
        /**
         * Button for spawning strong unit
         */
        sUnit = new JButton(
            StrongUnit.COST + "g",
            new ImageIcon(GameArea.unit_strong_red.getScaledInstance(50, 50, 0))    
        );
        sUnit.setMargin(new Insets(0, 0, 0, 0));
        sUnit.setVerticalTextPosition(SwingConstants.BOTTOM);
        sUnit.setHorizontalTextPosition(SwingConstants.CENTER);
        /**
         * Button for spawning fast unit
         */
        fUnit = new JButton(
            Knight.COST + "g",
            new ImageIcon(GameArea.red_dragon.getScaledInstance(50, 50, 0)) 
        );
        fUnit.setMargin(new Insets(0, 0, 0, 0));
        fUnit.setVerticalTextPosition(SwingConstants.BOTTOM);
        fUnit.setHorizontalTextPosition(SwingConstants.CENTER);
        
        /**
         * Button for spawning obstacle unit
         */
        oUnit = new JButton(
                Dragon.COST + "g",
                new ImageIcon(GameArea.red_knight.getScaledInstance(50, 50, 0))
        );
        oUnit.setMargin(new Insets(0, 0, 0, 0));
        oUnit.setVerticalTextPosition(SwingConstants.BOTTOM);
        oUnit.setHorizontalTextPosition(SwingConstants.CENTER);
        
        JLayeredPane sUnitPanel = new JLayeredPane();
        sUnitPanel.setPreferredSize(sUnit.getPreferredSize());
        sUnit.setBounds(0, 0, sUnit.getPreferredSize().width, sUnit.getPreferredSize().height);
        
        JButton infoButton = new JButton("i");
        infoButton.setMargin(new Insets(0, 4, 0, 4));
        infoButton.setBounds(
            sUnit.getPreferredSize().width - infoButton.getPreferredSize().width,
            0,
            infoButton.getPreferredSize().width,
            infoButton.getPreferredSize().height
        );
        sUnitPanel.add(sUnit, new Integer(0));
        sUnitPanel.add(infoButton, new Integer(1));
        
        unitSpawnPanel.add(sUnitPanel);
        unitSpawnPanel.add(fUnit);
        unitSpawnPanel.add(oUnit);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(hpPercentageLabel);
        add(healthBar);
        add(unitSpawnPanel);
        
        setVisible(false);
    }
    public void initGame(Game game) {
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
                    game.addUnit(new Dragon(
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
                    game.addUnit(new Knight(
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
    public void updateHealthBar(int hp) {
        int perc = hp * 100 / Castle.START_HP;
        Dimension d = new Dimension(
                healthBar.getSize().width * perc / 100,
                20
        );
        hpLabel.setText(hp + "/" + Castle.START_HP);
        hpLabel.setPreferredSize(d);
    }
}
