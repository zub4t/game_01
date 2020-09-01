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
    public static List<Entity> entities_to_add_at_runtime;
    public static List<Entity> entities_to_remove_at_runtime;

    public static SpriteSheet spritesheet;
    public static Player player;
    public static int fps;
    public boolean vk_right_press;
    public boolean vk_left_press;
    public static double timer_tick;

    private World world;

    public Game() {
	addKeyListener(this);
	jframe = new JFrame("Game");
	jframe.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
	entities = new ArrayList<>();
	entities_to_add_at_runtime = new ArrayList<>();
	entities_to_remove_at_runtime = new ArrayList<>();
	spritesheet = new SpriteSheet("/spriteSheet.png");
	player = new Player(0, 0, 16, 16, spritesheet.getSprite(2 * 16, 0 * 16, 16, 16));
	world = new World("/map_1.png");
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
	    for (Entity e : entities_to_add_at_runtime) {
		entities.add(e);

	    }
	    for (Entity e : entities_to_remove_at_runtime) {
		
		    entities.remove(e);

	    }
	    entities_to_add_at_runtime.clear();
	    entities_to_remove_at_runtime.clear();
	    if (timer + 1000 <= System.currentTimeMillis()) {
		timer = System.currentTimeMillis();
		//System.out.println("fps:" + fps);
		fps = 0;
		Astar.key = true;
	    }

	}

    }

    public synchronized void tick() {
	for (Entity entity : entities) {
	    timer_tick = System.currentTimeMillis();
	    entity.tick();

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

	if (e.getKeyCode() == KeyEvent.VK_X) {
	    player.setCan_shooting(true);
	}

	if (!vk_right_press && (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)) {
	    player.setRight(true);
	    vk_right_press = true;

	} else if (!vk_left_press && (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)) {
	    player.setLeft(true);
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

	if (e.getKeyCode() == KeyEvent.VK_X) {
	    player.setCan_shooting(false);
	}
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
