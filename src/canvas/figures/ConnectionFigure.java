/*
 * User: sergey
 * Date: Jan 24, 2002
 * Time: 6:49:23 AM
 */
package canvas.figures;

import canvas.Figure;


public interface ConnectionFigure extends Figure{
    canvas.Figure getStartFigure();
    canvas.Figure getEndFigure();
    boolean canConnect(canvas.Figure startFigure, canvas.Figure endFigure);
    void setStartFigure(BorderCalculatingFigure start);
    void setEndFigure(BorderCalculatingFigure end);
}
