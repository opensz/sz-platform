package org.sz.core.bpm.graph;

import org.sz.core.bpm.graph.Point;
import org.sz.core.bpm.graph.Shape;

import java.io.PrintStream;
import java.util.ArrayList;

import org.sz.core.util.StringUtil;

public class TransformUtil {
	public static int Offset = 24;

	public static int minLen = 4;

	public static String add(String para1, String para2) {
		float f1 = 0.0F;
		float f2 = 0.0F;
		if (StringUtil.isNotEmpty(para1)) {
			f1 = Float.parseFloat(para1);
		}
		if (StringUtil.isNotEmpty(para2)) {
			f2 = Float.parseFloat(para2);
		}
		f1 += f2;

		return String.valueOf(f1);
	}

	public static String min(String para1, String para2) {
		float f1 = 0.0F;
		float f2 = 0.0F;
		if (StringUtil.isNotEmpty(para1)) {
			f1 = Float.parseFloat(para1);
		}
		if (StringUtil.isNotEmpty(para2)) {
			f2 = Float.parseFloat(para2);
		}
		if (f1 < f2) {
			return String.valueOf(f1);
		}
		return String.valueOf(f2);
	}

	public static String calc(String fName, String fX, String fY, String fW,
			String fH, String fHOffset, String fVOffset, String fDirX,
			String fDirY, String tName, String tX, String tY, String tW,
			String tH, String tHOffset, String tVOffset, String tDirX,
			String tDirY) {
		float fromX = Float.parseFloat(fX);
		float fromY = Float.parseFloat(fY);
		float fromWidth = Float.parseFloat(fW);
		float fromHeight = Float.parseFloat(fH);

		float toX = Float.parseFloat(tX);
		float toY = Float.parseFloat(tY);
		float toWidth = Float.parseFloat(tW);
		float toHeight = Float.parseFloat(tH);

		Shape fromShape = new Shape(fName, fromX, fromY, fromWidth, fromHeight);
		fromShape.setDirectory(fDirX, fDirY);
		fromShape.setHV(fHOffset, fVOffset);

		Shape toShape = new Shape(tName, toX, toY, toWidth, toHeight);
		toShape.setDirectory(tDirX, tDirY);
		toShape.setHV(tHOffset, tVOffset);

		String str = caculate(fromShape, toShape);
		return str;
	}

	public static String caculate(Shape fromShape, Shape toShape) {
		ArrayList list = null;
		switch (fromShape.getDirectory()) {
		case Top:
			switch (toShape.getDirectory()) {
			case Top:
				System.out.println("Top Top");
				list = caculateTopTop(fromShape, toShape);
				break;
			case Left:
				System.out.println("Top Left");
				list = caculateTopLeft(fromShape, toShape);
				break;
			case Right:
				System.out.println("Top Right");
				list = caculateTopRight(fromShape, toShape);
				break;
			case Bottom:
				System.out.println("Top Bottom");
				list = caculateTopBottom(fromShape, toShape);
			}

			break;
		case Left:
			switch (toShape.getDirectory()) {
			case Top:
				System.out.println(" Left Top ");
				list = caculateLeftTop(fromShape, toShape);
				break;
			case Left:
				System.out.println(" Left Left ");
				list = caculateLeftLeft(fromShape, toShape);
				break;
			case Right:
				System.out.println(" Left Right ");
				list = caculateLeftRight(fromShape, toShape);
				break;
			case Bottom:
				System.out.println(" Left Bottom ");
				list = caculateLeftBottom(fromShape, toShape);
			}

			break;
		case Right:
			switch (toShape.getDirectory()) {
			case Top:
				System.out.println(" Right Top ");
				list = caculateRightTop(fromShape, toShape);
				break;
			case Left:
				System.out.println(" Right Left ");
				list = caculateRightLeft(fromShape, toShape);
				break;
			case Right:
				System.out.println(" Right Right ");
				list = caculateRightRight(fromShape, toShape);
				break;
			case Bottom:
				System.out.println(" Right Bottom ");
				list = caculateRightBottom(fromShape, toShape);
			}

			break;
		case Bottom:
			switch (toShape.getDirectory()) {
			case Top:
				System.out.println(" Bottom Top ");
				list = caculateBottomTop(fromShape, toShape);
				break;
			case Left:
				System.out.println(" Bottom Left ");
				list = caculateBottomLeft(fromShape, toShape);
				break;
			case Right:
				System.out.println(" Bottom Right ");
				list = caculateBottomRight(fromShape, toShape);
				break;
			case Bottom:
				System.out.println(" Bottom Bottom ");
				list = caculateBottomBottom(fromShape, toShape);
			}

		}

		String xml = getPointXml(list);
		return xml;
	}

