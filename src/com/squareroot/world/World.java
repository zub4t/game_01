package com.squareroot.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import com.squareroot.util.Astar;
import com.squareroot.util.Point;
import com.squareroot.entities.Ammo;
import com.squareroot.entities.Enemy;
import com.squareroot.entities.Entity;
import com.squareroot.entities.Health;
import com.squareroot.entities.Weapon;
import com.squareroot.main.Game;

public class World {
    public static Tile[][] tiles;
    public static boolean[][] tilesWORLD;
    public static int WIDTH, HEIGHT;

    public World(String path) {
	try {
	    BufferedImage map = ImageIO.read(getClass().getResource(path));
	    int[] pixels = new int[map.getTileWidth() * map.getHeight()];
	    tiles = new Tile[map.getTileWidth()][map.getHeight()];
	    WIDTH = map.getWidth();
	    HEIGHT = map.getHeight();
	    tiles = new Tile[HEIGHT][WIDTH];
	    tilesWORLD = new boolean[HEIGHT << 4][WIDTH << 4];

	    map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
	    int c = 0;
	    for (int p : pixels) {
		int i, j;
		j = c % WIDTH;

		i = (int) c / WIDTH;

		tiles[i][j] = new TileFloor(j << 4, i << 4, Tile.TILE_FLOOR);
		if (p == 0xFFFF0C00) {
		    Game.entities.add(new Enemy(j << 4, i << 4, 16, 16, Entity.ENEMY));
		} else if (p == 0xFFE5FF00) {
		    Game.entities.add(new Health(j << 4, i << 4, 16, 16, Entity.LIFE_PACK));
		} else if (p == 0xFFB200FF) {
		    Game.entities.add(new Ammo(j << 4, i << 4, 16, 16, Entity.BULLET));
		} else if (p == 0xFFFF00D0) {
		    Game.entities.add(new Weapon(j << 4, i << 4, 16, 16, Entity.GUN));
		} else if (p == 0xFFFFFFFF) {
		    tiles[i][j] = new TileWall(j << 4, i << 4, Tile.TILE_WALL);
		} else if (p == 0xFF0033FF) {
		    System.out.printf("[%d][%d]\n", i, j);
		    Game.player.setX(j << 4);
		    Game.player.setY(i << 4);

		}
		c++;
	    }

	    for (int i = 0; i < tiles.length; i++) {
		for (int j = 0; j < tiles[0].length; j++) {

		    for (int w = 0; w < 16; w++) {

			for (int z = 0; z < 16; z++) {
			    tilesWORLD[(i * 16) + w][(j * 16) + z] = tiles[i][j] instanceof TileWall;
			}
		    }

		}
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}

//	System.out.println("");
//	miniMap();
//	System.out.println("");
//
//	realMap();
//	System.out.println("");

    }

    public void miniMap() {
	for (int i = 0; i < tiles.length; i++) {
	    for (int j = 0; j < tiles[0].length; j++) {
		if (tiles[i][j] instanceof TileWall) {
		    System.out.print("*");
		} else {
		    System.out.print("-");
		}
	    }
	    System.out.println("");
	}
    }

    public void realMap() {

	for (int i = 0; i < tilesWORLD.length; i++) {
	    for (int j = 0; j < tilesWORLD[0].length; j++) {
		if (tilesWORLD[i][j]) {
		    System.out.print("*");
		} else {
		    System.out.print("-");
		}
	    }
	    System.out.println("");
	}

    }

    public void render(Graphics g) {

	g.setColor(Color.black);
	g.fillRect(0, 16, 16, 16);
	int offetset = 16;
	for (int i = 0; i < HEIGHT; i++) {
	    for (int j = 0; j < WIDTH; j++) {
		if (tiles[i][j] != null) {
		    tiles[i][j].tick();
		    tiles[i][j].render(g);

		}
	    }
	}
    }

}
