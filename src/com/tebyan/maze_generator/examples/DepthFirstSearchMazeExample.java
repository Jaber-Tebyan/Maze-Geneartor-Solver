package com.tebyan.maze_generator.examples;

import com.tebyan.maze_generator.MazePanel;
import com.tebyan.maze_generator.MazePanel.OnDrawCallback;

import java.awt.Color;
import java.awt.Graphics2D;

import com.tebyan.maze_generator.Cell;
import com.tebyan.maze_generator.MazeFrame;
import com.tebyan.maze_generator.MazeGenerator;
import com.tebyan.maze_generator.MazeGenerator.DelayCallback;

public class DepthFirstSearchMazeExample {

	public MazeFrame frame;
	public MazePanel panel;
	Cell currentCell=null;
	public DepthFirstSearchMazeExample(int width,int height,int mazeSize,boolean fullScreen) {
		frame=new MazeFrame(width, height, fullScreen);
		panel=new MazePanel(mazeSize,mazeSize,frame.inputManager);
		panel.addOnDrawCallback(new OnDrawCallback() {
			
			@Override
			public void onDrawBeforeMaze(Graphics2D g) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDrawAfterMaze(Graphics2D g) {
				if(currentCell!=null)
				currentCell.fillCell(g, Color.green);
			}
		});
		frame.add(panel);
		frame.setVisible(true);
	}
	public void start(int size,boolean visualize,int delay) {
		if(visualize) {
			Cell[][] map=MazeGenerator.generateIterativeDepthFirstWalledMaze(size, size, delay,visualize? new DelayCallback() {
				
				@Override
				public void callback(Cell[][] map, Cell currentCell) {
					panel.setMap(map);
					DepthFirstSearchMazeExample.this.currentCell=currentCell;
				}
			}:null);
		}
		else {
			Cell[][] map=MazeGenerator.generateIterativeDepthFirstWalledMaze(size, size, 0,null);
			panel.setMap(map);

		}
		
		
	}
}
