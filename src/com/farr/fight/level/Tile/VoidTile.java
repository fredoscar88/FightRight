package com.farr.fight.level.Tile;

import com.farr.fight.graphics.Screen;
import com.farr.fight.graphics.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
		width = 16;
		height = 16;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x * width, y * height, this);
	}
	
	public boolean getSolid() {
		return true;
	}
	
}
