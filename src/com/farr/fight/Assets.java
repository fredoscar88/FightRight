package com.farr.fight;

import com.farr.fight.graphics.Sprite;
import com.farr.fight.graphics.SpriteSheet;

public class Assets {

	private Assets() {
	}

	// Levels/maps
	public static final String LOBBY_LEVEL = "/maps/lobby.png";
	
	
	//------Spritesheets
	public static SpriteSheet groundTiles = new SpriteSheet("/ground/groundSprites.png", 512);
	public static SpriteSheet mageSprites = new SpriteSheet("/characters/mage.png", 256, 16);
		
	
	//------Tile sprites
	public static Sprite grass = new Sprite(16, 1, 1, groundTiles);
	public static Sprite brick = new Sprite(16, 1, 4, groundTiles);
	public static Sprite wood = new Sprite(16, 1, 7, groundTiles);
		
	
				
	//---------CHARACTER SPRITES
	public static Sprite playerMage = new Sprite(16, 0, 0, mageSprites);
}
