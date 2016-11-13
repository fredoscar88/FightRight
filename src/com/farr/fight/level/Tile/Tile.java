package com.farr.fight.level.Tile;

import com.farr.fight.graphics.Screen;
import com.farr.fight.graphics.Sprite;

public class Tile {

	public Sprite sprite;
	public int width, height;
//	protected int x, y;
	
	public static Tile grassTile = new GrassTile(Sprite.grass);
	public static Tile rockTile;
	public static Tile wallTile;
	public static Tile brickWallTile;
	
	protected Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
	}
	
	public boolean getSolid() {
		return false;
	}
	
}
