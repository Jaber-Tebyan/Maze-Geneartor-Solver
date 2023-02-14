package com.tebyan.maze_generator;

import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.CellRendererPane;

import com.tebyan.maze_generator.Cell.CellType;




public class MazeGenerator {
	
	
	public static BlockCell[][] generateRandomBlockMaze(int width,int height){
		BlockCell[][] map=new BlockCell[height][width];
		Random random=new Random();
		
		int r;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				r=random.nextInt(2);
				BlockCell cell=new BlockCell(j,i,r==0?CellType.WALKABLE:r==1?CellType.UNWALKABLE:null);
				if(r==0) {
					cell.cellType=CellType.WALKABLE;
				}
				else if(r==1) {
					cell.cellType=CellType.UNWALKABLE;
				}
				else {
				}
				map[i][j]=cell;
				
			}
		}
		return map;
	}
	
	public static BlockCell[][] generateDPSBlockMap(BlockCell[][] map,long delay,DelayCallback callback){
		Stack<BlockCell> temp=new Stack<>();
		Random random=new Random();
		int width=(map[0].length-1)/2,height=(map.length-1)/2;
		BlockCell currentCell=map[random.nextInt(height)*2+1][random.nextInt(width)*2+1];
		currentCell.visited=true;
		temp.push(currentCell);
		long timer=System.currentTimeMillis();
		while(!temp.isEmpty()) {
			if(System.currentTimeMillis()-timer>delay) {
				timer=System.currentTimeMillis();
				currentCell=temp.pop();
				if(callback!=null)callback.callback(map, currentCell);
				List<BlockCell> neighbors=currentCell.get4xUnvisitedRoomNeighbors(map);
				if(!neighbors.isEmpty()) {
					currentCell=temp.push(currentCell);
					BlockCell randNeighbor=neighbors.get(random.nextInt(neighbors.size()));
					BlockCell.carvePath(currentCell, randNeighbor, map);
					currentCell=temp.push(randNeighbor);
					randNeighbor.visited=true;				
				}

			}
			
		}
		return map;
	}
	public static Cell[][] generateDPSBlockMap(BlockCell[][] map) {
		return generateDPSBlockMap(map, 0, null);
		
	}
	
	public static BlockCell[][] createBlockCellRooms(int row,int column){
		row=row*2+1;column=column*2+1;
		
		BlockCell[][] map=new BlockCell[row][column];
		for (int i = 0; i < row; i++) {
			if(i%2==0) {
				for (int j = 0; j < column; j++) {
					BlockCell cell=new BlockCell(j, i, CellType.UNWALKABLE);
					map[i][j]=cell;
				}
			}else {
				for (int j = 0; j < column; j++) {
					if(j%2==0) {
						BlockCell cell=new BlockCell(j, i, CellType.UNWALKABLE);
						map[i][j]=cell;
					}
					else {
						BlockCell cell=new BlockCell(j, i, CellType.WALKABLE);
						map[i][j]=cell;
					}
				}
				
			}
		}
		return map;
	}
	public static WalledCell[][] createWalledCells(int row,int column){
		WalledCell[][] map=new WalledCell[row][column];
		Random random=new Random();
		
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				int r=random.nextInt(2);
				WalledCell cell=new WalledCell(i,j);
				for (int k = 0; k < cell.walls.length; k++) {
					if(r==0) {
						cell.walls[k]=false;
					}else {
						cell.walls[k]=true;
					}
				}
				map[i][j]=cell;
				
			}
		}
		return map;
	}
	public static WalledCell[][] createFullyWalledMaze(int row, int column){
		WalledCell[][] map=new WalledCell[row][column];
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j]=new WalledCell(j,i);
			}
		}
		return map;
	}
	
	public static WalledCell[][] generateIterativeDepthFirstWalledMaze(int row,int column,long delay,DelayCallback callback){
		WalledCell[][] map=createFullyWalledMaze(row, column);
		Random random=new Random();
		Stack<WalledCell> stack=new Stack<>();  
		WalledCell currentCell=map[random.nextInt(row)][random.nextInt(column)];
		currentCell.visited=true;
		stack.push(currentCell);
		long timer=System.currentTimeMillis();
		while(!stack.isEmpty()) {
			
			if(System.currentTimeMillis()-timer>=delay) {
				timer=System.currentTimeMillis();
				currentCell=stack.pop();
				Cell[] temp= currentCell.getUnvisitedNeighbors(map);
				WalledCell[] neighbors= Arrays.copyOf(temp, temp.length, WalledCell[].class);
				if(neighbors.length>0) {
					stack.push(currentCell);
					WalledCell c=neighbors[random.nextInt(neighbors.length)];
					currentCell.removeWallAgainst(c);
					c.visited=true;
					currentCell=stack.push(c);
				}
				if(callback!=null)callback.callback(map,currentCell);
			}
			
			
		}
		return map;
	}
	public static WalledCell[][] generateIterativeDepthFirstWalledMaze(int row,int column){
		return generateIterativeDepthFirstWalledMaze(row, column,0,null);
	}
	public static BlockCell[][] generateFullWalledBlockMaze(int row,int column){
		BlockCell[][] map=new BlockCell[row][column];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				BlockCell cell=new BlockCell(i,j,CellType.WALKABLE);
				map[i][j]=cell;
			}
		}
		return map;
	}
	public static BlockCell[][] generateIterativeDepthFirstBlockMaze(int row,int column){
		BlockCell[][] map=generateFullWalledBlockMaze(row, column);
		Stack<BlockCell> stack=new Stack<>();
		Random random=new Random();
		BlockCell currentCell=map[random.nextInt(row)][random.nextInt(column)];
		currentCell.visited=true;
		
		return map;
	}
	public static BlockCell[][] generateMazeFromIntMap(int[][] p){
		BlockCell[][] map=new BlockCell[p.length][p[0].length];
		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < p[i].length; j++) {
				switch (p[i][j]) {
					case 0:
						map[i][j]=new BlockCell(j,i,CellType.WALKABLE);
						break;
					case 1:
						map[i][j]=new BlockCell(j,i,CellType.UNWALKABLE);
						break;
	
					default:
						break;
				}
			}
		}
		return map;
	}
	public static BlockCell[][] generateFullyBLockMaze(int width,int height,CellType cellType){
		BlockCell[][] map=new BlockCell[width][height];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				BlockCell cell=new BlockCell(j,i,cellType);
				map[i][j] =cell;
			}
		}
		return map;
	}
	public static Node[][] blockToNodeMap(BlockCell[][] m){
		Node[][] map=new Node[m.length][m[0].length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				BlockCell c=m[i][j];
				Node n=new Node(j,i);
				n.cellType=c.cellType;
				map[i][j]=n;
			}
		}
		return map;
	}
	


	
	public interface DelayCallback{
		void callback(Cell[][] map,Cell currentCell);
	}




	
	
	

}
