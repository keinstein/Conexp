package conexp.frontend.contexteditor;

import conexp.core.*;
import conexp.util.gui.Command;
import conexp.util.gui.CommandBase;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.ParamsProvider;
import conexp.util.valuemodels.IntValueModel;
import util.Assert;
import util.BooleanUtil;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoableEditSupport;

public class ContextTableModel extends AbstractTableModel implements ParamsProvider {

    UndoableEditSupport undoableEditSupport = new UndoableEditSupport(this);

    CompoundEdit compoundEdit = null;


    protected synchronized void startCompoundCommand() {
        if (inCompoundEdit()) {
            throw new IllegalStateException("Can perform only one compound command per time");
        }
        compoundEdit = new CompoundEdit();
    }

    protected boolean inCompoundEdit() {
        return compoundEdit != null;
    }

    protected synchronized void endCompoundCommand() {
        compoundEdit.end();
        CompoundEdit toPerform = compoundEdit;
        compoundEdit = null;
        undoableEditSupport.postEdit(toPerform);
    }


    public void addUndoableEditListener(UndoableEditListener listener) {
        undoableEditSupport.addUndoableEditListener(listener);
    }

    public void removeUndoableEditListener(UndoableEditListener listener) {
        undoableEditSupport.removeUndoableEditListener(listener);
    }

    public synchronized void performCommand(Command command) {
        command.performCommand();
        if (inCompoundEdit()) {
            compoundEdit.addEdit(command);
        } else {
            undoableEditSupport.postEdit(command);
        }
    }

    ContextEditingInterface context;

    ContextListener listener = new DefaultContextListener() {
        public void contextStructureChanged() {
            fireTableStructureChanged();
        }
    };


    protected IntValueModel attribCountModel;
    protected IntValueModel objectCountModel;
    ParamInfo[] params;


    /**
     *  Constructor for the ContextTableModel object
     *
     *@param  context  Description of Parameter
     */
    public ContextTableModel(ContextEditingInterface context) {
        super();
        setContext(context);
    }

    public Class getColumnClass(int col) {
        if (!isAttributeColumn(col)) {
            return String.class;
        }
        return Boolean.class;
    }

    public int getColumnCount() {
        return context.getAttributeCount() + 1;
    }

    public int getRowCount() {
        return context.getObjectCount() + 1;
    }

    public boolean canDeleteColumn(int colIndex) {
        return canProcessColumns(new int[]{colIndex});
    }

    public boolean canProcessColumns(int[] columns) {
        //returns true, if there is at least one attribute columns, that is not selected
        //and selected only attributes column
        Assert.isTrue(isOrdered(columns));
        if (columns.length > 0) {
            for (int i = 0; i < columns.length; i++) {
                if (!isAttributeColumn(columns[i])) {
                    return false;
                }
            }
            return columns.length < getContext().getAttributeCount();
        }
        return false;
    }

    public void removeColumns(int[] columns) {
        performCommand(new RemoveColumnsCommand(columns));
    }

    private void doRemoveColumns(int[] columns) {
        Assert.isTrue(canProcessColumns(columns));
        for (int i = columns.length; --i >= 0;) {
            doRemoveColumn(columns[i]);
        }
    }

