package edu.chalmers.sankoss.desktop.mvc.lobby;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Room;
import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.client.SankossClientListener;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import edu.chalmers.sankoss.core.protocol.PlayerChangedName;
import edu.chalmers.sankoss.desktop.utils.Common;

import java.util.Map;

/**
 * Screen used at the game lobby when finding a game/room to join.
 * Handles game logic in lobby, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @modified Fredrik Thune, Niklas Tegnander
 * @date 3/24/14
 */
public class LobbyScreen extends AbstractScreen<LobbyModel, LobbyRenderer> {

    public LobbyScreen() {

        SankossClient.getInstance().addListener(new SankossClientListener() {
            /* DO STUFF */

            @Override
            public void errorMsg(Object errorObject, final String errorMessage) {

                /**
                 * A playerChangedName error
                 */
                if (errorObject instanceof PlayerChangedName) {
                    Gdx.app.debug(errorObject.getClass().toString(), errorMessage);
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog("Message", Common.getSkin()) {

                                {
                                    text(errorMessage);
                                    button("OK");
                                }
                            };

                            dialog.setMovable(false);
                            dialog.show(getRenderer().getStage());

                            /**
                             * If invalid name then you will go in to edit mode in the text box
                             */
                            getRenderer().getBtnEditName().setChecked(true);
                            
                            /**
                             * When errorMessege is closed the nameField will be selected
                             */
                            dialog.addListener(new InputListener() {
                                @Override
                                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                                    dialog.remove();
                                    getRenderer().getStage().setKeyboardFocus(getRenderer().getNameField());
                                    getRenderer().getNameField().selectAll();
                                    return false;
                                }
                            });
                        }
                    });
                }


            }

            @Override
            public void fetchedRooms(Map<Long, Room> rooms) {
                getModel().setRooms(rooms);

                Gdx.app.debug("LobbyScreen", "Fetched " + rooms.size() + " rooms");
            }

            @Override
            public void createdRoom(Room room) {
                Gdx.app.debug("LobbyScreen", "Room #" + room.getID() + " was created");

                Gdx.app.postRunnable(new Runnable() {

                    @Override
                    public void run() {
                        changeScreen("waiting");
                    }
                });
            }

            @Override
            public void joinedRoom(CorePlayer player) {
                if (player.getID().equals(SankossClient.getInstance().getPlayer().getID())) {
                    Gdx.app.postRunnable(new Runnable() {

                        @Override
                        public void run() {
                            changeScreen("waiting");
                        }
                    });
                }
            }

            @Override
            public void playerChangedName(CorePlayer player) {
                getModel().setName(player.getName());
            }
        });

        getRenderer().getBtnBack().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeScreen("mainmenu");
            }
        });

        getRenderer().getBtnHost().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // HOST
                SankossClient.getInstance().createRoom(SankossClient.getInstance().getPlayer().getName() + "'s room", "");

            }
        });

        getRenderer().getBtnJoin().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // JOIN
                Room room = getRenderer().getLstRooms().getSelected();

                if (room == null)
                    return;

                Gdx.app.debug("LobbyRenderer", "Joining room '" + room.getName() + "'");

                SankossClient.getInstance().joinRoom(room.getID());
            }
        });
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void show() {
        super.show();
        getModel().setName(SankossClient.getInstance().getPlayer().getName());
        System.out.println("NAMNET: " + getModel().getName());
        SankossClient.getInstance().fetchRooms();
    }
}
