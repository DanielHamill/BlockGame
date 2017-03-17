package block.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import block.game.screens.MenuScreen;

public class BlockGame extends Game {
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	
	public OrthographicCamera camera;
	public Viewport viewport;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 320, 240);
		batch = new SpriteBatch();
		
		this.setScreen(new MenuScreen(this));
	}
	
	@Override
	public void render () {
		batch.setProjectionMatrix(camera.combined);
		super.render();
	}

}
