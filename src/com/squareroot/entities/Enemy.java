package com.squareroot.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.squareroot.entities.Entity;
import com.squareroot.main.Game;
import com.squareroot.util.Astar;
import com.squareroot.util.Point;
import com.squareroot.world.Camera;

public class Enemy extends Entity {
    public double speed = 1;
    double timer;
    public static boolean teste = true;
    int size_list_random;
    public String name;
    private Random r;
    private Astar astar;
    private int frame_x = 2;
    private int frame_y = 2;
    private boolean ctr_x_right = false;
    private boolean ctr_x_left = true;
    public List<Point> _list = new ArrayList<>();

    public Enemy(int x, int y, int weight, int height, BufferedImage sprite) {
	super(x, y, weight, height, sprite);
	timer = System.currentTimeMillis();
	r = new Random();
	int low = 1000;
	int high = 2000;
	size_list_random = r.nextInt(high - low) + low;
	name = "Enemy_" + id;
	astar = new Astar();

    }

    public boolean hasSomeBodyHere(int x, int y) {
	Rectangle my_rect = new Rectangle(x, y, 16, 16);
	for (Entity entity : Game.entities) {

	    if (entity instanceof Enemy) {
		Enemy e = (Enemy) entity;
		Rectangle friend_rect = new Rectangle((int) e.x, (int) e.y, 16, 16);

		if (!this.name.equals(e.name) && my_rect.intersects(friend_rect)) {
		    return true;
		}

	    }
	}
	return false;
    }

    public void tick() {

	if (!hasSomeBodyHere((int) x, (int) y)) {

	    if (!_list.isEmpty())

	    {
		Point current = _list.get(0);
		_list.remove(0);
		this.x = current.getX();
		this.y = current.getY();

		return;
	    } else {
		if (true) {
		    teste = false;

		    _list = astar.findPathToInNormalMap((int) x, (int) y, (int) Game.player.getX(),
			    (int) Game.player.getY());
		    timer = System.currentTimeMillis();
		    teste = true;
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
	} else {
	    if (!_list.isEmpty()) {
		Point current = _list.get(0);
		_list.remove(0);
		this.x = current.getX();
		this.y = current.getY();

	    } else {
		int low = 10;
		int high = 320;
		_list = astar.findPathToInNormalMap((int) x, (int) y, (int) r.nextInt(320), (int) r.nextInt(320));
	    }

	}
    }

    @Override
    public void render(Graphics g) {
	super.render(g);
	if (frame_x < 6)
	    this.sprite = Game.spritesheet.getSprite(this.frame_x * 16, frame_y * 16, 16, 16);

	if (Game.fps % 10 == 0) {

	    frame_x += 1;
	    if (frame_x > 5)
		frame_x = 2;

	}

    }

}
