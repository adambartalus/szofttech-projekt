package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import model.BasicTower;
import model.FreezeSpell;
import model.Game;
import model.Goldmine;
import model.HealSpell;
import model.LongRangeTower;
import model.MeteorSpell;
import model.NotAvailableBuildingPositionException;
import model.NotEnoughGoldException;
import model.Position;
import model.ShortRangeTower;
import model.Spell;
import model.Tower;
import model.Unit;
import model.UnitInfoTableModel;

/**
 * The main window of the game
 */
public class GameView {
    
    private final String MAIN_CONTROL_PANEL = "MCP";
    private final String TOWER_CONTROL_PANEL = "TCP";
    private final String UNIT_INFO_PANEL = "UIP";
    
    private final JFrame frame;
    private final JPanel cards;
    private final CardLayout cardLayout;
    
    private final MainMenu mainMenu;
    private final GameSettings gameSettings;
    
    private final JLayeredPane layeredPane;
    
    private final JPanel gamePanel;
    public final GameArea gameArea;
    private final JScrollPane unitScrollPane;
    private final JTable unitInfoTable;
    private final JPanel unitInfoPanel;
    
    private final JPanel testPanel;
    
    private JPanel activeControlPanel;
    
    private final JPanel mainControlPanel;
    private final JPanel mainUpperControlPanel;
    private final JPanel towerButtonPanel;
    private final JPanel unitButtonPanel;
    
    private final JPanel spellPanel;
    private final JPanel towerPanel;
    private final JPanel towerStatPanel;
    private final JLabel towerNameLabel;
    private final JLabel towerDamageLabel;
    private final JLabel towerRangeLabel;
    
    private final JPanel towerControlPanel;
    private final TowerControlButton upgradeTowerButton;
    private final TowerControlButton demolishTowerButton;
    
    private CastlePanel castlePanel;
    private final JPanel statPanel;
    private final JLabel statLabel;
    
    private final JPanel errorPanel;
    private final JLabel errorLabel;
    
    private Class chosenTower;
    private Spell selectedSpell;
    
    private boolean goldMineSelected = false;
    private Game game;
    
    private final int FPS = 60;
    private final Timer timer;
    
