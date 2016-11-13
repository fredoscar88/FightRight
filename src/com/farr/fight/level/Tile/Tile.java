package com.farr.fight.level.Tile;

import com.farr.fight.Assets;
import com.farr.fight.graphics.Screen;
import com.farr.fight.graphics.Sprite;

public class Tile {

	public Sprite sprite;
	public int width, height;
	public static final int VOID_COLOR = 0x00CC66;
//	protected int x, y;
	
	public static Tile grassTile = new GrassTile(Assets.grass);
	public static Tile brickTile = new BrickTile(Assets.brick);
	public static Tile woodTile = new WoodTile(Assets.wood);
	public static Tile voidTile = new VoidTile(new Sprite(16, VOID_COLOR));
	
	protected Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
	}
	
	public boolean getSolid() {
		return false;
	}
	
}
