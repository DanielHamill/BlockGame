package block.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import block.game.World;

public class Player extends Entity {
	// Abilities
	public static final float DEFAULT_SPEED = 100;
	public static final float BLINK_SPEED = 450;
	boolean verticalMove;
	boolean horizontalMove;
	
	boolean local;
	float speed;
	float slowSpeed;
	
	public Rectangle bounds;
	private Texture playerOutTex;
	private Texture playerInTex;
	private TextureRegion[] outFrames;
	private TextureRegion[] inFrames;
	private Animation playerOut;
	private Animation playerIn;
	private float time;
	
	public boolean animOut;
	public boolean animIn;
	boolean still;
	
	boolean noHit;
	boolean blinking;
	int blinkFrames;
	float cooldown;
	
	public Player(Sprite player, Boolean local) {
		super(player, DEFAULT_SPEED);
		verticalMove = false;
		horizontalMove = false;
		this.local = local;
		xPos = 288;
		yPos = 208;
		this.speed = DEFAULT_SPEED;
		slowSpeed = this.speed/2.5f;
		time = 0;
		bounds = new Rectangle(xPos+BOUND_X_OFFSET, yPos+BOUND_Y_OFFSET, graphic.getWidth()+BOUND_WIDTH_OFFSET, graphic.getHeight()+BOUND_HEIGHT_OFFSET);
		
		playerOutTex = new Texture("gfx/game/player-animation-out.png");
		outFrames = TextureRegion.split(playerOutTex, 16, 16)[0];
		playerOut = new Animation(0.02f, outFrames);
		playerOut.setPlayMode(PlayMode.LOOP);
		
		playerInTex = new Texture("gfx/game/player-animation-in.png");
		inFrames = TextureRegion.split(playerInTex, 16, 16)[0];
		playerIn = new Animation(0.02f, inFrames);
		playerIn.setPlayMode(PlayMode.LOOP);
		
		still = false;
		noHit = false;
		blinkFrames = 0;
		blinking = false;
		cooldown = 2;
		
	}

	@Override
	public void tick(World world, float delta) {
		if(local&&!still) {
			
			float x = xPos;
			float y = yPos;
			
			if(Gdx.input.isKeyPressed(Input.Keys.W)||Gdx.input.isKeyPressed(Input.Keys.UP)) {
				yPos -= speed*delta;
			}
			if(Gdx.input.isKeyPressed(Input.Keys.A)||Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				xPos -= speed*delta;
			}
			if(Gdx.input.isKeyPressed(Input.Keys.S)||Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				yPos += speed*delta;
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)||Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				xPos += speed*delta;
			}
			
			horizontalMove = (x!=xPos) ? true : false;
			verticalMove = (y!=yPos) ? true : false;
		
			bounds.setPosition(xPos+BOUND_X_OFFSET, yPos+BOUND_Y_OFFSET);
			
			if(isColliding(world.getWorldBounds()[0])) {
				xPos = world.getWorldBounds()[0].x+world.getWorldBounds()[0].width-2;
			}
			if(isColliding(world.getWorldBounds()[1])) {
				xPos = world.getWorldBounds()[1].x-bounds.width-2;
			}
			if(isColliding(world.getWorldBounds()[2])) {
				yPos = world.getWorldBounds()[2].y+world.getWorldBounds()[2].height-2;
			}
			if(isColliding(world.getWorldBounds()[3])) {	
				yPos = world.getWorldBounds()[3].y-bounds.height-2;
			}
			
			blink();
			slow();
			
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		
		if(animOut) {
			spawnOut(batch);
		}
		else if (animIn) {
			spawnIn(batch);
		}
		else {
			batch.draw(graphic, xPos, yPos);
		}
	}
	
	void blink() {
		int distance = 7;
		
		if(Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT) && cooldown >= 2) {
			blinking = true;
			cooldown = 0;
		}
		
		if(blinking) {
			noHit = true;
			blinkFrames++;
			speed = BLINK_SPEED;
		}
		else {
			noHit = false;
			speed = DEFAULT_SPEED;
		}
		
		if((horizontalMove && verticalMove) && blinkFrames>=(distance/1.414)) {
			blinking=false;
			blinkFrames = 0;
		}
		else if(blinkFrames>=distance) {
			blinking=false;
			blinkFrames = 0;
		}
		cooldown += Gdx.graphics.getDeltaTime();
	}
	
	void slow() {
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			speed = slowSpeed;
		}
	}

	@Override
	public boolean isColliding(Polygon poly) {
		return Intersector.intersectPolygons(Player.toPoly(bounds), poly, new Polygon());
	}
	
	public void spawnOut(SpriteBatch batch) {
		if(batch != null) {
			batch.draw(outFrames[playerOut.getKeyFrameIndex(time)], xPos, yPos);
			time += Gdx.graphics.getDeltaTime();
			still = true;
		}
		animOut = true;
		if(time > playerOut.getAnimationDuration()) {
			animOut = false;
			xPos = World.SPAWN_X;
			yPos = World.SPAWN_Y;
			time = 0;
			still = false;
			spawnIn(null);
		}
	}
	
	public void spawnIn(SpriteBatch batch) {
		if(batch != null) {
			batch.draw(inFrames[playerIn.getKeyFrameIndex(time)], xPos, yPos);
			time += Gdx.graphics.getDeltaTime();
			still = true;
//			xPos = World.SPAWN_X;
//			yPos = World.SPAWN_Y;
		}
		animIn = true;
		if(time > playerIn.getAnimationDuration()) {
			animIn = false;
			time = 0;
			still = false;
		}
	}
	
	public boolean isColliding(Rectangle rect) {
		return bounds.overlaps(rect);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	public boolean isSpawining() {
		return animIn||animOut;
	}

}
