package block.game.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import block.game.World;
import block.game.entities.Entity;

public class Movement {
	
	public Entity entity;
	public World world;
	public int targetNode;
	public float[] points;
	public float slope, xCurrent, yCurrent, xTarget, yTarget, speed, distance, displacement, xDirection, yDirection;
	public boolean loop, userControlled, finished, scripted;
	
	public Movement(Boolean scripted, Entity entity, World world) {
		userControlled = false;
		this.scripted = scripted;
		this.entity = entity;
		this.world = world;
	}
	
	public Movement(Boolean scripted, Entity entity, boolean loop) {
		userControlled = false;
		this.scripted = scripted;
		this.entity = entity;
		this.loop = loop;
	}

	public Movement(Boolean scripted, Entity entity, boolean loop, float[] points) {
		userControlled = false;
		this.scripted = scripted;
		this.entity = entity;
		this.points = points;
		this.loop = loop;
		init();
	}
	
	public void init() {
		targetNode = 2;
		setTarget(entity.xPos,entity.yPos,points[targetNode],points[targetNode+1]);
		entity.setPosition(xCurrent, yCurrent);
		finished = false;
		speed = entity.speed;
	}
	
	public void tick(World world, float delta) {
		checkTarget(Gdx.graphics.getDeltaTime());
		move();
	}
	
	public void draw(SpriteBatch batch) {
		entity.draw(batch);
	}
	
	public void move() {
		if(userControlled) {
			entity.tick(world, Gdx.graphics.getDeltaTime());
		}
		else {
			if(finished)return;
			
			float directionX = xDirection;
			float directionY = (Math.abs(slope)/slope)/(Math.abs(directionX)/directionX);
			
			if(Math.abs(slope)>=1) {
				float x = Math.abs((speed*Gdx.graphics.getDeltaTime())/slope)*directionX;
				float y = speed*Gdx.graphics.getDeltaTime()*directionY;
				if(Float.isNaN(x)) x = 0;
				if(Float.isNaN(y)) y = 0;
				entity.xPos+=x;
				entity.yPos+=y;
			} else {
				float x = speed*Gdx.graphics.getDeltaTime()*directionX;
				float y = Math.abs(slope*speed*Gdx.graphics.getDeltaTime())*directionY;
				if(Float.isNaN(x)) x = 0;
				if(Float.isNaN(y)) y = 0;
				entity.xPos+=x;
				entity.yPos+=y;
			}
			entity.tick(world, Gdx.graphics.getDeltaTime());
		}
	}
	
//	public void checkTarget(float delta) {
//		if(passGoal() && targetNode==0 && !loop) {
//			finished = true;
//		}
//		if(passGoal() && !(targetNode >= points.length-2)) {
//			targetNode+=2;
//			setTarget(points[targetNode-2],points[targetNode-1],points[targetNode],points[targetNode+1]);
//		}
//		if(passGoal() && targetNode >= points.length-2) {
//			targetNode = 0;
//			setTarget(points[points.length-2],points[points.length-1],points[targetNode],points[targetNode+1]);
//		}
//	}
	
	public void checkTarget(float delta) {
		if(passGoal() && targetNode==0 && !loop) {
			finished = true;
		}
		if(passGoal() && !(targetNode >= points.length-2)) {
			targetNode+=2;
			setTarget(entity.xPos,entity.yPos,points[targetNode],points[targetNode+1]);
		}
		if(passGoal() && targetNode >= points.length-2) {
			targetNode = 0;
			setTarget(entity.xPos,entity.yPos,points[targetNode],points[targetNode+1]);
		}
	}
	
	private boolean passGoal() {
		boolean x = xDirection > 0 ? entity.xPos >= xTarget : entity.xPos <= xTarget;
		boolean y = yDirection > 0 ? entity.yPos >= yTarget : entity.yPos <= yTarget;
		if(x&&y) {
			entity.xPos = xTarget;
			entity.yPos = yTarget;
		}
		return x && y;
	}
	
	public void setTarget(float xCurrent, float yCurrent, float xTarget, float yTarget) {
		this.xCurrent = xCurrent;
		this.yCurrent = yCurrent;
		this.xTarget = xTarget;
		this.yTarget = yTarget;
		
		xDirection = xTarget>xCurrent ? 1:-1;
		yDirection = yTarget>yCurrent ? 1:-1;
		
		slope = (yCurrent-yTarget)/(xCurrent-this.xTarget);
		if(Float.isNaN(slope)) slope = 1;
	}
	
	private void reduceTarget() {};

}
