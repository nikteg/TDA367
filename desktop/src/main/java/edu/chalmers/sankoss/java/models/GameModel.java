package edu.chalmers.sankoss.java.models;

/**
 * Description of class.
 * More detailed description.
 *
 */
public class GameModel extends AbstractModel {
    private boolean myTurn;

    public GameModel() {
        myTurn = false;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;

        setChanged();
        notifyObservers("turn");
    }

    public boolean getMyTurn() {
        return myTurn;
    }


}
