package com.squareroot.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.squareroot.main.Game;
import com.squareroot.world.Camera;
import com.squareroot.world.World;

public class Entity {

    public Entity(int x, int y, int weight, int height, BufferedImage sprite) {
	super();
	this.x = x;
	this.y = y;
	this.weight = weight;
	this.height = height;
	this.sprite = sprite;
	id++;
    }

    public static BufferedImage LIFE_PACK = Game.spritesheet.getSprite(6 * 16, 0, 16, 16);
    public static BufferedImage GUN = Game.spritesheet.getSprite(7 * 16, 0, 16, 16);
    public static BufferedImage BULLET = Game.spritesheet.getSprite(8 * 16, 0, 16, 16);
    public static BufferedImage ENEMY = Game.spritesheet.getSprite(9 * 16, 0, 16, 16);

    protected double x;
    protected double y;
    protected int weight;
    protected int height;
    protected static int id = 0;

    protected BufferedImage sprite;

    public double getX() {
	return x;
    }

    public void setX(double x) {
	this.x = x;
    }

    public double getY() {
	return y;
    }

    public void setY(double y) {
	this.y = y;
    }

    public int getWeight() {
	return weight;
    }

    public void setWeight(int weight) {
	this.weight = weight;
    }

    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }

    public void debugCheckView(double d, double y, int box_size, int off_set_x) {
	for (int i = (int) d + off_set_x; i < box_size + (int) d - off_set_x; i++) {
	    if (World.tilesWORLD[i][(int) y]) {
		System.out.print("*");
	    } else {
		System.out.print("--");

	    }

	}
	System.out.println("");
	for (int i = (int) d + off_set_x; i < box_size + (int) d - off_set_x; i++) {

	    if (World.tilesWORLD[i][(int) y + box_size - off_set_x]) {
		System.out.print("*");
	    } else {
		System.out.print("--");

	    }

	}
    }

    public static boolean checkView(double d, double y) {
	int box_size = 16;
	int off_set_x = 2;
//		int off_set_y = 2;
	boolean res = false;
	try {
	    for (int i = (int) d + off_set_x; i < box_size + (int) d - off_set_x; i++) {
		res = res || World.tilesWORLD[i][(int) y];

	    }
	    for (int i = (int) d + off_set_x; i < box_size + (int) d - off_set_x; i++) {
		res = res || World.tilesWORLD[i][(int) y + box_size - off_set_x];

	    }
	    for (int i = (int) d + off_set_x; i < box_size + (int) d - off_set_x; i++) {
		res = res || World.tilesWORLD[i][(int) y + box_size - off_set_x];

	    }
	    // debugCheckView(d, y, box_size, off_set_x);

//			for (int i = (int) y + off_set_x; i < box_size + (int) y - off_set_y; i++) {
//				res = res || World.tilesWORLD[(int) d][i];
//
//			}
//			for (int i = (int) y + off_set_x; i < box_size + (int) y - off_set_y; i++) {
//				res = res || World.tilesWORLD[(int) d + box_size - off_set_x][i];
//
//			}

	} catch (Exception e) {

	}
	return res;

    }

    public static boolean canMoveTo(double d, double y) {

	boolean res = checkView(d, y);
	return !res;
    }

    public void render(Graphics g) {
	g.drawImage(sprite, (int) this.getX() - Camera.x, (int) this.getY() - Camera.y, null);

    }

    public void tick() {

    }

}
