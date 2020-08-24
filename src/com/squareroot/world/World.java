package com.squareroot.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.squareroot.entities.Enemy;
import com.squareroot.entities.Entity;
import com.squareroot.entities.Weapon;
import com.squareroot.main.Game;

public class World {
	public static Tile[][] tiles;
	public static int WIDTH, HEIGHT;
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getTileWidth() * map.getHeight()];
			tiles = new Tile[map.getTileWidth()][map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0,
					map.getWidth());
			int c = 0;
			for (int p : pixels) {
				int i, j;
				j = c % 20;
				i = (int) c / 20;

				tiles[i][j] = new TileFloor(i * 16, j * 16, Tile.TILE_FLOOR);

				if (p == 0xFFFF0C00) {
					Game.entities.add(
							new Enemy(i * 6, j * 16, 16, 16, Entity.ENEMY));
				} else if (p == 0xFFE5FF00) {
					Game.entities.add(new Weapon(i * 16, j * 16, 16, 16,
							Entity.LIFE_PACK));
				} else if (p == 0xFFB200FF) {
					Game.entities.add(
							new Weapon(i * 16, j * 16, 16, 16, Entity.BULLET));
				} else if (p == 0xFF007F0E) {
					Game.entities.add(
							new Weapon(i * 16, j * 16, 16, 16, Entity.GUN));
				} else if (p == 0xFFFFFFFF) {
					tiles[i][j] = new TileWall(i * 16, j * 16, Tile.TILE_WALL);
				} else if (p == 0xFF0033FF) {
					Game.player.setX(j * 16);
					Game.player.setY(i * 16);

				}
				c++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void render(Graphics g) {
		int xstart = Camera.y >> 4;
		int ystart = Camera.y >> 4;
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = xstart + (Game.HEIGHT >> 4);
		int offeset = 16;
		for (int i = 0; i <= HEIGHT; i++) {
			for (int j = 0; j <= WIDTH; j++) {

				if (j < 0 || i < 0 || i >= HEIGHT || j >= WIDTH)
					continue;
				if (tiles[i][j] != null) {
					tiles[i][j].tick();
					int x = tiles[i][j].x;
					int y = tiles[i][j].y;
					if (x+offeset >= 0 && x-offeset <= Game.WIDTH)
						if (y+offeset >= 0 && y-offeset <= Game.HEIGHT)
							tiles[i][j].render(g);
				}
			}
		}
	}

}
