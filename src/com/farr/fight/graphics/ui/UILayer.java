package com.farr.fight.graphics.ui;

import java.awt.Graphics;
import java.util.List;

import com.farr.Events.Event;
import com.farr.Events.Layer;

public interface UILayer {
	
	public void render(Graphics g);
	
	public void update();

	public void onEvent(Event e);
	
	public void init(List<Layer> l);
		
}
