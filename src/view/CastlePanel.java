package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Castle;
import model.unit.Knight;
import model.Game;
import exception.NotEnoughGoldException;
import model.unit.Dragon;
import model.unit.StrongUnit;


public class CastlePanel extends JPanel {
    private final GameView gameView;
    
    private final JPanel unitSpawnPanel;
    private final JButton sUnit;
    private final JButton fUnit;
    private final JButton oUnit;
    
    public JLayeredPane healthBar;
    private final JLabel hpPercentageLabel;
    private final JLabel hpLabel;
    private final JLabel hpLabelText;
    
    private final Icon blueStrongIcon;
    private final Icon blueKnightIcon;
    private final Icon blueDragonIcon;
    private final Icon redStrongIcon;
    private final Icon redKnightIcon;
    private final Icon redDragonIcon;
    
    private boolean blue = true;
    public CastlePanel(GameView gv) {
        
        blueStrongIcon = new ImageIcon(GameArea.unit_strong_blue.getScaledInstance(50, 50, 0));
        blueKnightIcon = new ImageIcon(GameArea.blue_knight.getScaledInstance(50, 50, 0));
        blueDragonIcon = new ImageIcon(GameArea.blue_dragon.getScaledInstance(50, 50, 0));
        redStrongIcon = new ImageIcon(GameArea.unit_strong_red.getScaledInstance(50, 50, 0));
        redKnightIcon = new ImageIcon(GameArea.red_knight.getScaledInstance(50, 50, 0));
        redDragonIcon = new ImageIcon(GameArea.red_dragon.getScaledInstance(50, 50, 0));
        
        gameView = gv;
        
        healthBar = new JLayeredPane();
        healthBar.setPreferredSize(new Dimension(0, 30));
        unitSpawnPanel = new JPanel();
        unitSpawnPanel.setBorder(null);
        
        hpPercentageLabel = new JLabel("Hp");
        hpPercentageLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        hpPercentageLabel.setAlignmentX(SwingConstants.LEFT);
        
        hpLabel = new JLabel();
        hpLabel.setBounds(2, 2, healthBar.getPreferredSize().width - 4, 26);
        hpLabel.setBackground(Color.red);
        hpLabel.setOpaque(true);
        
        hpLabelText = new JLabel();
        hpLabelText.setBounds(65, 5, 100, 20);
        
        healthBar.add(hpLabel, new Integer(0));
        healthBar.add(hpLabelText, new Integer(1));
        healthBar.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        
        JLabel unitLabel = new JLabel("Spawn units");
        unitLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        unitLabel.setAlignmentX(SwingConstants.LEFT);
        /**
         * Button for spawning strong unit
         */
        sUnit = new JButton(
            StrongUnit.COST + "g",
            blueStrongIcon   
        );
        sUnit.setMargin(new Insets(0, 0, 0, 0));
        sUnit.setVerticalTextPosition(SwingConstants.BOTTOM);
        sUnit.setHorizontalTextPosition(SwingConstants.CENTER);
        /**
         * Button for spawning fast unit
         */
        fUnit = new JButton(
            Knight.COST + "g",
            blueDragonIcon
        );
        fUnit.setMargin(new Insets(0, 0, 0, 0));
        fUnit.setVerticalTextPosition(SwingConstants.BOTTOM);
        fUnit.setHorizontalTextPosition(SwingConstants.CENTER);
        
        /**
         * Button for spawning obstacle unit
         */
        oUnit = new JButton(
                Dragon.COST + "g",
                blueKnightIcon
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
        add(unitLabel);
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
                   gameView.updateUnitInfoPanel(game.getActivePlayer().getCastlePosition());
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
                    gameView.updateUnitInfoPanel(game.getActivePlayer().getCastlePosition());
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
                    gameView.updateUnitInfoPanel(game.getActivePlayer().getCastlePosition());
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
        hpLabelText.setText(hp + "/" + Castle.START_HP);
        hpLabel.setBounds(2, 2, d.width-4, 26);
    }
    public void updateButtonIcons() {
        if(blue) {
            fUnit.setIcon(redKnightIcon);
            sUnit.setIcon(redStrongIcon);
            oUnit.setIcon(redDragonIcon);
        } else {
            fUnit.setIcon(blueKnightIcon);
            sUnit.setIcon(blueStrongIcon);
            oUnit.setIcon(blueDragonIcon);
        }
        blue = !blue;
    }
}
