package edu.chalmers.sankoss.java.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Class that contains data that are needed for other classes
 * @author Daniel Eineving
 * @date 2014-05-21
 */
public class Common {
	private static Skin skin;
	
	/**
	 * Skin that shows how the games elements should look like
	 * @return skin
	 */
	public static Skin getSkin(){
		if (skin == null){
			skin = new Skin(Gdx.files.internal("uiskin.json"));
		}
		return skin;
	}
}
