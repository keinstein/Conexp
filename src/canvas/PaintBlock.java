package canvas;

import java.awt.Graphics;
import java.awt.Graphics2D;


/**
 * Insert the type's description here.
 * Creation date: (13.01.01 19:50:48)
 * @author Serhiy Yevtushenko
 */
public class PaintBlock implements FigureBlock {
    Graphics2D g;
    private CanvasScheme opt;

    public PaintBlock(CanvasScheme opt) {
        super();
        this.opt = opt;
    }

    public void exec(Figure f) {
        f.draw(g, getOpt());
    }

    public Graphics2D getGraphics2D() {
        return g;
    }

    CanvasScheme getOpt() {
        return opt;
    }

    public void setGraphics(Graphics graphics) {
        g = (Graphics2D) graphics;
    }
}