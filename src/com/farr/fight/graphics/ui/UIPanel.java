package com.farr.fight.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.farr.Events.Event;
import com.farr.Events.Event.Type;
import com.farr.Events.EventDispatcher;
import com.farr.Events.Layer;
import com.farr.Events.BlockingLayer;
import com.farr.Events.types.KeyPressedEvent;
import com.farr.Events.types.KeyReleasedEvent;
import com.farr.Events.types.MouseMovedEvent;
import com.farr.Events.types.MousePressedEvent;
import com.farr.Events.types.MouseReleasedEvent;
import com.farr.fight.util.Vector2i;

public class UIPanel extends UIComponent implements Layer {
	
	private List<UIComponent> components = new ArrayList<UIComponent>();
	private List<UIFocusable> focusableComponents = new ArrayList<>();
	
	//private Color color;
	
	public UIPanel(Vector2i position) {
		super(position);
		this.position = position;
	}
	
	public UIPanel(Vector2i position, Vector2i size) {
		super(position);
		this.position = position;	//it may be that we'll want to automatically scale these vectors by 3. Or not, actually no, lets not because we might want precision
		this.size = size;
		color = new Color(0xAACACACA, true);	//surely we can make this a parameter, somewhere. Also this is a default param.
	}
	
	public void add(UIComponent c) {
		components.add(c);
		c.init(this);
		c.setOffset(position);	//Should only need to do this once, or whenever we update position. So I'm going to go ahead and add in a setPosition, that incorporates this, so I don't forget
	}
	
	public void addFocusable(UIFocusable c) {
		focusableComponents.add(c);
	}
	
	public void setFocus(UIFocusable f) {
		for (UIFocusable c : focusableComponents) {
			c.removeFocus();
			if (c.equals(f))
				c.giveFocus();
		}
	}
	
	public void setPosition(Vector2i position) {
		this.position = position;
		for (UIComponent component : components) {	//Hey now we're thinking like programmers
			component.setOffset(this.position);
		}
	}
	
	public boolean onMousePress(MousePressedEvent e) {
		for (UIComponent component : components) {
			if (component.onMousePress(e))
				return true;
		}
		return false;
	}
	
	public boolean onMouseRelease(MouseReleasedEvent e) {
		for (UIComponent component : components) {
			if (component.onMouseRelease(e))
				return true;
		}
		return false;
	}
	
	public boolean onMouseMove(MouseMovedEvent e) {
		for (UIComponent component : components) {
			if (component.onMouseMove(e))
				return true;
		}
		return false;
	}
	
	public boolean onKeyPress(KeyPressedEvent e) {
		for (UIComponent component : components) {
			if (component.onKeyPress(e))
				return true;
		}
		return false;
	}
	
	public boolean onKeyRelease(KeyReleasedEvent e) {
		for (UIComponent component : components) {
			if (component.onKeyRelease(e))
				return true;
		}
		return false;
	}
	
	public void update() {
		for(UIComponent component : components) {
//			component.setOffset(position);	//This only needs to happen once? Or, if more than once, only ever infrequently. Maybe not tho
			component.update();
		}
	}
	
	public void render(Graphics g) {
		g.setColor(color);	//right? right! confurmed by TerCherners
		g.fillRect(position.x, position.y, size.x, size.y);
		
		for(UIComponent component : components) {
			component.render(g);
		}
	}

	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Type.MOUSE_MOVED, (Event e) -> onMouseMove((MouseMovedEvent) e));
		dispatcher.dispatch(Type.MOUSE_PRESSED, (Event e) -> onMousePress((MousePressedEvent) e));
		dispatcher.dispatch(Type.MOUSE_RELEASED, (Event e) -> onMouseRelease((MouseReleasedEvent) e));
		dispatcher.dispatch(Type.KEY_PRESSED, (Event e) -> onKeyPress((KeyPressedEvent) e));
		dispatcher.dispatch(Type.KEY_RELEASED, (Event e) -> onKeyRelease((KeyReleasedEvent) e));
	}

	public void init(List<BlockingLayer> l) {
		
	}
	
}
