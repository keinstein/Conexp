/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import com.visibleworkings.trace.Trace;
import util.StringUtil;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;


public abstract class ZoomableCanvas extends JComponent implements IScreenImageProvider, Printable {
    protected AffineTransform scalingTransform;

    boolean fitToSize = false;
    private double zoom;
    protected ComponentListener resizeListener = new ComponentAdapter() {
        public void componentResized(ComponentEvent e) {
            onComponentResized();
        }
    };

    public void onComponentResized() {
        Dimension newViewportSize = getViewportSize();
        if (isFitToSize()) {
            //System.out.println("ZoomableCanvas.componentResized fitToSize="+fitToSize);
            adjustZoom(newViewportSize);
            repaint();
        }
    }

    protected ZoomableCanvas() {
        setZoom(1.0D);
        addComponentListener(resizeListener);
    }


    public double getZoom() {
        return zoom;
    }

    public void setZoom(double newZoom) {
        if (fitToSize) {
            return;
        }
        if (newZoom < 1e-4) {
            return;
        }
        doSetZoom(newZoom);
    }

    private void doSetZoom(double newZoom) {
        double oldValue = getZoom();
        if (newZoom != oldValue) {
            this.zoom = newZoom;
            Dimension viewDimension = getViewportSize();
            Dimension newDimension = calcNewDimension(viewDimension);
            updateSizeOfCanvas(newDimension);
            updateScalingTransform();
            repaint();

            firePropertyChange("ZOOM", oldValue, newZoom);
        }
    }

    protected Dimension calcNewDimension(Dimension viewDimension) {
        Dimension newDimension = new Dimension(getDrawingDimension());
        int newWidth = (int) Math.max(viewDimension.getWidth(), newDimension.getWidth() * getZoom());
        int newHeight = (int) Math.max(viewDimension.getHeight(), newDimension.getHeight() * getZoom());
        newDimension.setSize(newWidth, newHeight); //due to bug in jdk1.3
        return newDimension;
    }


    protected void drawingDimensionChanged(Dimension newDimension) {
        if (!fitToSize) {
            updateSizeOfCanvas(newDimension);
        } else {
            adjustZoom(getViewportSize());
        }
    }

    public Point2D getWorldCoords(Point srcPoint) {
        Point2D unscaledPoint = new Point2D.Double();
        try {
            scalingTransform.inverseTransform(srcPoint, unscaledPoint);
        } catch (NoninvertibleTransformException ex) {
            Trace.gui.errorm(StringUtil.stackTraceToString(ex));
            unscaledPoint = srcPoint;
        }
        return unscaledPoint;
    }

    protected abstract Dimension getDrawingDimension();

    protected void updateSizeOfCanvas(Dimension newDimension) {
        //System.out.println("ZoomableCanvas.updateSizeOfCanvas new size "+newDimension);
        setSize(newDimension);
        setPreferredSize(newDimension);
        revalidate();
        //switchViewportToScrollMode();
    }

    protected void adjustZoom(Dimension size) {
        doSetZoom(zoomForDimension(size));
    }

    protected double zoomForDimension(Dimension size) {
        Dimension2D dim = getDrawingDimension();
        double sx = size.getWidth() / dim.getWidth();
        double sy = size.getHeight() / dim.getHeight();
        double newZoom = Math.min(sx, sy);
        return newZoom;
    }

    protected void updateScalingTransform() {
        scalingTransform = makeScalingTransform(getZoom());
    }

    protected static AffineTransform makeScalingTransform(double zoom) {
        return AffineTransform.getScaleInstance(zoom, zoom);
    }

    public void setFitToSize(boolean fitToSize) {
        if (this.fitToSize != fitToSize) {
            this.fitToSize = fitToSize;
            if (fitToSize) {
                switchViewportToNoScrollerMode();
                Dimension size = getViewportSize();
                adjustZoom(size);
            } else {
                setZoom(1.0);
                switchViewportToScrollMode();
            }
            repaint();
        }
    }

    public boolean isFitToSize() {
        return fitToSize;
    }

    protected void switchViewportToScrollMode() {
        Component c = getParent();
        if (c instanceof JViewport) {
            JViewport viewport = (JViewport) c;
            Component viewportParent = viewport.getParent();
            Point oldPoint = viewport.getViewPosition();// old point to be reset after revalidation.
            if (viewportParent instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) viewportParent;
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.revalidate();
            }
            viewport.setViewPosition(oldPoint);
        }
    }

    protected void switchViewportToNoScrollerMode() {
        Component c = getParent();
        if (c instanceof JViewport) {
            JViewport viewport = (JViewport) c;
            Component viewportParent = viewport.getParent();
            if (viewportParent instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) viewportParent;
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                scrollPane.revalidate();
            }
        }
    }

    protected Dimension getViewportSize() {
        Dimension size = getSize();
        Component c = getParent();
        if (c instanceof JViewport) {
            JViewport viewport = (JViewport) c;
            size = viewport.getExtentSize();
        }
        return size;
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {
        if (pageIndex == 0) {
            Graphics2D graphics2D = (Graphics2D) graphics;
            Dimension printingDimension = new Dimension((int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight());
            drawOnGraphicsWithDimension(graphics2D, printingDimension,
                    makeScalingTransform(zoomForDimension(printingDimension)));

            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }

    protected abstract void doDrawOnGraphicsWithDimension(Graphics g, Dimension d, AffineTransform scalingTransform) throws sun.dc.pr.PRException;

    protected void drawOnGraphicsWithDimension(Graphics g, Dimension dimension, AffineTransform scalingTransform) {
        try {


            if (g instanceof Graphics2D) {
                Graphics2D g2D = (Graphics2D) g;
                Object oldAntiAlias = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
                try {
                    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
                    doDrawOnGraphicsWithDimension(g, dimension, scalingTransform);
                } finally {
                    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAntiAlias);
                }
            } else {
                doDrawOnGraphicsWithDimension(g, dimension, scalingTransform);
            }

        } catch (sun.dc.pr.PRError er) {
            //do nothing
            /**
             * this code is here to handle bug in JDK
             */
        } catch (sun.dc.pr.PRException ex) {
            //do nothing
            /**
             * this code is here to handle bug in JDK
             */
        }
    }


    public BufferedImage getScreenImage() {
        Dimension dim = getDrawingDimension();
        BufferedImage doubleBuffer = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
        drawOnGraphicsWithDimension(doubleBuffer.getGraphics(), dim, makeScalingTransform(1.0));
        return doubleBuffer;
    }

    boolean antiAlias = true;
    Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;

    public boolean isAntiAlias() {
        return antiAlias;
    }

    public void setAntiAlias(boolean antiAlias) {
        this.antiAlias = antiAlias;
        this.AntiAlias = antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF;
    }


    protected void paintComponent(Graphics g) {
        drawOnGraphicsWithDimension(g, getSize(), this.scalingTransform);
    }

}
