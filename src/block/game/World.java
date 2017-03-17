package block.game;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import block.game.entities.EntityManager;
import block.game.entities.Player;
import block.game.movement.Movement;
import block.game.net.NetOut;
import block.game.screens.GameScreen;
import block.game.screens.MenuScreen;

public class World {
	
	public static final int SPAWN_X = 288;
	public static final int SPAWN_Y = 208;
	public static final int GOAL_X = 16;
	public static final int GOAL_Y = 16;
	public static final int OFF_SCREEN_X = 10*BlockGame.WIDTH;
	public static final int OFF_SCREEN_Y = 10*BlockGame.HEIGHT;
	
	private GameScreen gameScreen;
	
	private Sprite backGround;
	private Sprite walls;
	private Rectangle[] worldBounds;
	
	private EntityManager entityManager;
	private LevelManager levelManager;
	private CharSequence instructions;
	
	private Texture worldSheet;
	private Sprite spawn;
	private Sprite goal;
	private Rectangle spawnBounds;
	private Rectangle goalBounds;
	
	public int stage;
	private boolean inGoal;

	private NetOut network;
	
	private String pass;
	private boolean devMode;
	private float time;
	
	private BitmapFont font;
	private CharSequence displayLevel;
	
	public World(GameScreen game, int netState) {
		this.gameScreen = game;

		worldSheet = new Texture("gfx/game/world-sheet.png");
		
		backGround = new Sprite(worldSheet, 320, 0, 320, 240);
		walls = new Sprite(worldSheet, 0, 0, 320, 240);
		entityManager = new EntityManager();

		worldBounds = new Rectangle[]{new Rectangle(0,16,16,224), new Rectangle(304,16,16,224), new Rectangle(16,0,288,16), new Rectangle(16,224,288,16)};
		
		spawn = new Sprite(worldSheet, 16, 240, 16, 16);
		spawnBounds = new Rectangle(SPAWN_X, SPAWN_Y, spawn.getWidth()+2, spawn.getHeight()+2);
		
		goal = new Sprite(worldSheet, 32, 240, 16, 16);
		goalBounds = new Rectangle(GOAL_X-2, GOAL_Y-2, goal.getWidth()+2, goal.getHeight()+2);
		
		levelManager = new LevelManager(this);
		
		this.setStage(1);
		
		if(netState == MenuScreen.SERVER) {
			network = new NetOut(this, true);
		}
		else if(netState == MenuScreen.CLIENT) {
			network = new NetOut(this, false);
		}
		
		pass = "";
		devMode = false;
		
		font = new BitmapFont(true);
		font.getData().setScale(0.8f);
		font.setColor(117, 117, 117, 1);
		displayLevel = Integer.toString(stage);
	}
	
	public void tick(float delta) {
		
		entityManager.tick(this, Gdx.graphics.getRawDeltaTime());
		if(goalBounds.contains(entityManager.getPlayer().bounds)) {
			entityManager.getPlayer().spawnOut(null);
			inGoal = true;
		}
		else {
			if(inGoal) {
				stageUp();
				inGoal = false;
			}
		}
		if(network!=null) {
			network.writeToStreams(this);
		}
		
		devModeStuff();
		
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(backGround, 0, 0);
		batch.draw(spawn, SPAWN_X, SPAWN_Y);
		batch.draw(goal, GOAL_X, GOAL_Y);
		font.draw(batch, instructions, BlockGame.WIDTH/2-instructions.length()*3f, BlockGame.HEIGHT/2-font.getCapHeight());
		entityManager.draw(this, batch);
		batch.draw(walls, 0, 0);
		font.draw(batch, displayLevel, BlockGame.WIDTH/2-displayLevel.length()*7, 3);
	}
	
	public void stageUp() {
		stage++;
		if(stage-2>levelManager.movements.length-1)
			entityManager.enemyList = new ArrayList<Movement>();
		else 
			entityManager.enemyList = new ArrayList<Movement>(Arrays.asList(levelManager.getLevel(stage-2)));
		displayLevel = Integer.toString(stage);
		
		if(stage-1>levelManager.instructions.length-1)
			instructions = "";
		else 
			instructions = levelManager.getInstructions(stage-1);
		
	}
	
	public void setStage(int num) {
		stage = num;
		if(stage-2>levelManager.movements.length-1||stage-2<levelManager.movements.length-1)
			entityManager.enemyList = new ArrayList<Movement>();
		else 
			entityManager.enemyList = new ArrayList<Movement>(Arrays.asList(levelManager.getLevel(stage-2)));
		
		instructions = levelManager.getInstructions(stage-1);
	}
	
	public Rectangle[] getWorldBounds() {
		return worldBounds;
	}

	public Rectangle getSpawnBounds() {
		return spawnBounds;
	}

	public Rectangle getGoalBounds() {
		return goalBounds;
	}
	
	public void addPlayer() {
		entityManager.addPlayer();
	}
	
	public Player getPlayer() {
		return entityManager.getPlayer();
	}
	
	public Player getPlayer(int num) {
		return entityManager.getPlayer(num);
	}

	public void dispose() {
		
	}
	
	public void devModeStuff() {
		String temp = pass;
		if(Gdx.input.isKeyJustPressed(Keys.D)) pass += "d";
		else if(Gdx.input.isKeyJustPressed(Keys.E)) pass += "e";
		else if(Gdx.input.isKeyJustPressed(Keys.V)) pass += "v";
		else if(Gdx.input.isKeyJustPressed(Keys.M)) pass += "m";
		else if(Gdx.input.isKeyJustPressed(Keys.O)) pass += "o";
		else if(Gdx.input.isKeyJustPressed(Keys.D)) pass += "d";
		else if(Gdx.input.isKeyJustPressed(Keys.E)) pass += "e";
		else if(Gdx.input.isKeyJustPressed(Keys.O)) pass += "o";
		else if(Gdx.input.isKeyJustPressed(Keys.F)) pass += "f";
		if (temp.equals(pass)) time += Gdx.graphics.getDeltaTime();
		else time = 0;
		
		if(time>1.5) {
			pass = "";
		}
		
		if(pass.equals("devmode")) {
			devMode = true;
		}
		
		if(pass.equals("devoff")) {
			devMode = false;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && devMode) {
			stageUp();
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) && devMode) {
			setStage(2);
		}
		
	}

}
