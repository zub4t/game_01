package com.squareroot.util;

import java.util.ArrayList;
import java.util.List;

import com.squareroot.world.World;

public class Astar {
    public List<Point> findPathTo(int origin_x, int origin_y, int destint_x, int destiny_y) {
	MinHeap minheap = new MinHeap(World.WIDTH << 4 * World.HEIGHT << 4);
	Point visited[][] = new Point[World.WIDTH << 4][World.HEIGHT << 4];
	Point root = new Point(origin_x, origin_y, calcManhattanDistance(origin_x, origin_y, destint_x, destiny_y),
		null);
	List<Point> list = new ArrayList<>();
	Point destiny = new Point(destint_x, destiny_y, 0, null);
	minheap.insert(root);
	while (true) {
	    minheap.minHeap();
	    Point current = minheap.remove();
	    if (current.compareTo(destiny) == 0) {
		while (current != null) {
		    list.add(current);
		    current = current.parent;
		}
		return list;
	    }

	    List<Point> neighborhood = current.makeNeighborhood();
	    for (Point p : neighborhood) {
		p.d = calcManhattanDistance(p.x, p.y, destint_x, destiny_y);
		try {
		    if (visited[p.x][p.y] == null) {
			visited[p.x][p.y] = p;
			minheap.insert(p);
		    } else {
			if (visited[p.x][p.y].getD() > p.getD()) {
			    visited[p.x][p.y] = p;
			    minheap.att(p);
			}
		    }
		} catch (Exception e) {
		    System.out.printf("valor fora do intervalo [%d][%d]\n", p.x, p.y);
		}
	    }

	}

    }

    public static int calcManhattanDistance(int x1, int y1, int x2, int y2) {

	return Math.abs(x1 - x2) + Math.abs(y1 - y2);

    }
}