    public GameView() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout(0, 5));
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        
        castlePanel = new CastlePanel(this);
        
        mainMenu = new MainMenu();
        gameSettings = new GameSettings(this);
        
        mainMenu.addNewGameButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "Settings");
                frame.pack();
            }
        });
        mainMenu.addExitButtonActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        
        layeredPane = new JLayeredPane();
        
        testPanel = new JPanel();
        testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.X_AXIS));
        
        gamePanel = new JPanel(new BorderLayout());
        
        statPanel = new JPanel();
        statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));
        statLabel = new JLabel();
        statLabel.setPreferredSize(new Dimension(300, 30));
        statLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        gameArea = new GameArea();
        layeredPane.add(gameArea, new Integer(0));
        
        unitInfoTable = new JTable();
        unitScrollPane = new JScrollPane(unitInfoTable);
        unitInfoPanel = new JPanel();
        unitInfoPanel.setLayout(new BorderLayout(0,5));
        unitInfoPanel.setPreferredSize(new Dimension(0, 100));
        unitInfoPanel.add(unitScrollPane, BorderLayout.CENTER);
        
        JButton cancelButton = new JButton("X");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveControlPanel(MAIN_CONTROL_PANEL);
                gameArea.setSelectedBuildingPos(null);
            }
            
        });
        unitInfoPanel.add(cancelButton, BorderLayout.EAST);
        
        spellPanel = new JPanel();
        spellPanel.setLayout(new BoxLayout(spellPanel, BoxLayout.X_AXIS));
        
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
        
        mainUpperControlPanel = new JPanel();
        towerControlPanel = new JPanel();
        towerControlPanel.setLayout(new BoxLayout(towerControlPanel, BoxLayout.Y_AXIS));
        
        upgradeTowerButton = new TowerControlButton("Upgrade");
        upgradeTowerButton.setMaximumSize(new Dimension(400, 30));
        upgradeTowerButton.addActionListener((ActionEvent e) -> {
            try {
                game.upgradeTower(upgradeTowerButton.getTower());
                updateTowerStats(upgradeTowerButton.getTower());
            } catch (NotEnoughGoldException ex) {
                displayErrorMessage("You don't have enough gold!");
            }
        });
        demolishTowerButton = new TowerControlButton("Demolish");
        demolishTowerButton.setMaximumSize(new Dimension(400, 30));
        demolishTowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.demolishTower(demolishTowerButton.getTower());
                setActiveControlPanel(MAIN_CONTROL_PANEL);
                gameArea.setSelectedBuildingPos(null);
            }
        });
        JButton cancel = new JButton("X");
        cancel.setMaximumSize(new Dimension(400, 30));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveControlPanel(MAIN_CONTROL_PANEL);
                gameArea.setSelectedBuildingPos(null);
            }
        });
        towerControlPanel.add(upgradeTowerButton);
        towerControlPanel.add(demolishTowerButton);
        towerControlPanel.add(cancel);
        
        towerButtonPanel = new JPanel();
        towerButtonPanel.setLayout(new BoxLayout(towerButtonPanel,BoxLayout.Y_AXIS));
        unitButtonPanel = new JPanel();
        unitButtonPanel.setLayout(new BoxLayout(unitButtonPanel, BoxLayout.Y_AXIS));
        
        gameArea.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(null == chosenTower && !goldMineSelected) {
                    return ;
                }
                gameArea.setPointedCell(getPositionFromPoint(e.getPoint()));
            }
        });
        gameArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)) {
                    chosenTower = null;
                    gameArea.setPointedCell(null);
                    goldMineSelected = false;
                    return;
                }
                layeredPane.remove(castlePanel);
                Position pos = getPositionFromPoint(e.getPoint());
                if(selectedSpell !=null) {
                    selectedSpell.Effect(pos, game, game.getActivePlayer());
                    selectedSpell = null;
                }
                if(game.isObstacleAtPos(pos)) return;
                if(goldMineSelected) {
                    try {
                        game.buildGoldmine(pos);
                    } catch(NotEnoughGoldException exc) {
                        displayErrorMessage("You don't have enough gold!");
                    } catch(NotAvailableBuildingPositionException exc) {
                        displayErrorMessage("You can't build there!");
                    }
                    gameArea.setPointedCell(null);
                    return;
                }
                if(null == chosenTower) {
                    gameArea.setSelectedBuildingPos(pos);
                    Tower t = game.getTowerAtPos(pos);
                    if(null != t && t.getOwner() == game.getActivePlayer()) {
                        updateTowerStats(t);
                        upgradeTowerButton.setTower(t);
                        demolishTowerButton.setTower(t);
                        setActiveControlPanel(TOWER_CONTROL_PANEL);
                    } else if(null == t && null == game.getGoldmineAtPos(pos)) { // listing units at the position
                        if(pos.equals(game.getActivePlayer().getCastlePosition())) {
                            int width = castlePanel.getPreferredSize().width;
                            int height = castlePanel.getPreferredSize().height;
                            int x = (pos.getX() + 1) * Game.cellSize;
                            int y = (pos.getY() + 1) * Game.cellSize;
                            if((game.getMapDimension().width - pos.getX()) * Game.cellSize < width ) {
                                x = pos.getX() * Game.cellSize - width;
                            }
                            if(game.getMapDimension().height - pos.getY() < 3) {
                                y = pos.getY() * Game.cellSize - height;
                            }
                            castlePanel.setBounds(
                                    x,
                                    y,
                                    width,
                                    height);
                            layeredPane.add(castlePanel, new Integer(1));
                            return;
                        }
                        ArrayList<Unit> units = game.getUnitsAtPos(pos);
                        unitInfoTable.setModel(new UnitInfoTableModel(units));
                        ((CardLayout)(activeControlPanel.getLayout())).show(
                                activeControlPanel, UNIT_INFO_PANEL);
                        frame.pack();
                    }
                    return ;
                }
                try{
                    game.buildTower(chosenTower, pos);
                } catch(NotEnoughGoldException exc) {
                    displayErrorMessage("You don't have enough gold!");
                } catch(NotAvailableBuildingPositionException exc) {
                    displayErrorMessage("You can't build there!");
                } catch(Exception exc) {
                    displayErrorMessage("Error");
                }
                // chosenTower = null;
                gameArea.setPointedCell(null);
            }
        });
        JButton gMine = new JButton("Goldmine (" + Goldmine.COST + "g)" );
        gMine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goldMineSelected = true;
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
        
        JButton freezeButton = new JButton("Freeze Spell (" + FreezeSpell.cost + "g)" );
        freezeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.getActivePlayer().getGold()>=FreezeSpell.cost) {
                	game.getActivePlayer().decreaseGold(FreezeSpell.cost);
                	selectedSpell = new FreezeSpell();
                }
            }
        });
        JButton meteorButton = new JButton("Meteor Spell (" + MeteorSpell.cost + "g)" );
        meteorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.getActivePlayer().getGold()>=MeteorSpell.cost) {
                	game.getActivePlayer().decreaseGold(MeteorSpell.cost);
                	selectedSpell = new MeteorSpell();
                }
            }
        });
        JButton healButton = new JButton("Heal Spell (" + HealSpell.cost + "g)" );
        healButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.getActivePlayer().getGold()>=HealSpell.cost) {
                	game.getActivePlayer().decreaseGold(HealSpell.cost);
                	selectedSpell = new HealSpell();
                }
            }
        });
              
        /**
         * Button processing a turn
         */
        JButton turnButton = new JButton("Turn");
        turnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.turn();
                if(game.isGameOver()) {
                    JOptionPane.showMessageDialog(gamePanel, game.getWinner().getName() + " Wins", "Game Over",JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        turnButton.setPreferredSize(new Dimension(160,30));
        
        statPanel.add(statLabel);
        
        towerButtonPanel.add(lrTower);
        towerButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        towerButtonPanel.add(basicTower);
        towerButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        towerButtonPanel.add(srTower);
        towerButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        towerButtonPanel.add(gMine);

        spellPanel.add(freezeButton);
        spellPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        spellPanel.add(meteorButton);
        spellPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        spellPanel.add(healButton);
        
        mainUpperControlPanel.add(towerButtonPanel);
        mainUpperControlPanel.add(unitButtonPanel);
        mainUpperControlPanel.add(turnButton);
        
        mainControlPanel = new JPanel();
        mainControlPanel.setLayout(new BoxLayout(mainControlPanel, BoxLayout.Y_AXIS));
        mainControlPanel.add(mainUpperControlPanel);
        mainControlPanel.add(spellPanel);
        mainControlPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        towerPanel.add(towerStatPanel);
        towerPanel.add(towerControlPanel);
        
        activeControlPanel.add(mainControlPanel, MAIN_CONTROL_PANEL);
        activeControlPanel.add(towerPanel, TOWER_CONTROL_PANEL);
        activeControlPanel.add(unitInfoPanel, UNIT_INFO_PANEL);
        ((CardLayout)(activeControlPanel.getLayout())).show(activeControlPanel, MAIN_CONTROL_PANEL);
        
        gamePanel.add(statPanel, BorderLayout.NORTH);
        gamePanel.add(layeredPane, BorderLayout.CENTER);
        gamePanel.add(activeControlPanel, BorderLayout.SOUTH);
        
        cards.add(mainMenu, "Menu");
        cards.add(gameSettings, "Settings");
        cards.add(gamePanel, "Game");
        
        errorPanel = new JPanel();
        errorPanel.setBackground(Color.red);
        errorLabel = new JLabel();
        JButton closeBtn = new JButton("X");
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setMargin(new Insets(0, 0, 0, 0));
        closeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeBtn.setForeground(Color.white);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                closeBtn.setForeground(Color.black);
            }
        });
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideErrorMessage();
            }
        });
        errorPanel.add(errorLabel);
        errorPanel.add(closeBtn);
        errorPanel.setVisible(false);
        
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(errorPanel);
        main.add(cards);
        frame.add(main);
        cardLayout.show(cards, "Menu");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        
        timer = new GameTimer(1000.0 / FPS);
        
        frame.setVisible(true);
    }
    private void updateTowerStats(Tower t) {
        towerNameLabel.setText(t.getClass().getName());
        towerDamageLabel.setText("Damage: " + t.getDamage());
        towerRangeLabel.setText("Range: " + t.getRange());
    }
    void displayErrorMessage(String message) {
        errorLabel.setText(message);
        errorPanel.setVisible(true);
    }
    private void hideErrorMessage() {
        errorPanel.setVisible(false);
    }
    void startNewGame() {
        int row, col;
        try {
            row = Integer.parseInt(gameSettings.getRowFieldText());
        } catch(NumberFormatException nfe) {
            displayErrorMessage("The rows should be a number between 8 and 10");
            return;
        }
        try {
            col = Integer.parseInt(gameSettings.getColFieldText());
        } catch(NumberFormatException nfe) {
            displayErrorMessage("The cols should be a number between 8 and 10");
            return;
        }
        if(row < 8 || row > 10) {
            displayErrorMessage("The number of rows should be between 8 and 10");
            return ;
        }
        if(col < 12 || col > 20) {
            displayErrorMessage("The number of columns should be between 12 and 20");
            return;
        }
        String player1 = gameSettings.getPlayer1FieldText();
        String player2 = gameSettings.getPlayer2FieldText();
        game = new Game(new Dimension(col, row), player1, player2);
        castlePanel.setUpGame(game);
        
        gameArea.setGame(game);
        gameArea.adjustSize();
        layeredPane.setPreferredSize(gameArea.getPreferredSize());
        gameArea.setBounds(0, 0, gameArea.getPreferredSize().width, gameArea.getPreferredSize().height);
       
        cardLayout.show(cards, "Game");
        hideErrorMessage();
        frame.pack();
        castlePanel.updateHealthBar(game.getActivePlayer().getCastleHp(), 5000);
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
                    castlePanel.updateHealthBar(game.getActivePlayer().getCastleHp(), 5000);
                    gameArea.repaint();
                }
            });
        }
    }
}
