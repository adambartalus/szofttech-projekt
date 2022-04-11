package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.JTextField;
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
    private final JPanel gameSettings;
    private final JTextField rowField;
    private final JTextField colField;
    private final JTextField player1NameField;
    private final JTextField player2NameField;
    private final JPanel gamePanel;
    
    private final GameArea gameArea;
    
    private JPanel activeControlPanel;
    
    private final JPanel mainControlPanel;
    private final JPanel towerButtonPanel;
    private final JPanel unitButtonPanel;
    
    private final JPanel towerPanel;
    private final JPanel towerStatPanel;
    private final JLabel towerNameLabel;
    private final JLabel towerDamageLabel;
    private final JLabel towerRangeLabel;
    
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
                cardLayout.show(cards, "Settings");
                frame.pack();
            }
        });
        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(200, 30));
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        gameSettings = new JPanel();
        gameSettings.setLayout(null);
        
        rowField = new JTextField();
        rowField.setSize(new Dimension(30, 25));
        rowField.setLocation(250, 150);
        colField = new JTextField();
        colField.setSize(new Dimension(30, 25));
        colField.setLocation(250, 180);
        
        JLabel rowLabel = new JLabel("Rows: ");
        rowLabel.setSize(new Dimension(50, 25));
        rowLabel.setLocation(205, 150);
        
        JLabel colLabel = new JLabel("Cols: ");
        colLabel.setSize(new Dimension(50, 25));
        colLabel.setLocation(210, 180);
        
        player1NameField = new JTextField("Player1");
        player1NameField.setSize(new Dimension(100, 25));
        player1NameField.setLocation(250, 210);
        player2NameField = new JTextField("Player2");
        player2NameField.setSize(new Dimension(100, 25));
        player2NameField.setLocation(250, 240);
        
        JLabel p1 = new JLabel("Player 1: ");
        p1.setSize(new Dimension(60, 25));
        p1.setLocation(190, 210);
        JLabel p2 = new JLabel("Player 2: ");
        p2.setSize(new Dimension(60, 25));
        p2.setLocation(190, 240);
        
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener((ActionEvent e) -> {
            startNewGame();
        });
        startGameButton.setSize(new Dimension(100, 25));
        startGameButton.setLocation(210, 270);
        
        gameSettings.add(rowLabel);
        gameSettings.add(rowField);
        gameSettings.add(colLabel);
        gameSettings.add(colField);
        gameSettings.add(p1);
        gameSettings.add(player1NameField);
        gameSettings.add(p2);
        gameSettings.add(player2NameField);
        gameSettings.add(startGameButton);
        
        gamePanel = new JPanel(new BorderLayout());
        
        statPanel = new JPanel();
        statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));
        statLabel = new JLabel();
        statLabel.setPreferredSize(new Dimension(300, 30));
        statLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        gameArea = new GameArea();
        
        towerPanel = new JPanel();
        towerPanel.setLayout(new GridLayout(0, 2));
        towerStatPanel = new JPanel();
        towerStatPanel.setLayout(new BoxLayout(towerStatPanel, BoxLayout.Y_AXIS));
        towerNameLabel = new JLabel();
        towerNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        towerDamageLabel = new JLabel();
        towerDamageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        towerRangeLabel = new JLabel();
        towerRangeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        towerStatPanel.add(towerNameLabel);
        towerStatPanel.add(Box.createRigidArea(new Dimension(0,10)));
        towerStatPanel.add(towerDamageLabel);
        towerStatPanel.add(Box.createRigidArea(new Dimension(0,10)));
        towerStatPanel.add(towerRangeLabel);
        
        activeControlPanel = new JPanel(new CardLayout());
        
        mainControlPanel = new JPanel();
        towerControlPanel = new JPanel();
        towerControlPanel.setLayout(new BoxLayout(towerControlPanel, BoxLayout.Y_AXIS));
        
        upgradeTowerButton = new TowerControlButton("Upgrade");
        upgradeTowerButton.addActionListener((ActionEvent e) -> {
            upgradeTowerButton.getTower().upgrade();
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
                        updateTowerStats(t);
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
            	game.reloadObstacles();
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
        
        towerPanel.add(towerStatPanel);
        towerPanel.add(towerControlPanel);
        
        activeControlPanel.add(mainControlPanel, MAIN_CONTROL_PANEL);
        activeControlPanel.add(towerPanel, TOWER_CONTROL_PANEL);
        ((CardLayout)(activeControlPanel.getLayout())).show(activeControlPanel, MAIN_CONTROL_PANEL);
        
        mainMenu.add(Box.createRigidArea(new Dimension(0, 200)));
        mainMenu.add(newGameButton);
        mainMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        mainMenu.add(exitButton);
        
        gamePanel.add(statPanel, BorderLayout.NORTH);
        gamePanel.add(gameArea, BorderLayout.CENTER);
        gamePanel.add(activeControlPanel, BorderLayout.SOUTH);
        
        cards.add(mainMenu, "Menu");
        cards.add(gameSettings, "Settings");
        cards.add(gamePanel, "Game");
        
        frame.add(cards);
        cardLayout.show(cards, "Menu");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        
        timer = new GameTimer(1000.0 / FPS);
        
        frame.setVisible(true);
    }
    private void updateTowerStats(Tower t) {
        towerNameLabel.setText(t.getClass().getName());
        towerDamageLabel.setText("Damage: " + t.getDamage());
        towerRangeLabel.setText("Range: " + t.getRange());
    }
    private void startNewGame() {
        int row, col;
        try {
            row = Integer.parseInt(this.rowField.getText());
        } catch(NumberFormatException nfe) {
            return;
        }
        try {
            col = Integer.parseInt(this.colField.getText());
        } catch(NumberFormatException nfe) {
            return;
        }
        if(row < 8 || row > 10) {
            System.err.println("The number of rows should be between 8 and 10");
            return ;
        }
        if(col < 12 || col > 20) {
            System.err.println("The number of columns should be between 9 and 20");
            return;
        }
        String player1 = player1NameField.getText();
        String player2 = player2NameField.getText();
        game = new Game(new Dimension(col, row), player1, player2);
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
