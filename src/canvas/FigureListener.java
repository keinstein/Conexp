package canvas;


/**
 * Insert the type's description here.
 * Creation date: (16.01.01 0:17:31)
 * @author
 */
public interface FigureListener {
    void afterFigureMove(Figure f);

    void beforeFigureMove(Figure f);
}