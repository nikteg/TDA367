package edu.chalmers.sankoss.core.protocol;

public class Miss {
	private int x;
	private int y;
	private boolean you;
	public Miss() {
		
	}
	public Miss(int x, int y, boolean you) {
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