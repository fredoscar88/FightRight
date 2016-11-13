package com.farr.fight.entity.mob;

import com.farr.fight.entity.Entity;
import com.farr.fight.graphics.Screen;
import com.farr.fight.graphics.Sprite;
import com.farr.fight.util.VectorVec;

public class Mob extends Entity {
	
	protected Sprite sprite;
	
	protected double speed;
	
	public void update() {
		
	}

	public void move(double theta, double speed) {
		double xa = speed * Math.cos(theta);
		double ya = speed * Math.sin(theta);

		//TODO update movement to test for collision w/ a method in level, and increment by 1 each x and y until full movement each update cycle.
		
		x += xa;
		y += ya;
		
	}
	
	public void move(VectorVec vector) {
		double xa = vector.magnitude * Math.cos(vector.theta);
		double ya = vector.magnitude * Math.sin(vector.theta);

		//TODO update movement to test for collision w/ a method in level, and increment by 1 each x and y until full movement each update cycle.
		
		x += xa;
		y += ya;
	}
	
	public void move(int xa, int ya) {
		//TODO update movement to test for collision w/ a method in level, and increment by 1 each x and y until full movement each update cycle.
		
		x += xa;
		y += ya;
	}
	
	//TODO Verify
	public void render(Screen screen) {
		screen.renderSprite(((int) x) - sprite.getWidth()/2, ((int) y) - sprite.getHeight()/2, sprite);
	}
	
}
