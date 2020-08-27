package com.squareroot.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.squareroot.entities.Enemy;
import com.squareroot.entities.Entity;
import com.squareroot.entities.Player;
import com.squareroot.graphics.SpriteSheet;
import com.squareroot.util.Astar;
import com.squareroot.util.Point;
import com.squareroot.util.UI;
import com.squareroot.world.Camera;
import com.squareroot.world.World;

public class Game extends Canvas implements Runnable, KeyListener {
    public static int WIDTH = 300;
    public static int HEIGHT = 200;
    public static JFrame jframe;
    public boolean isRunning;
    public static int SCALE = 2;
    public static BufferedImage layer;
    public static Graphics g;
    public static List<Entity> entities;
    public static SpriteSheet spritesheet;
    public static Player player;
    public static int fps;
    public boolean vk_right_press;
    public boolean vk_left_press;
    public static double timer_tick;

    private World world;

    public Game() {
	addKeyListener(this);
	jframe = new JFrame("RPG  game");
	jframe.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
	entities = new ArrayList<>();
	spritesheet = new SpriteSheet("/spriteSheet.png");
	player = new Player(0, 0, 16, 16, spritesheet.getSprite(2 * 16, 0 * 16, 16, 16));
	world = new World("/map.png");
	entities.add(player);
	layer = new BufferedImage(this.WIDTH, this.HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public static void main(String[] args) {
	Game game = new Game();

	jframe.setResizable(false);
	jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	jframe.add(game);
	jframe.pack();
	jframe.setLocationRelativeTo(null);
	jframe.setVisible(true);
	game.isRunning = true;
	new Thread(game).start();
    }

    @Override
    public void run() {
	requestFocus();
	long lastTime = System.nanoTime();
	double delta = 0;
	double ns = 1000000000 / 60.0;
	fps = 0;
	double timer = System.currentTimeMillis();
	while (isRunning) {
	    long now = System.nanoTime();
	    delta += ((now - lastTime) / ns);
	    lastTime = now;
	    if (delta >= 1) {
		tick();

		render();

		fps++;
		delta--;
	    }
	    if (timer + 1000 <= System.currentTimeMillis()) {
		timer = System.currentTimeMillis();
		// System.out.println("fps:" + fps);
		fps = 0;
		Astar.key = true;
	    }

	}

    }

    public synchronized void tick() {
	for (Entity entity : entities) {

	    timer_tick = System.currentTimeMillis();
	    entity.tick();
	    if (entity instanceof Enemy) {
		Enemy e = (Enemy) entity;
		// System.out.println(e.name);
		if (System.currentTimeMillis() - timer_tick > 0) {

		    System.out.printf("Time %.0f Name %s , [%.0f][%.0f] to [%.0f][%.0f]\n",
			    System.currentTimeMillis() - timer_tick, e.name, e.getX(), e.getY(), player.getX(),
			    player.getY());

		}
	    }

	}

    }

    public synchronized void render() {
	BufferStrategy bs = this.getBufferStrategy();
	if (bs == null) {
	    this.createBufferStrategy(3);
	    return;
	}
	g = layer.getGraphics();
	g.setColor(Color.white);
	g.fillRect(0, 0, this.WIDTH, this.HEIGHT);
	world.render(g);
	for (Entity entity : entities) {
	    entity.render(g);
	    if (entity instanceof Enemy) {
		Enemy e = (Enemy) entity;
		for (int i = 0; i < e._list.size() - 1; i++) {
		    Point p1 = e._list.get(i);
		    Point p2 = e._list.get(i + 1);
//		    g.drawLine(p1.x - Camera.x, p1.y - Camera.y, p2.x - Camera.x, p2.y - Camera.y);

		}
	    }
	}

	UI.render(g);

	g = bs.getDrawGraphics();
	g.drawImage(layer, 0, 0, this.WIDTH * this.SCALE, this.HEIGHT * this.SCALE, null);
	bs.show();
	g.dispose();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
	if (!vk_right_press && (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)) {

	    player.setRight(true);
	    vk_right_press = true;
	    player.setFrame_x(2);
	    player.setFrame_y(0);

	} else if (!vk_left_press && (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)) {
	    player.setLeft(true);
	    player.setFrame_x(5);
	    player.setFrame_y(1);
	    vk_left_press = true;

	}

	if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
	    player.setUp(true);

	} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
	    player.setDown(true);

	}

    }

    @Override
    public void keyReleased(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
	    player.setRight(false);
	    vk_right_press = false;
	} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
	    player.setLeft(false);
	    vk_left_press = false;
	}

	if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
	    player.setUp(false);

	} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
	    player.setDown(false);

	}
    }

}
