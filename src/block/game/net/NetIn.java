package block.game.net;

import java.io.BufferedReader;
import java.io.IOException;

import block.game.World;

public class NetIn implements Runnable{

	String message;
	Thread thread;
	BufferedReader streamIn;
	
	boolean running;
	World world;

	public NetIn(BufferedReader streamIn, World world) {
		this.streamIn = streamIn;
		this.world = world;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		running = true;
		
		while(running) {
			try {
				message = streamIn.readLine();
				parseMessage();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void parseMessage() {
		int x = Integer.parseInt(message.substring(0, message.indexOf('-')));
		int y = Integer.parseInt(message.substring(message.indexOf('-')+1, message.indexOf('/')));
		int stage = Integer.parseInt(message.substring(message.indexOf('/')+1, message.length()));
		
		world.getPlayer(1).setPosition(x, y);
		if(world.stage<stage) {
			world.stage = stage;
		}
		
	}

}
