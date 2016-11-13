package com.farr.fight;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.farr.Events.Event;
import com.farr.Events.EventListener;
import com.farr.Events.Layer;
import com.farr.fight.entity.mob.player.Mage;
import com.farr.fight.entity.mob.player.Player;
import com.farr.fight.graphics.ui.UIButton;
import com.farr.fight.input.Focus;
import com.farr.fight.input.Keyboard;
import com.farr.fight.input.Mouse;
import com.farr.fight.layers.MenuLayer;
import com.farr.fight.level.Level;
import com.farr.fight.level.LobbyLevel;
import com.farr.fight.net.Client;
import com.farr.fight.util.ImageUtils;
import com.farr.fight.util.Vector2i;

public class Game extends Canvas implements Runnable, EventListener {

	private JFrame frame;	//ugh
	public final static String title = "Fight Right";
	
	//Really really oughta be loading this stuff from file.
	//TODO Ensure all "magic numbers" are loaded from a file. =
	//TODO add the serialization library so I can store data more usefully
	public static int scale = 3;
	public static int gameWidth = 300 * scale;
	public static int gameHeight = 188 * scale;
	
	public List<Layer> layerStack = new ArrayList<>();
	public static MenuLayer mainMenu;
	public static MenuLayer optionMenu;
	private Level testLevel;
	//TODO create reference here to the current game layer;
	
	Client client;
	
	private BufferedImage backgroundTransparent;
	
	private Thread thread;
	boolean running;
	
	private Keyboard key;
	
	private static final String IMAGE_FILES_PATH = "/images";
	
	private static final long serialVersionUID = 1L;

	public Game() {
		Dimension size = new Dimension(gameWidth, gameHeight);
		
		setPreferredSize(size);
		frame = new JFrame();
		
		key = new Keyboard(this);
		addKeyListener(key);
		
		Mouse mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		addFocusListener(new Focus(this));
		
		backgroundTransparent = ImageUtils.loadImageFromFile(IMAGE_FILES_PATH + "/bgTransparent.png");
		backgroundTransparent = ImageUtils.replaceColor(backgroundTransparent, 0xC0FF00FF, 0xC0000000, true);
		
		mainMenu = new MenuLayer(ImageUtils.loadImageFromFile(IMAGE_FILES_PATH + "/bgMenu.png"), this);
		mainMenu.addComponent(new UIButton(new Vector2i(300,250), new Vector2i(42*3,10*3), () -> {addLayer(optionMenu);}, "Options"));
		mainMenu.addComponent(new UIButton(new Vector2i(300,300), new Vector2i(42*3,10*3), () -> {removeLayer(mainMenu); addLayer(testLevel);}, "Start"));
		mainMenu.addComponent(new UIButton(new Vector2i(300,350), new Vector2i(42*3,10*3), () -> {connectToServer();}, "Connect"));
		
		optionMenu = new MenuLayer(backgroundTransparent, this);
		optionMenu.addComponent(new UIButton(new Vector2i(350,300), new Vector2i(72*3, 10*3), () -> gotoMain(), "Return to main"));
		
		Player p = new Mage(16,16);	//thing is, this will eventually become a net player.
		
		testLevel = new LobbyLevel(this);
		testLevel.addClientPlayer(p);
		testLevel.clientPlayer = p;	// TODO this is dirty and rotten
		System.out.println(testLevel.clientPlayer);
		
		addLayer(mainMenu);

//		BufferedImage transparentBG = ImageUtils.replaceColor(ImageUtils.loadImageFromFile(IMAGE_FILES_PATH + "/bgTransparent.png"), 0xC0FF00FF, 0xC0000000, true);
//		addLayer(new MenuLayer(transparentBG));
		
	}
	
	/**
	 * Removes all layers and returns to the main menu layer
	 */
	public void gotoMain() {
		layerStack.clear();
		layerStack.add(mainMenu);
	}
	
