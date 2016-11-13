package com.farr.fight.level;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.farr.Events.Event;
import com.farr.Events.EventDispatcher;
import com.farr.Events.Layer;
import com.farr.Events.types.KeyPressedEvent;
import com.farr.Events.types.KeyReleasedEvent;
import com.farr.fight.Game;
import com.farr.fight.entity.Entity;
import com.farr.fight.graphics.Screen;
import com.farr.fight.level.Tile.Tile;

public class Level implements Layer {
	
	public List<Entity> entities = new ArrayList<Entity>();
	public Screen screen;
	public int screenWidth = Game.gameWidth;
	public int screenHeight = Game.gameHeight - 30*Game.scale;
	private int xScroll = 0; 
	private int yScroll = 0;
	private Game game;
	
	protected int width, height;	//Level width and height, in tiles
	private Tile[] tiles;
	
	public Level(Game game) {
		screen = new Screen(Game.gameWidth, Game.gameHeight - 30*3, 3);
		this.game = game;
	}
	
	public void loadMapFromFile(String path) {
		
	}
	
	public Tile getTile(int x, int y) {
		
		return Tile.grassTile;
	}
	
	public boolean update() {
		Scroll();
		
		for (Entity e : entities) {
			e.update();
		}
		return false;
	}
	
	public void render(Graphics g) {
		//TODO render screen
		
		//TODO render (applicable) entities
		screen.setOffset(xScroll, yScroll);
		
		screen.clear(0x0);
		renderVisibleTiles(screen);
		screen.renderPoint(16, 16, 0x0);
		
		
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

	private void Scroll() {
		if (scrollBool[0]) yScroll--;
		if (scrollBool[2]) yScroll++;
		if (scrollBool[1]) xScroll--;
		if (scrollBool[3]) xScroll++;
	}
	
	@Override
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.KEY_PRESSED, (Event e) -> onKeyPress((KeyPressedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_RELEASED, (Event e) -> onKeyRelease((KeyReleasedEvent) e));
		
	}

	
	private boolean[] scrollBool = new boolean[4];
	private void onKeyPress(KeyPressedEvent e) {

		switch (e.getKeyCode()) {
		case (KeyEvent.VK_W): scrollBool[0] = true; break;
		case (KeyEvent.VK_A): scrollBool[1] = true; break;
		case (KeyEvent.VK_S): scrollBool[2] = true; break;
		case (KeyEvent.VK_D): scrollBool[3] = true; break;
		case (KeyEvent.VK_ESCAPE): game.gotoMain(); break;
		}
		
	}
	
	private void onKeyRelease(KeyReleasedEvent e) {
		
		switch (e.getKeyCode()) {
		case (KeyEvent.VK_W): scrollBool[0] = false; break;
		case (KeyEvent.VK_A): scrollBool[1] = false; break;
		case (KeyEvent.VK_S): scrollBool[2] = false; break;
		case (KeyEvent.VK_D): scrollBool[3] = false; break;
		}
		
	}

	@Override
	public void init(List<Layer> l) {
		
	}
	
}
