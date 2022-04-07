package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.BasicTower;
import model.FastUnit;
import model.Game;
import model.LongRangeTower;
import model.ObstacleUnit;
import model.Position;
import model.ShortRangeTower;
import model.StrongUnit;
import model.Tower;

/**
 * The main window of the game
 */
public class GameView {
    
    private final JFrame frame;
    private final GameArea gameArea;
    
    private final JPanel mainControlPanel;
    private final JPanel towerButtonPanel;
    private final JPanel unitPanel;
    
    private final JPanel statPanel;
    private final JLabel statLabel;
    
    private final JPanel towerControlPanel;
    private final TowerControlButton upgradeTowerButton;
    private final TowerControlButton demolishTowerButton;
    
    private JPanel activeControlPanel;
    
    private Class chosenTower;
    private Game game;
    
    private final int FPS = 60;
    private final Timer timer;

    public GameView() {
        
        game = new Game(new Dimension(18, 10));
        
        frame = new JFrame();
        frame.setLayout(new BorderLayout(0, 5));
        gameArea = new GameArea(game);
        
        mainControlPanel = new JPanel();
        activeControlPanel = mainControlPanel;
        
        statPanel = new JPanel();
        statLabel = new JLabel("Player1: " + game.getActivePlayer().getGold() + " g");
        
        towerControlPanel = new JPanel();
        towerControlPanel.setLayout(new BoxLayout(towerControlPanel, BoxLayout.X_AXIS));
        
        upgradeTowerButton = new TowerControlButton("Upgrade");
        upgradeTowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upgradeTowerButton.getTower().upgrade();
            }
        });
        demolishTowerButton = new TowerControlButton("Demolish");
        demolishTowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.demolishTower(demolishTowerButton.getTower());
                setActiveControlPanel(mainControlPanel);
                gameArea.setSelectedBuildingPos(null);
            }
        });
        JButton exitButton = new JButton("X");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveControlPanel(mainControlPanel);
                gameArea.setSelectedBuildingPos(null);
            }
            
        });
        towerControlPanel.add(upgradeTowerButton);
        towerControlPanel.add(demolishTowerButton);
        towerControlPanel.add(exitButton);
        
        
        towerButtonPanel = new JPanel();
        towerButtonPanel.setLayout(new BoxLayout(towerButtonPanel,BoxLayout.Y_AXIS));
        unitPanel = new JPanel();
        unitPanel.setLayout(new BoxLayout(unitPanel, BoxLayout.Y_AXIS));
        
        gameArea.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(null == chosenTower) {
                    
                    return ;
                }
                gameArea.setPointedCell(getPositionFromPoint(e.getPoint()));
            }
        });
        gameArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Position pos = getPositionFromPoint(e.getPoint());
                if(null == chosenTower) {
                    Tower t = game.getTowerAtPos(pos);
                    if(null != t) {
                        upgradeTowerButton.setTower(t);
                        demolishTowerButton.setTower(t);
                        setActiveControlPanel(towerControlPanel);
                        gameArea.setSelectedBuildingPos(pos);
                    }
                    return ;
                }
                game.addTower(chosenTower, pos);
                chosenTower = null;
                gameArea.setPointedCell(null);
            }
        });
        
        /**
         * Button for spawning long range tower
         */
        JButton lrTower = new JButton("Long range tower (" + LongRangeTower.COST + "g)" );
        lrTower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenTower = LongRangeTower.class;
            }
        });
        //lrTower.setPreferredSize(new Dimension(200,30));
        /**
         * Button for spawning basic tower
         */
        JButton basicTower = new JButton("Basic tower (" + BasicTower.COST + "g)" );
        basicTower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenTower = BasicTower.class;
            }
        });
        //basicTower.setPreferredSize(new Dimension(200,30));
        /**
         * Button for spawning short range tower
         */
        JButton srTower = new JButton("Short range tower (" + ShortRangeTower.COST + "g)" );
        srTower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenTower = ShortRangeTower.class;
            }
        });
        //srTower.setPreferredSize(new Dimension(200,30));
        
        /**
         * Button for spawning strong unit
         */
        JButton sUnit = new JButton("Spawn strong unit (" + StrongUnit.COST + "g)");
        sUnit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameView.this.game.addUnit(new StrongUnit(game.getActivePlayer().getCastlePosition()));
            }
        });
        //sUnit.setPreferredSize(new Dimension(160,60));
        /**
         * Button for spawning fast unit
         */
        JButton fUnit = new JButton("Spawn fast unit (" + FastUnit.COST + "g)");
        fUnit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameView.this.game.addUnit(new FastUnit(game.getActivePlayer().getCastlePosition()));
            }
        });
        //fUnit.setPreferredSize(new Dimension(160,60));
        /**
         * Button for spawning obstacle unit
         */
        JButton oUnit = new JButton("Spawn obstacle unit (" + ObstacleUnit.COST + "g)");
        oUnit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameView.this.game.addUnit(new ObstacleUnit(game.getActivePlayer().getCastlePosition()));
            }
        });
        //oUnit.setPreferredSize(new Dimension(160,60));
        
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
        
        statPanel.add(statLabel);
        
        towerButtonPanel.add(lrTower);
        towerButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        towerButtonPanel.add(basicTower);
        towerButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        towerButtonPanel.add(srTower);
        
        unitPanel.add(sUnit);
        unitPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        unitPanel.add(fUnit);
        unitPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        unitPanel.add(oUnit);
        
        mainControlPanel.add(towerButtonPanel);
        mainControlPanel.add(unitPanel);
        mainControlPanel.add(turnButton);
        
        frame.add(statPanel, BorderLayout.NORTH);
        frame.add(gameArea, BorderLayout.CENTER);
        frame.add(activeControlPanel, BorderLayout.SOUTH);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        
        timer = new GameTimer(1000.0 / FPS);
        timer.start();
        
        frame.setVisible(true);
    }
    private Position getPositionFromPoint(Point p) {
        int x = (int)p.getX() / Game.cellSize;
        int y = (int)p.getY() / Game.cellSize;
        
        return new Position(x, y);
    }
    private void updateStatLabel() {
        statLabel.setText(game.getActivePlayer().getName()
                + ": " + game.getActivePlayer().getGold() + "g"
                + ", " + game.getActivePlayer().getCastleHp() + " hp");
    }
    private void setActiveControlPanel(JPanel p) {
        frame.remove(activeControlPanel);
        activeControlPanel = p;
        frame.add(activeControlPanel, BorderLayout.SOUTH);
        frame.pack();
    }
    /**
     * Periodically repaints the game area and updates the stat label
     */
    private class GameTimer extends Timer {
        public GameTimer(double ms) {
            super((int)ms, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    updateStatLabel();
                    gameArea.repaint();
                }
            });
        }
    }
    
}
