package com.tebyan.maze_generator;

import java.util.ArrayList;
import java.util.List;

import com.tebyan.maze_generator.Cell.CellType;


public class Node {
	public Node(int x, int y) {
		this.x=x;
		this.y=y;
		// TODO Auto-generated constructor stub
	}



	public static final int NONDIAGONALMOVE=10;
	public static final int DIAGONALMOVE=14;
	
	public Node parent;
	public CellType cellType;


	
	public int x,y;
	public int g,h,f;
	

	public void setG(int g) {
		this.g = g;
		this.f=g+h;
	}
	
	public void setH(int h) {
		this.h = h;
		this.f=g+h;
	}

	


	public static Node[] get8NeighboNodes(Node node,Node[][] map) {
		int width=map[0].length;
		int height=map.length;
		List<Node> list=new ArrayList<>();
		for (int i = -1; i <=1; i++) {
			for (int j = -1; j <=1; j++) {
				if(i==0&&j==0) {
					continue;
					// Node is the center meaning node itself
				}
				if(isPosValidOnMap(j, i,width, height)) {
					Node n=map[i][j];
					if(isNodeValid(n, map)) {
						list.add(n);
					}
				}
			}
		}
		return list.toArray(new Node[0]);
	}
	public Node[] get4Neighbors(Node[][] map) {
		return get4Neighbors(this,map);
	}
	
	public static Node[] get4Neighbors(Node node,Node[][] map) {
		List<Node> neighbors=new ArrayList<>();
		int width=map[0].length;
		int height=map.length;
		int x=node.x,y=node.y-1;
		if(isPosValidOnMap(x, y, width, height)) {
			Node n=map[y][x];
			
			if(isNodeWalkable(n)) {
				neighbors.add(n);
			}
		}
		x=node.x+1;y=node.y;
		if(isPosValidOnMap(x, y, width, height)) {
			
			Node n=map[y][x];
			if(isNodeWalkable(n)) {
				
				neighbors.add(n);
			}
		}
		x=node.x-1;y=node.y;
		if(isPosValidOnMap(x, y, width, height)) {
			Node n=map[y][x];
			if(isNodeWalkable(n)) {
				neighbors.add(n);
			}
		}
		x=node.x;y=node.y+1;
		if(isPosValidOnMap(x, y, width, height)) {
			Node n=map[y][x];
			if(isNodeWalkable(n)) {
				neighbors.add(n);
			}
		}
		return neighbors.toArray(new Node[0]);
	}
	public static boolean isNodeValid(Node node,Node[][] map) {
		return isNodeWalkable(node)&&(isPosValidOnMap(node.x, node.y, map[0].length, map.length));
	}
	public static boolean isNodeWalkable(Node node) {
		return node.cellType!=CellType.UNWALKABLE;
	}
	public static boolean isPosValidOnMap(int x,int y,int width,int height) {
		return x>=0&&x<width&&y>=0&&y<height;
	}
	@Override
	public String toString() {
		return "Node [x=" + x + ", y=" + y + ", g=" + g + ", h=" + h + ", f=" + f + "]";
	}

	
	
	
	
}
