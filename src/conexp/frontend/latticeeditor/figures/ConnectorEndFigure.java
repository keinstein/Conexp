/*
 * User: Serhiy Yevtushenko
 * Date: Aug 19, 2002
 * Time: 1:26:04 PM
 */
package conexp.frontend.latticeeditor.figures;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class ConnectorEndFigure extends NodeFigure implements BorderCalculatingLineDiagramFigure{
    public ConnectorEndFigure() {
        setRadius(3);
    }


    public void draw(Graphics g, canvas.CanvasScheme opt) {
        Graphics2D g2D = (Graphics2D)g;
        g2D.setColor(opt.getColorScheme().getNodeColor());
        Ellipse2D ellipse = getFigureEllipse();
        g2D.fill(ellipse);
    }

    public boolean contains(double x, double y) {
        return false;
    }


}
