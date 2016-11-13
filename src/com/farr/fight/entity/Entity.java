package com.farr.fight.entity;

import com.farr.fight.graphics.Screen;

public abstract class Entity {

	public double x, y;
	
	
	public abstract void update();
	
	public abstract void render(Screen screen);
	
}
