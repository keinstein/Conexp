/*
 * User: sergey
 * Date: Jan 27, 2002
 * Time: 3:03:39 PM
 */
package conexp.frontend.latticeeditor;


//todo: extract real LayoutParameters

public interface DrawParameters {
    int getGridSizeX();

    int getGridSizeY();

    int getMinGapX();

    int getMinGapY();

    float getMaxEdgeStroke();

    int getMinNodeRadius();

    int getMaxNodeRadius();
}
