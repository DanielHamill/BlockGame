package block.game.movement;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import block.game.BlockGame;
import block.game.World;
import block.game.entities.Entity;

public class FollowMovement extends Movement{
	
	private ArrayList<Float> target;
	private int slack;
	private boolean chasing;

	public FollowMovement(Entity entity, World world, float xPos, float yPos, int slack) {
		super(false, entity, true);
		entity.setPosition(xPos, yPos);
		speed = entity.speed;
		
		target = new ArrayList<Float>();
		this.slack = slack;
		chasing = false;
	}
	
	public void tick(World world, float delta) {
		target.add(0, world.getPlayer().xPos);
		target.add(1, world.getPlayer().yPos);
		if(target.size()>slack*2) {
			target.remove(target.size()-1);
			target.remove(target.size()-1);
		}
		float xAve = 0;
		float yAve = 0;
		for(int i=0; i<target.size(); i++) {
			if(i%2==0) {
				xAve += target.get(i);
			}
			else {
				yAve += target.get(i);
			}
		}
		xAve /= target.size()/2;
		yAve /= target.size()/2;
		
		if(world.getPlayer().xPos>BlockGame.WIDTH || world.getSpawnBounds().contains(world.getPlayer().bounds)) {
			setTarget(entity.xPos, entity.yPos, World.GOAL_X+world.getGoalBounds().width/2, World.GOAL_Y+world.getGoalBounds().height/2);
			
		}
		else {
			setTarget(entity.xPos, entity.yPos, (xAve+entity.BOUND_X_OFFSET)+(world.getPlayer().bounds.width+(entity.BOUND_WIDTH_OFFSET)/2), 
					(yAve+entity.BOUND_Y_OFFSET)+((world.getPlayer().bounds.height+entity.BOUND_HEIGHT_OFFSET)/2));
			chasing = true;
		}
		distance = (float) Math.sqrt((entity.xPos-xTarget)*(entity.xPos-xTarget) + (entity.yPos-yTarget)*(entity.yPos-yTarget));
		if(distance <=2 && target.size() > 20) {
			for(int i = 0; i<20; i++) {
				target.remove(target.size()-1);
			}
		}
		else if(distance <=2) {
			
		}
		else {
			super.move();
		}
	}
	
}
