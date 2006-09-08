/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.paramseditor;

import conexp.util.valuemodels.BoundedDoubleValue;

import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JSlider;

public class BoundedDoubleValueParamInfo extends AbstractParamInfo {
    private final BoundedDoubleValue valueModel;

    ;
    private javax.swing.JSlider fSlider;
    private static util.gui.celleditors.JComponentCellRenderer sCellRenderer = new util.gui.celleditors.JComponentCellRenderer();
    private static util.gui.celleditors.JComponentCellEditor sCellEditor = new util.gui.celleditors.JComponentCellEditor();
    ;

    /**
     * BoundedIntValueParamInfo constructor comment.
     *
     * @param label java.lang.String
     */
    public BoundedDoubleValueParamInfo(String label, BoundedDoubleValue value) {
        super(label);
        this.valueModel = value;
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 23:55:57)
     *
     * @return javax.swing.JSlider
     */
    private javax.swing.JSlider getFSlider() {
        if (null == fSlider) {
            BoundedRangeModel rangeModel = new DefaultBoundedRangeModel(0, 0, 0, valueModel.getResolution()) {
                public void setValue(int value) {
                    super.setValue(value);
                    valueModel.setValue(valueModel.minVal + super.getValue() * valueModel.tickVal());
                }

                public int getValue() {
                    return (int) Math.round((valueModel.getValue() - valueModel.minVal) / valueModel.tickVal());
                }
            };
            fSlider = new JSlider(rangeModel) {
                public String toString() {
                    return ""; // fix to bug with table cell renderer;
                }
            };
//		fSlider.setBackground(new java.awt.Color(17,204,204));
            fSlider.setPaintTicks(true);
            fSlider.setMinorTickSpacing(1);
            fSlider.setMajorTickSpacing(valueModel.getResolution() / 10);
            fSlider.setSnapToTicks(true);
            fSlider.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        }
        return fSlider;
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 23:35:43)
     *
     * @return javax.swing.table.TableCellRenderer
     */
    public javax.swing.table.TableCellRenderer getParamRenderer() {
        return sCellRenderer;
    }

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 0:04:11)
     *
     * @return java.lang.Object
     */
    public java.lang.Object getValue() {
        return getFSlider();
    }

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 0:01:26)
     *
     * @return javax.swing.table.TableCellEditor
     */
    protected javax.swing.table.TableCellEditor makeEditor() {
        return sCellEditor;
    }
}
