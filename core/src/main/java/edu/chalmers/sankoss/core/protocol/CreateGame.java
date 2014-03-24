package edu.chalmers.sankoss.core.protocol;

public class CreateGame {
    private Game game;
    
    public CreateGame() {
        
    }
    
    public CreateGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
