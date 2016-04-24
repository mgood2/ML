package ml.project4.grid.util;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Paints Agents.
 */
public class AgentPainter extends ObjPainter {

    private static final Color AGENT_COLOR = Color.GRAY;
    private static final float SCALE_FACTOR = 0.9f;

    public AgentPainter(int[][] map) {
        super(map);
    }

    @Override
    protected Color getColor() {
        return AGENT_COLOR;
    }

    @Override
    protected Shape getShape(float rx, float ry, float cellW, float cellH) {
        float aWidth = cellW * SCALE_FACTOR;
        float aHeight = cellH * SCALE_FACTOR;
        float padW = (cellW - aWidth) / 2.0f;
        float padH = (cellH - aHeight) / 2.0f;

        return new Ellipse2D.Float(rx + padW, ry + padH, aWidth, aHeight);
    }
}


