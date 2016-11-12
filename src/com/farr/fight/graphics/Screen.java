package com.farr.fight.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen {

	public int pixels[];
	public int width, height;
	public int scale;
	public int xOffset, yOffset;
	
	public BufferedImage image;
	public int[] imgPixels;
	
	//TODO there may be offset someday- but by default none of these things will use it, as the screen camera is static. So Im not going to worry.
	
	
	public Screen(int width, int height, int scale) {
		
		pixels = new int[width * height];
		this.width = width;
		this.height = height;
		this.scale = scale;
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		imgPixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
	}
	
	public void clear(int col) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = col;	//Colors the screen pink wherever we aren't rendering something, as a debug tool
		}
	}

	/**
	 * Draws screen pixels to the image.
	 */
	public void pack() {
		for (int i = 0; i < imgPixels.length; i++) {
			imgPixels[i] = pixels[i];
		}
	}
	
	/**
	 * @deprecated
	 * @param points
	 */
	public void renderBackground(int[] points) {
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = points[i];
		}
		
	}

	public void fillRect(int x, int y, int width, int height, int color) {
		
		for (int yi = 0; yi < height; yi++) {
			
			int yf = yi + y;
			if (yf >= this.height || yf < 0) continue;
			for (int xi = 0; xi < width; xi++) {
				int xf = xi + x;
				if (xf >= this.width || xf < 0) continue;
				pixels[xf + yf * this.width] = color;
			}
		}
		
	}
	
	public void renderPoint(int x, int y, int color) {
		if (x < 0 || x >= this.width) return;
		if (y < 0 || y >= this.height) return;
		pixels[x + y * width] = color;
	}

	public void renderTextCharacter(int i, int j, Sprite sprite, int color, boolean b) {
		// TODO Auto-generated method stub
		
	}
	
}
