package block.game.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import block.game.BlockGame;
import block.game.World;
import block.game.entities.Entity;

public class FollowMovement extends Movement{

	public FollowMovement(Entity entity, World world, float xPos, float yPos) {
		super(entity, true);
		entity.setPosition(xPos, yPos);
		speed = entity.speed;
	}
	
	public void tick(World world, float delta) {
		if(world.getPlayer().xPos>BlockGame.WIDTH || world.getSpawnBounds().contains(world.getPlayer().bounds)) {
			setTarget(entity.xPos, entity.yPos, World.GOAL_X+world.getGoalBounds().width/2, World.GOAL_Y+world.getGoalBounds().height/2);
			
		}
		else {
			setTarget(entity.xPos, entity.yPos, (world.getPlayer().xPos+entity.BOUND_X_OFFSET)+(world.getPlayer().bounds.width+(entity.BOUND_WIDTH_OFFSET)/2), 
					(world.getPlayer().yPos+entity.BOUND_Y_OFFSET)+((world.getPlayer().bounds.height+entity.BOUND_HEIGHT_OFFSET)/2));
		}
		super.move();
	}

}
