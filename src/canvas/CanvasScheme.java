/*
 * User: Serhiy Yevtushenko
 * Date: Aug 15, 2002
 * Time: 3:05:29 PM
 */
package canvas;



public interface CanvasScheme {
    CanvasColorScheme getColorScheme();
    IHighlightStrategy getHighlightStrategy();
}
