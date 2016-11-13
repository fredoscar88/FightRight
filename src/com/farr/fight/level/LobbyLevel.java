package com.farr.fight.level;


import com.farr.fight.Assets;
import com.farr.fight.Game;
import com.farr.fight.level.Tile.Tile;

public class LobbyLevel extends Level {

	private final int GRASS_COLOR = 0xFF00FF00;
	private final int BRICK_COLOR = 0xFF7C7C7C;
	private final int WOOD_COLOR = 0xFF7F3300;
	
	public LobbyLevel(Game game) {
		
		super(game);
		loadMapFromFile(Assets.LOBBY_LEVEL);
		generateTiles();
		System.out.println(tiles[0]);
	}	
	
	protected void generateTiles() {
		
		mapPixels = new int[mapWidth * mapHeight];
		map.getRGB(0, 0, mapWidth, mapHeight, mapPixels, 0, mapWidth);
		int index;
		
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				
				index = x + y * mapWidth;
				
				switch(mapPixels[index]) {
				case(GRASS_COLOR): tiles[index] = Tile.grassTile; break;
				case(BRICK_COLOR): tiles[index] = Tile.brickTile; break;
				case(WOOD_COLOR): tiles[index] = Tile.woodTile; break;
				default: tiles[index] = Tile.voidTile;
				}
			}
		}
	}
	
}
