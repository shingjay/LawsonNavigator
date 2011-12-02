
package com.purdue.LawsonNavigator;

import java.io.Serializable;
import java.util.ArrayList;

public class Point implements Serializable{
	private int x;
	private int y;
	
	public static void main(String[] args) {
		int x = 0;
		int y = 0;
		int magic[] = {0,0,1,1,2,3,0,3};
		ArrayList<Point> p = new ArrayList<Point>();
		p = getTrigger(x,y,magic);
		for(int i = 0; i < p.size(); i++){
			int w = p.get(i).getx(); // first way to acess points in an array list
			int u = p.get(i).gety();
	
			Point c = p.get(i); // second way to aceess points in an array list.
			System.out.print(c.getx() + " ");
			System.out.println(c.gety());
		}
	}
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getx(){
		return x;
	}
	public int gety(){
		return y;
	}
	
	public static ArrayList<Point> getTrigger( int startx,int starty, int[] directions){
		ArrayList<Point> trigger = new ArrayList<Point>(); 
		Point temp = new Point(startx,starty);
		for(int i = 0; i < directions.length -1; i++){
			if(directions[i] == 0){ // increase y because the user is going up
					starty--;
			}
			else if(directions[i] == 1){ //increase x because the user is going right
					startx++;
			}
			else if(directions[i] == 2){ // decrease y because the user is going down
					starty++;
			}
			else if(directions[i] == 3){ // decrease x because the user is going left
				startx--;
			}
			if(directions[i] != directions[i+1]){ // if they are differnt then add a trigger point
			trigger.add(new Point(startx,starty));
		
			
			}
		}
		if(directions[directions.length-1] == 0){ // increase y because the user is going up
			starty++;
		}
		else if(directions[directions.length-1] == 1){ //increase x because the user is going right
			startx++;
		}
		else if(directions[directions.length-1] == 2){ // decrease y because the user is going down
			starty--;
		}
		else if(directions[directions.length-1] == 3){ // decrease x because the user is going left
			startx--;
		}
		trigger.add(new Point(startx,starty)); // add a trigger point to say you are at the end
		return trigger;
	}
}
