/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jun 8, 2002
 * Time: 2:40:45 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import conexp.frontend.latticeeditor.NestedLineDiagramCanvas;
import conexp.frontend.latticeeditor.NestedLineDiagramDrawing;

import javax.swing.*;

public class NestedLineDiagramView extends JScrollPane implements View, OptionPaneProvider {
    NestedLineDiagramCanvas canvas;
    NestedLineDiagramDrawing nestedLineDiagramDrawing = new NestedLineDiagramDrawing();

    public NestedLineDiagramView() {
        super();
        this.canvas = new NestedLineDiagramCanvas(nestedLineDiagramDrawing);
        add(canvas);
        setViewportView(canvas);
    }

    public NestedLineDiagramDrawing getDrawing() {
        return nestedLineDiagramDrawing;
    }

    public void initialUpdate() {
        canvas.setFitToSize(true);
    }

    public JComponent getViewOptions() {
        return canvas.getViewOptions();
    }


}
