package ml.project4.grid.util;

import burlap.oomdp.core.states.State;
import burlap.oomdp.visualizer.StaticPainter;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Paints walls.
 */
public class WallPainter implements StaticPainter {

    private static final int WALL = 1;
    private static final Color WALL_COLOR = Color.BLACK;

    private final int[][] map;
    private final int mapWidth;
    private final int mapHeight;

    public WallPainter(int[][] map) {
        this.map = map;
        mapWidth = this.map.length;
        mapHeight = this.map[0].length;
    }

    @Override
    public void paint(Graphics2D g2, State s, float cWidth, float cHeight) {

        g2.setColor(WALL_COLOR);

        // determine the width of a single cell
        // on our canvas such that the whole map can be painted
        float cellWidth = cWidth / ((float) mapWidth);
        float cellHeight = cHeight / ((float) mapHeight);

        // pass through each cell of our map and if it's a wall,
        // paint a black rectangle on our canvas of dimension width x height
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {

                // is there a wall here?
                if (this.map[x][y] == WALL) {

                    // left coordinate of cell on our canvas
                    float rx = x * cellWidth;

                    // top coordinate of cell on our canvas
                    // coordinate system adjustment because the java canvas
                    // origin is in the top left instead of the bottom right
                    float ry = cHeight - cellHeight - y * cellHeight;

                    // paint the rectangle
                    g2.fill(new Rectangle2D.Float(rx, ry, cellWidth, cellHeight));
                }
            }
        }
    }

}
