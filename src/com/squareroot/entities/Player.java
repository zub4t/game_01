package com.squareroot.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.squareroot.main.Game;
import com.squareroot.main.Game.GAME_STATE;
import com.squareroot.world.Camera;
import com.squareroot.world.TileWall;
import com.squareroot.world.World;

public class Player extends Entity {
    private boolean right, up, left, down;
    private int speed = 2;
    private int frame_x = 2;
    private int frame_y = 0;
    private boolean ctr_x_right = false;
    private boolean ctr_x_left = true;
    private boolean can_shooting = false;
    private boolean gun_equipped = false;
    private boolean attacked;

    public static double life = 100;

    public Player(int x, int y, int weight, int height, BufferedImage sprite) {
	super(x, y, weight, height, sprite);
	frame_x = 2;
	frame_y = 0;

    }

    public synchronized void tick() {
	if (life < 1) {
	    

	}
	if (hasEnemyHere()) {
	    attacked = true;
	    Player.life -= 0.6;
	    Game.game_attacked_music.play();
	}
	if (hasWeaponHere()) {
	    Game.game_powerUP_music.play();
	    frame_y = 3;
	    gun_equipped = true;

	}
	if (isCan_shooting() && frame_y > 2 && Game.fps % 10 == 0) {
	    Game.game_shooting_music.play();
	    Game.entities_to_add_at_runtime.add(new Bullet(this.x, this.y, 5, 5, null, (frame_y == 3 ? true : false)));
	}

	Camera.x = Camera.clamp((int) this.getX() - (Game.WIDTH >> 1), 0, (World.WIDTH << 4) - Game.WIDTH);
	Camera.y = Camera.clamp((int) this.getY() - (Game.HEIGHT >> 1), 0, (World.HEIGHT << 4) - Game.HEIGHT);

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

    }

    public boolean hasEnemyHere() {
	for (Entity entity : Game.entities) {

	    if (entity instanceof Enemy) {
		Enemy e = (Enemy) entity;
		if (isColliding(e)) {
		    return true;

		}

	    }
	}
	return false;
    }

    public boolean hasWeaponHere() {
	Entity target = null;
	boolean flag = false;
	for (Entity entity : Game.entities) {

	    if (entity instanceof Weapon) {
		Weapon e = (Weapon) entity;
		if (isColliding(e)) {
		    target = e;

		    flag = true;
		    break;

		}

	    }
	}
	if (flag) {
	    Game.entities_to_remove_at_runtime.add(target);
	}
	return flag;
    }

    @Override
    public void render(Graphics g) {
	if (!attacked) {
	    Game.player.sprite = Game.spritesheet.getSprite(this.frame_x * 16, frame_y * 16, 16, 16);

	    if (this.left) {
		if (gun_equipped)
		    this.frame_y = 4;
		else
		    this.frame_y = 1;

		if (ctr_x_left) {
		    ctr_x_left = false;
		    ctr_x_right = true;
		    frame_x = 5;
		}
	    } else if (this.right) {
		if (gun_equipped)
		    this.frame_y = 3;
		else
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
	} else {
	    frame_x = 6;
	    (new Thread() {
		@Override
		public void run() {
		    try {
			Thread.sleep(100);
			attacked = false;
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }

		}
	    }).start();
	}
	super.render(g);
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

    public boolean isCan_shooting() {
	return can_shooting;
    }

    public void setCan_shooting(boolean can_shooting) {
	this.can_shooting = can_shooting;
    }
}
