/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas;

import com.visibleworkings.trace.Trace;
import util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
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
            System.out.println("ZoomableCanvas.componentResized fitToSize="+fitToSize);
            adjustZoom(newViewportSize);
            repaint();
        }
    }

    public ZoomableCanvas() {
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
        System.out.println("ZoomableCanvas.doSetZoom "+newZoom);
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
        System.out.println("new width "+newWidth+" new height:"+newHeight);
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
        } catch (java.awt.geom.NoninvertibleTransformException ex) {
            Trace.gui.errorm(StringUtil.stackTraceToString(ex));
            unscaledPoint = srcPoint;
        }
        return unscaledPoint;
    }

    protected abstract Dimension getDrawingDimension();

    protected void updateSizeOfCanvas(Dimension newDimension) {
        System.out.println("ZoomableCanvas.updateSizeOfCanvas new size "+newDimension);
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
        System.out.println("ZoomableCanvas.setFitToSize:"+fitToSize);
        if (this.fitToSize != fitToSize) {
            this.fitToSize = fitToSize;
            if (fitToSize) {
                switchViewportToNoScrollerMode();
                Dimension size = getViewportSize();
                //updateSizeOfCanvas(size);
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

    protected void switchViewportToScrollMode(){
        System.out.println("ZoomableCanvas.switchViewportToScrollMode");
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

    protected void switchViewportToNoScrollerMode(){
        System.out.println("ZoomableCanvas.switchViewportToNoScrollerMode");
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
            Dimension printingDimension = new Dimension(
                    (int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight());
            drawOnGraphicsWithDimension(graphics2D, printingDimension,
                    makeScalingTransform(zoomForDimension(printingDimension))
            );

            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }

    protected abstract void drawOnGraphicsWithDimension(Graphics g, Dimension d, AffineTransform scalingTransform);

    public BufferedImage getScreenImage() {
        Dimension dim = getDrawingDimension();
        BufferedImage doubleBuffer = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
        drawOnGraphicsWithDimension(doubleBuffer.getGraphics(), dim, makeScalingTransform(1.0));
        return doubleBuffer;
    }

    protected void paintComponent(Graphics g) {
        drawOnGraphicsWithDimension(g, getSize(), scalingTransform);
    }

}
