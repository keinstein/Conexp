/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.contexteditor.tests;

import conexp.core.ContextEditingInterface;
import conexp.core.tests.SetBuilder;
import conexp.frontend.contexteditor.ContextTableModel;
import conexp.frontend.contexteditor.FillByValueCellTransformer;
import conexp.frontend.contexteditor.InverseValueCellTransformer;
import conexp.util.valuemodels.IIntValueModel;
import junit.framework.TestCase;
import util.BooleanUtil;
import util.DataFormatException;
import util.testing.MockTableModelListener;
import util.testing.MockUndoableEditListener;
import util.testing.TestUtil;

import java.beans.PropertyVetoException;

public class ContextTableModelTest extends TestCase {
    private ContextTableModel tableModel;

    protected void setUp() {
        tableModel = new ContextTableModel(SetBuilder.makeContext(new int[][]{
                {1, 0, 1},
                {0, 1, 0},
        }));
    }

    private static void doTestValueModelThatShouldBeGreaterOrEqualZero(IIntValueModel intValueModel) {
        try {
            intValueModel.setValue(1);
            intValueModel.setValue(3);
            intValueModel.setValue(0);

        } catch (PropertyVetoException ex) {
            fail("values greater or equal to zero should be accepted");
        }
        try {
            intValueModel.setValue(-1);
            fail("value should be greater than zero");
        } catch (PropertyVetoException ex) {
        }
    }


    public void testAttribCountIntModel() {
        IIntValueModel intValueModel = tableModel.getAttribCountModel();
        assertEquals(3, intValueModel.getValue());
        doTestValueModelThatShouldBeGreaterOrEqualZero(intValueModel);
    }

    public void testAttribCountIntModelConnectionWithContext() {
        IIntValueModel intValueModel = tableModel.getAttribCountModel();
        assertEquals(tableModel.getContext().getAttributeCount(), intValueModel.getValue());
        MockTableModelListener mockListener = new MockTableModelListener();
        tableModel.addTableModelListener(mockListener);
        mockListener.setExpectStructureChanged();
        try {
            intValueModel.setValue(5);
        } catch (PropertyVetoException ex) {
            fail("Exception shoud'nt be thrown");
        }
        assertEquals(intValueModel.getValue(), tableModel.getContext().getAttributeCount());
        mockListener.verify();

        tableModel.getContext().setDimension(tableModel.getContext().getObjectCount(), 4);
        assertEquals(tableModel.getContext().getAttributeCount(), intValueModel.getValue());

    }

    public void testObjectCountIntModel() {
        IIntValueModel intValueModel = tableModel.getObjectCountModel();
        assertEquals(2, intValueModel.getValue());
        doTestValueModelThatShouldBeGreaterOrEqualZero(intValueModel);
    }

    public void testObjectCountIntModelConnectionWithContext() {
        IIntValueModel intValueModel = tableModel.getObjectCountModel();
        assertEquals(tableModel.getContext().getObjectCount(), intValueModel.getValue());
        MockTableModelListener mockListener = new MockTableModelListener();
        tableModel.addTableModelListener(mockListener);
        mockListener.setExpectStructureChanged();
        try {
            intValueModel.setValue(5);
        } catch (PropertyVetoException ex) {
            fail("Exception shoud'nt be thrown");
        }
        assertEquals(intValueModel.getValue(), tableModel.getContext().getObjectCount());
        mockListener.verify();
        tableModel.getContext().setDimension(4, tableModel.getContext().getAttributeCount());

        assertEquals(tableModel.getContext().getObjectCount(), intValueModel.getValue());
    }

    public void testSetContext() {
        assertEquals(2, tableModel.getObjectCountModel().getValue());
        assertEquals(3, tableModel.getAttribCountModel().getValue());
        tableModel.setContext(SetBuilder.makeContext(new int[][]{{1, 0}}));
        assertEquals(1, tableModel.getObjectCountModel().getValue());
        assertEquals(2, tableModel.getAttribCountModel().getValue());
    }

    public void testRemoveColumn() {
        tableModel.setContext(SetBuilder.makeContext(new int[][]{{1, 0}}));
        assertEquals(2, tableModel.getContext().getAttributeCount());
        assertEquals(false, tableModel.canDeleteColumn(0));

        assertTrue(tableModel.canDeleteColumn(1));
        assertTrue(tableModel.canDeleteColumn(2));

        tableModel.removeColumns(new int[]{1});

        assertEquals(1, tableModel.getContext().getAttributeCount());
        assertEquals(false, tableModel.canDeleteColumn(1));
    }

