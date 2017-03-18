package block.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import block.game.entities.Enemy;
import block.game.entities.Player;
import block.game.movement.FollowMovement;
import block.game.movement.Movement;

public class LevelManager {

	private static Circle circle = new Circle(World.OFF_SCREEN_X, World.OFF_SCREEN_Y, 5);
	private static Texture entitySheet = new Texture("gfx/game/entity-sheet.png");
	private static Sprite red1Sprite = new Sprite(entitySheet, 16, 0, 16, 16);
	
	private static TextureRegion[] frames = TextureRegion.split(entitySheet, 16, 16)[1];
	private static Animation animation = new Animation(.05f, frames);
	
	Movement[][] movements;
	CharSequence[] instructions;
	ArrayList<Movement> stage4;
	ArrayList<Movement> stage5;

	public LevelManager(World world) {
		stage4 = new ArrayList<Movement>();
		for(int i = 0; i < 25; i++) {
			stage4.add(new Movement(false, new Enemy("3", red1Sprite, 400, circle, 280-i*10, 219+i*100), true, new float[]{280-i*10,219,281-i*10,21}));
		}
		
		stage5 = new ArrayList<Movement>();
		for(int i = 0; i < 15; i++) {
			stage5.add(new Movement(false, new Enemy("3", red1Sprite, 0, circle, BlockGame.WIDTH/2-8, 217-i*15), true, new float[]{1,1,1,1}));
		}
		
		Movement[] stage4Arr = new Movement[stage4.size()];
		stage4Arr = stage4.toArray(stage4Arr);
		
		Movement[] stage5Arr = new Movement[stage5.size()];
		stage5Arr = stage5.toArray(stage5Arr);
		
		movements = new Movement[][]{
			{
				new Movement(true, new Enemy("1", red1Sprite, 110, circle,152,24), true, new float[]{152,24,248,119,152,214,57,119}),
				new Movement(true, new Enemy("1", red1Sprite, 110, circle,248,119), true, new float[]{248,119,152,214,57,119,152,24}),
				new Movement(true, new Enemy("1", red1Sprite, 110, circle,152,214), true, new float[]{152,214,57,119,152,24,248,119}),
				new Movement(true, new Enemy("1", red1Sprite, 110, circle,57,119), true, new float[]{57,119,152,24,248,119,152,214})
			},
			{
				new FollowMovement(new Enemy("2", red1Sprite, Player.DEFAULT_SPEED*.7f, circle), world, World.GOAL_X+world.getGoalBounds().width/2, World.GOAL_Y+world.getGoalBounds().height/2)
			},
			stage4Arr,
			stage5Arr,
			{
				new FollowMovement(new Enemy("4", animation, frames, Player.DEFAULT_SPEED*1.165f, circle), world, World.GOAL_X+world.getGoalBounds().width/2, World.GOAL_Y+world.getGoalBounds().height/2)
			},
		};

		instructions = new CharSequence[] {
			"WASD To Move",
			"Avoid the Enemies",
			"Beware of Different Enemies",
			"Space to Slow Down",
			"Shift to Dash",
			"Most Enemies Will Have Difficult Variants",
			"Good luck",
			"nothing here for now"
		};
	}

	public Movement[] getLevel(int level) {
		return movements[level];
	}

	public CharSequence getInstructions(int level) {
		return instructions[level];
	}

}
