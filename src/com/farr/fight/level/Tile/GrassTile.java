package com.farr.fight.level.Tile;

import com.farr.fight.graphics.Screen;
import com.farr.fight.graphics.Sprite;

public class GrassTile extends Tile {

	
	public GrassTile(Sprite sprite) {
		super(sprite);
		width = 16;
		height = 16;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x * width, y * height, this);
	}
	
}
