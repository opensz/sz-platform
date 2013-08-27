package org.sz.core.bpm.graph;

import org.sz.core.bpm.graph.DirEnum;
import org.sz.core.bpm.graph.Point;

public class Shape {
	private float x = 0.0F;
	private float y = 0.0F;
	private float w = 0.0F;
	private float h = 0.0F;
	private String name = "";
	private DirEnum dir;
	private float offset = 0.0F;

	private float fHOffset = 0.0F;
	private float fVOffset = 0.0F;

	public Shape(String name, float x, float y, float w, float h) {
		this.h = h;
		if ((name.equals("bg:StartEvent")) || (name.equals("bg:EndEvent"))) {
			this.h = w;
		}

		this.name = name;

		this.x = x;
		this.y = y;
		this.w = w;
	}

	public float getOffset() {
		return this.offset;
	}

	public void setOffset(float offset) {
		this.offset = offset;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDirectory(String dirx, String diry) {
		if (!dirx.equals("")) {
			if (dirx.equals("0"))
				this.dir = DirEnum.Left;
			if (dirx.equals("1"))
				this.dir = DirEnum.Right;
		}
		if (!diry.equals("")) {
			if (diry.equals("0"))
				this.dir = DirEnum.Top;
			if (diry.equals("1"))
				this.dir = DirEnum.Bottom;
		}
	}

	public DirEnum getDirectory() {
		return this.dir;
	}

	public void setHV(String hOffset, String vOffset) {
		if (!hOffset.equals(""))
			this.fHOffset = Float.parseFloat(hOffset);
		if (!vOffset.equals(""))
			this.fVOffset = Float.parseFloat(vOffset);
	}

	public float getX() {
		return this.x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return this.y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getW() {
		return this.w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return this.h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public Point getTopCenter() {
		float tmpx = this.x + this.w / 2.0F;
		Point p = new Point(tmpx, this.y);

		setHV(p);
		return p;
	}

	public Point getLeftCenter() {
		float tmpy = this.y + this.h / 2.0F;
		Point p = new Point(this.x, tmpy);
		setHV(p);
		return p;
	}

	public Point getBottomCenter() {
		float tmpx = this.x + this.w / 2.0F;
		float tmpy = this.y + this.h;
		Point p = new Point(tmpx, tmpy);

		setHV(p);
		return p;
	}

	public Point getRightCenter() {
		float tmpx = this.x + this.w;
		float tmpy = this.y + this.h / 2.0F;
		Point p = new Point(tmpx, tmpy);

		setHV(p);
		return p;
	}

	public Point getPoint() {
		Point p;
		if (this.dir == DirEnum.Top) {
			p = getTopCenter();
		} else {
			if (this.dir == DirEnum.Bottom) {
				p = getBottomCenter();
			} else {
				if (this.dir == DirEnum.Left)
					p = getLeftCenter();
				else
					p = getRightCenter();
			}
		}
		return p;
	}

	private void setHV(Point p) {
		p.setX(p.getX() + this.fHOffset);
		p.setY(p.getY() + this.fVOffset);
	}
}