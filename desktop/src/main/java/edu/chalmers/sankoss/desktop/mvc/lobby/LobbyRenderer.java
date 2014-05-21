package edu.chalmers.sankoss.desktop.mvc.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import edu.chalmers.sankoss.core.core.Room;
import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;
import edu.chalmers.sankoss.desktop.utils.Common;
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
public class LobbyRenderer extends AbstractRenderer<LobbyModel> {
    TextureRegionDrawable penTexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/pen.png"))));
    TextureRegionDrawable checkTexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/check.png"))));
    private ImageButton btnEditName = new ImageButton(penTexture, null);

    private Label infoLabel = new Label("Join or host a game", Common.getSkin());
    private TextField nameField;

    private List<Room> lstRooms = new List<Room>(Common.getSkin());
    private TextButton btnHost = new TextButton("Host", Common.getSkin());
    private TextButton btnJoin = new TextButton("Join", Common.getSkin());
    private TextButton btnBack = new TextButton("Back", Common.getSkin());

    public LobbyRenderer(LobbyModel model) {
        super(model);

        getTable().pad(8f);

        nameField = new TextField("", Common.getSkin());
        nameField.setDisabled(true);
        nameField.setMaxLength(16);
        nameField.setRightAligned(true);

        getTable().add(infoLabel).expandX().left().fillX().top();
        getTable().add(nameField).expandX().right().top().width(250);
        getTable().add(btnEditName).right().fillX().top().width(44f).height(44f);

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

        btnEditName.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (nameField.isDisabled()) {
                    getStage().setKeyboardFocus(nameField);
                    nameField.setDisabled(false);
                    nameField.selectAll();
                    nameField.setRightAligned(false);
                    btnEditName.getImage().setDrawable(checkTexture);
                } else {
                    String name = nameField.getText();
                    SankossGame.getInstance().getClient().playerChangeName(name);
                }
            }
        });
    }

    public TextButton getBtnBack() {
        return btnBack;
    }

    public TextButton getBtnHost() {
        return btnHost;
    }

    public TextButton getBtnJoin() {
        return btnJoin;
    }

    public List<Room> getLstRooms() {
        return lstRooms;
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
        } else if (arg.equals("name")) {
            btnEditName.getImage().setDrawable(penTexture);
            nameField.setText(((LobbyModel) object).getName());
            nameField.setDisabled(true);
            getStage().unfocus(nameField);
            nameField.setRightAligned(true);
        }
    }
}
