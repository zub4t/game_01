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
import com.squareroot.entities.Bullet;
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
	    tilesWORLD = new boolean[map.getTileWidth() * 16][map.getHeight() * 16];
	    WIDTH = map.getWidth();
	    HEIGHT = map.getHeight();
	    map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
	    int c = 0;
	    for (int p : pixels) {
		int i, j;
		j = (int) c / 20;
		;
		i = c % 20;

		tiles[i][j] = new TileFloor(i << 4, j << 4, Tile.TILE_FLOOR);
		if (p == 0xFFFF0C00) {
		    Game.entities.add(new Enemy(i << 4, j << 4, 16, 16, Entity.ENEMY));
		} else if (p == 0xFFE5FF00) {
		    Game.entities.add(new Health(i << 4, j << 4, 16, 16, Entity.LIFE_PACK));
		} else if (p == 0xFFB200FF) {
		    Game.entities.add(new Bullet(i << 4, j << 4, 16, 16, Entity.BULLET));
		} else if (p == 0xFF007F0E) {
		    Game.entities.add(new Weapon(i << 4, j << 4, 16, 16, Entity.GUN));
		} else if (p == 0xFFFFFFFF) {
		    tiles[i][j] = new TileWall(i << 4, j << 4, Tile.TILE_WALL);
		} else if (p == 0xFF0033FF) {
		    Game.player.setX(i << 4);
		    Game.player.setY(j << 4);

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
	double timer = System.currentTimeMillis();
	Astar astar = new Astar();
	//List<Point> list = astar.findPathTo(190, 16, 14, 16);
	List<Point> list_y = astar.findPathToInNormalMap(190, 16, 14, 16);

	System.out.println(System.currentTimeMillis() - timer);
	System.out.println("");
    }

    public void miniMap() {
	for (int i = 0; i < tiles.length; i++) {
	    for (int j = 0; j < tiles[0].length; j++) {
		if (tiles[j][i] instanceof TileWall) {
		    System.out.print("*");
		} else {
		    System.out.print("-");
		}
	    }
	    System.out.println("");
	}
	System.out.printf("outra  tamanho [%d][%d]\n", tilesWORLD[0].length, tilesWORLD.length);
    }

    public void realMap() {

	for (int i = 0; i < tilesWORLD.length; i++) {
	    for (int j = 0; j < tilesWORLD[0].length; j++) {
		if (tilesWORLD[j][i]) {
		    System.out.print("*");
		} else {
		    System.out.print("-");
		}
	    }
	    System.out.println("");
	}

    }

    public void render(Graphics g) {
	int offetset = 16;
	for (int i = 0; i < HEIGHT; i++) {
	    for (int j = 0; j < WIDTH; j++) {
		if (tiles[i][j] != null) {
		    int x = tiles[i][j].x;
		    int y = tiles[i][j].y;
		    if (Camera.x <= x + offetset && x - offetset <= Camera.x + Game.WIDTH) {
			if (Camera.y <= y + offetset && y - offetset <= Camera.y + Game.HEIGHT) {
			    tiles[i][j].tick();
			    tiles[i][j].render(g);
			}
		    }

		}
	    }
	}
    }

}
