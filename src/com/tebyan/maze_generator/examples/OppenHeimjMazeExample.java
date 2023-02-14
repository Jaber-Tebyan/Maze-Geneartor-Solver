package com.tebyan.maze_generator.examples;

import java.awt.event.KeyEvent;

import com.tebyan.maze_generator.Cell;
import com.tebyan.maze_generator.MazeFrame;
import com.tebyan.maze_generator.MazeGenerator;
import com.tebyan.maze_generator.MazePanel;
import com.tebyan.maze_generator.InputManager.InputListener;
import com.tebyan.maze_generator.oppen_heimj.OppenHeimjMazeGenerator;

public class OppenHeimjMazeExample {
	static int dim=10;

	public OppenHeimjMazeExample(int height,int width,int mazeSize,boolean fullScreen) {
		frame=new MazeFrame(width,height,fullScreen);
		maze=new MazePanel(mazeSize,mazeSize,frame.inputManager);
	}
	MazeFrame frame;
	MazePanel maze;
	
	public void oppenHeimj() {
		int row=20,column=row;
		
		maze.scale=1;
		OppenHeimjMazeGenerator generator=new OppenHeimjMazeGenerator(dim);
		generator.generateMaze();
		
		
		
		maze.setMap(MazeGenerator.generateMazeFromIntMap(generator.getMaze()));
		frame.add(maze);
		frame.setVisible(true);
		frame.inputManager.addInputCallback(new InputListener() {
			
			@Override
			public void onKeyReleased(int keyCode) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onKeyPressed(int keyCode) {
				if(keyCode==KeyEvent.VK_NUMPAD2||keyCode==KeyEvent.VK_NUMPAD8) {
					if(keyCode==KeyEvent.VK_NUMPAD8) {
						dim++;
					}
					if(keyCode==KeyEvent.VK_NUMPAD2) {
						dim--;
					}
					dim=Math.max(dim, 1);
					
					OppenHeimjMazeGenerator generator=new OppenHeimjMazeGenerator(dim);
					generator.generateMaze();
					Cell[][] map=MazeGenerator.generateMazeFromIntMap(generator.getMaze());
					maze.setMap(map);
				}
				
				
				
			}

			@Override
			public void onKeyPressedOnce(int keyCode) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
