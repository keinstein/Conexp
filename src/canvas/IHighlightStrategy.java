package canvas;
/*
 * User: Serhiy Yevtushenko
 * Date: Oct 19, 2002
 * Time: 3:52:49 PM
 */
public interface IHighlightStrategy {
    boolean highlightFigure(Figure figure);

    boolean isActive();
}
