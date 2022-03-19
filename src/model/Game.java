package model;


public class Game {
    private int activePlayerIndex;
    private final Player[] players;

    public Game() {
        this.players = new Player[]{new Player(), new Player()};
        
    }
    
    
}
