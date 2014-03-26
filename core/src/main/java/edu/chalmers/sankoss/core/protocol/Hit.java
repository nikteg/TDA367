package edu.chalmers.sankoss.core.protocol;
/**
 * 
 * @author Fredrik Thune
 * 
 */
public class Hit {
	private int x;
	private int y;
	private boolean you;
	public Hit() {
		
	}
	public Hit(int x, int y, boolean you) {
		this.x = x;
		this.y = y;
		this.you = you;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean isYou() {
		return you;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setYou(boolean you) {
		this.you = you;
	}
}
