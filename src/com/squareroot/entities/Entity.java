package com.squareroot.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.squareroot.main.Game;
import com.squareroot.world.Camera;

public class Entity {

	public Entity(int x, int y, int weight, int height, BufferedImage sprite) {
		super();
		this.x = x;
		this.y = y;
		this.weight = weight;
		this.height = height;
		this.sprite = sprite;
	}
	public static BufferedImage LIFE_PACK = Game.spritesheet.getSprite(6 * 16,
			0, 16, 16);
	public static BufferedImage GUN = Game.spritesheet.getSprite(7 * 16, 0, 16,
			16);
	public static BufferedImage BULLET = Game.spritesheet.getSprite(8 * 16, 0,
			16, 16);
	public static BufferedImage ENEMY = Game.spritesheet.getSprite(9 * 16, 0,
			16, 16);

	protected int x;
	protected int y;
	protected int weight;
	protected int height;

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

	public void render(Graphics g) {
		g.drawImage(sprite, this.getX(), this.getY(), null);

	}
	public void tick() {
		//this.x -= Camera.x;
		//this.y -= Camera.y;

	}

}
