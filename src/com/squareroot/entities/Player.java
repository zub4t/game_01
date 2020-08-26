package com.squareroot.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.squareroot.main.Game;
import com.squareroot.world.Camera;
import com.squareroot.world.TileWall;
import com.squareroot.world.World;

public class Player extends Entity {
	private boolean right, up, left, down;
	private double speed = 2;
	private int frame_x = 2;
	private int frame_y = 0;
	private boolean ctr_x_right = false;
	private boolean ctr_x_left = true;

	public Player(int x, int y, int weight, int height, BufferedImage sprite) {
		super(x, y, weight, height, sprite);
		// TODO Auto-generated constructor stub

	}

	public void tick() {

		Camera.x = Camera.clamp((int) this.getX() - (Game.WIDTH >> 1), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int) this.getY() - (Game.HEIGHT >> 1), 0, World.HEIGHT * 16 - Game.HEIGHT + 16 * 1);

		if (right) {
			if (canMoveTo(this.x + this.speed, this.y)) {
				x += speed;
			}
		} else if (left) {
			if (canMoveTo(this.x - this.speed, this.y)) {
				x -= speed;
			}
		}
		if (up) {
			if (canMoveTo(this.x, this.y - this.speed)) {
				y -= speed;
			}
		} else if (down) {
			if (canMoveTo(this.x, this.y + this.speed)) {
				y += speed;
			}
		}
		super.tick();

	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		if (frame_x < 6)
			Game.player.sprite = Game.spritesheet.getSprite(this.frame_x * 16, frame_y * 16, 16, 16);

		if (this.left) {
			this.frame_y = 1;
			if (ctr_x_left) {
				ctr_x_left = false;
				ctr_x_right = true;
				frame_x = 5;
			}
		} else if (this.right) {
			this.frame_y = 0;
			if (ctr_x_right) {
				ctr_x_right = false;
				ctr_x_left = true;
				frame_x = 2;
			}
		}

		if (Game.fps % 10 == 0) {


			
			frame_x += 1;
			if (frame_x > 5)
				frame_x = 2;

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

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
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
