package canvas.figures;

import canvas.BaseFigureVisitor;
import canvas.Figure;
import util.Assert;

/**
 * Author: Serhiy Yevtushenko
 * Date: Dec 2, 2002
 * Time: 3:37:40 PM
 */
public abstract class AbstractFigure implements Figure{
    protected canvas.FigureListener fListener;

    public void removeFigureListener() {
        fListener = null;
    }

    public void setFigureListener(canvas.FigureListener newFListener) {
        fListener = newFListener;
    }

    public Figure findFigureInside(double x, double y) {
        Assert.isTrue(contains(x, y));
        return this;
    }

    public Figure findFigureInsideExceptFor(double x, double y, Figure toExclude) {
        Assert.isTrue(contains(x, y));
        return toExclude == this ? null: this;
    }

    public void visit(BaseFigureVisitor visitor) {
    }

    public void moveBy(double dx, double dy) {
        beforeMove();
        basicMoveBy(dx, dy);
        afterMove();
    }

    private void beforeMove() {
        if (null != fListener) {
            fListener.beforeFigureMove(this);
        }
    }

    private void afterMove() {
        if (null != fListener) {
            fListener.afterFigureMove(this);
        }
    }

    protected abstract void basicMoveBy(double x, double y);

    public boolean contains(double x, double y) {
        return false;
    }
}
