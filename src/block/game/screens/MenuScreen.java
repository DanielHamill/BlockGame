package block.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import block.game.BlockGame;

public class MenuScreen implements Screen{
	public static int SERVER = 1;
	public static int CLIENT = 2;
	public static int LOCAL = 0;

	private int netState = LOCAL;
	
	BlockGame game;
	Vector3 mouse;
	
	Sprite menu;
	Sprite title;
	Sprite play;
	Sprite exit;
	Sprite select;
	
	boolean playSelected;
	boolean exitSelected;
	
	public MenuScreen(BlockGame game) {
		this.game = game;
		
		mouse = new Vector3();
		
		menu = new Sprite(new Texture("gfx/menu/menu.png"));
		title = new Sprite(new Texture("gfx/menu/title.png"));
		title.flip(false, true);
		play = new Sprite(new Texture("gfx/menu/play.png"));
		play.flip(false, true);
		exit = new Sprite(new Texture("gfx/menu/exit.png"));
		exit.flip(false, true);
		select = new Sprite(new Texture("gfx/menu/select.png"));
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.35f,0f,0f,1f);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		tick();
		draw();
	}
	
	public void tick() {
		mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		game.camera.unproject(mouse);
		
		if(mouse.x>BlockGame.WIDTH/2-play.getWidth()/2 && 
				mouse.x<BlockGame.WIDTH/2+play.getWidth()/2 &&
				mouse.y>80 &&
				mouse.y<80+play.getHeight()){
			playSelected = true;
			exitSelected = false;
			
			if(Gdx.input.isTouched()) {
				this.dispose();
				game.setScreen(new GameScreen(game, netState));
			}
			
		}
		else if(mouse.x>BlockGame.WIDTH/2-exit.getWidth()/2 && 
				mouse.x<BlockGame.WIDTH/2+exit.getWidth()/2 &&
				mouse.y>120 &&
				mouse.y<120+exit.getHeight()){
			playSelected = false;
			exitSelected = true;
			
			if(Gdx.input.isTouched()) {
				this.dispose();
				Gdx.app.exit();
			}
			
		}
		else {
			playSelected = false;
			exitSelected = false;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			netState = SERVER;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.C)) {
			netState = CLIENT;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.L)) {
			netState = LOCAL;
		}
	}
	
	public void draw() {
		game.batch.begin();
		
		game.batch.draw(menu, 0, 0);
		
		if(playSelected)
			game.batch.draw(select, BlockGame.WIDTH/2-play.getWidth()/2-2, 80-2);
		if(exitSelected)
			game.batch.draw(select, BlockGame.WIDTH/2-play.getWidth()/2-2, 120-4);
		
		game.batch.draw(title, BlockGame.WIDTH/2-title.getWidth()/2, 20);
		game.batch.draw(play, BlockGame.WIDTH/2-play.getWidth()/2, 80);
		game.batch.draw(exit, BlockGame.WIDTH/2-exit.getWidth()/2, 120);
		
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
		menu.getTexture().dispose();
		title.getTexture().dispose();
		play.getTexture().dispose();
		exit.getTexture().dispose();
		select.getTexture().dispose();
	}

}
