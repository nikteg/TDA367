package edu.chalmers.sankoss.java.misc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.renderers.GameRenderer;

public class PlayerPanel extends Table {
    private Label lblName;
    private Image imgNationality;

    public PlayerPanel(String name, CorePlayer.Nationality nationality, Alignment align) {
        super(SankossGame.getInstance().getSkin());

        lblName = new Label(name, SankossGame.getInstance().getSkin());
        imgNationality = new Image(new Texture(nationality.getPath()));

        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(Color.GRAY);
        pix.fill();
        setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pix))));

        if (align == Alignment.LEFT) {
            align(Align.left);
            add(imgNationality).padRight(8f);
            add(lblName);
        } else {
            align(Align.right);
            add(lblName).padRight(8f);
            add(imgNationality);
        }




        //debug();

    }


    public enum Alignment{
        LEFT, RIGHT;
    };


}