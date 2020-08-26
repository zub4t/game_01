package com.squareroot.util;

import java.util.ArrayList;
import java.util.List;

public class Point implements Comparable<Point> {
    public Point(int x, int y, int d, Point parent) {
	super();
	this.x = x;
	this.y = y;
	this.d = d;
	this.parent = parent;
    }

    int x;

    public Point(int x, int y, int d) {
	super();
	this.x = x;
	this.y = y;
	this.d = d;
    }

    int y;
    int d;
    Point parent;

    public int getX() {
	return x;
    }

    public void setX(int x) {
	this.x = x;
    }

    public int getY() {
	return y;
    }

    public void setY(int y) {
	this.y = y;
    }

    public int getD() {
	return d;
    }

    public void setD(int d) {
	this.d = d;
    }

    public Point getParent() {
	return parent;
    }

    public void setParent(Point parent) {
	this.parent = parent;
    }

    @Override
    public int compareTo(Point o) {
	if (this.x == o.x) {
	    if (this.y == o.y)
		return 0;
	    else
		return 1;
	} else
	    return -1;

    }

    @Override
    public String toString() {
	return "[" + x + "][" + y + "]->(" + d + ")";
    }

    public List<Point> makeNeighborhood() {
	List<Point> list = new ArrayList<>();
	Point W = new Point(x - 1, y, 0, this);
	Point L = new Point(x + 1, y, 0, this);
	Point S = new Point(x, y + 1, 0, this);
	Point N = new Point(x, y - 1, 0, this);
	Point NW = new Point(x - 1, y - 1, 0, this);
	Point NE = new Point(x + 1, y - 1, 0, this);
	Point SW = new Point(x - 1, y + 1, 0, this);
	Point SE = new Point(x + 1, y + 1, 0, this);

	list.add(W);
	list.add(L);
	list.add(S);
	list.add(N);
	list.add(NW);
	list.add(NE);
	list.add(SW);
	list.add(SE);

	return list;

    }

}