	public static ArrayList<Point> caculateTopTop(Shape fromShape, Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (toY < fromShape.getBottomCenter().getY() + minLen) {
			if ((toShape.getLeftCenter().getX() > fromX + minLen)
					|| (toShape.getRightCenter().getX() + minLen < fromX)) {
				float tmpy = 0.0F;
				if (toY < fromY)
					tmpy = toY - Offset;
				else {
					tmpy = fromY - Offset;
				}
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, tmpy);
				Point p3 = new Point(toX, tmpy);
				Point p4 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			} else {
				float tmpx = 0.0F;
				float tmpy = (fromY + toShape.getBottomCenter().getY()) / 2.0F;
				if (toX < fromX)
					tmpx = toShape.getRightCenter().getX() + Offset;
				else {
					tmpx = toShape.getLeftCenter().getX() - Offset;
				}
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, tmpy);
				Point p3 = new Point(tmpx, tmpy);
				Point p4 = new Point(tmpx, toY - Offset);
				Point p5 = new Point(toX, toY - Offset);
				Point p6 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			}

		} else if ((fromShape.getRightCenter().getX() + minLen < toShape
				.getLeftCenter().getX())
				|| (fromShape.getLeftCenter().getX() > toShape.getRightCenter()
						.getX())) {
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, fromY - Offset);
			Point p3 = new Point(toX, fromY - Offset);
			Point p4 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
		} else {
			float tmpy = (toShape.getBottomCenter().getY() + fromY) / 2.0F;
			float tmpx = 0.0F;

			if (fromX < toX)
				tmpx = fromShape.getRightCenter().getX() + Offset;
			else {
				tmpx = fromShape.getLeftCenter().getX() - Offset;
			}
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, fromY - Offset);
			Point p3 = new Point(tmpx, fromY - Offset);
			Point p4 = new Point(tmpx, tmpy);
			Point p5 = new Point(toX, tmpy);
			Point p6 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		}

		return list;
	}

	public static ArrayList<Point> caculateTopRight(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();
		if (fromX >= toX) {
			if (toY + minLen < fromY) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, toY);
				Point p3 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
			} else if (toX + minLen < fromShape.getLeftCenter().getX()) {
				float tmpx = (fromShape.getLeftCenter().getX() + toX) / 2.0F;
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, fromY - Offset);
				Point p3 = new Point(tmpx, fromY - Offset);
				Point p4 = new Point(tmpx, toY);
				Point p5 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			} else {
				float tmpy = 0.0F;
				if (toShape.getTopCenter().getY() < toY)
					tmpy = toShape.getTopCenter().getY() - Offset;
				else {
					tmpy = fromY - Offset;
				}
				float tmpx = fromShape.getRightCenter().getX() + Offset;

				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, tmpy);
				Point p3 = new Point(tmpx, tmpy);
				Point p4 = new Point(tmpx, toY);
				Point p5 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}

		} else if (toShape.getBottomCenter().getY() + minLen < fromY) {
			float tmpy = (fromY + toShape.getBottomCenter().getY()) / 2.0F;

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, tmpy);
			Point p3 = new Point(toX + Offset, tmpy);
			Point p4 = new Point(toX + Offset, toY);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		} else {
			float tmpy = 0.0F;
			float tmpx = 0.0F;
			Point p1 = new Point(fromX, fromY);

			if (toX >= fromShape.getRightCenter().getX())
				tmpx = toX + Offset;
			else {
				tmpx = fromX + Offset;
			}

			if (toShape.getTopCenter().getY() <= fromY)
				tmpy = toY - Offset;
			else {
				tmpy = fromY - Offset;
			}
			Point p2 = new Point(fromX, tmpy);
			Point p3 = new Point(tmpx, tmpy);
			Point p4 = new Point(tmpx, toY);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	public static ArrayList<Point> caculateTopBottom(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (fromX < toX) {
			if (toY + minLen <= fromY) {
				float tmpy = (fromY + toY) / 2.0F;
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, tmpy);
				Point p3 = new Point(toX, tmpy);
				Point p4 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			} else if (fromShape.getRightCenter().getX() + minLen < toShape
					.getLeftCenter().getX()) {
				float tmpx = (toShape.getLeftCenter().getX() + fromShape
						.getRightCenter().getX()) / 2.0F;

				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, fromY - Offset);
				Point p3 = new Point(tmpx, fromY - Offset);
				Point p4 = new Point(tmpx, toY + Offset);
				Point p5 = new Point(toX, toY + Offset);
				Point p6 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			} else if (fromY <= toShape.getTopCenter().getY()) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, fromY - Offset);
				Point p3 = new Point(toShape.getRightCenter().getX() + Offset,
						fromY - Offset);

				Point p4 = new Point(toShape.getRightCenter().getX() + Offset,
						toY + Offset);

				Point p5 = new Point(toX, toY + Offset);
				Point p6 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			} else {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, toShape.getTopCenter().getY()
						- Offset);

				Point p3 = new Point(toShape.getRightCenter().getX() + Offset,
						toShape.getTopCenter().getY() - Offset);

				Point p4 = new Point(toShape.getRightCenter().getX() + Offset,
						toY + Offset);

				Point p5 = new Point(toX, toY + Offset);
				Point p6 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			}

		} else if (fromX == toX) {
			if (toY < fromY) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
			} else if ((toY >= fromY)
					&& (toShape.getTopCenter().getY() <= fromY)) {
				float fx = fromShape.getLeftCenter().getX();
				float tx = toShape.getLeftCenter().getX();
				float tmpx = Math.min(fx, tx);

				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(toX, toShape.getTopCenter().getY()
						- Offset);

				Point p3 = new Point(tmpx - Offset, toShape.getTopCenter()
						.getY() - Offset);

				Point p4 = new Point(tmpx - Offset, toShape.getTopCenter()
						.getY() + Offset);

				Point p5 = new Point(toX, toShape.getTopCenter().getY()
						+ Offset);

				Point p6 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			} else {
				float fx = fromShape.getLeftCenter().getX();
				float tx = toShape.getLeftCenter().getX();
				float tmpx = Math.min(fx, tx);

				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, fromY - Offset);
				Point p3 = new Point(tmpx - Offset, fromY - Offset);
				Point p4 = new Point(tmpx - Offset, toY + Offset);
				Point p5 = new Point(toX, toY + Offset);
				Point p6 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			}

		} else if (toY + minLen < fromY) {
			float tmpy = (fromY + toY) / 2.0F;
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, tmpy);
			Point p3 = new Point(toX, tmpy);
			Point p4 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
		} else if (toShape.getRightCenter().getX() + minLen < fromShape
				.getLeftCenter().getX()) {
			float tmpx = (fromShape.getLeftCenter().getX() + toShape
					.getRightCenter().getX()) / 2.0F;

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, fromY - Offset);
			Point p3 = new Point(tmpx, fromY - Offset);
			Point p4 = new Point(tmpx, toY + Offset);
			Point p5 = new Point(toX, toY + Offset);
			Point p6 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		} else if (fromY <= toShape.getTopCenter().getY()) {
			float fx = fromShape.getLeftCenter().getX();
			float tx = toShape.getLeftCenter().getX();
			float tmpx = Math.min(fx, tx);

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, fromY - Offset);
			Point p3 = new Point(tmpx - Offset, fromY - Offset);
			Point p4 = new Point(tmpx - Offset, toY + Offset);
			Point p5 = new Point(toX, toY + Offset);
			Point p6 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		} else {
			float fx = fromShape.getLeftCenter().getX();
			float tx = toShape.getLeftCenter().getX();
			float tmpx = Math.min(fx, tx);

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, toShape.getTopCenter().getY() - Offset);

			Point p3 = new Point(tmpx - Offset, toShape.getTopCenter().getY()
					- Offset);

			Point p4 = new Point(tmpx - Offset, toY + Offset);
			Point p5 = new Point(toX, toY + Offset);
			Point p6 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		}

		return list;
	}

	public static ArrayList<Point> caculateTopLeft(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		float toLeftX = toShape.getLeftCenter().getX();

		if (toLeftX + minLen > fromX) {
			if (fromY > toY + minLen) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, toY);
				Point p3 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
			} else if (fromShape.getRightCenter().getX() + minLen < toX) {
				float tmpx = (toX + fromShape.getRightCenter().getX()) / 2.0F;
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, fromY - Offset);
				Point p3 = new Point(tmpx, fromY - Offset);
				Point p4 = new Point(tmpx, toY);
				Point p5 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			} else {
				float tmpy = 0.0F;
				if (toShape.getTopCenter().getY() <= fromY)
					tmpy = toShape.getTopCenter().getY() - Offset;
				else {
					tmpy = fromY - Offset;
				}
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, tmpy);
				Point p3 = new Point(fromShape.getLeftCenter().getX() - Offset,
						tmpy);

				Point p4 = new Point(fromShape.getLeftCenter().getX() - Offset,
						toY);

				Point p5 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}

		} else if (fromY >= toShape.getBottomCenter().getY() + minLen) {
			float tmpy = (fromY + toShape.getBottomCenter().getY()) / 2.0F;
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, fromY - tmpy);
			Point p3 = new Point(toX - Offset, fromY - tmpy);
			Point p4 = new Point(toX - Offset, toY);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		} else {
			float tmpy = 0.0F;

			if (toShape.getTopCenter().getY() < fromY)
				tmpy = toY - Offset;
			else {
				tmpy = fromY - Offset;
			}
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, tmpy);
			Point p3 = new Point(toX - Offset, tmpy);
			Point p4 = new Point(toX - Offset, toY);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	public static ArrayList<Point> caculateRightTop(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (fromX + minLen < toShape.getLeftCenter().getX()) {
			if (fromY > toY + minLen) {
				float tmpx = (toShape.getLeftCenter().getX() + fromX) / 2.0F;
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(tmpx, fromY);
				Point p3 = new Point(tmpx, toY - Offset);
				Point p4 = new Point(toX, toY - Offset);
				Point p5 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			} else {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(toX, fromY);
				Point p3 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
			}
		} else if (fromShape.getBottomCenter().getY() + minLen >= toY) {
			float tmpy = 0.0F;
			float tmpx = 0.0F;

			if (fromShape.getTopCenter().getY() + minLen >= toY)
				tmpy = toY - Offset;
			else {
				tmpy = fromShape.getTopCenter().getY() - Offset;
			}
			tmpx = fromX + Offset;

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(tmpx, fromY);
			Point p3 = new Point(tmpx, tmpy);
			Point p4 = new Point(toX, tmpy);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		} else if (toX < fromX) {
			float tmpy = (fromShape.getBottomCenter().getY() + toY) / 2.0F;
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX + Offset, fromY);
			Point p3 = new Point(fromX + Offset, tmpy);
			Point p4 = new Point(toX, tmpy);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		} else {
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(toX, fromY);
			Point p3 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
		}

		return list;
	}

	public static ArrayList<Point> caculateRightRight(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (toShape.getLeftCenter().getX() >= minLen + fromX) {
			if ((fromY > toShape.getBottomCenter().getY() + minLen)
					|| (fromY + minLen < toShape.getTopCenter().getY())) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(toX + Offset, fromY);
				Point p3 = new Point(toX + Offset, toY);
				Point p4 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			} else {
				float tmpx = (toShape.getLeftCenter().getX() + fromX) / 2.0F;
				float tmpy = 0.0F;
				if ((fromY > toY) && (fromY < toShape.getBottomCenter().getY()))
					tmpy = toShape.getBottomCenter().getY() + Offset;
				else {
					tmpy = toShape.getTopCenter().getY() - Offset;
				}
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(tmpx, fromY);
				Point p3 = new Point(tmpx, tmpy);
				Point p4 = new Point(toX + Offset, tmpy);
				Point p5 = new Point(toX + Offset, toY);
				Point p6 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			}

		} else if ((toShape.getLeftCenter().getX() < fromX + minLen)
				&& (toX > fromShape.getRightCenter().getX() + minLen)) {
			float tmpx = 0.0F;
			if (toX > fromX)
				tmpx = toX + Offset;
			else {
				tmpx = fromX + Offset;
			}

			if (fromY == toY) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(tmpx, fromY);
				Point p3 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
			} else {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(tmpx, fromY);
				Point p3 = new Point(tmpx, toY);
				Point p4 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			}

		} else if ((toY + minLen <= fromShape.getTopCenter().getY())
				|| (fromShape.getBottomCenter().getY() + minLen <= toY)) {
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX + Offset, fromY);
			Point p3 = new Point(fromX + Offset, toY);
			Point p4 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
		} else {
			float tmpx = (fromShape.getLeftCenter().getX() + toX) / 2.0F;
			float tmpy = 0.0F;
			if ((fromY < toY)
					&& (toY < fromShape.getBottomCenter().getY() + minLen)) {
				tmpy = fromShape.getBottomCenter().getY() + Offset;
			} else
				tmpy = fromShape.getTopCenter().getY() - Offset;

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX + Offset, fromY);
			Point p3 = new Point(fromX + Offset, tmpy);
			Point p4 = new Point(tmpx, tmpy);
			Point p5 = new Point(tmpx, toY);
			Point p6 = new Point(fromX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		}

		return list;
	}

	public static ArrayList<Point> caculateRightBottom(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (toShape.getLeftCenter().getX() > fromX + minLen) {
			if (fromY > toY + minLen) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(toX, fromY);
				Point p3 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
			} else {
				float tmpx = (fromX + toShape.getLeftCenter().getX()) / 2.0F;
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(tmpx, fromY);
				Point p3 = new Point(tmpx, toY + Offset);
				Point p4 = new Point(toX, toY + Offset);
				Point p5 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}

		} else if (toY + minLen < fromShape.getTopCenter().getY()) {
			float tmpy = (fromShape.getTopCenter().getY() + toY) / 2.0F;
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX + Offset, fromY);
			Point p3 = new Point(fromX + Offset, tmpy);
			Point p4 = new Point(toX, tmpy);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		} else {
			float tmpx = 0.0F;
			float tmpy = 0.0F;
			if (toShape.getRightCenter().getX() > fromX)
				tmpx = toX + Offset;
			else {
				tmpx = fromX + Offset;
			}
			if (toY < fromShape.getBottomCenter().getY())
				tmpy = fromShape.getBottomCenter().getY() + Offset;
			else {
				tmpy = toShape.getBottomCenter().getY() + Offset;
			}
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(tmpx, fromY);
			Point p3 = new Point(tmpx, tmpy);
			Point p4 = new Point(toX, tmpy);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	public static ArrayList<Point> caculateRightLeft(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (toShape.getLeftCenter().getX() > fromX + minLen) {
			if (toY == fromY) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
			} else {
				float tmpx = (fromX + toX) / 2.0F;
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(tmpx, fromY);
				Point p3 = new Point(tmpx, toY);
				Point p4 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			}

		} else if ((toShape.getBottomCenter().getY() + minLen < fromShape
				.getTopCenter().getY())
				|| (fromShape.getBottomCenter().getY() + minLen < toShape
						.getTopCenter().getY())) {
			float tmpy = 0.0F;
			if (toShape.getBottomCenter().getY() + minLen < fromShape
					.getTopCenter().getY()) {
				tmpy = (toShape.getBottomCenter().getY() + fromShape
						.getTopCenter().getY()) / 2.0F;
			} else {
				tmpy = (toShape.getTopCenter().getY() + fromShape
						.getBottomCenter().getY()) / 2.0F;
			}

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX + Offset, fromY);
			Point p3 = new Point(fromX + Offset, tmpy);
			Point p4 = new Point(toX - Offset, tmpy);
			Point p5 = new Point(toX - Offset, toY);
			Point p6 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		} else {
			float tmpx = 0.0F;
			float tmpy = 0.0F;
			if (toShape.getRightCenter().getX() > fromX)
				tmpx = toShape.getRightCenter().getX() + Offset;
			else {
				tmpx = fromX + Offset;
			}
			if ((toY < fromY)
					&& (toShape.getTopCenter().getY() < fromShape
							.getTopCenter().getX())) {
				tmpy = toShape.getTopCenter().getY() - Offset;
			} else
				tmpy = fromShape.getTopCenter().getY() - Offset;

			if ((toY >= fromY)
					&& (toShape.getBottomCenter().getY() > fromShape
							.getBottomCenter().getY())) {
				tmpy = toShape.getBottomCenter().getY() + Offset;
			} else
				tmpy = fromShape.getBottomCenter().getY() + Offset;

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(tmpx, fromY);
			Point p3 = new Point(tmpx, tmpy);
			Point p4 = new Point(toX - Offset, tmpy);
			Point p5 = new Point(toX - Offset, toY);
			Point p6 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		}

		return list;
	}

	public static ArrayList<Point> caculateLeftTop(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (fromX + minLen < toShape.getLeftCenter().getX()) {
			if (toY >= fromShape.getBottomCenter().getY() + minLen) {
				float tmpy = (fromShape.getBottomCenter().getY() + toY) / 2.0F;
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX - Offset, fromY);
				Point p3 = new Point(fromX - Offset, tmpy);
				Point p4 = new Point(toX, tmpy);
				Point p5 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			} else {
				float tmpx = 0.0F;
				float tmpy = 0.0F;
				if (toShape.getLeftCenter().getX() >= fromX)
					tmpx = toShape.getLeftCenter().getX() - Offset;
				else {
					tmpx = fromShape.getLeftCenter().getX() - Offset;
				}
				if (fromShape.getTopCenter().getY() > toY)
					tmpy = toY - Offset;
				else {
					tmpy = fromShape.getTopCenter().getY();
				}
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(tmpx, fromY);
				Point p3 = new Point(tmpx, tmpy);
				Point p4 = new Point(toX, tmpy);
				Point p5 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}
		} else if (toY >= fromY + minLen) {
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(toX, fromY);
			Point p3 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
		} else {
			float tmpx = (toShape.getRightCenter().getX() + fromX) / 2.0F;
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(tmpx, fromY);
			Point p3 = new Point(tmpx, toY - Offset);
			Point p4 = new Point(toX, toY - Offset);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	public static ArrayList<Point> caculateLeftRight(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (toX + minLen <= fromX) {
			if (fromY == toY) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
			} else {
				float tmpx = (toX + fromX) / 2.0F;
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(tmpx, fromY);
				Point p3 = new Point(tmpx, toY);
				Point p4 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			}

		} else if ((toShape.getBottomCenter().getY() + minLen <= fromShape
				.getTopCenter().getY())
				|| (toShape.getTopCenter().getY() >= fromShape
						.getBottomCenter().getY() + minLen)) {
			float tmpy = 0.0F;
			if (toShape.getBottomCenter().getY() + minLen <= fromShape
					.getTopCenter().getY()) {
				tmpy = (toShape.getBottomCenter().getY() + fromShape
						.getTopCenter().getY()) / 2.0F;
			} else {
				tmpy = (toShape.getTopCenter().getY() + fromShape
						.getBottomCenter().getY()) / 2.0F;
			}

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX - Offset, fromY);
			Point p3 = new Point(fromX - Offset, tmpy);
			Point p4 = new Point(toX + Offset, tmpy);
			Point p5 = new Point(toX + Offset, toY);
			Point p6 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		} else {
			float tmpx = 0.0F;
			float tmpy = 0.0F;

			if (toShape.getLeftCenter().getX() < fromX)
				tmpx = toShape.getLeftCenter().getX() - Offset;
			else {
				tmpx = fromX - Offset;
			}

			if (fromY > toY) {
				if (toShape.getTopCenter().getY() < fromShape.getTopCenter()
						.getY()) {
					tmpy = toShape.getTopCenter().getY() - Offset;
				} else
					tmpy = fromShape.getTopCenter().getY() - Offset;

			} else if (toShape.getBottomCenter().getY() > fromShape
					.getBottomCenter().getY()) {
				tmpy = toShape.getBottomCenter().getY() + Offset;
			} else
				tmpy = fromShape.getBottomCenter().getY() + Offset;

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(tmpx, fromY);
			Point p3 = new Point(tmpx, tmpy);
			Point p4 = new Point(toX + Offset, tmpy);
			Point p5 = new Point(toX + Offset, toY);
			Point p6 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		}

		return list;
	}

	public static ArrayList<Point> caculateLeftBottom(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (toX + minLen < fromX) {
			if (toY + minLen <= fromY) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(toX, fromY);
				Point p3 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
			} else {
				float tmpx = (toShape.getRightCenter().getX() + fromX) / 2.0F;
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(tmpx, fromY);
				Point p3 = new Point(tmpx, toShape.getBottomCenter().getY()
						+ Offset);

				Point p4 = new Point(toX, toShape.getBottomCenter().getY()
						+ Offset);

				Point p5 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}

		} else if (toShape.getBottomCenter().getY() + minLen < fromShape
				.getTopCenter().getY()) {
			float tmpy = (toShape.getBottomCenter().getY() + fromShape
					.getTopCenter().getY()) / 2.0F;

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX - Offset, fromY);
			Point p3 = new Point(fromX - Offset, tmpy);
			Point p4 = new Point(toX, tmpy);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		} else {
			float tmpx = 0.0F;
			float tmpy = 0.0F;

			if (toShape.getLeftCenter().getX() < fromX)
				tmpx = toShape.getLeftCenter().getX() - Offset;
			else {
				tmpx = fromX - Offset;
			}

			if (toShape.getBottomCenter().getY() < fromShape.getBottomCenter()
					.getY()) {
				tmpy = fromShape.getBottomCenter().getY() + Offset;
			} else
				tmpy = toShape.getBottomCenter().getY() + Offset;

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(tmpx, fromY);
			Point p3 = new Point(tmpx, tmpy);
			Point p4 = new Point(toX, tmpy);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	public static ArrayList<Point> caculateLeftLeft(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (toShape.getLeftCenter().getX() + minLen <= fromX) {
			System.out.println("caculateLeftLeft目标在源对象的左边");

			if ((toShape.getBottomCenter().getY() + minLen < fromY)
					|| (toShape.getTopCenter().getY() > fromY + minLen)) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(toX - Offset, fromY);
				Point p3 = new Point(toX - Offset, toY);
				Point p4 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			} else {
				float tmpx = (toShape.getRightCenter().getX() + fromY) / 2.0F;
				float tmpy = 0.0F;

				if (toY > fromY)
					tmpy = toShape.getTopCenter().getY() - Offset;
				else {
					tmpy = toShape.getBottomCenter().getY() + Offset;
				}
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(tmpx, fromY);
				Point p3 = new Point(tmpx, tmpy);
				Point p4 = new Point(toX - Offset, tmpy);
				Point p5 = new Point(toX - Offset, toY);
				Point p6 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			}

		} else if ((toShape.getRightCenter().getX() + minLen > fromX)
				&& (toShape.getLeftCenter().getX() < fromShape.getRightCenter()
						.getX() + minLen)) {
			System.out.println("caculateLeftLeft中部");
			float tmpx = 0.0F;
			if (toX < fromX)
				tmpx = toX - Offset;
			else {
				tmpx = fromX - Offset;
			}
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(tmpx, fromY);
			Point p3 = new Point(tmpx, toY);
			Point p4 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
		} else {
			System.out.println("caculateLeftLeft右边");

			if ((toY + minLen < fromShape.getTopCenter().getY())
					|| (toY > fromShape.getBottomCenter().getY() + minLen)) {
				System.out.println("caculateLeftLeft上方下方");
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX - Offset, fromY);
				Point p3 = new Point(fromX - Offset, toY);
				Point p4 = new Point(toX, toY);
				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			} else {
				System.out.println("caculateLeftLeft纵向中部");
				float tmpx = (fromShape.getRightCenter().getX() + toX) / 2.0F;
				float tmpy = 0.0F;

				if (fromY > toY)
					tmpy = fromShape.getTopCenter().getY() - Offset;
				else {
					tmpy = fromShape.getBottomCenter().getY() + Offset;
				}
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX - Offset, fromY);
				Point p3 = new Point(fromX - Offset, tmpy);
				Point p4 = new Point(tmpx, tmpy);
				Point p5 = new Point(tmpx, toY);
				Point p6 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			}
		}
		return list;
	}

	public static ArrayList<Point> caculateBottomTop(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (toY < fromY + minLen) {
			if ((toShape.getRightCenter().getX() + minLen < fromShape
					.getLeftCenter().getX())
					|| (toShape.getLeftCenter().getX() > fromShape
							.getRightCenter().getX() + minLen)) {
				float tmpx = 0.0F;
				if (toShape.getRightCenter().getX() + minLen < fromShape
						.getLeftCenter().getX()) {
					tmpx = (toShape.getRightCenter().getX() + fromShape
							.getLeftCenter().getX()) / 2.0F;
				} else {
					tmpx = (toShape.getLeftCenter().getX() + fromShape
							.getRightCenter().getX()) / 2.0F;
				}

				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, fromY + Offset);
				Point p3 = new Point(tmpx, fromY + Offset);
				Point p4 = new Point(tmpx, toY - Offset);
				Point p5 = new Point(toX, toY - Offset);
				Point p6 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			} else {
				float tmpx = 0.0F;
				float tmpy = 0.0F;

				if (toX > fromX) {
					if (toShape.getRightCenter().getX() > fromShape
							.getRightCenter().getX()) {
						tmpx = toShape.getRightCenter().getX() + Offset;
					} else
						tmpx = fromShape.getRightCenter().getX() + Offset;

				} else if (toShape.getLeftCenter().getX() < fromShape
						.getLeftCenter().getX()) {
					tmpx = toShape.getLeftCenter().getX() - Offset;
				} else
					tmpx = fromShape.getLeftCenter().getX() - Offset;

				if (toShape.getBottomCenter().getY() > fromY)
					tmpy = toShape.getBottomCenter().getY() + Offset;
				else {
					tmpy = fromY + Offset;
				}
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, tmpy);
				Point p3 = new Point(tmpx, tmpy);
				Point p4 = new Point(tmpx, toY - Offset);
				Point p5 = new Point(toX, toY - Offset);
				Point p6 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			}

		} else if (toX == fromX) {
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(toX, toY);
			list.add(p1);
			list.add(p2);
		} else {
			float tmpy = (fromShape.getBottomCenter().getY() + toShape
					.getTopCenter().getY()) / 2.0F;

			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, tmpy);
			Point p3 = new Point(toX, tmpy);
			Point p4 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
		}

		return list;
	}

	public static ArrayList<Point> caculateBottomRight(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (toY > fromY + minLen) {
			if (toX <= fromX) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, toY);
				Point p3 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
			} else if (toShape.getTopCenter().getY() > fromY + minLen) {
				float tmpy = (fromY + toShape.getTopCenter().getY()) / 2.0F;
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, tmpy);
				Point p3 = new Point(toX + Offset, tmpy);
				Point p4 = new Point(toX + Offset, toY);
				Point p5 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			} else {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, toY + Offset);
				Point p3 = new Point(toX + Offset, toY + Offset);
				Point p4 = new Point(toX + Offset, toY);
				Point p5 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			}

		} else if (toX < fromShape.getLeftCenter().getX()) {
			float tmpx = (toX + fromShape.getLeftCenter().getX()) / 2.0F;
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, fromY + Offset);
			Point p3 = new Point(tmpx, fromY + Offset);
			Point p4 = new Point(tmpx, toY);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		} else {
			float tmpx = 0.0F;
			float tmpy = 0.0F;
			if (toX > fromShape.getRightCenter().getX())
				tmpx = toX + Offset;
			else {
				tmpx = fromShape.getRightCenter().getX();
			}
			if (toShape.getBottomCenter().getY() > fromY)
				tmpy = toShape.getBottomCenter().getY() + Offset;
			else {
				tmpy = fromY + Offset;
			}
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, tmpy);
			Point p3 = new Point(tmpx, tmpy);
			Point p4 = new Point(tmpx, toY);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	public static ArrayList<Point> caculateBottomBottom(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (toY + minLen < fromShape.getTopCenter().getY()) {
			if ((toX + minLen < fromShape.getLeftCenter().getX())
					|| (toX > fromShape.getRightCenter().getX() + minLen)) {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, fromY + Offset);
				Point p3 = new Point(toX, fromY + Offset);
				Point p4 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
			} else {
				float tmpy = (toY + fromShape.getTopCenter().getY()) / 2.0F;
				float tmpx = 0.0F;
				if (toX < fromX)
					tmpx = fromShape.getLeftCenter().getX() - Offset;
				else {
					tmpx = fromShape.getRightCenter().getX() + Offset;
				}
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, fromY + Offset);
				Point p3 = new Point(tmpx, fromY + Offset);
				Point p4 = new Point(tmpx, tmpy);
				Point p5 = new Point(toX, tmpy);
				Point p6 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
				list.add(p6);
			}

		} else if ((toShape.getLeftCenter().getX() > fromX + minLen)
				|| (toShape.getRightCenter().getX() + minLen < fromX)) {
			float tmpy = 0.0F;
			if (toY > fromY)
				tmpy = toY + Offset;
			else {
				tmpy = fromY + Offset;
			}
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, tmpy);
			Point p3 = new Point(toX, tmpy);
			Point p4 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
		} else {
			float tmpx = 0.0F;
			float tmpy = (toShape.getTopCenter().getY() + fromY) / 2.0F;
			if (toX < fromX)
				tmpx = toShape.getRightCenter().getX() + Offset;
			else {
				tmpx = toShape.getLeftCenter().getX() - Offset;
			}
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, tmpy);
			Point p3 = new Point(tmpx, tmpy);
			Point p4 = new Point(tmpx, toY + Offset);
			Point p5 = new Point(toX, toY + Offset);
			Point p6 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
			list.add(p6);
		}

		return list;
	}

	public static ArrayList<Point> caculateBottomLeft(Shape fromShape,
			Shape toShape) {
		ArrayList list = new ArrayList();
		float fromX = fromShape.getPoint().getX();
		float fromY = fromShape.getPoint().getY();
		float toX = toShape.getPoint().getX();
		float toY = toShape.getPoint().getY();

		if (fromY + minLen < toShape.getTopCenter().getY()) {
			if (fromX > toX) {
				float tmpy = (fromY + toShape.getTopCenter().getY()) / 2.0F;
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, tmpy);
				Point p3 = new Point(toX - Offset, tmpy);
				Point p4 = new Point(toX - Offset, toY);
				Point p5 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
				list.add(p4);
				list.add(p5);
			} else {
				Point p1 = new Point(fromX, fromY);
				Point p2 = new Point(fromX, toY);
				Point p3 = new Point(toX, toY);

				list.add(p1);
				list.add(p2);
				list.add(p3);
			}
		} else if (toX < fromShape.getRightCenter().getX() + minLen) {
			float tmpy = 0.0F;
			float tmpx = 0.0F;
			if (toShape.getBottomCenter().getY() > fromY)
				tmpy = toShape.getBottomCenter().getY() + Offset;
			else {
				tmpy = fromY + Offset;
			}
			if (toX < fromShape.getRightCenter().getX())
				tmpx = toX - Offset;
			else {
				tmpx = fromShape.getRightCenter().getX() - Offset;
			}
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, tmpy);
			Point p3 = new Point(tmpx, tmpy);
			Point p4 = new Point(tmpx, toY);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		} else {
			float tmpx = (fromShape.getRightCenter().getX() + toX) / 2.0F;
			Point p1 = new Point(fromX, fromY);
			Point p2 = new Point(fromX, fromY + Offset);
			Point p3 = new Point(tmpx, fromY + Offset);
			Point p4 = new Point(tmpx, toY);
			Point p5 = new Point(toX, toY);

			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}

		return list;
	}

	private static String getPointXml(ArrayList<Point> list) {
		StringBuffer sb = new StringBuffer();

		for (Point p : list) {
			sb.append("\n<omgdi:waypoint x=\"" + p.getX() + "\" y=\""
					+ p.getY() + "\"></omgdi:waypoint>\n");
		}

		return sb.toString();
	}
}