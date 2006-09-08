/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;


import conexp.util.gui.paramseditor.ParamEditorTable;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.ParamsTableModel;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;

public class PainterOptionsPaneEditor extends javax.swing.JPanel {


    private LatticePainterOptions opt;
    private ParamInfo[] latticeDrawingOptions;
    private LatticePainterDrawParams latticeDrawParams;

    /**
     * Constructor for the PainterOptionsEditor object
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


    private ParamEditorTable layoutParams;

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 23:12:51)
     */
    private JComponent makeDrawingOptionsPage() {
        JScrollPane paneDrawing = new JScrollPane();
        paneDrawing.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        ParamEditorTable table = new ParamEditorTable();
        fillParamsModel(table.getParamsModel());

        paneDrawing.add(table);

        paneDrawing.setViewportView(table);
        return paneDrawing;
    }

    private void fillParamsModel(final ParamsTableModel paramsModel) {
        if (null != latticeDrawingOptions) {
            paramsModel.addParams(latticeDrawingOptions);
        }
        paramsModel.addParams(opt.getLatticePainterDrawStrategyContext().getParams());
        //todo: write check on update, when params changing and correct listener setup
        paramsModel.addParams(opt.getParams());

        paramsModel.addParams(latticeDrawParams.getParams());
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

        opt.addPropertyChangeListener("layout", new PropertyChangeListener() {
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
        layoutParams.getParamsModel().setParams(opt.getLatticePainterDrawStrategyContext().getLayouter().getParams());
    }
}
