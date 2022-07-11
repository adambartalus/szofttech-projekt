package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.tower.Tower;

public class TowerStatPanel extends JPanel {
    
    private final JLabel damageLabel;
    private final JLabel levelLabel;
    private final JLabel rangeLabel;
    private final JLabel nameLabel;
    
    public TowerStatPanel() {
        nameLabel = new JLabel();
        nameLabel.setFont(new Font(
                nameLabel.getFont().getFontName(),
                Font.PLAIN,
                (int) (1.5 * nameLabel.getFont().getSize())
            )
        );
        nameLabel.setMaximumSize(new Dimension(1000, 30));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        levelLabel = new JLabel();
        levelLabel.setMaximumSize(new Dimension(1000, 30));
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        damageLabel = new JLabel();
        damageLabel.setMaximumSize(new Dimension(1000, 30));
        damageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        damageLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        
        rangeLabel = new JLabel();
        rangeLabel.setMaximumSize(new Dimension(1000, 30));
        rangeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        rangeLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        add(nameLabel);
        add(levelLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(damageLabel);
        add(rangeLabel);
    }
    
    public void updateLabels(Tower tower) {
        nameLabel.setText(tower.getName());
        levelLabel.setText("Level " + tower.getLevel());
        damageLabel.setText("Damage: " + tower.getDamage());
        rangeLabel.setText("Range: " + tower.getRange());
    }
}
