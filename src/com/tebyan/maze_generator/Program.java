package com.tebyan.maze_generator;



import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import com.tebyan.maze_generator.Cell.CellType;
import com.tebyan.maze_generator.InputManager.InputListener;
import com.tebyan.maze_generator.MazeGenerator.DelayCallback;
import com.tebyan.maze_generator.MazePanel.OnDrawCallback;
import com.tebyan.maze_generator.examples.AStarExample;
import com.tebyan.maze_generator.examples.DPSBlockMazeExample;
import com.tebyan.maze_generator.examples.DepthFirstSearchMazeExample;
import com.tebyan.maze_generator.examples.OppenHeimjMazeExample;
import com.tebyan.maze_generator.oppen_heimj.OppenHeimjMazeGenerator;




public class Program {
	static List<Node> path;
	public static void main(String[] args) {
//		DepthFirstSearchMazeExample example=new DepthFirstSearchMazeExample(600,600, 300, true);
//		example.start(10,true,10);
		
		DPSBlockMazeExample example=new DPSBlockMazeExample(1920,1080,800,true);
		example.start(10,false);
		
//		
		
//		
		
		
//		AStarExample example=new AStarExample(500, 500, 500, false);
//		example.start();
	}
	
	
	
	
	public static void print(Object[] obj) {
		for (int i = 0; i < obj.length; i++) {
			Object o=obj[i];
			System.out.println(o);
		}
	}

}
