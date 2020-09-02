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

    public Point(int x, int y, int g) {
	super();
	this.x = x;
	this.y = y;
	this.g = g;
    }

    public Point(int x, int y) {
	super();
	this.x = x;
	this.y = y;
    }

    public int x;
    public int y;
    public int d;
    public int h;
    public int g;
    public Point parent;

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
	Point W = new Point(x - 1, y, this.g + 1, this);
	Point L = new Point(x + 1, y, this.g + 1, this);
	Point S = new Point(x, y + 1, this.g + 1, this);
	Point N = new Point(x, y - 1, this.g + 1, this);

	list.add(W);
	list.add(L);
	list.add(S);
	list.add(N);

	return list;

    }

    public List<Point> makeNeighborhoodHard() {
	List<Point> list = new ArrayList<>();
	Point W = new Point(x - 1, y, this.g + 1, this);
	Point L = new Point(x + 1, y, this.g + 1, this);
	Point S = new Point(x, y + 1, this.g + 1, this);
	Point N = new Point(x, y - 1, this.g + 1, this);
	Point NW = new Point(x - 1, y - 1, this.g + 1, this);
	Point NE = new Point(x + 1, y - 1, this.g + 1, this);
	Point SW = new Point(x - 1, y + 1, this.g + 1, this);
	Point SE = new Point(x + 1, y + 1, this.g + 1, this);

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

    public static Point add(Point a, Point b) {

	return new Point(a.x + b.x, a.y + b.y);
    }
}
