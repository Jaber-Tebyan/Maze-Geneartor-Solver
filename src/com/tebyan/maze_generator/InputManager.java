package com.tebyan.maze_generator;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;



public class InputManager implements KeyListener{

	
	public interface InputListener{
		void onKeyPressed(int keyCode);
		void onKeyReleased(int keyCode);
		void onKeyPressedOnce(int keyCode);
	}
	
	List<InputListener> listeners=new ArrayList<>();
	
	static boolean[] keys=new boolean[256];
	
	
	
	public void addInputCallback(InputListener listener) {
		listeners.add(listener);
	}
	public void removeInputCallback(InputListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(!keys[e.getKeyCode()]) {
			for (InputListener b : listeners) {
				b.onKeyPressedOnce(e.getKeyCode());
			}
		}
		keys[e.getKeyCode()]=true;
		for (InputListener b : listeners) {
			b.onKeyPressed(e.getKeyCode());
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		keys[e.getKeyCode()]=false;
		for (InputListener b : listeners) {
			b.onKeyReleased(e.getKeyCode());
		}
		// TODO Auto-generated method stub
		
	}
	public static boolean GetKeyDown(int keyCode) {
		return keys[keyCode];
	}

}
