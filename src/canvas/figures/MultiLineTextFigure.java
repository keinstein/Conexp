/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import canvas.CanvasScheme;
import canvas.IHighlightStrategy;
import util.StringUtil;
import util.collection.CollectionFactory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class MultiLineTextFigure extends BaseTextFigure {

    static class TextLine {

        double width;
        double height;
        float ascent;
        float descent;
        String text;

        TextLine(String s, TextLayout textlayout) {
            text = s;
            Rectangle2D rectangle2d = textlayout.getBounds();
            width = rectangle2d.getWidth();
            height = rectangle2d.getHeight();
            ascent = textlayout.getAscent();
            descent = textlayout.getDescent();
        }
    }

    public boolean isContentDirty() {
        return contentDirty;
    }

    protected boolean shouldHighlight(IHighlightStrategy highlightStrategy) {
        return highlightStrategy.highlightFigure(this);
    }


    public void draw(Graphics g, CanvasScheme opt) {
        Graphics2D graphics2d = (Graphics2D) g;
        recalcSizeIfNeeded(graphics2d);
        if (!shouldDrawText()) {
            return;
        }
        graphics2d.setColor(getBackground(opt));
        graphics2d.fillRect((int) getLeftX(), (int) getTopY(), (int) getWidth(), (int) getHeight());

        graphics2d.setColor(getBorderColor(opt));
        drawBorder(graphics2d);

        graphics2d.setColor(getTextColor(opt));
        graphics2d.setFont(font);

        double x = getLeftX() + xTextOffset / 2;
        double y = getTopY() + yTextOffset / 2;
        for (int i = 0; i < lines.size(); i++) {
            TextLine textline = getTextLine(i);
            y += textline.ascent;
            double lineXOffset = calcLineXOffset(textline);
            graphics2d.drawString(textline.text, (int) (x + lineXOffset), (int) y);
            y += textline.descent;
        }

    }

    private void recalcSizeIfNeeded(Graphics2D graphics2d) {
        if (contentDirty) {
            newSize(graphics2d.getFontRenderContext());
        }
    }

    protected boolean shouldDrawText() {
        return text.length() > 0 && visible;
    }

    protected void drawBorder(Graphics2D graphics2d) {
        graphics2d.drawRect((int) getLeftX(), (int) getTopY(), (int) getWidth(), (int) getHeight());
    }

    protected Color getBackground(CanvasScheme opt) {
        if (shouldHighlight(opt.getHighlightStrategy())) {
            return opt.getColorScheme().getSelectedTextBackground();
        }
        return super.getBackground(opt);
    }


    private double calcLineXOffset(TextLine textline) {
        switch (alignment) {
            default: //FALLTHROUGH
            case ALIGN_LEFT:
                return 0.0D;

            case ALIGN_CENTER:
                return (textWidth - textline.width) / 2D;

            case ALIGN_RIGHT:
                return textWidth - textline.width;
        }
    }

    public void changeLayout(FontRenderContext fontrendercontext) {
        lines.clear();
        String textTail = text;
        for (int i = textTail.indexOf("\n"); i >= 0; i = textTail.indexOf("\n")) {
            String currentLine = textTail.substring(0, i);
            if (currentLine.length() == 0) {
                currentLine = " ";
            }
            lines.add(new TextLine(currentLine, new TextLayout(currentLine, font, fontrendercontext)));
            textTail = textTail.substring(i + 1);
        }
        if (textTail.length() == 0) {
            textTail = " ";
        }
        lines.add(new TextLine(textTail, new TextLayout(textTail, font, fontrendercontext)));

        textWidth = textHeight = 0.0D;
        for (int j = lines.size(); --j >= 0;) {
            TextLine textline = getTextLine(j);
            if (textline.width > textWidth) {
                textWidth = textline.width;
            }
            textHeight += textline.ascent + textline.descent;
        }
        calcDimensions();
    }

    protected void calcDimensions() {
        setWidth(Math.max(textWidth + xTextOffset, minimalWidth));
        setHeight(Math.max(textHeight + yTextOffset, minimalHeight));
    }

    private TextLine getTextLine(int j) {
        return (TextLine) lines.get(j);
    }

    public void newSize(FontRenderContext fontrendercontext) {
        if (text.length() == 0) {
            setSizeOnEmptyText();
        } else {
            changeLayout(fontrendercontext);
        }
        contentDirty = false;
    }

    protected void setSizeOnEmptyText() {
        textWidth = minimalWidth;
        textHeight = minimalHeight;
        calcDimensions();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean flag) {
        visible = flag;
    }

    public void setText(String s) {
        contentDirty = true;
        text = StringUtil.safeText(s);
    }

    public String getText() {
        return text;
    }

    public void setAlignment(int newAlignment) {
        alignment = newAlignment;
    }

    public int getAlignment() {
        return alignment;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font newFont) {
        font = newFont;
        contentDirty = true;
    }

    public void setFontName(String s) {
        setFont(new Font(s, font.getStyle(), font.getSize()));
    }

    public String getFontName() {
        return font.getName();
    }

    public int getFontSize() {
        return font.getSize();
    }

    public void setFontSize(int i) {
        setFont(font.deriveFont(i));
    }

    public int getFontStyle() {
        return font.getStyle();
    }

    public void setFontStyle(int i) {
        setFont(font.deriveFont(i));
    }

    public void setMinimalWidth(double minimalWidth) {
        this.minimalWidth = minimalWidth;
        calcDimensions();
    }

    public void setMinimalHeight(double minimalHeight) {
        this.minimalHeight = minimalHeight;
        calcDimensions();
    }

    public MultiLineTextFigure() {
        visible = true;
        font = new Font("Dialog", 0, 12);
        alignment = ALIGN_LEFT;
        lines = CollectionFactory.createDefaultList();
        text = "";
        contentDirty = true;
        minimalWidth = 0;
        minimalHeight = 0;
    }

    public static final byte ALIGN_LEFT = 0;
    public static final byte ALIGN_CENTER = 1;
    public static final byte ALIGN_RIGHT = 2;
    private String text;
    private boolean visible;
    private double xTextOffset = 12;
    private double yTextOffset = 4;
    private List lines;
    private Font font;
    private double textWidth;
    private double textHeight;
    private double minimalWidth;
    private double minimalHeight;

    private int alignment;
    protected boolean contentDirty;
}
