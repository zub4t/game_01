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
    public static boolean teste = true;
    int wating_time;

    public  List<Point> _list = new ArrayList<>();

    public Enemy(int x, int y, int weight, int height, BufferedImage sprite) {
	super(x, y, weight, height, sprite);
	timer = System.currentTimeMillis();
	Random r = new Random();
	int low = 1000;
	int high = 2000;
	wating_time = r.nextInt(high - low) + low;

    }

    public void tick() {

	if (!_list.isEmpty())

	{
	    Point current = _list.get(0);
	    _list.remove(0);
	    this.x = current.getX();
	    this.y = current.getY();

	    return;
	} else {
	    Astar astar = new Astar();
	    if (teste & Astar.calcManhattanDistance((int) x, (int) y, (int) Game.player.getX(),
		    (int) Game.player.getY()) > 0) {

		_list = astar.findPathTo((int) x, (int) y, (int) Game.player.getX(), (int) Game.player.getY());

		teste = false;
	    } else {

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
	    }
	    return;
	}

    }

}
