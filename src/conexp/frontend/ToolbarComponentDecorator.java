package conexp.frontend;

import conexp.frontend.util.ToolBuilder;

import javax.swing.*;
import java.awt.BorderLayout;

/**
 * ToolbarComponentDecorator.java
 *
 *
 * Created: Sun Apr 30 20:58:02 2000
 *
 * @author
 * @version
 */


public class ToolbarComponentDecorator extends JPanel implements View, OptionPaneProvider {

    private ViewChangeInterfaceWithConfig inner;

    public ToolbarComponentDecorator(ViewChangeInterfaceWithConfig inner, boolean inScrollPane) {
        this.inner = inner;
        setLayout(new BorderLayout());
        add(inScrollPane ? new JScrollPane(inner.getViewComponent()): inner.getViewComponent(), BorderLayout.CENTER);
        ToolBuilder toolBuilder = new ToolBuilder(inner.getResourceManager(), inner.getActionChain());
        JToolBar tool = toolBuilder.createToolBar(JToolBar.VERTICAL);
        tool.setFloatable(false);
        add(tool, BorderLayout.WEST);
    }


    public JComponent getViewOptions() {
        return inner.getViewOptions();
    }

    /**
     * Insert the method's description here.
     * Creation date: (16.06.01 21:37:05)
     */
    public void initialUpdate() {
        inner.initialUpdate();
    }
}