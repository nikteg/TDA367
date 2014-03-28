package edu.chalmers.sankoss.java;

import java.io.IOException;
import java.util.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import edu.chalmers.sankoss.core.protocol.*;


/**
 * 
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 * 
 */
public class SankossServer {
	private Server server;

	public static void main(String[] args) throws IOException {
		new SankossServer();
	}

	public SankossServer() throws IOException {
		server = new Server() {
			protected Connection newConnection() {
				return new PlayerConnection();
			}
		};

		Network.register(server);

		server.addListener(new Listener() {
			public void connected(Connection connection) {
                System.out.println(connection.getRemoteAddressTCP() + " connected...");
            }

			public void received(Connection c, Object object) {
                PlayerConnection connection = (PlayerConnection) c;
                Player player = connection.getPlayer();

				if (object instanceof CreateRoom) {
					CreateRoom msg = (CreateRoom) object;

					System.out.println(String.format("%s: Created room '%s' with password '%s'", connection.getRemoteAddressTCP(), msg.getName(), msg.getPassword()));

                    player = new Player(connection.getID());

                    try {
                        RoomFactory.createRoom(msg.getName(), msg.getPassword(), player);
                    } catch (RoomNotFoundException e) {
                        connection.sendTCP(new CreateRoom());
                    }

					connection.sendTCP(msg);

					return;
				}

				if (object instanceof JoinRoom) {
					JoinRoom msg = (JoinRoom) object;

                    Room room;
                    try {
                        room = RoomFactory.getRoom(msg.getID());
                    } catch (RoomNotFoundException e) {
                        connection.sendTCP(new JoinRoom());

                        return;
                    }

                    for (Player roomPlayer : room.getPlayers()) {
                        server.getConnections()[roomPlayer.getID()].sendTCP(msg);
                    }

					return;
				}

                if (object instanceof StartGame) {
                    StartGame msg = (StartGame) object;

                    Room room;
                    try {
                        room = RoomFactory.getRoom(msg.getID());
                    } catch (RoomNotFoundException e) {
                        connection.sendTCP(new StartGame());

                        return;
                    }

                    // If the host pressed start game
                    if (room.getPlayers().get(0).equals(player)) {
                        Game game = GameFactory.createGame(room.getPlayers());

                        for (Player gamePlayer : game.getPlayers()) {
                            server.getConnections()[gamePlayer.getID()].sendTCP(game);
                        }

                        try {
                            RoomFactory.removeRoom(room);
                        } catch (RoomNotFoundException e) {
                            e.printStackTrace();
                        }

                        return;
                    }

                    return;
                }

				if (object instanceof FetchRooms) {
					System.out.println(String.format("%s: Fetching rooms", connection.getRemoteAddressTCP()));

					FetchRooms fetchRooms = new FetchRooms(RoomFactory.getRooms());
					connection.sendTCP(fetchRooms);

					return;
				}

				if (object instanceof PlayerReady) {
					PlayerReady msg = (PlayerReady) object;

					player.setFleet(msg.getFleet());
					player.setReady(true);

                    Game game;
                    try {
                        game = GameFactory.getGame(msg.getGameID());
                    } catch (GameNotFoundException e) {
                        return;
                    }

                    boolean allReady = true;
                    for (Player gamePlayer : game.getPlayers()) {
                        if (!gamePlayer.isReady()) {
                            allReady = false;
                            break;
                        }
                    }

                    if (allReady) {
                        for (Player gamePlayer : game.getPlayers()) {
                            server.getConnections()[gamePlayer.getID()].sendTCP(new GameReady());
                        }
                    } else {
                        for (Player gamePlayer : game.getPlayers()) {
                            server.getConnections()[gamePlayer.getID()].sendTCP(player);
                        }
                    }

                    int starter = new Random().nextInt(game.getPlayers().size() - 1);
                    game.setAttacker(game.getPlayers().get(starter));
                    server.getConnections()[game.getAttacker().getID()].sendTCP(new Turn());

                    return;
				}

				if (object instanceof Fire) {
					Fire msg = (Fire) object;

                    Game game;
                    try {
                        game = GameFactory.getGame(msg.getGameID());
                    } catch (GameNotFoundException e) {
                        return;
                    }
					
					if (!game.getAttacker().equals(player))
						return;

                    boolean isHit = game.fire(msg.getTarget(), msg.getCoordinate());

                    for (Player gamePlayer : game.getPlayers()) {
                        if (gamePlayer.equals(msg.getTarget())) {
                            server.getConnections()[gamePlayer.getID()].sendTCP(
                                    isHit ? new Hit(msg.getCoordinate(), true) : new Miss(msg.getCoordinate(), true)
                            );
                        } else {
                            server.getConnections()[gamePlayer.getID()].sendTCP(
                                    isHit ? new Hit(msg.getCoordinate(), true) : new Miss(msg.getCoordinate(), false)
                            );
                        }
                    }

					game.changeAttacker();
                    server.getConnections()[game.getAttacker().getID()].sendTCP(new Turn());

					return;
				}

			}

			public void disconnected(Connection c) {
				PlayerConnection connection = (PlayerConnection) c;
				Player player = connection.getPlayer();

                System.out.println(c.getRemoteAddressTCP() + " disconnected...");
            }
		});

		server.bind(Network.port);
		server.start();
		System.out.println("Server started on port: " + Network.port);

	}

    private  class PlayerConnection extends Connection {
        private Player player;

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }
    }
}