	//TODO update to let players choose ip
	public void connectToServer() {
		client = new Client("localhost", 25564);
		if (!client.connect()) {
			//TODO connection failed
			System.out.println("Failed to connect");
		}
	}
	
	//synchronized - to avoid memory conflicts, or overlapping. dont want to screw up.
	public synchronized void start() {
		
		running = true;
		
		//new thread of THIS game class, named "Display"
		thread = new Thread(this, "Game");
		thread.start();	//automatically runs the run() method as well
		
	}
	
	public synchronized void stop() {
		
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		
		final double updatesPerSec = 60.0;
		final double updatesPerSecRatio = 1_000_000_000/updatesPerSec;	// Every one billion nano seconds has 60 updates per second
		
//		final double framesPerSec = 60.0;	// Frame Rate cap
//		final double framesPerSecRatio = (framesPerSec == 0.0) ? 0 : 1_000_000_000/framesPerSec;

		long lastTime = System.nanoTime();
		long currentTime;
		double updateDelta = 0;
		
		long timer = System.currentTimeMillis();
		int updates = 0; 
		int frames = 0;
		
		while (running) {
			currentTime = System.nanoTime();
			updateDelta += (currentTime - lastTime) / updatesPerSecRatio;
			lastTime = currentTime;
			
			while(updateDelta >= 1) {
				update();
				updates++;
				updateDelta--;
			}
			
			//Frames are uncapped at the moment
			render();
			frames++;
			
			//Display the framerate and update rate every second
			while(System.currentTimeMillis() - timer >= 0) {
				timer += 1000;
				frame.setTitle(title + " DEBUG: FPS: " + frames + " UPS: " + updates);
				updates = 0;
				frames = 0;
			}
			
		}
		stop();
	}

	public void update() {
		
		//Updates from top layer to bottom. actually doesn't matter, and Im going to make it from bottom up.
		for (int i = layerStack.size() - 1; i >= 0; i--) {
			if (layerStack.get(i).update() == true) break;
		}
		
	}
	
	public void render() {
		
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;	//quit out for this iteration of rendering
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(new Color(0xFF00FF));
		g.fillRect(0, 0, gameWidth, gameHeight);
		
		g.setColor(new Color(0x0));
		g.setFont(new Font("Verdana", Font.PLAIN, 20));
		g.drawString("This screen should not be visible! Restart game!", 5, 20);
		
		for (int i = 0; i < layerStack.size(); i++) {
			//LAYERS ONLY USE ONE OF SCREEN, OR G. NEVER BOTH. This tells me they are separate layers- meaning I should split the interface up into two different
			//	render types. Alternatively, I structure this so that screen extends graphics... which would still let it draw stuff. BUt for now this oughta work
			layerStack.get(i).render(g);
			
		}
		
//		for (int i = 0; i < pixels.length; i++) {
//			pixels[i] = screen.pixels[i];
//		}
		
//		g.drawImage(image, screenX, screenY, width * scale, height * scale, null);	//Draw our screen view port
		
//		g.setFont(new Font("Helvetica", Font.BOLD, 72));
//		g.drawString("Pong", screen.width*3/2 - 90, screen.height*3/2 + 80);
		
		bs.show();
		g.dispose();
		
	}
	
	public void addLayer(Layer l) {
		layerStack.add(l);
		l.init(layerStack);
		
	}
	public void removeLayer(Layer l) {
		layerStack.remove(l);
	}
	public void removeLayer(int index) {
		if (index < layerStack.size() - 1)
			layerStack.remove(index);
	}
	
	public void onEvent(Event event) {
		
		//events go down the layer stack in reverse order
		for (int i = layerStack.size() - 1; i >= 0; i--) {
			layerStack.get(i).onEvent(event);
		}
	}
	
	public static void main(String args[]) {
		
		Game game = new Game();
		
		game.frame.setResizable(false);
		game.frame.setTitle(title);
		game.frame.add(game);	//adds "this" canvas component, our game, onto the frame
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
		
	}

}
