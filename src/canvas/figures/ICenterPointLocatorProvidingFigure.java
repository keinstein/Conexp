package canvas.figures;

/**
 * Author: Serhiy Yevtushenko
 * Date: Dec 2, 2002
 * Time: 5:19:39 PM
 */
public interface ICenterPointLocatorProvidingFigure {
    CenterPointLocator getCenterPointLocator();

    void setCenterPointLocator(CenterPointLocator centerPointLocator);
}
