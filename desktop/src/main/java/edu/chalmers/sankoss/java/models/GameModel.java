package edu.chalmers.sankoss.java.models;

import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.java.misc.ShipButton;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description of class.
 * More detailed description.
 *
 */
public class GameModel extends ScreenModel{

    private Map<Coordinate, ShipButton.Direction> rotationMap;
    private Map<Integer, Set<Coordinate>> shipMap;
    private Map<Coordinate, Boolean> hitMap;
    private int yourShipsDestroyed;
    private int oppShipsDestroyed;
    private boolean gameOver;
    private BasePlayer loser;

    public GameModel(Map<Integer, Set<Coordinate>> shipMap, Map<Coordinate, ShipButton.Direction> rotationMap) {
        this.shipMap = shipMap;
        this.rotationMap = rotationMap;
        yourShipsDestroyed = 0;
        oppShipsDestroyed = 0;

        hitMap = new HashMap<Coordinate, Boolean>();
    }

    public void incrementShipsDestroyed(BasePlayer player) {

        // Determines if it was your ship or the opponents
        if(player.equals(getClient().getPlayer())) {
            yourShipsDestroyed++;

        } else {
            oppShipsDestroyed++;
        }
    }

    public Map<Coordinate, Boolean> getHitMap() {
        return hitMap;
    }

    public int getYourShipsDestroyed() {
        return yourShipsDestroyed;
    }

    public int getOppShipsDestroyed() {
        return oppShipsDestroyed;
    }

    /**
     * Checks if all ships are destroyed.
     * If more than 4 ships are destroyed you have lost.
     */
    public void updateGameOverStatus() {

        if(yourShipsDestroyed > 4 || oppShipsDestroyed > 4) {
            setGameOver(true);
        }
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;

        // Determines whom is the loser
        if(yourShipsDestroyed > 4) {
            setLoser(client.getPlayer());
        } else {
            setLoser(client.getOpponents().get(0));
        }
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setLoser(BasePlayer loser) {
        this.loser = loser;
    }

    public BasePlayer getLoser() {
        return loser;
    }

    public Map<Integer, Set<Coordinate>> getShipMap() {
        return shipMap;
    }

    public Map<Coordinate, ShipButton.Direction> getRotationMap() {
        return rotationMap;
    }

    /**
     * @inheritdoc
     */
    public Room getRoomByName(String roomName, Map<Long, Room> rooms) {
        return null;
    }

    /**
     * @inheritdoc
     */
    public void setRoomMap(Map<Long, Room> roomMap) {

    }

}
