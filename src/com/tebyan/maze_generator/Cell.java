package com.tebyan.maze_generator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tebyan.maze_generator.WalledCell.WallDir;



public class Cell {
	@Override
	public String toString() {
		return "Cell [visited=" + visited + ", x=" + x + ", y=" + y + "]";
	}
	public static int cellSize=0;
	boolean visited=false;
	int x,y;
	
	public Point getAbsPos() {
		return new Point(x*cellSize,y*cellSize);
	}
	public Point getEndPoints() {
		Point a=getAbsPos();
		return new Point(a.x+cellSize,a.y+cellSize);
	}

	public Cell(int x,int y) {
		this.y=y;
		this.x=x;
	}

	public void fillCell(Graphics2D g,Color color) {
		g.setColor(color);
		Point absPos=getAbsPos();
		int width=(int)(cellSize);
		int height=(int)(cellSize);
		g.fillRect((int)(absPos.x), (int)(absPos.y), width, height);
	}
	public void fillCell(Graphics2D g,Color color,float scale) {
		g.setColor(color);
		Point absPos=getAbsPos();
		int center=(int) (cellSize*(1-scale))/2;
		
		g.fillRect((int) ((int)absPos.x+center),(int) ((int)absPos.y+center),(int) (cellSize*scale),(int) (cellSize*scale));
	}
	public Cell[] getUnvisitedNeighbors(Cell[][] map) {
		return getUnvisitedNeighbors(this,map);
	}
	public static Cell[] get4x4Neighbors(Cell cell,Cell[][] map) {
		List<Cell> list=new ArrayList<>();
		if(cell.x>0) {
			Cell i=map[cell.y][cell.x-1];
			
				list.add(i);
			
		}
		if(cell.x<map[cell.y].length-1) {
			Cell i=map[cell.y][cell.x+1];
			
				list.add(i);
			
			
		}
		if(cell.y>0) {
			Cell i=map[cell.y-1][cell.x];
			
				list.add(i);
			
		}
		if(cell.y<map.length-1) {
			Cell i=map[cell.y+1][cell.x];
			
				list.add(i);
			
		}
		return list.toArray(new Cell[0]);
	}
	public static Cell[] getUnvisitedNeighbors(Cell cell,Cell[][] map) {
		
		
		
		List<Cell> neighbors=Arrays.asList(get4x4Neighbors(cell, map));
		List<Cell> unvisitedNeighbors=new ArrayList<>();
		for (int i = 0; i < neighbors.size(); i++) {
			if(!neighbors.get(i).visited)
			{
				unvisitedNeighbors.add(neighbors.get(i));
			}
			
		}
		return unvisitedNeighbors.toArray(new Cell[0]);
	}
	
	public void DrawOutline(Graphics2D g,Color color) {
		Point v=getAbsPos();
		Point e=getEndPoints();
		g.setStroke(new BasicStroke(.1f));
		g.setColor(color);
		g.drawLine(v.x, v.y, e.x, v.y);
		g.drawLine(e.x, v.y, e.x, e.y);
		g.drawLine(v.x, e.y, e.x, e.y);
		g.drawLine(v.x, v.y, v.x, e.y);
		
	}
	public enum CellType{
		WALKABLE,UNWALKABLE,ENTRANCE,TARGET
	}
}
