package edu.chalmers.sankoss.java.mvc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.SankossGame;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

/**
 * Abstraction of Screen implementation.
 *
 * @author Mikael Malmqvist
 * @modified Niklas Tegnander
 * @date 3/31/14
 */
public abstract class AbstractScreen<M extends AbstractModel, R extends AbstractRenderer> implements Screen, InputProcessor {

    private M model;
    private R renderer;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private Class<M> generateModel() {
        ParameterizedType superclass = (ParameterizedType)
                getClass().getGenericSuperclass();

        return (Class<M>) superclass.getActualTypeArguments()[0];
    }

    private Class<R> generateRenderer() {
        ParameterizedType superclass = (ParameterizedType)
                getClass().getGenericSuperclass();

        return (Class<R>) superclass.getActualTypeArguments()[1];
    }

    public AbstractScreen() {
        try {
            setModel(generateModel().newInstance());
            setRenderer(generateRenderer().getDeclaredConstructor(getModel().getClass()).newInstance(getModel()));
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            //Gdx.app.debug("AbstractScreen", e.getMessage() );
            //System.out.println(e.getMessage());
            //e.getCause().printStackTrace();
        }
    }

    public PropertyChangeSupport getPcs() {
		return pcs;
	}
    
    public void addPcl(PropertyChangeListener pcl){
    	pcs.addPropertyChangeListener(pcl);
    	//getRenderer().addPropertyChangeListener(pcl);
    }

    public void removePcl(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }

    public M getModel() {
        return model;
    }

    private void setModel(M model) {
        this.model = model;
    }

    public R getRenderer() {
        return renderer;
    }

    private void setRenderer(R renderer) {
        this.renderer = renderer;
    }

    public void changeScreen(String screen) {
        pcs.firePropertyChange("changescreen", null, screen);
    }

    public void exitGame() {
        pcs.firePropertyChange("exitgame", null, null);
    }

    /**
     * Game loop for the current Screen.
     * This method loops as long this Screen is active.
     * @param delta is time interval for rendering
     */
    @Override
    public void render(float delta) {
        renderer.render(delta);
        update(delta);
    }

    public abstract void update(float delta);

    /**
     * Resizes necessary variables to fit.
     * @param width is the new width of the window
     * @param height is the new height of the window
     */
    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    /**
     * Method for when this Screen is set.
     * This method is called automatically when the game sets
     * this Screen as its active Screen. It instantiates its
     * model and renderer.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

        Gdx.app.debug("AbstractScreen", "Changed input processor to " + this.getClass());
    }

    /**
     * Method called when this Screen is no longer the active Screen.
     */
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
        renderer.dispose();
        model.dispose();
    }


    @Override
    public boolean keyDown(int keyCode) {
        renderer.getStage().keyDown(keyCode);

        if (keyCode == Input.Keys.ESCAPE) {
            SankossGame.getInstance().exitApplication();
        }

        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        renderer.getStage().keyUp(keyCode);

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        renderer.getStage().keyTyped(character);

        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        renderer.getStage().touchDown(x, y, pointer, button);

        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        renderer.getStage().touchUp(x, y, pointer, button);

        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        renderer.getStage().touchDragged(x, y, pointer);

        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        renderer.getStage().mouseMoved(x, y);

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        renderer.getStage().scrolled(amount);
        return false;
    }
}