    private static boolean isOrdered(int[] columns) {
        if (columns.length < 2) {
            return true;
        }
        for (int i = 0; i < columns.length - 1; i++) {
            if (columns[i] >= columns[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private int colToAttributeIndex(int col) {
        return isAttributeColumn(col) ? col - 1 : -1;
    }

    private int rowToObjectIndex(int row) {
        return isObjectRow(row) ? row - 1 : -1;
    }

    private boolean isAttributeColumn(int col) {
        return col > 0;
    }

    private boolean isObjectRow(int row) {
        return row > 0;
    }

    /**
     *  performs actual column removal
     *  is not public because all work should go
     *  through removeColumns(int[] columns)
     */

    protected void doRemoveColumn(int colIndex) {
        if (canDeleteColumn(colIndex)) {
            getContext().removeAttribute(colToAttributeIndex(colIndex));
        }
    }

    public boolean canDeleteRow(int row) {
        return canDeleteRows(new int[]{row});
    }

    public boolean canDeleteRows(int[] rows) {
        Assert.isTrue(isOrdered(rows));
        if (rows.length > 0) {
            for (int i = 0; i < rows.length; i++) {
                if (!isObjectRow(rows[i])) {
                    return false;
                }
            }
            return rows.length < getContext().getObjectCount();
        }
        return false;
    }

    public Object getValueAt(int row, int col) {
        if (0 == col) {
            if (0 == row) {
                return "";
            }
            return context.getObject(rowToObjectIndex(row)).getName();
        }
        if (0 == row) {
            return context.getAttribute(colToAttributeIndex(col)).getName();
        }
        return BooleanUtil.valueOf(context.getRelationAt(rowToObjectIndex(row), colToAttributeIndex(col)));
    }


    /*
	 * Don't need to implement this method unless your table's
	 * editable.
	 */
    /**
     *  Gets the CellEditable attribute of the ContextTableModel object
     *
     *@param  row  Description of Parameter
     *@param  col  Description of Parameter
     *@return      The CellEditable value
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (0 == col && 0 == row) {
            return false;
        }
        return true;
    }

    public void setContext(ContextEditingInterface cxt) {
        if (null != context) {
            cxt.removeContextListener(listener);
        }
        Assert.isTrue(null != cxt, "Context can't be null");
        context = cxt;
        context.addContextListener(listener);
        fireTableStructureChanged();
        fireTableDataChanged();
    }

    public void setValueAt(final Object value, final int row, final int col) {
        if (!isAttributeColumn(col)) {
            if (isObjectRow(row)) {
                performCommand(new SetObjectNameCommand(row, value));
            }
        } else {
            if (!isObjectRow(row)) {
                performCommand(new SetAttributeNameCommand(col, value));
            } else {
                performCommand(new ModifyRelationCommand(row, col,
                        ((Boolean) value).booleanValue()));

            }
        }
        //*DBG*/ printDebugData();
    }

    static abstract class SyncListener extends DefaultContextListener implements TableModelListener {
        IntValueModel valueModel;

        public SyncListener(IntValueModel valueModel) {
            this.valueModel = valueModel;
        }

        void doSync() {
            try {
                valueModel.setValue(getValue());
            } catch (java.beans.PropertyVetoException ex) {
                util.Assert.isTrue(false);
            }
        }

        protected abstract int getValue();

        public void contextStructureChanged() {
            doSync();
        }

        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() != TableModelEvent.UPDATE ||
                    evt.getFirstRow() == TableModelEvent.HEADER_ROW) {
                doSync();
            }
        }

    }


    public synchronized IntValueModel getAttribCountModel() {
        if (null == attribCountModel) {
            attribCountModel = new IntValueModel("Attrib Count", context.getAttributeCount());
            attribCountModel.addVetoableChangeListener(makeVetoableGreaterOrEqualToZeroChecker("Number of attributes should be greater than zero"));

            attribCountModel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    final int objectCount = getContext().getObjectCount();
                    final int numAttr = ((Number) evt.getNewValue()).intValue();
                    if (numAttr != getContext().getAttributeCount()) {
                        performCommand(new SetDimensionCommand("Set attribute count", objectCount, numAttr));
                    }
                }
            }
            );

            SyncListener lst = new SyncListener(attribCountModel) {
                protected int getValue() {
                    return getContext().getAttributeCount();
                }
            };