    public void testCanDeleteColumns() {
        assertEquals(3, tableModel.getAttribCountModel().getValue());
        assertEquals("No columns are specified for deletion", false, tableModel.canProcessColumns(new int[0]));
        assertEquals("can delete attribute column", true, tableModel.canProcessColumns(new int[]{1}));
        assertEquals("can't delete names column", false, tableModel.canProcessColumns(new int[]{0}));
        assertEquals("can't delete names column", false, tableModel.canProcessColumns(new int[]{0, 1}));
        assertEquals("can delete columns, when at least one attribute column left", true, tableModel.canProcessColumns(new int[]{1, 3}));
        assertEquals("can't delete all attribute columns", false, tableModel.canProcessColumns(new int[]{1, 2, 3}));
    }

    public void testRemoveColumns() {
        assertEquals(3, tableModel.getAttribCountModel().getValue());
        tableModel.removeColumns(new int[]{1, 3});
        assertEquals(1, tableModel.getAttribCountModel().getValue());
        assertEquals(Boolean.FALSE, tableModel.getValueAt(1, 1));
    }

    public void testCanDeleteRows() {
        assertEquals(2, tableModel.getObjectCountModel().getValue());
        assertEquals("No rows are specified for deletion", false, tableModel.canDeleteRows(new int[0]));
        assertEquals("Can't delete names row", false, tableModel.canDeleteRows(new int[]{0}));
        assertEquals("Can delete object row", true, tableModel.canDeleteRows(new int[]{2}));
        assertEquals("Can't delete names row", false, tableModel.canDeleteRows(new int[]{0, 2}));
        assertEquals("Can't delete all objects rows", false, tableModel.canDeleteRows(new int[]{1, 2}));
    }

    public void testDeleteRows() {
        tableModel = new ContextTableModel(SetBuilder.makeContext(new int[][]{
                {0, 1},
                {1, 0},
                {0, 1}
        }));
        assertEquals(3, tableModel.getObjectCountModel().getValue());
        tableModel.removeRows(new int[]{1, 3});
    }

    public void testHasAtLeastOneNonHeaderCell() {
        assertEquals(3, tableModel.getAttribCountModel().getValue());
        assertEquals(2, tableModel.getObjectCountModel().getValue());
        assertEquals(false, tableModel.hasAtLeastOneNonHeaderCell(new int[0], new int[0]));
        assertEquals(true, tableModel.hasAtLeastOneNonHeaderCell(new int[]{1}, new int[]{1}));
        assertEquals(false, tableModel.hasAtLeastOneNonHeaderCell(new int[]{0}, new int[]{1}));
        assertEquals(false, tableModel.hasAtLeastOneNonHeaderCell(new int[]{2}, new int[]{0}));
    }

    public void testApplyOperationForSelection() {
        tableModel = new ContextTableModel(SetBuilder.makeContext(new int[][]{{0, 0}}));
        tableModel.applyCellTransformerToNonHeaderCells(new int[]{1}, new int[]{1, 2}, new FillByValueCellTransformer(Boolean.TRUE));
        assertEquals(Boolean.TRUE, tableModel.getValueAt(1, 1));
        assertEquals(Boolean.TRUE, tableModel.getValueAt(1, 2));
        tableModel.applyCellTransformerToNonHeaderCells(new int[]{0, 1}, new int[]{0, 1}, new FillByValueCellTransformer(Boolean.FALSE));
        assertEquals(Boolean.FALSE, tableModel.getValueAt(1, 1));
        assertEquals(Boolean.TRUE, tableModel.getValueAt(1, 2));
        tableModel.applyCellTransformerToNonHeaderCells(new int[]{1}, new int[]{1, 2}, new InverseValueCellTransformer());
        assertEquals(Boolean.TRUE, tableModel.getValueAt(1, 1));
        assertEquals(Boolean.FALSE, tableModel.getValueAt(1, 2));
    }

    interface ContextTableModifier {
        void modifyTable(ContextTableModel tableModel);
    }

