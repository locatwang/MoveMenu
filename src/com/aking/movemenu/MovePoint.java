package com.aking.movemenu;

/**
 * @author aking
 * 坐标
 */

public class MovePoint {
	public int fromX;
	public int fromY;

	public int toX;
	public int toY;
	public int getFromX() {
		return fromX;
	}
	public void setFromX(int fromX) {
		this.fromX = fromX;
	}
	public int getFromY() {
		return fromY;
	}
	public void setFromY(int fromY) {
		this.fromY = fromY;
	}
	public int getToX() {
		return toX;
	}
	public void setToX(int toX) {
		this.toX = toX;
	}
	public int getToY() {
		return toY;
	}
	public void setToY(int toY) {
		this.toY = toY;
	}
	public MovePoint(int fromX, int toX, int fromY, int toY) {
		super();
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
	}
	@Override
	public String toString() {
		return "MyPoint [fromX=" + fromX + ", fromY=" + fromY + ", toX=" + toX
				+ ", toY=" + toY + "]";
	}
	
	

}
