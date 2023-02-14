package com.tebyan.maze_generator;

import java.util.ArrayList;
import java.util.List;

public class BlockCell extends Cell {

	



	public BlockCell(int x,int y,CellType cellType) {
		super(x,y);
		this.cellType=cellType;
		// TODO Auto-generated constructor stub
	}

	public CellType cellType;


	public List<BlockCell> get4xRoomNeighbors(Cell[][] map) {
		
		int width=(map[0].length-1)/2,height=(map.length-1)/2;
		int x=(this.x-1)/2,y=(this.y-1)/2;
		List<BlockCell> list=new ArrayList<>();
		
		if(x>0) {
			Cell i=map[this.y][this.x-2];
			list.add((BlockCell) i);
		}
		if(x<width-1) {
			Cell i=map[this.y][this.x+2];
				list.add((BlockCell) i);
			
			
		}
		if(y>0) {
			Cell i=map[this.y-2][this.x];
			
				list.add((BlockCell) i);
			
		}
		if(y<height-1) {
			Cell i=map[this.y+2][this.x];
			
				list.add((BlockCell) i);
			
		}
		
		return list;
	}
	public List<BlockCell> get4xUnvisitedRoomNeighbors(Cell[][] map){
		List<BlockCell> temp=get4xRoomNeighbors(map);
		List<BlockCell> s=new ArrayList<>();
		for (BlockCell i : temp) {
			if(!i.visited) {
				s.add(i);
			}
		}
		return s;
	}
	public static void carvePath(BlockCell start,BlockCell end,Cell[][] map) {
		
		int x=(start.x+end.x)/2,y=(start.y+end.y)/2;
		BlockCell cell=(BlockCell) map[y][x];
		cell.cellType=CellType.WALKABLE;
		
	}

	
}
