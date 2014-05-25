package edu.chalmers.sankoss.desktop.mvc.mainMenu;

import static org.junit.Assert.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.scenes.scene2d.Event;

import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;

public class MainMenuTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testMainMenuScreen() {
		class Pcl implements PropertyChangeListener {
			public void propertyChange(PropertyChangeEvent evt) {
				assertTrue(evt.getPropertyName().equals("exitgame"));
			}
		}
		AbstractScreen<MainMenuModel, MainMenuRenderer> screen = new MainMenuScreen();
		screen.addPcl(new Pcl());
		
		MainMenuRenderer renderer = screen.getRenderer();
		
		

		renderer.btnExit.fire(new Event());

	}

	@Test
	public void testGetModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRenderer() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangeScreen() {
		fail("Not yet implemented");
	}

	@Test
	public void testExitGame() {
		fail("Not yet implemented");
	}

}
