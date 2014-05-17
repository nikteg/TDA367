package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.Screens;
import edu.chalmers.sankoss.java.models.LobbyModel;

import java.util.Collection;
import java.util.Observable;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 * @modified Daniel Eineving 2014-05-12
 */
public class LobbyRenderer extends AbstractRenderer {

    private List<Room> lstRooms = new List<Room>(SankossGame.getInstance().getSkin());
    private TextButton btnHost = new TextButton("Host", SankossGame.getInstance().getSkin());
    private TextButton btnJoin = new TextButton("Join", SankossGame.getInstance().getSkin());
    private TextButton btnBack = new TextButton("Back", SankossGame.getInstance().getSkin());

    public LobbyRenderer(Observable observable) {
        super(observable);

        getTable().pad(8f);

        getTable().add(lstRooms).colspan(3).expand().fill();
        getTable().row();
        getTable().add(btnBack);
        getTable().add(btnHost);
        getTable().add(btnJoin);
        //getTable().debug();

        getStage().addActor(getTable());

        btnJoin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Room room = (Room) lstRooms.getSelected();

                Gdx.app.debug("LobbyRenderer", "Joining room '" + room.getName() + "'");

                SankossGame.getInstance().getClient().joinRoom(room.getID());
            }
        });

        btnHost.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SankossGame.getInstance().getClient().createRoom(SankossGame.getInstance().getClient().getPlayer().getName() + "'s room", "");
            }
        });

        btnBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Screens.MAIN_MENU.show();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.58f, 0.2f, 1);

        getStage().act(delta);
        getStage().draw();
        Table.drawDebug(getStage());
    }

    @Override
    public void update(Observable object, Object arg) {
        if (arg.equals("rooms")) {

            Collection<Room> values = ((LobbyModel) object).getRooms().values();
            lstRooms.setItems(values.toArray(new Room[values.size()]));
        }
    }
}
