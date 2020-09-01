package com.squareroot.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.squareroot.main.Game;
import com.squareroot.world.Camera;
import com.squareroot.world.World;

enum Entity_Type {
    BULLET, ENEMY, PLAYER, WEAPON, AMMO, HEALTH,
}

public class Entity {

    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
	super();
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
	this.sprite = sprite;
	id++;
    }

    public static BufferedImage LIFE_PACK = Game.spritesheet.getSprite(2 * 16, 16*5, 16, 16);
    public static BufferedImage GUN = Game.spritesheet.getSprite(3 * 16, 16*5, 16, 16);
    public static BufferedImage BULLET = Game.spritesheet.getSprite(4 * 16, 16*5, 16, 16);
    public static BufferedImage ENEMY = Game.spritesheet.getSprite(9 * 16, 0, 16, 16);


    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected static int id = 0;

    protected BufferedImage sprite;

    public int getX() {
	return x;
    }

    public void setX(int x) {
	this.x = x;
    }

    public int getY() {
	return y;
    }

    public void setY(int y) {
	this.y = y;
    }

    public int getWidth() {
	return width;
    }

    public void setWidth(int weight) {
	this.width = weight;
    }

    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }



    public static boolean checkView(int y, int d) {
	boolean res = false;
	int off_set = 14;
	try {
	    res = res || World.tilesWORLD[d][(int) y] || World.tilesWORLD[d][(int) y+off_set]
		    || World.tilesWORLD[d+off_set][(int) y+off_set]|| World.tilesWORLD[d+off_set][(int) y];

	} catch (Exception e) {

	}
	return res;

    }

    public static boolean canMoveTo(int d, int y) {

	boolean res = checkView(d, y);
	return !res;
    }

    public boolean isColliding(Entity o) {
	boolean is_colliding = false;
	Rectangle rect_1 = new Rectangle(this.x, this.y, this.width, this.height);
	Rectangle rect_2 = new Rectangle(o.x, o.y, o.width, o.height);

	return rect_1.intersects(rect_2);

    }

    public boolean isColliding(Entity o, int offset_x, int offset_y) {
	boolean is_colliding = false;
	Rectangle rect_1 = new Rectangle(this.x, this.y, this.width - offset_x, this.height - offset_y);
	Rectangle rect_2 = new Rectangle(o.x, o.y, o.width, o.height);

	return rect_1.intersects(rect_2);

    }

    public void render(Graphics g) {
	g.drawImage(sprite, (int) this.getX() - Camera.x, (int) this.getY() - Camera.y, null);

    }

    public void tick() {

    }

}
