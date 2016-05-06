package maths;

import java.io.Serializable;

public class Vector2D implements Serializable {

	private static final long serialVersionUID = -187158972847118631L;

	private double x;
	private double y;

	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Vector2D(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double length() {
		return Math.sqrt(x * x + y * y);
	}

	public Vector2D normalise() {
		double x = this.x;
		double y = this.y;

		double length = length();

		if (length != 0) {
			x /= length;
			y /= length;
		}

		return new Vector2D(x, y);
	}

	public Vector2D multiply(double factor) {
		return new Vector2D(x * factor, y * factor);
	}

	public Vector2D add(Vector2D vec) {
		return new Vector2D(getX() + vec.getX(), getY() + vec.getY());
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return x + ", " + y;
	}

}
