package com.squareroot.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {
    public Menu(String title, String[] options) {
	super();
	this.title = title;
	this.options = options;

    }

    private String title;
    private String[] options;
    private int line_spacing = 20;
    private int tab_spacing = 20;
    public int cursor_line = 1;
    public boolean enter_pressed;

    public void tick() {
	if (cursor_line < 1)
	    cursor_line = options.length;
	else if (cursor_line > options.length)
	    cursor_line = 1;

	if (enter_pressed) {
	    switch (cursor_line) {
	    case 1:
		Game.state = Game.GAME_STATE.GAME_RUNNING;
		break;

	    default:
		System.out.println("não implementado ainda");
		break;
	    }
	   
	}
    }

    public void render(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;

	g2.setColor(new Color(0, 0, 0, 100));
	g2.fillRect(0, 0, (Game.WIDTH * Game.SCALE), Game.HEIGHT * Game.SCALE);
	g2.setColor(Color.white);
	g2.setFont(new Font("Arial", Font.BOLD, 25));
	g2.drawString(title, (Game.WIDTH >> 1) - 70, (Game.HEIGHT >> 1) - 70);
	g2.setFont(new Font("Arial", Font.BOLD, 12));
	int cur_line_spacing = line_spacing;
	for (String op : options) {
	    g2.drawString(op, (Game.WIDTH >> 1) - 70 + tab_spacing, (Game.HEIGHT >> 1) - 70 + cur_line_spacing);
	    cur_line_spacing += line_spacing;

	}
	drawCursor(g, (Game.HEIGHT >> 1) - 70 + (line_spacing * cursor_line));
    }

    public void drawCursor(Graphics g, int y) {
	g.drawString(">", (Game.WIDTH >> 1) - 70, y);

    }

}
