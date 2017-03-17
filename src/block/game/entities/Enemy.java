package block.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

import block.game.World;

public class Enemy extends Entity {
	
	public String tag;

	public Enemy(String tag, Sprite graphic, float speed, float xPos, float yPos) {
		super(graphic, speed);
		this.tag = tag;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public Enemy(String tag, Sprite graphic, float speed, Shape2D bounds) {
		super(graphic, speed);
		this.tag = tag;
		this.bounds = bounds;
	}
	
	public Enemy(String tag, Sprite graphic, float speed, Shape2D bounds, float xPos, float yPos) {
		super(graphic, speed);
		this.tag = tag;
		this.bounds = bounds;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	@Override
	public void tick(World world, float delta) {
		if(bounds instanceof Rectangle) {
			((Rectangle)bounds).setPosition(xPos+BOUND_X_OFFSET, yPos+BOUND_Y_OFFSET);
		}
		else if(bounds instanceof Circle) {
			((Circle)bounds).setPosition(xPos, yPos);
		}
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		if(bounds instanceof Circle) {
			batch.draw(graphic, xPos-8, yPos-8);
		}
		else {
			batch.draw(graphic, xPos, yPos);
		}
	}

	@Override
	public boolean isColliding(Polygon poly) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	public String getTag() {
		return tag;
	}

}