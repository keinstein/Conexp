/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import conexp.frontend.util.ToolBuilder;

import javax.swing.*;
import java.awt.*;


public class ToolbarComponentDecorator extends JPanel implements View, OptionPaneProvider {

    private ViewChangeInterfaceWithConfig inner;

    public ToolbarComponentDecorator(ViewChangeInterfaceWithConfig inner, boolean inScrollPane) {
        this.inner = inner;
        setLayout(new BorderLayout());
        add(inScrollPane ? new JScrollPane(inner.getViewComponent()) : inner.getViewComponent(), BorderLayout.CENTER);
        ToolBuilder toolBuilder = new ToolBuilder(inner.getResourceManager(), inner.getActionChain());
        JToolBar tool = toolBuilder.createToolBar(JToolBar.VERTICAL);
        tool.setFloatable(false);
        add(tool, BorderLayout.WEST);
    }


    public JComponent getViewOptions() {
        return inner.getViewOptions();
    }

    public void initialUpdate() {
        inner.initialUpdate();
    }

    public ViewChangeInterfaceWithConfig getInner() {
        return inner;
    }
}
