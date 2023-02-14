package com.tebyan.maze_generator.examples;

import java.awt.Color;
import java.awt.Graphics2D;

import com.tebyan.maze_generator.BlockCell;
import com.tebyan.maze_generator.Cell;
import com.tebyan.maze_generator.MazeFrame;
import com.tebyan.maze_generator.MazeGenerator;
import com.tebyan.maze_generator.MazePanel;
import com.tebyan.maze_generator.MazeGenerator.DelayCallback;
import com.tebyan.maze_generator.MazePanel.OnDrawCallback;

public class DPSBlockMazeExample {

	public BlockCell[][] map;
	public MazeFrame frame;
	public MazePanel panel;
	Cell currentCella;
	public DPSBlockMazeExample(int height,int width,int mazeSize,boolean fullscreen) {
		frame=new MazeFrame(width, height, fullscreen);
		panel=new MazePanel(mazeSize,mazeSize,frame.inputManager);
		frame.add(panel);
		frame.setVisible(true);
		panel.addOnDrawCallback(new OnDrawCallback() {
			
			@Override
			public void onDrawBeforeMaze(Graphics2D g) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDrawAfterMaze(Graphics2D g) {
				if(currentCella!=null)currentCella.fillCell(g, Color.green);
				
			}
		});
	}
	

	public void start(int roomCount,boolean visualise) {
		
		map=MazeGenerator.createBlockCellRooms(roomCount,roomCount);
		
		if(visualise) {
			MazeGenerator.generateDPSBlockMap(map,10,new DelayCallback() {
				
				@Override
				public void callback(Cell[][] map, Cell currentCell) {
					panel.setMap(map);
					currentCella=currentCell;
					
				}
			});
		}else {
			MazeGenerator.generateDPSBlockMap(map);
			panel.setMap(map);
		}
	}
}