            context.addContextListener(lst);
            addTableModelListener(lst);
        }
        return attribCountModel;
    }

    public ContextEditingInterface getContext() {
        return context;
    }


    public synchronized IntValueModel getObjectCountModel() {
        if (null == objectCountModel) {
            objectCountModel = new IntValueModel("Object Count", context.getObjectCount());
            objectCountModel.addVetoableChangeListener(makeVetoableGreaterOrEqualToZeroChecker("Object Count should be greater than zero"));
            objectCountModel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    final int numObj = ((Number) evt.getNewValue()).intValue();
                    final int attributeCount = getContext().getAttributeCount();
                    if (numObj != getContext().getObjectCount()) {
                        performCommand(new SetDimensionCommand("Set objects count", numObj, attributeCount));
                    }
                }
            }
            );

            SyncListener lst = new SyncListener(objectCountModel) {
                protected int getValue() {
                    return getContext().getObjectCount();
                }
            };
            context.addContextListener(lst);
            addTableModelListener(lst);
        }
        return objectCountModel;
    }


    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 22:40:44)
     * @return conexp.util.gui.paramseditor.ParamInfo[]
     */
    public ParamInfo[] getParams() {
        if (null == params) {
            params = new ParamInfo[]{
                new conexp.util.gui.paramseditor.IntValueParamInfo("Object count", getObjectCountModel()),
                new conexp.util.gui.paramseditor.IntValueParamInfo("Attribute count", getAttribCountModel())
            };
        }
        return params;
    }

    protected java.beans.VetoableChangeListener makeVetoableGreaterOrEqualToZeroChecker(String msg) {

        class GreaterOrEqualToZeroChecker implements java.beans.VetoableChangeListener {
            final String prefix;

            GreaterOrEqualToZeroChecker(String msg) {
                prefix = msg;
            }

            public void vetoableChange(java.beans.PropertyChangeEvent evt)
                    throws java.beans.PropertyVetoException {
                if (((Number) evt.getNewValue()).intValue() < 0) {
                    throw new java.beans.PropertyVetoException(prefix, evt);
                }
            }
        }

        return new GreaterOrEqualToZeroChecker(msg);
    }

    public void removeRows(final int[] rows) {
        performCommand(new RemoveRowsCommand(rows));
    }

    private void doRemoveRows(int[] rows) {
        Assert.isTrue(canDeleteRows(rows));
        for (int i = rows.length; --i >= 0;) {
            getContext().removeObject(rowToObjectIndex(rows[i]));
        }
    }

    public boolean hasAtLeastOneNonHeaderCell(int[] rows, int[] columns) {
        return (hasAtLeastOneObjectRow(rows) && hasAtLeastOneAttributeColumn(columns));
    }

    private boolean hasAtLeastOneAttributeColumn(int[] columns) {
        for (int j = 0; j < columns.length; j++) {
            if (isAttributeColumn(columns[j])) {
                return true;
            }
        }
        return false;
    }

    private boolean hasAtLeastOneObjectRow(int[] rows) {
        for (int i = 0; i < rows.length; i++) {
            if (isObjectRow(rows[i])) {
                return true;
            }
        }
        return false;
    }

    public void applyCellTransformerToNonHeaderCells(int[] rows, int[] columns, CellTransformer cellTransformer) {
        Assert.isTrue(hasAtLeastOneNonHeaderCell(rows, columns));
        try {
            startCompoundCommand();
            for (int i = 0; i < rows.length; i++) {
                int currRow = rows[i];
                if (isObjectRow(currRow)) {
                    for (int j = 0; j < columns.length; j++) {
                        int currCol = columns[j];
                        if (isAttributeColumn(currCol)) {
                            setValueAt(cellTransformer.transformedValue(getValueAt(currRow, currCol)),
                                    currRow, currCol);
                        }
                    }
                }
            }
        } finally {
            endCompoundCommand();
        }
//        fireTableDataChanged();
    }


    private abstract class SetValueBase extends CommandBase {
        public SetValueBase(String name) {
            super(name);
        }

        protected abstract int getRow();

        protected abstract int getColumn();

        protected int getAttributeId() {
            return colToAttributeIndex(getColumn());
        }

        protected int getObjectId() {
            return rowToObjectIndex(getRow());
        }

        protected void fireUpdate() {
            fireTableCellUpdated(getRow(), getColumn());
        }
    }


    private abstract class ContextEntityCommand extends SetValueBase {
        String oldName;
        private final Object value;

        public ContextEntityCommand(String name, Object value) {
            super(name);
            this.value = value;
        }

        public void doCommand() {
            ContextEntity contextEntity = getContextEntity();
            oldName = contextEntity.getName();
            contextEntity.setName(String.valueOf(value));
            fireUpdate();
        }

        protected abstract ContextEntity getContextEntity();

        public void undoCommand() {
            getContextEntity().setName(oldName);
            fireUpdate();
        }
    }


    private class SetObjectNameCommand extends ContextEntityCommand {
        final int row;

        public SetObjectNameCommand(int row, Object value) {
            super("Set Object Name", value);
            this.row = row;
        }

        protected ContextEntity getContextEntity() {
            return getContext().getObject(getObjectId());
        }

        protected int getRow() {
            return row;
        }

        protected int getColumn() {
            return 0;
        }

    }


    private class SetAttributeNameCommand extends ContextEntityCommand {
        int col;

        public SetAttributeNameCommand(int col, Object value) {
            super("Set Attribute Name", value);
            this.col = col;
        }

        protected ContextEntity getContextEntity() {
            return getContext().getAttribute(getAttributeId());
        }

        protected int getRow() {
            return 0;
        }

        protected int getColumn() {
            return col;
        }

    }

    private class ModifyRelationCommand extends SetValueBase {
        boolean oldValue;
        int row;
        int column;
        private final boolean newRelValue;

        public ModifyRelationCommand(int row, int column, boolean newRelValue) {
            super("Modify relation");
            this.row = row;
            this.column = column;
            this.newRelValue = newRelValue;
        }

        protected int getRow() {
            return row;
        }

        protected int getColumn() {
            return column;
        }

        public void undoCommand() {
            context.setRelationAt(getObjectId(), getAttributeId(), oldValue);
            fireUpdate();
        }

        public void doCommand() {
            oldValue = context.getRelationAt(getObjectId(), getAttributeId());
            context.setRelationAt(getObjectId(), getAttributeId(), newRelValue);
            fireUpdate();
        }
    }


    protected abstract class ContextDestroyingCommandBase extends CommandBase{
        public ContextDestroyingCommandBase(String name) {
            super(name);
        }

        ContextEditingInterface oldContext;

        public void doCommand() {
            oldContext = getContext().makeCopy();
            modifyContext();
        }

        protected abstract void modifyContext();

        public void undoCommand() {
            boolean shouldCallStructureChange = isStructureChange();
            context.copyFrom(oldContext);
            oldContext=null;
            fireTableDataChanged();
            if (shouldCallStructureChange) {
                fireTableStructureChanged();
            }
        }

        private boolean isStructureChange() {
            boolean ret = oldContext.getAttributeCount() != getContext().getAttributeCount();
            return ret || oldContext.getObjectCount() != getContext().getObjectCount();
        }
    }

    protected abstract class ExtendedContextDestroyingCommandBase extends ContextDestroyingCommandBase{
        boolean enabled;
        public ExtendedContextDestroyingCommandBase(String name) {
            super(name);
            enabled = (getContext() instanceof ExtendedContextEditingInterface);
        }

        protected ExtendedContextEditingInterface getExtendedContext(){
            return (ExtendedContextEditingInterface)getContext();
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void doCommand() {
            if(isEnabled()){
                super.doCommand();
            }
        }

        public void undoCommand() {
            if(isEnabled()){
                super.undoCommand();
            }
        }

    }

    private class RemoveColumnsCommand extends ContextDestroyingCommandBase {
        private final int[] columns;

        public RemoveColumnsCommand(int[] columns) {
            super("Remove Column(s)");
            this.columns = columns;
        }

        protected void modifyContext() {
            doRemoveColumns(columns);
        }
    }

    private class RemoveRowsCommand extends ContextDestroyingCommandBase {
        private final int[] rows;

        public RemoveRowsCommand(int[] rows) {
            super("Remove row(s)");
            this.rows = rows;
        }

        protected void modifyContext() {
            doRemoveRows(rows);
        }
    }

    private class SetDimensionCommand extends ContextDestroyingCommandBase {
        private final int numObj;
        private final int attributeCount;

        public SetDimensionCommand(String name, int numObj, int attributeCount) {
            super(name);
            this.numObj = numObj;
            this.attributeCount = attributeCount;
        }

        protected void modifyContext() {
            getContext().setDimension(numObj, attributeCount);
            fireTableStructureChanged();
        }
    }

    public class TransposeContextCommand extends ExtendedContextDestroyingCommandBase{
        public TransposeContextCommand() {
            super("Transpose context");
        }

        protected void modifyContext() {
            getExtendedContext().transpose();
        }
    }

    public class PurifyContextCommand extends ExtendedContextDestroyingCommandBase{
        public PurifyContextCommand() {
            super("Purify context");
        }

        protected void modifyContext() {
            getExtendedContext().purifyObjects();
            getExtendedContext().purifyAttributes();
        }
    }

    public class PurifyObjectsCommand extends ExtendedContextDestroyingCommandBase{
        public PurifyObjectsCommand() {
            super("Purify objects");
        }

        protected void modifyContext() {
            getExtendedContext().purifyObjects();
        }
    }

    public class PurifyAttributesCommand extends ExtendedContextDestroyingCommandBase{
        public PurifyAttributesCommand() {
            super("Purify attributes");
        }

        protected void modifyContext() {
            getExtendedContext().purifyAttributes();
        }
    }


    public class ReduceContextCommand extends ExtendedContextDestroyingCommandBase{
        public ReduceContextCommand() {
            super("Reduce context");
        }

        protected void modifyContext() {
            getExtendedContext().reduceObjects();
            getExtendedContext().reduceAttributes();
        }
    }

    public class ReduceObjectsCommand extends ExtendedContextDestroyingCommandBase{
        public ReduceObjectsCommand() {
            super("Reduce objects");
        }

        protected void modifyContext() {
            getExtendedContext().reduceObjects();
        }
    }

    public class ReduceAttributesCommand extends ExtendedContextDestroyingCommandBase{
        public ReduceAttributesCommand() {
            super("Reduce attributes");
        }

        protected void modifyContext() {
            getExtendedContext().reduceAttributes();
        }
    }

    public class AddAttribCommand extends CommandBase{
        public AddAttribCommand() {
            super("Add attribute");
        }

        public void doCommand() {
            int oldObjCount = getContext().getObjectCount();
            int oldAttributeCount = getContext().getAttributeCount();
            getContext().setDimension(oldObjCount, oldAttributeCount+1);
        }

        public void undoCommand() {
            int newObjCount = getContext().getObjectCount();
            int newAttributeCount = getContext().getAttributeCount();
            getContext().setDimension(newObjCount, newAttributeCount-1);
        }
    }

    public class AddRowCommand extends CommandBase{
        public AddRowCommand() {
            this("Add row");
        }

        protected AddRowCommand(String commandName){
            super(commandName);
        }

        public void doCommand() {
            int oldObjCount = getContext().getObjectCount();
            int oldAttributeCount = getContext().getAttributeCount();
            getContext().setDimension(oldObjCount+1, oldAttributeCount);
        }

        public void undoCommand() {
            int newObjCount = getContext().getObjectCount();
            int newAttributeCount = getContext().getAttributeCount();
            getContext().setDimension(newObjCount-1, newAttributeCount);
        }
    }

     public class AddObjectWithNameCommand extends AddRowCommand{
         String objectName;
         Set intent;
         public AddObjectWithNameCommand(String objectName, Set intent) {
           super("Add object with name and intent");
           this.objectName = objectName;
           this.intent = intent.makeModifiableSetCopy();
         }

         public void doCommand() {
             getContext().addObjectWithNameAndIntent(objectName, intent);
         }
     }

}