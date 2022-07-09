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
import model.BasicTower;
import model.FreezeSpell;
import model.HealSpell;
import model.LongRangeTower;
import model.MeteorSpell;
import model.ShortRangeTower;


public class SpellPanel extends JPanel {
    private final JButton freezeButton;
    private final JButton meteorButton;
    private final JButton healButton;
    
    private final ImageIcon freezeIcon;
    private final ImageIcon meteorIcon;
    private final ImageIcon healIcon;
    
    public SpellPanel(GameView gv) {
        freezeIcon = new ImageIcon(GameArea.freeze.getScaledInstance(50, 50, 0));
        meteorIcon = new ImageIcon(GameArea.meteor.getScaledInstance(50, 50, 0));
        healIcon = new ImageIcon(GameArea.heal.getScaledInstance(50, 50, 0));

        freezeButton = new JButton(
            FreezeSpell.COST + "g",
            freezeIcon
        );
        freezeButton.setMargin(new Insets(0, 0, 0, 0));
        freezeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        freezeButton.setHorizontalTextPosition(SwingConstants.CENTER);
        freezeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gv.setSelectedSpell(new FreezeSpell());
                gv.chosenBuildingImage = freezeButton.getIcon();
            }
        });

        meteorButton = new JButton(
            MeteorSpell.COST + "g",
            meteorIcon
        );
        meteorButton.setMargin(new Insets(0, 0, 0, 0));
        meteorButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        meteorButton.setHorizontalTextPosition(SwingConstants.CENTER);
        meteorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gv.setSelectedSpell(new MeteorSpell());
                gv.chosenBuildingImage = meteorButton.getIcon();
            }
        });

        healButton = new JButton(
            HealSpell.COST + "g",
            healIcon
        );
        healButton.setMargin(new Insets(0, 0, 0, 0));
        healButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        healButton.setHorizontalTextPosition(SwingConstants.CENTER);
        healButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gv.setSelectedSpell(new HealSpell());
                gv.chosenBuildingImage = healButton.getIcon();
            }
        });
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(SwingConstants.CENTER);
        
        add(freezeButton);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(meteorButton);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(healButton);
    }
}
