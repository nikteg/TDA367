package edu.chalmers.sankoss.java.misc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.java.SankossGame;

public class PlayerPanel extends Table {
    private Label lblName;
    private Image imgNationality;
    private Label turnLabel;


    public PlayerPanel(String name, CorePlayer.Nationality nationality, Alignment align) {
        super(SankossGame.getInstance().getSkin());

        lblName = new Label(name, SankossGame.getInstance().getSkin());
        //imgNationality = new Image(new Texture(nationality.getPath()));
        turnLabel = new Label("", SankossGame.getInstance().getSkin());

        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(Color.GRAY);
        pix.fill();
        setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pix))));

        if (align == Alignment.LEFT) {
            align(Align.left);
            add(imgNationality).padRight(8f);
            add(lblName);
            add(turnLabel).expandX().right().pad(8f);
        } else {
            align(Align.right);
            add(turnLabel).expandX().left().pad(8f);
            add(lblName).padRight(8f);
            add(imgNationality);
        }
        //debug();

    }


    public void setNationality(CorePlayer.Nationality nationality) {
        this.imgNationality.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(nationality.getPath()))));
    }

    public void setName(String name) {
        lblName.setText(name);
    }

    public void setTurnLabelText(String text) {
        turnLabel.setText(text);
    }

    public void setImgNationality(CorePlayer.Nationality nationality) {

        imgNationality = new Image(new Texture(nationality.getPath()));
        add(imgNationality);
    }

    public void setLblName(String name) {
        lblName.setText(name);
    }

    public enum Alignment{
        LEFT, RIGHT;
    };


}