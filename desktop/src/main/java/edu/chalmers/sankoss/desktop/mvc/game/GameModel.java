package edu.chalmers.sankoss.desktop.mvc.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import edu.chalmers.sankoss.core.model.Coordinate;
import edu.chalmers.sankoss.core.model.CorePlayer;
import edu.chalmers.sankoss.desktop.misc.FlagImage;
import edu.chalmers.sankoss.desktop.mvc.AbstractModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model for GameScreen.
 * Handles all data for the GameScreen and listens
 * to the renderer.
 *
 */
public class GameModel extends AbstractModel {
    private boolean myTurn;
    private boolean won;
    private List<Coordinate> yourShots = new ArrayList<Coordinate>();
    private Map<Coordinate, Image> flags = new HashMap<Coordinate, Image>();
    private CorePlayer opponent;

    public GameModel() {
        myTurn = false;
    }

    public void setOpponent(CorePlayer opponent) {
        this.opponent = opponent;
    }

    public CorePlayer getOpponent() {
        return this.opponent;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;

        setChanged();
        notifyObservers("turn");
    }

    /**
     * Method for adding shots to model's shotList.
     * @param coordinate position of shot.
     */
    public void addToYourShots(Coordinate coordinate) {
        yourShots.add(coordinate);

        setChanged();
        notifyObservers("yourShots");
    }

    /**
     * Method for determine to remove or add flags.
     * @param coordinate position of flag.
     */
    public void addOrRemoveFlags(Coordinate coordinate) {
        if(flags.get(coordinate) == null) {
            // Adding flag
            System.out.println("Adding flag..");
            addToYourFlags(coordinate);
            flags.get(coordinate).setVisible(true);

        } else {
            // Removing flag
            flags.get(coordinate).setVisible(false);
            flags.remove(coordinate);
        }
    }

    /**
     * Method for adding flags to model's flagList.
     * @param coordinate position of flag.
     */
    public void addToYourFlags(Coordinate coordinate) {
        flags.put(coordinate, new FlagImage());

        setChanged();
        notifyObservers("addFlags");
    }

    public boolean getMyTurn() {
        return myTurn;
    }

    public void setWon(boolean won) {
        this.won = won;

        setChanged();
        if(won) {
            notifyObservers("won");
        } else {
            notifyObservers("lost");
        }
    }

    public List<Coordinate> getYourShots () {
        return yourShots;
    }

    public Map<Coordinate, Image> getFlags () {
        return flags;
    }

}
