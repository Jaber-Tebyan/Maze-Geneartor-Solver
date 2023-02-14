package com.tebyan.maze_generator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;



public class WalledCell extends Cell{


	public WalledCell(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	boolean[] walls=new boolean[] {true,true,true,true};

	public void DrawWalls(Graphics2D g,Color color) {
		g.setStroke(new BasicStroke(1));
		Point v=getAbsPos();
		Point e=getEndPoints();
		g.setColor(color);
		if(walls[WallDir.N.toInt()]) {
			g.drawLine(v.x, v.y, e.x, v.y);
		}
		if(walls[WallDir.E.toInt()]) {
			g.drawLine(e.x, v.y, e.x, e.y);
		}
		if(walls[WallDir.S.toInt()]) {
			g.drawLine(v.x, e.y, e.x, e.y);
		}
		if(walls[WallDir.W.toInt()]) {
			g.drawLine(v.x, v.y, v.x, e.y);
		}
	}
	
	public boolean removeWallAgainst(WalledCell to) {
		return removeWallBetween(this, to);
	}
	public static boolean removeWallBetween(WalledCell from,WalledCell to) {
		boolean flag=false;
		if(from.x==to.x) {

			if(from.y==to.y+1) {
				from.removeWall(WallDir.N);
				to.removeWall(WallDir.S);
				flag=true;
			}else if(from.y==to.y-1) {
				from.removeWall(WallDir.S);
				to.removeWall(WallDir.N);
				flag=true;
			}
		}
		else if(from.y==to.y) {

			if(from.x==to.x-1) {
				from.removeWall(WallDir.E);
				to.removeWall(WallDir.W);
				flag=true;
			}else if(from.x==to.x+1) {
				
				from.removeWall(WallDir.W);
				to.removeWall(WallDir.E);
				flag=true;
			}
		}
		return flag;
		
	}
	
	public void removeWall(WallDir dir) {
		walls[dir.toInt()]=false;
	}

	public enum WallDir{
		N,E,S,W;
		public int toInt() {
			return toInt(this);
		}
		public static int toInt(WallDir dir) {
			switch (dir) {
				case N:
					return 0;
				case E:
					return 1;
				case S:
					return 2;
				case W:
					return 3;
				default:
					return 0;
			}
		}
		public static WallDir fromInt(int i) {
			switch (i) {
			case 0:
				return N;
			case 1:
				return E;
			case 2:
				return S;
			case 3:
				return W;
			default: 
				return null;
				
			}
		}
	}
	
}
