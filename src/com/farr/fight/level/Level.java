package com.farr.fight.level;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.farr.Events.Event;
import com.farr.Events.EventDispatcher;
import com.farr.Events.BlockingLayer;
import com.farr.Events.types.KeyPressedEvent;
import com.farr.Events.types.KeyReleasedEvent;
import com.farr.Events.types.MousePressedEvent;
import com.farr.fight.Game;
import com.farr.fight.entity.Entity;
import com.farr.fight.entity.mob.player.Player;
import com.farr.fight.graphics.Screen;
import com.farr.fight.level.Tile.Tile;
import com.farr.fight.net.NetPlayer;
import com.farr.fight.util.ImageUtils;

public class Level implements BlockingLayer {
	
	//TODO make abstract
	
	public List<Entity> entities = new ArrayList<Entity>();
	public List<NetPlayer> connectedPlayers = new ArrayList<NetPlayer>();	//All entities are in the entities list, including players, for rendering, etc. I elected to have different sub sets exist in sub lists, not mutually exclude them
	
	
	public Player clientPlayer = null;
	
	public Screen screen;
	public int screenWidth = Game.gameWidth;
	public int screenHeight = Game.gameHeight - 30*Game.scale;
	protected int mapWidth, mapHeight;
	private int xScroll = 0; 
	private int yScroll = 0;
	private Game game;
	
	protected BufferedImage map;
	protected int[] mapPixels;
	
	protected int width, height;	//Level width and height, in tiles
	protected Tile[] tiles;
	
	public Level(Game game) {
		screen = new Screen(Game.gameWidth, Game.gameHeight - 30*3, 3);
		this.game = game;
	}
	
	public Player getClientPlayer() {
		try {
			return clientPlayer;
		} catch (Exception e) {
			return null;
		}
	}
	
//	public void updateClientPlayer(Player p) {
//		
//		clientPlayer = p;		
//		connectedPlayers.add(0, p);
//		
//	}
	
	public void addClientPlayer(Player p) {
		addEntity(p);
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public void loadMapFromFile(String path) {
		map = ImageUtils.loadImageFromFile(path);
		mapWidth = map.getWidth();
		mapHeight = map.getHeight();
		
		tiles = new Tile[mapWidth * mapHeight];
		
	}
	
	protected void generateTiles() {
	}
	
	public Tile getTile(int x, int y) {
		
		if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) 
			return Tile.voidTile;
		
		return tiles[x + y * mapWidth];
	}
	
	public boolean update() {
		
		for (Entity e : entities) {
			e.update();
		}
		return false;
	}
	
	public void render(Graphics g) {
		//TODO render screen
		
		//TODO render (applicable) entities
		
		if (clientPlayer != null) setScroll(((int) clientPlayer.x) - screenWidth/(Game.scale * 2), ((int) clientPlayer.y) - screenHeight/(Game.scale * 2)); //- (screenWidth/2) - (screenHeight/2)
		screen.setOffset(xScroll, yScroll);
		screen.clear(0x0);
		
		renderVisibleTiles(screen);	//render the tile map
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		
		screen.pack();
		g.drawImage(screen.image, 0, 30*3, screenWidth * Game.scale, screenHeight * Game.scale, null);
		
	}
	
	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}
	
	public void renderVisibleTiles(Screen screen) {
		int x0 = (xScroll >> 4);
		int y0 = (yScroll >> 4);
		int x1 = ((xScroll + screenWidth / Game.scale) >> 4) + 1;
		int y1 = ((yScroll + screenHeight / Game.scale)>> 4) + 1;
		
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		
	}

//	private void Scroll() {
//		if (scrollBool[0]) yScroll--;
//		if (scrollBool[2]) yScroll++;
//		if (scrollBool[1]) xScroll--;
//		if (scrollBool[3]) xScroll++;
//	}
	
	@Override
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.KEY_PRESSED, (Event e) -> onKeyPress((KeyPressedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_RELEASED, (Event e) -> onKeyRelease((KeyReleasedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePress((MousePressedEvent) e));
		
	}

	
//	private boolean[] scrollBool = new boolean[4];
	private void onKeyPress(KeyPressedEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.gotoMain();
		
		//TODO implement this with a server, to send the events to a server.
		clientPlayer.onKeyPress(e);
		
	}
	
	private void onKeyRelease(KeyReleasedEvent e) {
		
		//TODO implement this with a server, to send the events to a server.
		clientPlayer.onKeyRelease(e);
		
	}
	
	private void onMousePress(MousePressedEvent e) {
//		System.out.println(e.getButton());
	}

	@Override
	public void init(List<BlockingLayer> l) {
		
	}
	
}
