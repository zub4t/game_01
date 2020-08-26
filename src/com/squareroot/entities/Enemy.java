package com.squareroot.entities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

//import com.squareroot.Util.Astart;
//import com.squareroot.Util.Point;
import com.squareroot.entities.Entity;
import com.squareroot.main.Game;

public class Enemy extends Entity {
	public double speed = 1;
	double timer;
	//List<Point> _list = new ArrayList<>();

	public Enemy(int x, int y, int weight, int height, BufferedImage sprite) {
		super(x, y, weight, height, sprite);
		//_list = Astart.moveTo((int) x, (int) y, (int) Game.player.getX(), (int) Game.player.getY());

	}

	public void tick() {

		if (Game.player.getX() > this.getX()) {
			if (canMoveTo(this.x + this.speed, this.y))
				this.setX(this.getX() + this.speed);
		} else if (Game.player.getX() < this.getX()) {
			if (canMoveTo(this.x - this.speed, this.y))
				this.setX(this.getX() - this.speed);
		}

		if (Game.player.getY() > this.getY()) {
			if (canMoveTo(this.x, this.y + this.speed))
				this.setY(this.getY() + this.speed);
		} else if (Game.player.getY() < this.getY()) {
			if (canMoveTo(this.x, this.y - this.speed))
				this.setY(this.getY() - this.speed);
		}

		// meter um A* dps
//		if (test) {
//			for (Point p : _list) {
//				if (timer + 1000 <= System.currentTimeMillis()) {
//					this.x = p.getX();
//					this.y = p.getY();
//					timer = System.currentTimeMillis();
//
//				}
//
//			}
//			test = false;
//		}
//		Thread thread = new Thread() {
//			public void run() {
//				_list.clear();
//				_list = Astart.moveTo((int) x, (int) y, (int) Game.player.getX(), (int) Game.player.getY());
//
//			}
//		};
//		if (timer + 1000 <= System.currentTimeMillis()) {
//			thread.start();
//			timer = System.currentTimeMillis();

	}

}
