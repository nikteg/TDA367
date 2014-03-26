package edu.chalmers.sankoss.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
	private HashMap<Player, PlayerConnection> playerConnections = new HashMap<Player, PlayerConnection>();
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private ArrayList<Game> games = new ArrayList<Game>();

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
			public void connected(Connection c) {
				PlayerConnection connection = (PlayerConnection) c;

				if (connection.getPlayer() == null) {
					int id = (new Random()).nextInt(); // TODO create better ID
					connection.setPlayer(new Player(id, "Player"));
				}
			}

			public void received(Connection c, Object object) {
				PlayerConnection connection = (PlayerConnection) c;
				Player player = connection.getPlayer();

				if (object instanceof CreateRoom) {
					CreateRoom msg = (CreateRoom) object;

					System.out.println(String.format("%s: Created room '%s' with password '%s'", connection.getRemoteAddressTCP(), msg.getName(), msg.getPassword()));

					playerConnections.put(player, connection);

					int id = (new Random()).nextInt(); // TODO create better ID
					Room room = new Room(id, msg.getName(), msg.getPassword(), player);

					// Add to room list
					rooms.add(room);

					connection.sendTCP(new CreateRoom());

					return;
				}

				if (object instanceof JoinRoom) {
					JoinRoom msg = (JoinRoom) object;

					Room toJoinRoom = null;
					for (Room room : rooms) {
						if (room.getId() == msg.getId()) {
							toJoinRoom = room;
							rooms.remove(room);
							break;
						}
					}

					// Room does not exist, send error message to client
					if (toJoinRoom == null)
						return;

					System.out.println(String.format("%s: Joining room '#%d %s'", connection.getRemoteAddressTCP(), toJoinRoom.getId(), toJoinRoom.getName()));

					int id = toJoinRoom.getId();
					Player opponent = toJoinRoom.getPlayer();

					playerConnections.put(player, connection);

					Game game = new Game(id, new Player[] { player, opponent });

					System.out.println(String.format("%s: Creating game '#%d' with players '#%d' and '#%d'", connection.getRemoteAddressTCP(), game.getId(), player.getId(), opponent.getId()));

					games.add(game);

					CreateGame createGame = new CreateGame(game);

					playerConnections.get(player).sendTCP(createGame);

					// Send message to opponent
					playerConnections.get(opponent).sendTCP(createGame);

					return;
				}

				if (object instanceof FetchRooms) {
					System.out.println(String.format("%s: Fetching rooms", connection.getRemoteAddressTCP()));

					FetchRooms fetchRooms = new FetchRooms(rooms.toArray(new Room[rooms.size()]));

					connection.sendTCP(fetchRooms);

					return;
				}

				if (object instanceof PlayerReady) {
					PlayerReady msg = (PlayerReady) object;

					player.setGrid(msg.getGrid());
					player.setReady(true);
					
					Player[] players = null;
					Game activeGame = null;
					for (Game game : games) {
						if (player.getId() == game.getPlayers()[0].getId() || player.getId() == game.getPlayers()[1].getId()) {
							players = new Player[] { game.getPlayers()[0], game.getPlayers()[1] };
							activeGame = game;
							break;
						}
					}
					if (players == null)
						return;
					
					if (players[0].isReady() && players[1].isReady()) {

						for (int i = 0; i < players[0].getGrid().length; i++) {
							System.out.println();
							for (int j = 0; j < players[0].getGrid()[0].length; j++) {
								System.out.print(players[0].getGrid()[i][j] ? "\u2588" : " ");
							}
						}
						System.out.println();
						for (int i = 0; i < players[1].getGrid().length; i++) {
							System.out.println();
							for (int j = 0; j < players[1].getGrid()[0].length; j++) {
								System.out.print(players[1].getGrid()[i][j] ? "\u2588" : " ");
							}
						}
						
						playerConnections.get(players[0]).sendTCP(new GameReady());
						playerConnections.get(players[1]).sendTCP(new GameReady());
						
						int starter = new Random().nextInt(1);
						activeGame.setAttacker(players[starter]);
						playerConnections.get(players[starter]).sendTCP(new Turn());
						return;
					}
					
				}
			
				

				if (object instanceof Fire) {
					Fire msg = (Fire) object;
					Game activeGame = null;
					Player[] players = null;
					for (Game game : games) {
						if (player.equals(game.getPlayers()[0])) {
							players = new Player[] { player, game.getPlayers()[1] };
							activeGame = game;
						} else if (player.equals(game.getPlayers()[1])) {
							players = new Player[] { player, game.getPlayers()[0] };
							activeGame = game;
						}
					}
					
					if (!activeGame.getAttacker().equals(player)) 
						return;
					
					if (players == null)
						return;
					

					System.out.println("Fire:" + players[1].isHit(msg.getX(), msg.getY()) + " " + players[0].getId() + " attack " + players[1].getId());
					if (players[1].isHit(msg.getX(), msg.getY())) {
						playerConnections.get(players[0]).sendTCP(new Hit(msg.getX(), msg.getY(), false));
						playerConnections.get(players[1]).sendTCP(new Hit(msg.getX(), msg.getY(), true));
					} else {
						playerConnections.get(players[0]).sendTCP(new Miss(msg.getX(), msg.getY(), false));
						playerConnections.get(players[1]).sendTCP(new Miss(msg.getX(), msg.getY(), true));
					}
					activeGame.changeAttacker();
					playerConnections.get(activeGame.getAttacker()).sendTCP(new Turn());
					return;
				}

			}

			public void disconnected(Connection c) {
				PlayerConnection connection = (PlayerConnection) c;
				Player player = connection.getPlayer();

				playerConnections.remove(player);
			}
		});

		server.bind(Network.port);
		server.start();
		System.out.println("Server started on port: " + Network.port);

	}
}
