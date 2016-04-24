package ml.project4.grid.util;

import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.visualizer.ObjectPainter;
import ml.project4.grid.BasicGridWorld;

import java.awt.*;

/**
 * Paints objects.
 */
public abstract class ObjPainter implements ObjectPainter {


    private final int[][] map;
    private final int mapWidth;
    private final int mapHeight;

    public ObjPainter(int[][] map) {
        this.map = map;
        mapWidth = this.map.length;
        mapHeight = this.map[0].length;
    }

    protected abstract Color getColor();

    protected abstract Shape getShape(float rx, float ry, float cellW, float cellH);

    @Override
    public void paintObject(Graphics2D g2, State s, ObjectInstance ob,
                            float cWidth, float cHeight) {

        g2.setColor(getColor());

        // determine the width of a single cell on our canvas
        // such that the whole map can be painted
        float cellWidth = cWidth / ((float) mapWidth);
        float cellHeight = cHeight / ((float) mapHeight);

        int ax = ob.getIntValForAttribute(BasicGridWorld.ATTX);
        int ay = ob.getIntValForAttribute(BasicGridWorld.ATTY);

        // left coordinate of cell on our canvas
        float rx = ax * cellWidth;

        // top coordinate of cell on our canvas
        // coordinate system adjustment because the java canvas
        // origin is in the top left instead of the bottom right
        float ry = cHeight - cellHeight - ay * cellHeight;

        // paint the rectangle
        g2.fill(getShape(rx, ry, cellWidth, cellHeight));
    }
}