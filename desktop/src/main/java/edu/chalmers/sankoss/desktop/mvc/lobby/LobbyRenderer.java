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
import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;
import edu.chalmers.sankoss.desktop.utils.Common;

import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.Map;
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
    private ImageButton btnEditName = new ImageButton(penTexture, null, checkTexture);

    private Label infoLabel = new Label("Join or host a game", Common.getSkin());
    private TextField nameField;

    private List<Room> lstRooms = new List<Room>(Common.getSkin());
    private TextButton btnHost = new TextButton("Host", Common.getSkin());
    private TextButton btnJoin = new TextButton("Join", Common.getSkin());
    private TextButton btnBack = new TextButton("Back", Common.getSkin());

    private ScrollPane scrollRooms= new ScrollPane(lstRooms, Common.getSkin());
    
    public LobbyRenderer(LobbyModel model) {
        super(model);

        getTable().pad(8f);

        nameField = new TextField("", Common.getSkin());
        nameField.setDisabled(true);
        nameField.setMaxLength(16);
        nameField.setRightAligned(true);

        getTable().add(infoLabel).expandX().left().fillX().top();
        getTable().add(nameField).expandX().right().top().width(250);
        getTable().add(btnEditName).right().fillX().top().width(32f).height(32f).padLeft(4f);

        getTable().row();
        
        scrollRooms.setFadeScrollBars(false);
        getTable().add(scrollRooms).colspan(3).expand().fill().padTop(8).padBottom(8);

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
                if (btnEditName.isChecked()) {
                    getStage().setKeyboardFocus(nameField);
                    nameField.setDisabled(false);
                    nameField.selectAll();
                    nameField.setRightAligned(false);
                } else {
                    SankossClient.getInstance().playerChangeName(nameField.getText());
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

    public ImageButton getBtnEditName() {
        return btnEditName;
    }

    public TextField getNameField() {
        return nameField;
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
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("rooms")) {
            Map<Long, Room> msg = (Map<Long, Room>)evt.getNewValue();
            Collection<Room> values = msg.values();
            lstRooms.setItems(values.toArray(new Room[values.size()]));
        } else if (evt.getPropertyName().equals("name")) {
            String msg = (String)evt.getNewValue();
            nameField.setText(msg);
            nameField.setDisabled(true);
            getStage().unfocus(nameField);
            nameField.setRightAligned(true);
        }
    }

}
