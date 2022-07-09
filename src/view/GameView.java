package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.TableCellRenderer;
import model.Game;
import model.Goldmine;
import model.NotAvailableBuildingPositionException;
import model.NotEnoughGoldException;
import model.Player;
import model.Position;
import model.Spell;
import model.Tower;
import model.UnitInfoTableModel;

/**
 * The main window of the game
 */
public class GameView {
    
    private final String MAIN_CONTROL_PANEL = "MCP";
    private final String TOWER_CONTROL_PANEL = "TCP";
    
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
    
    private JPanel activeControlPanel;
    
    private final JPanel mainControlPanel;
    private final TowerButtonPanel towerButtonPanel;
    
    private final SpellPanel spellPanel;
    
    private final TowerControlPanel towerControlPanel;
    
    private CastlePanel castlePanel;
    private Position[] castlePanelPositions;
    
    private final JPanel statPanel;
    private final JLabel activePlayerLabel;
    private final JLabel activePlayerGoldLabel;
    private final JLabel activePlayerHpLabel;
    
    private final JPanel errorPanel;
    private final JLabel errorLabel;
    
    Class chosenTower;
    private Spell selectedSpell;
    
    private boolean goldMineSelected = false;
    private Game game;
    
    private final int FPS = 60;
    private final Timer timer;
    Icon chosenBuildingImage;
    
    
    public GameView() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout(0, 5));
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        
        castlePanel = new CastlePanel(this);
        towerControlPanel = new TowerControlPanel(this);
        
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

        
        gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.X_AXIS));
        
        statPanel = new JPanel();
        statPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        statPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));
        
        activePlayerLabel = new JLabel();
        activePlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        activePlayerLabel.setMaximumSize(new Dimension(1000, 30));
        activePlayerLabel.setFont(new Font("", Font.PLAIN, 20));
        
        activePlayerGoldLabel = new JLabel();
        activePlayerGoldLabel.setHorizontalAlignment(SwingConstants.CENTER);
        activePlayerGoldLabel.setMaximumSize(new Dimension(1000, 30));
        activePlayerGoldLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        
        activePlayerHpLabel = new JLabel();
        activePlayerHpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        activePlayerHpLabel.setMaximumSize(new Dimension(1000, 30));
        
        gameArea = new GameArea();
        
        layeredPane.add(gameArea, new Integer(0));
        layeredPane.add(castlePanel, new Integer(1));
        layeredPane.add(towerControlPanel, new Integer(1));
        
        unitInfoTable = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setBackground(getBackground());
                if (getModel().getValueAt(row, 0).equals(game.getPlayer(1).getName())) {
                   c.setBackground(Color.red);
                } else if(getModel().getValueAt(row, 0).equals(game.getPlayer(0).getName())) {
                   c.setBackground(Color.blue);
                }
                
                return c;
            }
        };
        unitScrollPane = new JScrollPane(unitInfoTable);
        unitInfoPanel = new JPanel();
        unitInfoPanel.setVisible(false);
        unitInfoPanel.setLayout(new BorderLayout(0,5));
        unitInfoPanel.setPreferredSize(new Dimension(0, 100));
        unitInfoPanel.add(unitScrollPane, BorderLayout.CENTER);
        
        JButton cancelButton = new JButton("X");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideUnitInfoPanel();
                gameArea.setSelectedBuildingPos(null);
            }
        });
        unitInfoPanel.add(cancelButton, BorderLayout.EAST);
        
        spellPanel = new SpellPanel(this);
        
        activeControlPanel = new JPanel(new CardLayout());
        activeControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        towerButtonPanel = new TowerButtonPanel(this);
        
        gameArea.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(null == chosenTower && !goldMineSelected && null == selectedSpell) {
                    return ;
                }
                gameArea.setPointedCell(getPositionFromPoint(e.getPoint()));
                gameArea.setChosenBuildingImage(chosenBuildingImage);
                gameArea.setChosenBuildingImagePos(e.getPoint());
            }
        });
        gameArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)) {
                    chosenTower = null;
                    chosenBuildingImage = null;
                    gameArea.setChosenBuildingImage(null);
                    gameArea.setPointedCell(null);
                    goldMineSelected = false;
                    return;
                }
                hideCastlePanel();
                Position pos = getPositionFromPoint(e.getPoint());
                updateUnitInfoPanel(pos);
                if(selectedSpell !=null) {
                    game.applySpell(selectedSpell, pos);
                    return;
                }
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
                    if(!unitInfoPanel.isVisible()) {
                        displayUnitInfoPanel(pos);
                    }
                    Tower t = game.getTowerAtPos(pos);
                    if(null != t && t.getOwner() == game.getActivePlayer()) {
                        towerControlPanel.setTower(t);
                        displayTowerControlPanel();
                    } else if(null == t && null == game.getGoldmineAtPos(pos)) { // listing units at the position
                        if(pos.equals(game.getActivePlayer().getCastlePosition())) {
                            displayCastlePanel(pos);
                        }
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
            }
        });
        JButton gMine = new JButton("Goldmine (" + Goldmine.COST + "g)" );
        gMine.addActionListener(new ActionListener() {
            private final Icon mine_blue = new ImageIcon(GameArea.mine_blue.getScaledInstance(50, 50, 0));
            private final Icon mine_red = new ImageIcon(GameArea.mine_red.getScaledInstance(50, 50, 0));
            
            
            @Override
            public void actionPerformed(ActionEvent e) {
                goldMineSelected = true;
                chosenBuildingImage = mine_blue;
            }
        });
        
        /**
         * Button processing a turn
         */
        JButton turnButton = new JButton("Turn");
        turnButton.setMaximumSize(new Dimension(1000, 30));
        turnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.turn();
                hideCastlePanel();
                towerButtonPanel.updateButtonIcons();
                if(game.isGameOver()) {
                    JOptionPane.showMessageDialog(gamePanel, game.getWinner().getName() + " Wins", "Game Over",JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        
        statPanel.add(activePlayerLabel);
        statPanel.add(activePlayerGoldLabel);
        statPanel.add(activePlayerHpLabel);
        
        mainControlPanel = new JPanel();
        mainControlPanel.setLayout(new BoxLayout(mainControlPanel, BoxLayout.Y_AXIS));
        mainControlPanel.add(new JLabel("Build towers"));
        mainControlPanel.add(towerButtonPanel);
        mainControlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainControlPanel.add(gMine);
        mainControlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainControlPanel.add(spellPanel);
        mainControlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainControlPanel.add(turnButton);
        
        // towerControlPanel.setBorder(BorderFactory.createLineBorder(Color.yellow));
        // towerPanel.setBorder(BorderFactory.createLineBorder(Color.yellow));
        
        activeControlPanel.add(mainControlPanel, MAIN_CONTROL_PANEL);
        ((CardLayout)(activeControlPanel.getLayout())).show(activeControlPanel, MAIN_CONTROL_PANEL);
        
        //gamePanel.add(statPanel, BorderLayout.NORTH);
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(layeredPane);
        leftPanel.add(unitInfoPanel);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(statPanel);
        rightPanel.add(activeControlPanel);
        
        gamePanel.add(leftPanel);
        gamePanel.add(rightPanel);
        
        cards.add(mainMenu, "Menu");
        cards.add(gameSettings, "Settings");
        cards.add(gamePanel, "Game");
        
        errorPanel = new JPanel(new BorderLayout());
        errorPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
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
        errorPanel.add(errorLabel, BorderLayout.WEST);
        errorPanel.add(closeBtn, BorderLayout.EAST);
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
    private void initCastlePanel() {
        castlePanelPositions = new Position[2];
        int width = castlePanel.getPreferredSize().width;
        int height = castlePanel.getPreferredSize().height;
        
        Position pos = game.getPlayer(0).getCastlePosition();
        
        int x = (pos.getY() + 1) * Game.cellSize;
        int y = (pos.getX() + 1) * Game.cellSize;
        if(x + width > game.getMapDimension().height * Game.cellSize ) {
            x = pos.getY() * Game.cellSize - width;
        }
        if(game.getMapDimension().width - pos.getX() < 3) {
            y = pos.getX() * Game.cellSize - height;
        }
        castlePanelPositions[0] = new Position(x, y);
        
        pos = game.getPlayer(1).getCastlePosition();
        
        castlePanel.setBounds(
            x,
            y,
            width,
            height
        );
        x = (pos.getY() + 1) * Game.cellSize;
        y = (pos.getX() + 1) * Game.cellSize;
        if(x + width > game.getMapDimension().height * Game.cellSize ) {
            x = pos.getY() * Game.cellSize - width;
        }
        if(game.getMapDimension().width - pos.getX() < 3) {
            y = pos.getX() * Game.cellSize - height;
        }
        castlePanelPositions[1] = new Position(x, y);
    }
    private void displayTowerControlPanel() {
        towerControlPanel.setVisible(true);
    }
    void updateUnitInfoPanel(Position pos) {
        unitInfoTable.setModel(new UnitInfoTableModel(game.getUnitsAtPos(pos)));
    }
    void displayUnitInfoPanel(Position pos) {
        unitInfoPanel.setVisible(true);
        frame.pack();
    }
    void displayErrorMessage(String message) {
        errorLabel.setText(message);
        errorPanel.setVisible(true);
        frame.pack();
    }
    void displayCastlePanel(Position pos) {
        castlePanel.updateHealthBar(game.getActivePlayer().getCastleHp());
        castlePanel.setBounds(
            castlePanelPositions[game.getActivePlayerIndex()].getX(),
            castlePanelPositions[game.getActivePlayerIndex()].getY(),
            castlePanel.getPreferredSize().width,
            castlePanel.getPreferredSize().height
        );
        castlePanel.setVisible(true);
    }
    private void hideUnitInfoPanel() {
        unitInfoPanel.setVisible(false);
        frame.pack();
    }
    private void hideErrorMessage() {
        errorPanel.setVisible(false);
        frame.pack();
    }
    private void hideCastlePanel() {
        castlePanel.setVisible(false);
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
        if(col < 12 || col > 18) {
            displayErrorMessage("The number of columns should be between 12 and 18");
            return;
        }
        String player1 = gameSettings.getPlayer1FieldText();
        String player2 = gameSettings.getPlayer2FieldText();
        game = new Game(new Dimension(row, col), player1, player2);
        
        castlePanel.initGame(game);
        towerControlPanel.initGame(game);
        initCastlePanel();
        
        gameArea.setGame(game);
        gameArea.adjustSize();
        layeredPane.setPreferredSize(gameArea.getPreferredSize());
        gameArea.setBounds(0, 0, gameArea.getPreferredSize().width, gameArea.getPreferredSize().height);
        
        cardLayout.show(cards, "Game");
        hideErrorMessage();
        frame.pack();
        frame.setLocationRelativeTo(null);
        timer.restart();
    }
    private Position getPositionFromPoint(Point p) {
        int x = (int)p.getX() / Game.cellSize;
        int y = (int)p.getY() / Game.cellSize;
        
        return new Position(y, x);
    }
    private void updateStatLabel() {
        Player player = game.getActivePlayer();
        Color color = Color.red;
        if(player == game.getPlayer(0)) {
            color = Color.blue;
        }
        activePlayerLabel.setText(player.getName());
        activePlayerLabel.setForeground(color);
        activePlayerGoldLabel.setText(player.getGold() + " g");
        activePlayerHpLabel.setText(player.getCastleHp() + " hp");
    }
    private void setActiveControlPanel(String s) {
        ((CardLayout)(activeControlPanel.getLayout())).show(activeControlPanel, s);
    }

    void setSelectedSpell(Spell spell) {
        selectedSpell = spell;
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
