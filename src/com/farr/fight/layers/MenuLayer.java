package com.farr.fight.layers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import com.farr.Events.Event;
import com.farr.Events.Event.Type;
import com.farr.Events.EventDispatcher;
import com.farr.Events.BlockingLayer;
import com.farr.Events.types.FocusLostEvent;
import com.farr.fight.Game;
import com.farr.fight.graphics.ui.UIComponent;
import com.farr.fight.graphics.ui.UIPanel;
import com.farr.fight.util.Vector2i;

public class MenuLayer implements BlockingLayer {

	//Static image that I cba to animate
	BufferedImage backgroundImage;
	UIPanel menuUI;	//Some layers contain other layers
	
	public MenuLayer(BufferedImage image, Game game) {
		backgroundImage = image;
		
		menuUI = new UIPanel(new Vector2i(0,0), new Vector2i(game.getWidth(), game.getHeight()));
		
	}
	
	public void addComponent(UIComponent c) {
		menuUI.add(c);
	}
	
	public void render(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, null);
		menuUI.render(g);
		
	}

	public boolean update() {
		menuUI.update();
		return true;	//menu layers will always block updates from other layers
	}
	
	public void onEvent(Event event) {
		
		EventDispatcher dispatcher = new EventDispatcher(event);									//The (Event e) tells it replace function bodies that have the same parameter in the EventHandler interface, with the given method or method body
		dispatcher.dispatch(Type.FOCUS_LOST, (Event e) -> onFocusLost((FocusLostEvent) e));
		
		//dispatch events to the other layers we have, which is just the menuUI layer
		menuUI.onEvent(event);		
		dispatcher.blockEvent();	//Blocks the event, no matter what kind it is, from the rest of the layers. (menu layers ALWAYS do this)
	}
	
	public boolean onFocusLost(FocusLostEvent e) {
		//	TODO pause game by creating another layer that simply blocks updates
		return true;
	}

	public void init(List<BlockingLayer> l) {
		
	}

}
