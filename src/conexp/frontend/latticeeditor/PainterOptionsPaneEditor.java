/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor;


import conexp.util.gui.paramseditor.ParamEditorTable;
import conexp.util.gui.paramseditor.ParamInfo;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;

public class PainterOptionsPaneEditor extends javax.swing.JPanel {


    private LatticePainterOptions opt;
    private ParamInfo[] latticeDrawingOptions;
    private LatticePainterDrawParams latticeDrawParams;

    /**
     *  Constructor for the PainterOptionsEditor object
     */
    public PainterOptionsPaneEditor(LatticePainterOptions opt, LatticePainterDrawParams drawParams, ParamInfo[] latticeDrawingOptions) {
        this.opt = opt;
        this.latticeDrawingOptions = latticeDrawingOptions;
        this.latticeDrawParams = drawParams;
        setLayout(new BorderLayout());
        JTabbedPane tabPane = new JTabbedPane();

        tabPane.addTab("Drawing options", makeDrawingOptionsPage());
        tabPane.addTab("Layout options", makeLayoutOptionsPane());

        add(tabPane);


    }


    /**
     * Insert the method's description here.
     * Creation date: (17.01.01 22:39:31)
     * @return conexp.frontend.latticeeditor.LatticePainterOptions
     */
    private LatticePainterOptions getOpt() {
        return opt;
    }


    private ParamEditorTable layoutParams;

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 23:12:51)
     */
    private JComponent makeDrawingOptionsPage() {
        JScrollPane paneDrawing = new JScrollPane();
        paneDrawing.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        ParamEditorTable table = new ParamEditorTable();
        if (null != latticeDrawingOptions) {
            table.getParamsModel().addParams(latticeDrawingOptions);
        }
        table.getParamsModel().addParams(getOpt().getLatticePainterDrawStrategyContext().getParams());
        table.getParamsModel().addParams(latticeDrawParams.getParams());
        paneDrawing.add(table);

        paneDrawing.setViewportView(table);
        return paneDrawing;
    }

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 23:17:38)
     */
    private JComponent makeLayoutOptionsPane() {
        JScrollPane layoutPane = new JScrollPane();
        layoutPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        layoutParams = new ParamEditorTable();
        setLayouterParams();

        layoutPane.add(layoutParams);
        layoutPane.setViewportView(layoutParams);

        getOpt().addPropertyChangeListener("layout", new PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent ev) {
                setLayouterParams();
            }
        });
        return layoutPane;
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 0:21:35)
     */
    private void setLayouterParams() {
        layoutParams.getParamsModel().setParams(getOpt().getLatticePainterDrawStrategyContext().getLayouter().getParams());
    }
}
