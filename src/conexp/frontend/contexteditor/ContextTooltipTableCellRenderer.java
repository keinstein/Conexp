/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.contexteditor;

import conexp.core.ContextEditingInterface;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.ParamsProvider;
import conexp.util.gui.paramseditor.StrategyValueItemParamInfo;
import conexp.util.gui.strategymodel.StrategyValueItem;
import util.Assert;
import util.gui.celleditors.MyDefaultCellRenderer;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.beans.PropertyChangeListener;

public class ContextTooltipTableCellRenderer extends MyDefaultCellRenderer implements ParamsProvider {


    protected StrategyValueItem drawStrategy;
    ParamInfo[] params;


    /**
     * Creates a default table cell renderer.
     */
    public ContextTooltipTableCellRenderer() {
        super();
    }

//---------------------------------------------------------

    private void processTooltip(ContextEditingInterface cxt, int row, int col) {
        String msg = "";
        if (row > 0 && row <= cxt.getObjectCount()) {
            msg = cxt.getObject(row - 1).getName();
        }
        if (col > 0 && col <= cxt.getAttributeCount()) {
            if (msg.length() > 0) {
                msg += " ";
            }

            msg += cxt.getAttribute(col - 1).getName();
        }
        setToolTipText(msg);
    }

    protected void doSetupValue(JTable table, Object value, int row, int col) {
        TableModel model = table.getModel();

        Icon icon = null;
        String txt = "";

        if (model instanceof ContextTableModel) {
            ContextEditingInterface cxt = ((ContextTableModel) model).getContext();
            processTooltip(cxt, row, col);
//-----------------------------------------
//value processing
            if (row > 0 && col > 0) {
                icon = getRelationIcon(cxt, row, col);
                setHorizontalAlignment(JLabel.CENTER);
            } else {
//text value processing
                txt = value.toString();
                setHorizontalAlignment(JLabel.LEFT);
            }
//------------------------------------------
        } else {
//default value processing
            txt = value.toString();
        }
        setText(txt);
        setIcon(icon);
    }

    /**
     * Insert the method's description here.
     * Creation date: (22.04.01 21:56:23)
     *
     * @param lst java.beans.PropertyChangeListener
     */
    public void addRenderingChangeListener(PropertyChangeListener lst) {
        getDrawStrategy().getPropertyChange().addPropertyChangeListener(lst);
    }


    /**
     * Insert the method's description here.
     * Creation date: (22.04.01 21:35:26)
     */
    private ContextRenderStrategy getContextRenderStrategy() {
        return (ContextRenderStrategy) getDrawStrategy().getStrategy();
    }


    /**
     * Insert the method's description here.
     * Creation date: (22.04.01 21:46:46)
     *
     * @return conexp.util.gui.strategymodel.StrategyValueItem
     */
    public synchronized StrategyValueItem getDrawStrategy() {
        if (null == drawStrategy) {
            drawStrategy = new StrategyValueItem("contextRenderStrategy", new DrawArrowsStrategyModel(), null);
        }
        return drawStrategy;
    }


    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 22:40:44)
     *
     * @return conexp.util.gui.paramseditor.ParamInfo[]
     */
    public ParamInfo[] getParams() {
        if (null == params) {
            params = new ParamInfo[]{
                    new StrategyValueItemParamInfo("Show arrow relation", getDrawStrategy())
            };
        }
        return params;
    }


    /**
     * Insert the method's description here.
     * Creation date: (22.04.01 21:09:13)
     */
    protected Icon getRelationIcon(ContextEditingInterface cxt, int row, int col) {
        Assert.isTrue(row > 0);
        Assert.isTrue(col > 0);
        return getContextRenderStrategy().getRelationIcon(cxt, row, col);
    }
}
