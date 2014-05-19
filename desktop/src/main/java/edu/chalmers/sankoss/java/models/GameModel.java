package edu.chalmers.sankoss.java.models;

import edu.chalmers.sankoss.core.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Description of class.
 * More detailed description.
 *
 */
public class GameModel extends AbstractModel {
    private boolean myTurn;
    private List<Coordinate> yourShots = new ArrayList<Coordinate>();

    public GameModel() {
        myTurn = false;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;

        setChanged();
        notifyObservers("turn");
    }

    public void addToList(Coordinate coordinate) {
        yourShots.add(coordinate);

        setChanged();
        notifyObservers("yourShots");
    }

    public boolean getMyTurn() {
        return myTurn;
    }


    public List<Coordinate> getYourShots () {
        return yourShots;
    }

}
