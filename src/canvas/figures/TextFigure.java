package canvas.figures;

import canvas.CanvasScheme;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Creation date: (17.12.00 22:41:48)
 * @author sergey
 */
public abstract class TextFigure extends BaseTextFigure implements BorderCalculatingFigure{

    protected TextFigure() {
        setColorTransformer(ColorTransformerWithFadeOut.getInstance());
    }

    public void draw(Graphics g, CanvasScheme opt) {
        Graphics2D g2D = (Graphics2D) g;
        FontMetrics fm = g.getFontMetrics();
        final int xTextOffset = 10;
        final int yTextOffset = 4;

        setWidth(fm.stringWidth(getString()) + xTextOffset);
        setHeight(fm.getHeight() + yTextOffset);

        Rectangle2D rect = new Rectangle2D.Double();
        boundingBox(rect);

        g.setColor(getBackground(opt));
        g2D.fill(rect);

        g2D.setColor(getBorderColor(opt));
        g2D.draw(rect);
        g2D.setColor(getTextColor(opt));
        g2D.drawString(getString(), (float) (getCenterX() - (getWidth() - xTextOffset) / 2), (float) ((getCenterY() - (getHeight() - yTextOffset) / 2) + fm.getAscent()));
    }

    protected abstract String getString();

}