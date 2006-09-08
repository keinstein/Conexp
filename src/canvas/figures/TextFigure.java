/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import canvas.CanvasScheme;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public abstract class TextFigure extends BaseTextFigure {

    protected TextFigure() {
        setColorTransformer(ColorTransformerWithFadeOut.getInstance());
    }

    public void draw(Graphics g, CanvasScheme opt) {
        Graphics2D g2D = (Graphics2D) g;
        Font paintFont = opt.getLabelsFont(g);
        Font oldFont = g.getFont();
        try {
            g.setFont(paintFont);
            FontMetrics fm = getFontMetrics(g, opt);

            int xTextOffset = 10;
            int yTextOffset = 4;

            setWidth(fm.stringWidth(getString()) + xTextOffset);
            setHeight(fm.getHeight() + yTextOffset);

            Rectangle2D rect = new Rectangle2D.Double();
            boundingBox(rect);

            g.setColor(getBackground(opt));
            g2D.fill(rect);

            g2D.setColor(getBorderColor(opt));
            g2D.draw(rect);
            g2D.setColor(getTextColor(opt));
            g2D.drawString(getString(), (float) (getCenterX() - (getWidth() - xTextOffset) / 2), (float) (getCenterY() - (getHeight() - yTextOffset) / 2 + fm.getAscent()));
        } finally {
            g.setFont(oldFont);
        }

    }

    private static FontMetrics getFontMetrics(Graphics g, CanvasScheme opt) {
        return opt.getLabelsFontMetrics(g);
    }

    protected abstract String getString();

}
