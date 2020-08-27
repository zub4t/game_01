package com.squareroot.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.squareroot.entities.Enemy;
import com.squareroot.entities.Entity;
import com.squareroot.main.Game;
import com.squareroot.world.World;

public class Astar {
    public static int max = 1;
    public static boolean key = true;

    public List<Point> findPathTo(int origin_x, int origin_y, int destiny_x, int destiny_y) {
	MinHeap openList = new MinHeap(World.WIDTH << 4 * World.HEIGHT << 4);
	Point start_node = new Point(origin_x, origin_y, 0, null);
	Point destiny = new Point(destiny_x, destiny_y, 0, null);

	Point isInOpenList[][] = new Point[World.WIDTH << 4][World.HEIGHT << 4];
	Point isInClosedList[][] = new Point[World.WIDTH << 4][World.HEIGHT << 4];
	List<Point> list = new ArrayList();

	openList.insert(start_node);
	while (openList.size > 0) {
	    Point current_node = openList.remove();
	    if (current_node.compareTo(destiny) == 0) {
		while (current_node != null) {
		    list.add(current_node);
		    current_node = current_node.parent;

		}
		list = reverseArrayList(list);
		return list;
	    }
	    List<Point> neighborhood = current_node.makeNeighborhood();
	    for (Point neighbor : neighborhood) {
		try {
		    if (Entity.canMoveTo(neighbor.x, neighbor.y)) {
			int successor_current_cost = current_node.g + 1;
			if (isInOpenList[neighbor.x][neighbor.y] != null) {
			    if (neighbor.g <= successor_current_cost) {
				continue;
			    }
			} else if (isInClosedList[neighbor.x][neighbor.y] != null) {
			    if (neighbor.g <= successor_current_cost)
				continue;
			    isInOpenList[neighbor.x][neighbor.y] = isInClosedList[neighbor.x][neighbor.y];
			    isInClosedList[neighbor.x][neighbor.y] = null;
			    openList.insert(neighbor);
			} else {
			    neighbor.h = calcManhattanDistance(neighbor.x, neighbor.y, destiny.x, destiny.y);
			    neighbor.d = neighbor.h + neighbor.g;
			    isInOpenList[neighbor.x][neighbor.y] = neighbor;
			    openList.insert(neighbor);
			}
		    }

		} catch (Exception e) {
		    System.out.println("fora do intervalo");
		}

	    }
	    isInClosedList[current_node.x][current_node.y] = current_node;
	    isInOpenList[current_node.x][current_node.y] = null;

	}

	return new ArrayList<Point>();

    }

    public List<Point> findPathToInNormalMap(int origin_x, int origin_y, int destiny_x, int destiny_y) {
	origin_x = origin_x >> 4;
	origin_y = origin_y >> 4;
	destiny_x = destiny_x >> 4;
	destiny_y = destiny_y >> 4;
	MinHeap openList = new MinHeap(World.WIDTH * World.HEIGHT);
	Point start_node = new Point(origin_x, origin_y, 0, null);
	Point destiny = new Point(destiny_x, destiny_y, 0, null);
	start_node.h = calcManhattanDistance(start_node.x, start_node.y, destiny.x, destiny.y);

	Point isInOpenList[][] = new Point[World.WIDTH][World.HEIGHT];
	Point isInClosedList[][] = new Point[World.WIDTH][World.HEIGHT];
	List<Point> list = new ArrayList();

	openList.insert(start_node);
	while (openList.size > 0) {
	    Point current_node = openList.remove();
	    if (current_node.compareTo(destiny) == 0 || current_node.h < 1) {
		while (current_node != null) {
		    current_node.x = current_node.x << 4;
		    current_node.y = current_node.y << 4;

		    list.add(current_node);
		    current_node = current_node.parent;

		}
		// list = reverseArrayList(list);
		printElements(list);
		// list = SmoothArray(list);
		printElements(list);
		return list;
	    }
	    List<Point> neighborhood = current_node.makeNeighborhood();
	    for (Point neighbor : neighborhood) {
		try {
		    if (Entity.canMoveTo(neighbor.x << 4, neighbor.y << 4)) {
			int successor_current_cost = current_node.g + 1;
			if (isInOpenList[neighbor.x][neighbor.y] != null) {
			    if (neighbor.g <= successor_current_cost) {
				continue;
			    }
			} else if (isInClosedList[neighbor.x][neighbor.y] != null) {
			    if (neighbor.g <= successor_current_cost)
				continue;
			    isInOpenList[neighbor.x][neighbor.y] = isInClosedList[neighbor.x][neighbor.y];
			    isInClosedList[neighbor.x][neighbor.y] = null;
			    openList.insert(neighbor);
			} else {
			    neighbor.h = calcManhattanDistance(neighbor.x, neighbor.y, destiny.x, destiny.y);
			    neighbor.d = neighbor.h + neighbor.g;
			    isInOpenList[neighbor.x][neighbor.y] = neighbor;
			    openList.insert(neighbor);
			}
		    }

		} catch (Exception e) {
		    System.out.println("fora do intervalo");
		}

	    }
	    isInClosedList[current_node.x][current_node.y] = current_node;
	    isInOpenList[current_node.x][current_node.y] = null;

	}
	System.out.printf("path n finded [%d][%d] to [%d][%d]", origin_x, origin_y, destiny_x, destiny_y);

	return new ArrayList<Point>();

    }

    public static ArrayList<Point> SmoothArray(List<Point> list) {
	ArrayList<Point> returned_list = new ArrayList<>();
	for (int i = 0; i < list.size() - 1; i++) {
	    Point previus = list.get(i);
	    Point next = list.get(i + 1);
	    returned_list.add(previus);

	    int aux_x = next.x - previus.x;
	    int what_happened_x = 0;
	    if (aux_x > 0) {
		what_happened_x = 1;
	    } else if (aux_x < 0) {
		what_happened_x = -1;

	    }

	    int aux_y = next.y - previus.y;
	    int what_happened_y = 0;
	    if (aux_y > 0) {
		what_happened_y = 1;
	    } else if (aux_y < 0) {
		what_happened_y = -1;

	    }

	    for (int j = 0; j < 16; j++) {
		returned_list.add(new Point(previus.x + what_happened_x, previus.y + what_happened_y));
		what_happened_x = what_happened_x == 0 ? 0
			: what_happened_x > 0 ? what_happened_x + 1 : what_happened_x - 1;
		what_happened_y = what_happened_y == 0 ? 0
			: what_happened_y > 0 ? what_happened_y + 1 : what_happened_y - 1;
	    }
	}

	return returned_list;

    }

    public static ArrayList<Point> reverseArrayList(List<Point> alist) {
	// Arraylist for storing reversed elements
	ArrayList<Point> revArrayList = new ArrayList<>();
	for (int i = alist.size() - 1; i >= 0; i--) {

	    // Append the elements in reverse order
	    revArrayList.add(alist.get(i));
	}

	// Return the reversed arraylist
	return revArrayList;
    }

    // Iterate through all the elements and print
    public static void printElements(List<Point> list) {
	for (int i = 0; i < list.size(); i++) {
	    System.out.print(list.get(i) + " ");
	}
	System.out.println(" ");
    }

    public static int calcManhattanDistance(int x1, int y1, int x2, int y2) {

	return Math.abs(x1 - x2) + Math.abs(y1 - y2);

    }

    public double calculateDistanceBetweenPoints(double x1, double y1, double x2, double y2) {
	return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}
