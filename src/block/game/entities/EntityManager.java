package block.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import block.game.World;
import block.game.movement.Movement;

public class EntityManager {
	
	public ArrayList<Movement> enemyList;
	private ArrayList<Player> playerList;
	
	private Texture entityTexture;
	private Sprite entitySprite;

	public EntityManager() {
		enemyList = new ArrayList<Movement>();
		playerList = new ArrayList<Player>();
		init();
	}
	
	public void init() {
		entityTexture = new Texture("gfx/game/entity.png");
		entitySprite = new Sprite(entityTexture);
		
		playerList.add(new Player(entitySprite, true));
	}

	public void tick(World world, float delta) {
		if(world.stage>1/*temporary*/) {
			for(Movement e: enemyList) {
				e.tick(world, delta);

				if(e.entity.bounds instanceof Rectangle) {
					if(Intersector.overlaps((Rectangle) e.entity.bounds, playerList.get(0).bounds)) {
						playerList.get(0).spawnOut(null);
						world.death();
					}
				}
				else {
					
					if(Intersector.overlaps((Circle) e.entity.bounds, playerList.get(0).bounds) && !playerList.get(0).noHit) {
						playerList.get(0).spawnOut(null);
						world.death();
					}
				}
				
				if(Intersector.overlaps((Circle) e.entity.bounds, world.getSpawnBounds())) {
					
				}
			}

			
		}
		playerList.get(0).tick(world, delta);
	}

	public void draw(World world, SpriteBatch batch) {
		if(world.stage>1/*temporary*/) {
			for(Movement e: enemyList) {
				e.draw(batch);
			}
		}
		
		for(Player p: playerList) {
			p.draw(batch);
		}
	}

	public boolean isColliding(Polygon poly) {
		return false;

	}

	public void dispose() {

	}
	
	public void addPlayer() {
		playerList.add(new Player(entitySprite, false));
	}
	
	public Player getPlayer() {
		return playerList.get(0);
	}
	
	public Player getPlayer(int num) {
		return playerList.get(num);
	}
	
	public void setPlayerPosition(float x, float y) {
		playerList.get(0).setPosition(x, y);
	}

}
