package block.game.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import block.game.World;

public class NetOut{
	
	final String HOSTNAME = "192.168.0.94";
	final int PORT = 8888;
	
	String message;
	PrintStream streamOut;
	NetIn streamIn;
	Socket socket;
	Thread thread;
	World world;
	
	public NetOut(World world, boolean hosting) {
		this.world = world;
		try {
			if(!hosting) {
				this.socket = new Socket(HOSTNAME,PORT);
			} else {
				this.socket = new ServerSocket(PORT).accept();
			}
			
			world.addPlayer();
			
			setStreams();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setStreams() throws IOException {
		streamOut = new PrintStream(socket.getOutputStream());	
		streamIn = new NetIn(new BufferedReader(new InputStreamReader(socket.getInputStream())), world);
	}
	
	public void writeToStreams(World world) {
		message = Integer.toString((int)world.getPlayer().xPos) + "-" + (Integer.toString((int)world.getPlayer().yPos) + "/" + Integer.toString((int)world.stage));
		streamOut.println(message);
	}

}
