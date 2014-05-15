package edu.chalmers.sankoss.java2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java2.ScreenManager;
import edu.chalmers.sankoss.java2.models.AbstractModel;
import edu.chalmers.sankoss.java2.renderers.AbstractRenderer;

/**
 * Created by nikteg on 15/05/14.
 */
public abstract class AbstractScreen<R extends AbstractRenderer, M extends AbstractModel> implements Screen, InputProcessor {
    private Game game = ScreenManager.getInstance().getGame();
    private R renderer;
    private M model;

    public AbstractScreen() {

    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public M getModel() {
        return model;
    }

    public M setModel(M model) {
        this.model = model;

        return this.model;
    }

    public R getRenderer() {
        return renderer;
    }

    public R setRenderer(R renderer) {
        this.renderer = renderer;

        return this.renderer;
    }

    @Override
    public final void render(float delta) {
        renderer.render(delta);
        update(delta);
    }

    public abstract void update(float delta);

    @Override
    public final void resize(int width, int height) {
        renderer.getStage().getViewport().update((width / 2) * 2, (height / 2) * 2, false);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        renderer.getStage().dispose();
        //renderer.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
