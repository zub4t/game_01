package com.squareroot.util;

import java.awt.Color;
import java.awt.Graphics;

import com.squareroot.entities.Player;

public class UI {

    public static void render(Graphics g) {
	g.setColor(Color.red);
	g.fillRect(20, 20, 50, 8);
	g.setColor(Color.green);
	g.fillRect(20, 20, (int) ((Player.life / 100) * 50), 8);
    }
}
