package ml.project4.grid.util;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Paints traps.
 */
public class TrapPainter extends ObjPainter {

    private static final Color TRAP_COLOR = Color.RED;

    public TrapPainter(int[][] map) {
        super(map);
    }

    @Override
    protected Color getColor() {
        return TRAP_COLOR;
    }

    @Override
    protected Shape getShape(float rx, float ry, float cellW, float cellH) {
        return new Rectangle2D.Float(rx, ry, cellW, cellH);
    }
}