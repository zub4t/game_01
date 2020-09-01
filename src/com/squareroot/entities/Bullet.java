package com.squareroot.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.squareroot.main.Game;
import com.squareroot.world.Camera;

public class Bullet extends Ammo {
    public boolean right;
    private int life = 1000;

    public Bullet(int x, int y, int width, int height, BufferedImage sprite, boolean right) {
	super(x, y, width, height, sprite);
	this.right = right;
    }

    @Override
    public void render(Graphics g) {
	g.setColor(Color.yellow);
	g.fillOval(x - Camera.x, y - Camera.y, width, height);

    }

    @Override
    public void tick() {
	life--;
	if (life < 0) {
	    Game.entities_to_remove_at_runtime.add(this);
	}
	Enemy target = hasEnemyHere();
	if (target != null) {
	    Game.entities_to_remove_at_runtime.add(this);
	    target.attacked = true;
	    target.life--;
	}
	if (right)
	    this.x += 5;

	else
	    this.x -= 5;
    }

    public Enemy hasEnemyHere() {
	Enemy target = null;
	for (Entity entity : Game.entities) {
	    if (entity instanceof Enemy) {
		Enemy e = (Enemy) entity;
		if (isColliding(e)) {
		    target = e;
		    break;

		}

	    }
	}
	return target;
    }

}
