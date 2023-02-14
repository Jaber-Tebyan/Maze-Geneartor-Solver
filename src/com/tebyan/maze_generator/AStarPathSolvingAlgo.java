package com.tebyan.maze_generator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import com.tebyan.maze_generator.MazeGenerator.DelayCallback;


public class AStarPathSolvingAlgo {

	public static List<Node> solvePath(Node startNode,Node endNode,Node[][] map) {
		List<Node> path=new ArrayList<>();
		List<Node> openList=new ArrayList<>();
		List<Node> closedList=new ArrayList<>();
		openList.add(startNode);
		while(!openList.isEmpty()) {
			Node current=getNodeWithLeastFCost(openList);
			
			
			
			
			if(nodesCorrespondToSamePos(current, endNode)) {
				
				return retracePath(current);
				
			}
			closedList.add(current);
			openList.remove(current);
			Node[] neighbors=current.get4Neighbors(map);
			
			for (int i = 0; i < neighbors.length; i++) {
				
				Node n=neighbors[i];
				if(closedList.contains(n)) {
					continue;
				}
				
				int newGCost=current.g+getNonDiagonalDistance(n, current);
				boolean flag=openList.contains(n);
				if(newGCost<n.g||!flag) {
					n.setG(newGCost);
					n.setH(getNonDiagonalDistance(n, endNode));
					n.parent=current;
					if(!flag) {
						openList.add(n);
					}
				}
				
			}
		}
		
		return path;
	}
	public static boolean nodesCorrespondToSamePos(Node a,Node b) {
		return a.x==b.x&&a.y==b.y;
	}
	public static int getNonDiagonalDistance(Node a,Node b) {
		int xDis=(b.x-a.x)*10;
		int yDis=(b.y-a.y)*10;
		return Math.abs(xDis)+Math.abs(yDis);
	}
	public static Node getNodeWithLeastFCost(List<Node> list) {
		Node least=list.get(0);
		for (Node node : list) {
			if(node.f<least.f) {
				least=node;
			}else if(node.f==least.f&&node.h<least.h) {
				least=node;
			}
		}
		return least;
	}
	public static List<Node> retracePath(Node current) {
		List<Node> path=new ArrayList<>();
        Node temp = current;
        while(temp!=null) {
        	path.add(temp);
        	temp=temp.parent;
        }
        
        return path;
        
        
    }
}
