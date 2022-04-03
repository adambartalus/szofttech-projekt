package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.BasicTower;
import model.Game;
import model.LongRangeTower;
import model.Position;
import model.ShortRangeTower;
import model.StrongUnit;

/**
 * The main window of the game
 */
public class GameView {
    
    private final JFrame frame;
    private final GameArea gameArea;
    private final JPanel controlPanel;
    
    private final JPanel towerPanel;
    
    private Class chosenTower;
    
    private Game game;

    public GameView() {
        
        game = new Game(new Dimension(18, 10));
        
        frame = new JFrame();
        frame.setLayout(new BorderLayout(0, 5));
        gameArea = new GameArea(game);
        
        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(gameArea.getPreferredSize().width,100));
        
        towerPanel = new JPanel();
        towerPanel.setLayout(new BoxLayout(towerPanel,BoxLayout.Y_AXIS));
        
        
        gameArea.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(null == chosenTower) {
                    return ;
                }
                Point p = e.getPoint();
                int x = (int)p.getX() / Game.cellSize;
                int y = (int)p.getY() / Game.cellSize;
                gameArea.setPointedCell(new Position(x, y));
            }
        });
        gameArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(null == chosenTower) {
                    System.out.println("there is not a chosen tower");
                    return ;
                }
                Point p = e.getPoint();
                int x = (int)p.getX() / Game.cellSize;
                int y = (int)p.getY() / Game.cellSize;
                game.addTower(chosenTower, new Position(x, y));
                chosenTower = null;
            }
        });
        
        /**
         * Button for spawning long range tower
         */
        JButton lrTower = new JButton("Long range tower(" + LongRangeTower.COST + "g)" );
        lrTower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenTower = LongRangeTower.class;
            }
        });
        lrTower.setPreferredSize(new Dimension(200,30));
        /**
         * Button for spawning basic tower
         */
        JButton basicTower = new JButton("Basic tower(" + BasicTower.COST + "g)" );
        basicTower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenTower = BasicTower.class;
            }
        });
        basicTower.setPreferredSize(new Dimension(200,30));
        /**
         * Button for spawning short range tower
         */
        JButton srTower = new JButton("Short range tower(" + ShortRangeTower.COST + "g)" );
        srTower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenTower = ShortRangeTower.class;
            }
        });
        srTower.setPreferredSize(new Dimension(200,30));
        
        /**
         * Button for spawning unit
         */
        JButton b = new JButton("Spawn strongunit");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameView.this.game.addUnit(new StrongUnit(game.getActivePlayer().getCastlePosition()));
            }
        });
        b.setPreferredSize(new Dimension(160,30));
        
        /**
         * Button processing a turn
         */
        JButton turnButton = new JButton("Turn");
        turnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < game.getUnits().size();i++) {
                	game.getUnits().get(i).step();
                	gameArea.repaint();
                }
                game.nextPlayer();
            }
        });
        turnButton.setPreferredSize(new Dimension(160,30));
        
        towerPanel.add(lrTower);
        towerPanel.add(basicTower);
        towerPanel.add(srTower);
        
        controlPanel.add(towerPanel);
        controlPanel.add(b);
        controlPanel.add(turnButton);
        
        
        frame.add(gameArea, BorderLayout.NORTH);
        frame.add(controlPanel, BorderLayout.SOUTH);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
    
}
