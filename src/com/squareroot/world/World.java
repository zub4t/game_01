package com.squareroot.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.squareroot.entities.Bullet;
import com.squareroot.entities.Enemy;
import com.squareroot.entities.Entity;
import com.squareroot.entities.Health;
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
				j = (int) c / 20;;
				i = c % 20;

				tiles[i][j] = new TileFloor(i << 4, j << 4, Tile.TILE_FLOOR);
				if (p == 0xFFFF0C00) {
					Game.entities.add(
							new Enemy(i << 4, j << 4, 16, 16, Entity.ENEMY));
				} else if (p == 0xFFE5FF00) {
					Game.entities.add(new Health(i << 4, j << 4, 16, 16,
							Entity.LIFE_PACK));
				} else if (p == 0xFFB200FF) {
					Game.entities.add(
							new Bullet(i << 4, j << 4, 16, 16, Entity.BULLET));
				} else if (p == 0xFF007F0E) {
					Game.entities.add(
							new Weapon(i << 4, j << 4, 16, 16, Entity.GUN));
				} else if (p == 0xFFFFFFFF) {
					tiles[i][j] = new TileWall(i << 4, j << 4, Tile.TILE_WALL);
				} else if (p == 0xFF0033FF) {
					Game.player.setX(j << 4);
					Game.player.setY(i << 4);

				}
				c++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void render(Graphics g) {

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