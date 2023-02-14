package com.tebyan.maze_generator;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.tebyan.maze_generator.Cell.CellType;
import com.tebyan.maze_generator.InputManager.InputListener;

public class MazePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Cell[][] map;
	Dimension size;
	Image mazeImage;
	public float scale=1;
	float editScale=1;
	float xP=0,yP=0;
	public boolean editMode=false;

	public List<OnDrawCallback> callbacks;
	
	
	public void executeDrawBefore(Graphics2D g) {
		for (OnDrawCallback onDrawCallback : callbacks) {
			onDrawCallback.onDrawBeforeMaze(g);
		}
	}
	public void executeDrawAfter(Graphics2D g) {
		for (OnDrawCallback onDrawCallback : callbacks) {
			onDrawCallback.onDrawAfterMaze(g);
		}
	}
	public MazePanel(int width,int height,InputManager manager) {
		size=new Dimension(width,height);
		callbacks=new ArrayList<>();
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(editMode) {
					Point p=e.getPoint();
					
					Point pos=new Point((int)((p.x/editScale)/Cell.cellSize),((int)(p.y/editScale)/Cell.cellSize));
					Cell c=map[pos.y][pos.x];
					
					
					if(c instanceof BlockCell) {
						
						if(e.getButton()==1) {
							((BlockCell)c).cellType=CellType.UNWALKABLE;
						}else if(e.getButton()==3) {
							((BlockCell)c).cellType=CellType.WALKABLE;
						}
						DrawBlockMazeOnImage();
						repaint();
						
					}
					
				}
				

				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				
				
				float speed=1f/10;
				if(InputManager.GetKeyDown(KeyEvent.VK_H)) {
					speed=1;
				}
				scale-=e.getPreciseWheelRotation()*speed;
				if(scale<.1f) {
					scale=.1f;
				}
				
				repaint();
			}
		});
		if(manager!=null) {
			manager.addInputCallback(new InputListener() {
				
				@Override
				public void onKeyReleased(int keyCode) {
					
					if(keyCode==KeyEvent.VK_I) {
						setEditMode(!editMode);
						repaint();
					}
					
				}
				
				@Override
				public void onKeyPressed(int keyCode) {
					float speed=20;
					switch (keyCode) {
						case KeyEvent.VK_UP:
							yP+=speed;
							repaint();
							break;
						case KeyEvent.VK_DOWN:
							yP-=speed;
							repaint();
							break;
						case KeyEvent.VK_LEFT:
							xP+=speed;
							repaint();
							break;
						case KeyEvent.VK_RIGHT:
							xP-=speed;
							repaint();
							break;

					default:
						break;
					}
				}

				@Override
				public void onKeyPressedOnce(int keyCode) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		
	}
	
	public void setEditMode(boolean value) {
		this.editMode=value;
	}
	public void setMap(Cell[][] map) {
		this.map=map;
		Cell.cellSize=(size.height/map.length);
		if(map instanceof BlockCell[][]) {
			DrawBlockMazeOnImage();
		}else if(map instanceof WalledCell[][]){
			DrawWalledCellOnImage();
		}
		repaint();
	}
	public void setCurrentPointer(Cell cell) {
		if(map instanceof BlockCell[][]) {
			DrawBlockMazeOnImage();
		}else if(map instanceof WalledCell[][]){
			DrawWalledCellOnImage();
		}
		repaint();
	}



	
	

	@Override
	public void paintComponent(Graphics g2) {
		// TODO Auto-generated method stub
		
		
		Graphics2D g=(Graphics2D)g2;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		Dimension sizeDim=new Dimension(getParent().getWidth(), getParent().getHeight());
		g.setColor(Color.getHSBColor(.1f,.1f,.1f));
		g.fillRect(0, 0, sizeDim.width,sizeDim.height);
		if(!editMode) {
			g.translate(sizeDim.width/2,sizeDim.height/2);
			
		    
		    g.scale(scale, scale);
		    g.translate(-sizeDim.width/2, -sizeDim.height/2);
		    g.translate(sizeDim.width/2-size.width/2, sizeDim.height/2-size.height/2);
		    g.translate(xP, yP);
		}
		else {
			editScale=Math.min((float)sizeDim.width/size.width, (float)sizeDim.height/size.height);
			g.scale(editScale, editScale);
		}
		executeDrawBefore(g);
		g.drawImage(mazeImage, 0, 0, this);
		executeDrawAfter(g);
		
	}
	private void DrawBlockMazeOnImage() {

		BufferedImage image=new BufferedImage(size.width,size.height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g=(Graphics2D) image.getGraphics();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				
				BlockCell cell=(BlockCell) map[i][j];
				switch (cell.cellType) {
					case UNWALKABLE:
						cell.fillCell(g, Color.DARK_GRAY);
						break;
					case WALKABLE:
						cell.fillCell(g, Color.white);
						break;
					case ENTRANCE:
						cell.fillCell(g, Color.GREEN);
						break;
					case TARGET:
						cell.fillCell(g, Color.red);
						break;
					default:
						break;
				}
				cell.DrawOutline(g, Color.getHSBColor(.05f, .05f, .05f));
			}
			mazeImage=image;
			
		}
		
		
	}
	private void DrawWalledCellOnImage() {
		
		BufferedImage image=new BufferedImage(size.width*20,size.height*20,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g=(Graphics2D) image.getGraphics();
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				WalledCell cell=(WalledCell) map[i][j];
				cell.fillCell(g, Color.getHSBColor(.5f, .2f, .2f));
				cell.DrawWalls(g, Color.red);
			}
			mazeImage=image;
			
		}

	}

	public void addOnDrawCallback(OnDrawCallback callback) {
		callbacks.add(callback);
	}
	public void removeOnDrawCallbacks(OnDrawCallback callback) {
		callbacks.remove(callback);
	}
	public interface OnDrawCallback{
		void onDrawBeforeMaze(Graphics2D g);
		void onDrawAfterMaze(Graphics2D g);
	}
	
}
