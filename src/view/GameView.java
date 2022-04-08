package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
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
    
    private final String MAIN_CONTROL_PANEL = "MCP";
    private final String TOWER_CONTROL_PANEL = "TCP";
    
    private final JFrame frame;
    private final JPanel cards;
    private final CardLayout cardLayout;
    
    private final JPanel mainMenu;
    private final JPanel gamePanel;
    
    private final GameArea gameArea;
    
    private JPanel activeControlPanel;
    
    private final JPanel mainControlPanel;
    private final JPanel towerButtonPanel;
    private final JPanel unitButtonPanel;
    
    private final JPanel towerControlPanel;
    private final TowerControlButton upgradeTowerButton;
    private final TowerControlButton demolishTowerButton;
    
    private final JPanel statPanel;
    private final JLabel statLabel;
    
    private Class chosenTower;
    private Game game;
    
    private final int FPS = 60;
    private final Timer timer;

    public GameView() {
        
        frame = new JFrame();
        frame.setLayout(new BorderLayout(0, 5));
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        
        mainMenu = new JPanel();
        mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.Y_AXIS));
        mainMenu.setPreferredSize(new Dimension(500, 500));
        
        JButton newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setMaximumSize(new Dimension(200, 30));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(200, 30));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gamePanel = new JPanel(new BorderLayout());
        
        statPanel = new JPanel();
        statLabel = new JLabel();
        
        gameArea = new GameArea();
        
        activeControlPanel = new JPanel(new CardLayout());
        
        mainControlPanel = new JPanel();
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
                setActiveControlPanel(MAIN_CONTROL_PANEL);
                gameArea.setSelectedBuildingPos(null);
            }
        });
        JButton cancelButton = new JButton("X");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveControlPanel(MAIN_CONTROL_PANEL);
                gameArea.setSelectedBuildingPos(null);
            }
            
        });
        towerControlPanel.add(upgradeTowerButton);
        towerControlPanel.add(demolishTowerButton);
        towerControlPanel.add(cancelButton);
        
        towerButtonPanel = new JPanel();
        towerButtonPanel.setLayout(new BoxLayout(towerButtonPanel,BoxLayout.Y_AXIS));
        unitButtonPanel = new JPanel();
        unitButtonPanel.setLayout(new BoxLayout(unitButtonPanel, BoxLayout.Y_AXIS));
        
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
                    if(null != t && t.getOwner() == game.getActivePlayer()) {
                        upgradeTowerButton.setTower(t);
                        demolishTowerButton.setTower(t);
                        setActiveControlPanel(TOWER_CONTROL_PANEL);
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
        /**
         * Button for spawning obstacle unit
         */
        JButton oUnit = new JButton("Spawn obstacle unit (" + ObstacleUnit.COST + "g)");
        oUnit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.addUnit(new ObstacleUnit(game.getActivePlayer().getCastlePosition()));
            }
        });        
        /**
         * Button processing a turn
         */
        JButton turnButton = new JButton("Turn");
        turnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < game.getUnits().size();i++) {
                	game.getUnits().get(i).step();
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
        
        unitButtonPanel.add(sUnit);
        unitButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        unitButtonPanel.add(fUnit);
        unitButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        unitButtonPanel.add(oUnit);
        
        mainControlPanel.add(towerButtonPanel);
        mainControlPanel.add(unitButtonPanel);
        mainControlPanel.add(turnButton);
        
        activeControlPanel.add(mainControlPanel, MAIN_CONTROL_PANEL);
        activeControlPanel.add(towerControlPanel, TOWER_CONTROL_PANEL);
        ((CardLayout)(activeControlPanel.getLayout())).show(activeControlPanel, MAIN_CONTROL_PANEL);
        
        mainMenu.add(Box.createRigidArea(new Dimension(0, 200)));
        mainMenu.add(newGameButton);
        mainMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        mainMenu.add(exitButton);
        
        gamePanel.add(statPanel, BorderLayout.NORTH);
        gamePanel.add(gameArea, BorderLayout.CENTER);
        gamePanel.add(activeControlPanel, BorderLayout.SOUTH);
        
        cards.add(mainMenu, "Menu");
        cards.add(gamePanel, "Game");
        
        frame.add(cards);
        cardLayout.show(cards, "Menu");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        
        timer = new GameTimer(1000.0 / FPS);
        
        frame.setVisible(true);
    }
    private void startNewGame() {
        game = new Game(new Dimension(18, 10));
        gameArea.setGame(game);
        gameArea.adjustSize();
       
        cardLayout.show(cards, "Game");
        frame.pack();
        
        timer.restart();
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
    private void setActiveControlPanel(String s) {
        ((CardLayout)(activeControlPanel.getLayout())).show(activeControlPanel, s);
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
