package canvas;

import util.gui.GraphicObjectsFactory;

import java.awt.geom.Rectangle2D;

/**
 * Insert the type's description here.
 * Creation date: (13.01.01 2:00:37)
 * @author Serhiy Yevtushenko
 */
public class BoundsBlock implements FigureBlock {
    Rectangle2D bounds;

    Rectangle2D temp = GraphicObjectsFactory.makeRectangle2D();

    boolean first = true;

    public BoundsBlock() {
        super();
    }

    public void exec(Figure f) {
        f.boundingBox(temp);
        if(first){
            bounds.setFrame(temp);
            first = false;
        }else{
            bounds.add(temp);
        }
    }

    public void init(Rectangle2D bounds) {
        this.bounds = bounds;
    }

}