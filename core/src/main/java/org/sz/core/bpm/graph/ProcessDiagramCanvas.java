package org.sz.core.bpm.graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;

public class ProcessDiagramCanvas extends
		org.activiti.engine.impl.bpmn.diagram.ProcessDiagramCanvas {

	public ProcessDiagramCanvas(int width, int height) {
		super(width, height);
	}

	public ProcessDiagramCanvas(int width, int height, int minX, int minY) {
		this(width, height);
		this.minX = minX;
		this.minY = minY;
	}

	public void drawHighLight(int x, int y, int width, int height, Color color) {
		Paint originalPaint = g.getPaint();
		Stroke originalStroke = g.getStroke();

		if (color == null) {
			color = HIGHLIGHT_COLOR;
		}
		g.setPaint(color);
		g.setStroke(THICK_TASK_BORDER_STROKE);

		RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width,
				height, 20, 20);
		g.draw(rect);

		g.setPaint(originalPaint);
		g.setStroke(originalStroke);
	}

	public void drawHighLight(int x, int y, int width, int height) {
		drawHighLight(x, y, width, height, null);
	}

}
