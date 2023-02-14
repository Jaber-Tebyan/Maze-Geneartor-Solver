package com.tebyan.maze_generator;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.tebyan.maze_generator.InputManager.InputListener;


public class MazeFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InputManager inputManager;
	public MazeFrame(int width,int height,boolean fullScreen) {
		setTitle("Maze");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		inputManager=new InputManager();
		addKeyListener(inputManager);
		Dimension screenDimension=Toolkit.getDefaultToolkit().getScreenSize();
		addKeyListener(new InputManager());
		
		if(fullScreen) {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setUndecorated(true);
		}
		else{setLocation(screenDimension.width/2-width/2,screenDimension.height/2-height/2);}
		pack();
		setSize(width,height);
		
		
		
	}

	

}