    public void testUndoCommandOnSetObjectName() {
        ContextTableModifier modifier = new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.setValueAt("One", 1, 0); //Object name for first object
            }
        };

        doTestDoUndoCommand(modifier);
    }


    private void doTestDoUndoCommand(ContextTableModifier modifier) {
        final MockUndoableEditListener mockUndoableEditListener = new MockUndoableEditListener();
        MockTableModelListener tableModelListener = new MockTableModelListener();

        tableModel.addUndoableEditListener(mockUndoableEditListener);
        tableModel.addTableModelListener(tableModelListener);

        ContextEditingInterface old = tableModel.getContext().makeCopy();
        assertEquals(old, tableModel.getContext());

        mockUndoableEditListener.setExpectedCalls(1);
        tableModelListener.setExpectSomeEvent();
        modifier.modifyTable(tableModel);
        mockUndoableEditListener.verify();
        tableModelListener.verify();
        final ContextEditingInterface newContext = tableModel.getContext().makeCopy();
        TestUtil.testNotEquals(old, newContext);

        tableModelListener.setExpectSomeEvent();
        mockUndoableEditListener.getUndoManager().undo();
        assertEquals(old, tableModel.getContext());
        tableModelListener.verify();

        tableModelListener.setExpectSomeEvent();
        mockUndoableEditListener.getUndoManager().redo();
        tableModelListener.verify();

        assertEquals(newContext, tableModel.getContext());
        tableModelListener.setExpectSomeEvent();
        mockUndoableEditListener.getUndoManager().undo();
        assertEquals(old, tableModel.getContext());
        tableModelListener.verify();
    }

    public void testUndoCommandOnSetAttributeName() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.setValueAt("AttrOne", 0, 1);
            }
        });
    }

    public void testUndoCommandOnSetRelationValue() {
        assertFalse(tableModel.getContext().getRelationAt(1, 0));
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.setValueAt(BooleanUtil.valueOf(true), 2, 1);
            }
        });
    }

    public void testUndoOnCellTransformer() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.applyCellTransformerToNonHeaderCells(new int[]{0, 1},
                        new int[]{0, 1, 2},
                        new FillByValueCellTransformer(Boolean.TRUE));
            }
        });
    }

    public void testUndoOnRemoveColumns() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.removeColumns(new int[]{1, 2});
            }
        });
    }

    public void testUndoOnRemoveObject() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.removeRows(new int[]{1});
            }
        });
    }

    public void testUndoOnSetObjectCount() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                try {
                    tableModel.getObjectCountModel().setValue(5);
                } catch (PropertyVetoException e) {
                    TestUtil.reportUnexpectedException(e);
                }
            }
        });
    }

    public void testUndoOnSetAttributeCount() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                try {
                    tableModel.getAttribCountModel().setValue(5);
                } catch (PropertyVetoException e) {
                    TestUtil.reportUnexpectedException(e);
                }
            }
        });
    }

    public void testUndoOnTransposeContext() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.performCommand(tableModel.new TransposeContextCommand());
            }
        });
    }

    public void testUndoOnClarifyContext() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.performCommand(tableModel.new ClarifyContextCommand());
            }
        });
    }

    public void testUndoOnClarifyObjects() {
        tableModel = new ContextTableModel(SetBuilder.makeContext(new int[][]{
                {1, 0, 1},
                {0, 1, 0},
                {0, 1, 0}
        }));

        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.performCommand(tableModel.new ClarifyObjectsCommand());
            }
        });
    }

    public void testUndoOnClarifyAttributes() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.performCommand(tableModel.new ClarifyAttributesCommand());
            }
        });
    }

    public void testUndoOnReduceContext() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.performCommand(tableModel.new ReduceContextCommand());
            }
        });
    }

    public void testUndoOnReduceObjects() {
        tableModel = new ContextTableModel(SetBuilder.makeContext(new int[][]{
                {1, 0, 1},
                {0, 1, 0},
                {0, 1, 0}
        }));

        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.performCommand(tableModel.new ReduceObjectsCommand());
            }
        });
    }

    public void testUndoOnReduceAttributes() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.performCommand(tableModel.new ReduceAttributesCommand());
            }
        });
    }


    public void testUndoOnAddAttrib() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.performCommand(tableModel.new AddAttribCommand());
            }
        });
    }

    public void testUndoOnAddObject() {
        doTestDoUndoCommand(new ContextTableModifier() {
            public void modifyTable(ContextTableModel tableModel) {
                tableModel.performCommand(tableModel.new AddRowCommand());
            }
        });
    }


    public void testConvertToInternal() {
        expectSuccessfullConvesion("", "", 0, 0);
        expectUnsuccessfullConvesion("1", 0, 0);

        expectSuccessfullConvesion("Obj 1", "Obj 1", 1, 0);
        expectUnsuccessfullConvesion(" ", 1, 0);
        expectSuccessfullConvesion("Attr 1", "Attr 1", 0, 1);
        expectUnsuccessfullConvesion(" ", 0, 1);
        expectSuccessfullConvesion("1", "1", 0, 1);
        expectSuccessfullConvesion("1", "1", 1, 0);


        expectUnsuccessfullConvesion("Obj", 1, 1);
        expectSuccessfullConvesion("1", Boolean.TRUE, 1, 1);
        expectSuccessfullConvesion("0", Boolean.FALSE, 1, 1);
    }

    private void expectSuccessfullConvesion(String input, Object expected, int row, int col) {
        try {
            Object result = tableModel.convertToInternal(input, row, col);
            assertEquals(expected, result);
        } catch (DataFormatException e) {
            TestUtil.reportUnexpectedException(e);
        }
    }


    private void expectUnsuccessfullConvesion(String input, int row, int col) {
        try {
            tableModel.convertToInternal(input, row, col);
            fail("should not get here");
        } catch (DataFormatException e) {
            assertTrue("expect exception", true);
        }
    }

}
