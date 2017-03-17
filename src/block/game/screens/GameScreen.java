package block.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;

import block.game.BlockGame;
import block.game.World;
import block.game.net.NetOut;

public class GameScreen implements Screen{
	
	public BlockGame game;
	
	private World world;
	
	public GameScreen(BlockGame game, int netState) {
		this.game = game;
		
		world = new World(this, netState);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.3f,.3f,.3f,.3f);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		tick(delta);
		draw();
		
	}
	
	public void tick(float delta) {
		world.tick(delta);
	}
	
	public void draw() {
		game.batch.begin();
		
		world.draw(game.batch);
		
		game.batch.end();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public Input getInput() {
		return Gdx.input;
	}

}
