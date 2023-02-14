package com.tebyan.maze_generator.examples;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.tebyan.maze_generator.AStarPathSolvingAlgo;
import com.tebyan.maze_generator.BlockCell;
import com.tebyan.maze_generator.MazeFrame;
import com.tebyan.maze_generator.MazeGenerator;
import com.tebyan.maze_generator.MazePanel;
import com.tebyan.maze_generator.Node;
import com.tebyan.maze_generator.Cell.CellType;
import com.tebyan.maze_generator.InputManager.InputListener;
import com.tebyan.maze_generator.MazePanel.OnDrawCallback;

public class AStarExample {

	List<Node> path;
	MazePanel maze;
	MazeFrame frame;
	public AStarExample(int width,int height,int mazeSize,boolean fullScreen) {
		frame=new MazeFrame(width, height, fullScreen);
		maze=new MazePanel(mazeSize, mazeSize, frame.inputManager);
	}
	public void start() {
		BlockCell[][] map=MazeGenerator.generateFullyBLockMaze(15, 15, CellType.WALKABLE);
		
		
		map[0][0].cellType=CellType.ENTRANCE;
		map[14][14].cellType=CellType.TARGET;
		maze.setMap(map);
		frame.add(maze);
		path=new ArrayList<>();
//		tracePath(map);
		maze.addOnDrawCallback(new OnDrawCallback() {
			
			@Override
			public void onDrawBeforeMaze(Graphics2D g) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDrawAfterMaze(Graphics2D g) {
				for (Node node : path) {
					map[node.y][node.x].fillCell(g, Color.CYAN,.5f);
				}
				
			}
		});
		frame.inputManager.addInputCallback(new InputListener() {
			
			@Override
			public void onKeyReleased(int keyCode) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onKeyPressedOnce(int keyCode) {
				if((char)keyCode=='U') {
					
					tracePath(map);
					maze.repaint();
				}
				
			}
			
			@Override
			public void onKeyPressed(int keyCode) {
				// TODO Auto-generated method stub
				
			}
		});
		frame.setVisible(true);
		
		
		
	}
	public void tracePath(BlockCell[][] map) {
		Node[][] nodeMap=MazeGenerator.blockToNodeMap(map);
		path=AStarPathSolvingAlgo.solvePath(nodeMap[0][0], nodeMap[14][14], nodeMap);
		
	}

}
