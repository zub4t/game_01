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
    public int speed = 1;
    double timer;
    int size_list_random;
    public String name;
    private Random r;
    private Astar astar;
    private int frame_x = 2;
    private int frame_y = 2;
    private boolean ctr_x_right = false;
    private boolean ctr_x_left = true;
    public List<Point> _list = new ArrayList<>();
    public int life = 3;
    public boolean attacked;

    public Enemy(int x, int y, int weight, int height, BufferedImage sprite) {
	super(x, y, weight, height, sprite);
	r = new Random();
	int low = 1000;
	int high = 2000;
	size_list_random = r.nextInt(high - low) + low;
	name = "Enemy_" + id;
	astar = new Astar();

    }

    public boolean hasAnyBodyHere() {
	for (Entity entity : Game.entities) {

	    if (entity instanceof Enemy) {
		Enemy e = (Enemy) entity;
		if (!(e.name.equals(this.name)) && isColliding(e)) {
		    return true;

		}

	    }
	}
	return false;
    }

    public void tick() {

	if (life < 1) {
	    Game.entities_to_remove_at_runtime.add(this);
	}
	if (!_list.isEmpty())

	{
	    Point current = _list.get(0);
	    _list.remove(0);
	    this.x = current.getX();
	    this.y = current.getY();

	    return;
	} else {
	    _list = astar.findPathToInNormalMap((int) x, (int) y, (int) Game.player.getX(), (int) Game.player.getY());
	    timer = System.currentTimeMillis();

	    return;
	}

    }

    @Override
    public void render(Graphics g) {

	this.sprite = Game.spritesheet.getSprite(this.frame_x * 16, frame_y * 16, 16, 16);

	if (Game.fps % 10 == 0) {
	    if (!attacked) {
		frame_x += 1;
		if (frame_x > 5)
		    frame_x = 2;
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

	}
	super.render(g);

    }

}
