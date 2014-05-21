package edu.chalmers.sankoss.java.mvc.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.mvc.AbstractRenderer;
import edu.chalmers.sankoss.java.mvc.game.GameModel;

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
    TextureRegionDrawable penTexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/pen.png"))));
    TextureRegionDrawable checkTexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/check.png"))));
    private ImageButton editBtn = new ImageButton(penTexture, null, checkTexture);

    private Label infoLabel = new Label("Join or host a game", SankossGame.getInstance().getSkin());
    private TextField nameField;

    private List<Room> lstRooms = new List<Room>(SankossGame.getInstance().getSkin());
    private TextButton btnHost = new TextButton("Host", SankossGame.getInstance().getSkin());
    private TextButton btnJoin = new TextButton("Join", SankossGame.getInstance().getSkin());
    private TextButton btnBack = new TextButton("Back", SankossGame.getInstance().getSkin());

    public LobbyRenderer(Observable observable) {
        super(observable);

        getTable().pad(8f);

        nameField = new TextField("", SankossGame.getInstance().getSkin());
        nameField.setDisabled(true);
        nameField.setMaxLength(16);
        nameField.setRightAligned(true);

        getTable().add(infoLabel).expandX().left().fillX().top();
        getTable().add(nameField).expandX().right().top().width(250);
        getTable().add(editBtn).right().fillX().top().width(44f).height(44f);

        getTable().row();

        getTable().add(lstRooms).colspan(3).expand().fill().padTop(8).padBottom(8);

        getTable().row();


        getTable().add(btnBack.pad(8f)).expandX().left().bottom().width(160);
        Table joinHostTable = new Table();

        joinHostTable.add(btnHost.pad(8f)).width(160);
        joinHostTable.add(btnJoin.pad(8f)).padLeft(8f).width(160);

        getTable().add(joinHostTable).expandX().right().bottom().colspan(2);


        //getTable().debug();

        getStage().addActor(getTable());

        btnJoin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Room room = (Room) lstRooms.getSelected();

                if (room == null)
                    return;

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
            	getProptertyChangeSupport().firePropertyChange("showMainMenu", true, false);
            }
        });

        editBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (nameField.isDisabled()) {
                    getStage().setKeyboardFocus(nameField);
                    nameField.setDisabled(false);
                    nameField.selectAll();
                    nameField.setRightAligned(false);
                } else {
                    String name = nameField.getText();
                    SankossGame.getInstance().getClient().getPlayer().setName(name);
                    SankossGame.getInstance().getClient().playerChangeName(name);
                    nameField.setDisabled(true);
                    getStage().unfocus(nameField);
                    nameField.setRightAligned(true);
                }
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
        } else if (arg.equals("nameChange")) {
            nameField.setText(((LobbyModel) object).getName());
        }
    }
}
