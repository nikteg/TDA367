package edu.chalmers.sankoss.java.Renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.chalmers.sankoss.java.Models.ScreenModel;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class PlacementRenderer extends Renderer{

	private Sprite box= new Sprite(new Texture("src/main/java/edu/chalmers/sankoss/java/texures/testSquare.png"));
	
	private final int GRID_SIDE=10;
	
	private SpriteBatch batch = new SpriteBatch();
	
    /**
     * @inheritdoc
     */
    public PlacementRenderer(ScreenModel currentModel) {
        super(currentModel);
    }


    @Override
    public void render() {
    	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.28f, 0.5f, 1);
        
        
        //Writes the grid as textures
       
        
        /*batch.begin();
        for(int y = 0; y < GRID_SIDE; y++){
            for(int x = 0; x < GRID_SIDE; x++){
                box.setX(x * 50);
                box.setY(y * 50);
                box.draw(batch);
             }
         }
         batch.end();
        */
      
    }
}
