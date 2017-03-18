package block.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Shape;

import block.game.World;

public abstract class Entity {
	
	public final int BOUND_X_OFFSET = 2;
	public final int BOUND_Y_OFFSET = 2;
	public final int BOUND_WIDTH_OFFSET = -4;
	public final int BOUND_HEIGHT_OFFSET = -4;
	
	public Sprite graphic;
	public Animation animation;
	public TextureRegion[] frames;
	
	public float speed;
	public float xPos;
	public float yPos;
	
	public Shape2D bounds;
	
	public Entity(Sprite graphic, float speed) {
		this.graphic = graphic;
		this.speed = speed;
	}
	
	public Entity(Animation animation, float speed) {
		this.animation = animation;
		this.speed = speed;
	}
	
	public abstract void tick(World world, float delta);
	public abstract void draw(SpriteBatch batch);
	public abstract boolean isColliding(Polygon poly);
	public abstract void dispose();
	
	public void setPosition(float xPos, float yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public static Polygon toPoly(Rectangle rect) {
		return new Polygon(new float[]{rect.x,rect.y,rect.x+rect.width,rect.y,rect.x+rect.width,rect.y+rect.height,rect.x,rect.y+rect.height});
	}
	
}
