package com.farr.fight.graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.farr.Events.types.MousePressedEvent;
import com.farr.fight.util.Vector2i;

public class UITextField extends UIComponent implements UIFocusable {

	private String enteredText = "";
	private String displayWhenEmpty = "";
	private Font font = new Font("Times New Roman", Font.PLAIN, 24); //default font;
	private Color backgroundColor = Color.WHITE;
	private Color textColor = Color.BLACK;
	
	private Rectangle rect;
	
	private int xMargin = 2;
	private int yMargin = 2;
	
	private boolean focused = false;
	
	//TODO create a larger variety of constructors
	/**
	 * Constructs a UITextField component
	 * @param position
	 * @param displayWhenEmpty
	 */
	public UITextField(Vector2i position, int width, String displayWhenEmpty) {
				
		super(position);
		this.size = new Vector2i(width, font.getSize() + 2 * yMargin);
		this.displayWhenEmpty = displayWhenEmpty;
	}
	
	public boolean onMousePress(MousePressedEvent e) {
		if (rect.contains(new Point(e.getX(), e.getY()))) {
			
			panel.setFocus(this);
			
			return true;
		}
		return false;
	}
	
	//TODO !!!IMPORTANT!!! HANDLE KEYPRESSES TO ADD LETTERS TO enteredText
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		int x = position.x + offset.x;
		int y = position.y + offset.y;
		
		g.setColor(backgroundColor);
		g.fillRect(x, y, size.x, size.y);
		
		g.setColor(textColor);
		g.setFont(font);
		String drawString = enteredText.equals("") ? displayWhenEmpty : enteredText;
		
		g.drawString(drawString, x + xMargin, y + size.y - yMargin);
	}
	
	public void init(UIPanel p) {
		super.init(p);
		panel.addFocusable(this);
		rect = new Rectangle(position.x + offset.x, position.y + offset.y, size.x, size.y);
	}

	public boolean getFocused() {
		return focused;
	}
	public void removeFocus() {
		focused = false;
	}
	public void giveFocus() {
		focused = true;
	}
	
}
