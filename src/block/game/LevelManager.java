package block.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

import block.game.entities.Enemy;
import block.game.entities.Player;
import block.game.movement.FollowMovement;
import block.game.movement.Movement;

public class LevelManager {

	private static Circle circle = new Circle(World.OFF_SCREEN_X, World.OFF_SCREEN_Y, 5);
	private static Texture entitySheet = new Texture("gfx/game/entity-sheet.png");
	private static Sprite red1Sprite = new Sprite(entitySheet, 16, 0, 16, 16);

	Movement[][] movements;
	CharSequence[] instructions;
	ArrayList<Movement> stage4;

	public LevelManager(World world) {
		stage4 = new ArrayList<Movement>();
		for(int i = 0; i < 25; i++) {
			stage4.add(new Movement(new Enemy("3", red1Sprite, 400, circle, 280-i*10, 219+i*100), true, new float[]{280-i*10,219,281-i*10,21}));
		}
		
		Movement[] stage4Arr = new Movement[stage4.size()];
		stage4Arr = stage4.toArray(stage4Arr);
		
		movements = new Movement[][]{
			{
				new Movement(new Enemy("1", red1Sprite, 110, circle,152,24), true, new float[]{152,24,248,119,152,214,57,119}),
				new Movement(new Enemy("1", red1Sprite, 110, circle,248,119), true, new float[]{248,119,152,214,57,119,152,24}),
				new Movement(new Enemy("1", red1Sprite, 110, circle,152,214), true, new float[]{152,214,57,119,152,24,248,119}),
				new Movement(new Enemy("1", red1Sprite, 110, circle,57,119), true, new float[]{57,119,152,24,248,119,152,214})
			},
			{
				new FollowMovement(new Enemy("2", red1Sprite, Player.DEFAULT_SPEED*.7f, circle), world, World.GOAL_X+world.getGoalBounds().width/2, World.GOAL_Y+world.getGoalBounds().height/2)
			},
			stage4Arr,
		};

		instructions = new CharSequence[] {
			"WASD To Move",
			"Avoid the Enemies",
			"Beware of Different Enemies",
			"Space to Slow Down",
			"Shift to Dash"
		};
	}

	public Movement[] getLevel(int level) {
		return movements[level];
	}

	public CharSequence getInstructions(int level) {
		return instructions[level];
	}

}
