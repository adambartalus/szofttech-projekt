package view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.tower.BasicTower;
import model.tower.LongRangeTower;
import model.tower.ShortRangeTower;


public class TowerButtonPanel extends JPanel {
    
    private final JButton lrTower;
    private final JButton srTower;
    private final JButton basicTower;
    
    private final ImageIcon lrTower_blue;
    private final ImageIcon srTower_blue;
    private final ImageIcon basicTower_blue;
    private final ImageIcon lrTower_red;
    private final ImageIcon srTower_red;
    private final ImageIcon basicTower_red;
    
    private boolean blue = true;
    
    public TowerButtonPanel(GameView gv) {
        lrTower_blue = new ImageIcon(GameArea.tower_long_blue.getScaledInstance(50, 50, 0));
        srTower_blue = new ImageIcon(GameArea.tower_short_blue.getScaledInstance(50, 50, 0));
        basicTower_blue = new ImageIcon(GameArea.tower_basic_blue.getScaledInstance(50, 50, 0));
        lrTower_red = new ImageIcon(GameArea.tower_long_red.getScaledInstance(50, 50, 0));
        srTower_red = new ImageIcon(GameArea.tower_short_red.getScaledInstance(50, 50, 0));
        basicTower_red = new ImageIcon(GameArea.tower_basic_red.getScaledInstance(50, 50, 0));
        /**
         * Button for spawning long range tower
         */
        lrTower = new JButton(
            LongRangeTower.COST + "g",
            lrTower_blue
        );
        lrTower.setMargin(new Insets(0, 0, 0, 0));
        lrTower.setVerticalTextPosition(SwingConstants.BOTTOM);
        lrTower.setHorizontalTextPosition(SwingConstants.CENTER);
        lrTower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gv.chosenTower = LongRangeTower.class;
                gv.chosenBuildingImage = lrTower.getIcon();
            }
        });
        /**
         * Button for spawning basic tower
         */
        basicTower = new JButton(
            BasicTower.COST + "g",
            basicTower_blue
        );
        basicTower.setMargin(new Insets(0, 0, 0, 0));
        basicTower.setVerticalTextPosition(SwingConstants.BOTTOM);
        basicTower.setHorizontalTextPosition(SwingConstants.CENTER);
        basicTower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gv.chosenTower = BasicTower.class;
                gv.chosenBuildingImage = basicTower.getIcon();
            }
        });
        /**
         * Button for spawning short range tower
         */
        srTower = new JButton(
            ShortRangeTower.COST + "g",
            srTower_blue
        );
        srTower.setMargin(new Insets(0, 0, 0, 0));
        srTower.setVerticalTextPosition(SwingConstants.BOTTOM);
        srTower.setHorizontalTextPosition(SwingConstants.CENTER);
        srTower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gv.chosenTower = ShortRangeTower.class;
                gv.chosenBuildingImage = srTower.getIcon();
            }
        });
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(SwingConstants.CENTER);
        
        add(lrTower);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(basicTower);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(srTower);
    }
    public void updateButtonIcons() {
        if(blue) {
            lrTower.setIcon(lrTower_red);
            srTower.setIcon(srTower_red);
            basicTower.setIcon(basicTower_red);
        } else {
            lrTower.setIcon(lrTower_blue);
            srTower.setIcon(srTower_blue);
            basicTower.setIcon(basicTower_blue);
        }
        blue = !blue;
    }
}
