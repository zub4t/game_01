package com.squareroot.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.squareroot.main.Game;
import com.squareroot.world.Camera;
import com.squareroot.world.World;

public class Player extends Entity {
	private boolean right, up, left, down;
	private int speed = 1;
	private int frame_x = 2;
	private int frame_y = 0;

	public Player(int x, int y, int weight, int height, BufferedImage sprite) {
		super(x, y, weight, height, sprite);
		// TODO Auto-generated constructor stub
	}
	public void tick() {
		super.tick();

		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH >> 1), 0,
				World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT >> 1), 0,
				World.HEIGHT*16 - Game.HEIGHT);
		if (right) {
			x += speed;
		} else if (left) {
			x -= speed;
		}
		if (up) {
			y -= speed;
		} else if (down) {
			y += speed;

		}

	}
	@Override
	public void render(Graphics g) {
		super.render(g);

		Game.player.sprite = Game.spritesheet.getSprite(this.frame_x * 16,
				frame_y * 16, 16, 16);

		if (Game.fps % 30 == 0) {
			if (this.right) {
				frame_x++;
				if (frame_x > 5) {
					frame_x = 2;
				}
			} else {
				frame_x--;
				if (frame_x < 2) {
					frame_x = 5;
				}
			}
			if (this.left)
				this.frame_y = 1;
			else
				this.frame_y = 0;
		}
	}
	public boolean isRight() {
		return right;

	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getFrame_x() {
		return frame_x;
	}
	public void setFrame_x(int frame_x) {
		this.frame_x = frame_x;
	}
	public int getFrame_y() {
		return frame_y;
	}
	public void setFrame_y(int frame_y) {
		this.frame_y = frame_y;
	}
}
