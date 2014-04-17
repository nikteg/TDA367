package edu.chalmers.sankoss.java;

import edu.chalmers.sankoss.core.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nikteg on 28/03/14.
 */
public class GameFactory {
    private static Map<Long, Game> games = new HashMap<Long, Game>();
    private static AtomicLong counter = new AtomicLong();

    public static Game createGame(List<Player> players) {
        Long gameID = counter.incrementAndGet();
        Game game = new Game(gameID, players);
        games.put(gameID, game);

        return game;
    }

    public static Game getGame(Long id) throws GameNotFoundException {
        if (games.containsKey(id)) {
            return games.get(id);
        }

        throw new GameNotFoundException("Game was not found with given id: " + id.toString());
    }

    public static void removeGame(Game game) throws GameNotFoundException {
        Iterator it = games.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();

            if (pairs.getValue().equals(game)) {
                it.remove();

                return;
            }
        }

        throw new GameNotFoundException("Game was not found");
    }

    public static void removeGame(Long id) throws GameNotFoundException {
        if (games.remove(id) == null) {
            throw new GameNotFoundException("Game was not found with given id: " + id.toString());
        }
    }

    public static Map<Long, Game> getGames() {
        return games;
    }
}
