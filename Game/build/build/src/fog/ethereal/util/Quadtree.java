package fog.ethereal.util;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import fog.ethereal.sprite.Sprite;
import fog.ethereal.world.Platform;

public class Quadtree {
	private int MAX_OBJECTS = 10;
	private int MAX_LEVELS = 10;
 	private int level;
 	private List<Platform> objects;
	private Rectangle bounds;
	private Quadtree[] nodes;
	 
	public Quadtree(int pLevel, Rectangle pBounds) {
		level = pLevel;
		objects = new ArrayList<Platform>();
		bounds = pBounds;
		nodes = new Quadtree[4];
	}
	  
	public void clear() {
		objects.clear();	 
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}
	
	private void split() {
		int subWidth = (int)(bounds.getWidth() / 2);
		int subHeight = (int)(bounds.getHeight() / 2);
		int x = (int)bounds.getX();
		int y = (int)bounds.getY();
		
		nodes[0] = new Quadtree(level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
		nodes[1] = new Quadtree(level+1, new Rectangle(x, y, subWidth, subHeight));
		nodes[2] = new Quadtree(level+1, new Rectangle(x, y + subHeight, subWidth, subHeight));
		nodes[3] = new Quadtree(level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
	}
	
	private int[] getIndex(Platform p) {
		int[] index = {-1, -1, -1, -1};
		for(int i = 0; i < 4; i++) {
			if(p.fitsWithin(nodes[i].getBounds())) {
				index[i] = i + 1;
			}
		}
		return index;
	}
	
	private int[] getIndex(Sprite s) {
		int[] index = {-1, -1, -1, -1};
		for(int i = 0; i < 4; i++) {
			if(s.fitsWithin(nodes[i].getBounds())) {
				index[i] = i + 1;
			}
		}
		return index;
	}
	
	public List<Platform> retrieve(Sprite s) {
		ArrayList<Platform> platforms = new ArrayList<Platform>();
		return retrieve(platforms, s);
	}
	
	public List<Platform> retrieve(List<Platform> platforms, Sprite s) {
		int[] index = getIndex(s);
		for(int i = 0; i < index.length; i++) {
			if(index[i] != -1 && nodes[0] != null) {
				nodes[index[i]].retrieve(platforms, s);
			}
		}
		platforms.addAll(objects);
		return platforms;
	}
	
	public void insert(Platform p) {
		if(nodes[0] != null) {
			int[] index = getIndex(p);
			for(int i = 0; i < index.length; i++) {
				if(index[i] != -1) {
					nodes[index[i]].insert(p);
				}
			}
		}
		objects.add(p);
		if(objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			if(nodes[0] == null) {
				split();
			}
			while(0 < objects.size()) {
				Platform current = objects.get(0);
				int[] index = getIndex(current);
				for(int j = 0; j < index.length; j++) {
					if(index[j] != -1) {
						nodes[index[j]].insert(current);
					}
				}
				objects.remove(0);
			}
		}
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}
