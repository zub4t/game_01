package com.squareroot.entities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.squareroot.entities.Entity;
import com.squareroot.main.Game;
import com.squareroot.util.Astar;
import com.squareroot.util.Point;

public class Enemy extends Entity {
    public double speed = 1;
    double timer;
    boolean teste = true;
    int wating_time;
    List<Point> _list = new ArrayList<>();

    public Enemy(int x, int y, int weight, int height, BufferedImage sprite) {
	super(x, y, weight, height, sprite);
	timer = System.currentTimeMillis();
	Random r = new Random();
	int low = 2000;
	int high = 6000;
	wating_time = r.nextInt(high - low) + low;

    }

    public void tick() {
	if (teste) {
	    Thread thread = new Thread() {
		public void run() {
		    _list.clear();
		    Astar astar = new Astar();
		    _list = astar.findPathTo((int) x, (int) y, (int) Game.player.getX(), (int) Game.player.getY());

		}
	    };
	    thread.start();
	    teste = false;
	    return ;
	}
	if (!_list.isEmpty()) {
	    Point current = _list.get(0);
	    _list.remove(0);
	    this.x = current.getX();
	    this.y = current.getY();

	    return;
	} else if (timer + 1000 <= System.currentTimeMillis()) {
	    teste = true;
	    timer = System.currentTimeMillis();

	    return;
	}

//
//	if (Game.player.getX() > this.getX()) {
//	    if (canMoveTo(this.x + this.speed, this.y))
//		this.setX(this.getX() + this.speed);
//	} else if (Game.player.getX() < this.getX()) {
//	    if (canMoveTo(this.x - this.speed, this.y))
//		this.setX(this.getX() - this.speed);
//	}
//
//	if (Game.player.getY() > this.getY()) {
//	    if (canMoveTo(this.x, this.y + this.speed))
//		this.setY(this.getY() + this.speed);
//	} else if (Game.player.getY() < this.getY()) {
//	    if (canMoveTo(this.x, this.y - this.speed))
//		this.setY(this.getY() - this.speed);
//	}

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

//		if (timer + 1000 <= System.currentTimeMillis()) {
//			thread.start();
//			timer = System.currentTimeMillis();

    }

}
