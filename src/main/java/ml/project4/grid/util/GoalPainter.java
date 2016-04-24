package ml.project4.grid.util;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Paints goals.
 */
public class GoalPainter extends ObjPainter {

    private static final Color GOAL_COLOR = Color.BLUE;

    public GoalPainter(int[][] map) {
        super(map);
    }

    @Override
    protected Color getColor() {
        return GOAL_COLOR;
    }

    @Override
    protected Shape getShape(float rx, float ry, float cellW, float cellH) {
        return new Rectangle2D.Float(rx, ry, cellW, cellH);
    }
}
