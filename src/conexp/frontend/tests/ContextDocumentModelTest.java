package conexp.frontend.tests;

/**
 * User: sergey
 * Date: 11/5/2005
 * Time: 10:12:00
 */

import junit.framework.*;
import conexp.frontend.ContextDocumentModel;
import conexp.core.Context;
import conexp.core.tests.SetBuilder;

public class ContextDocumentModelTest extends TestCase {
    public void testClearingLatticesAfterSetingContext(){
        Context cxt = SetBuilder.makeContext(new int[][]{{1, 0},
                                                         {0, 1}});
        ContextDocumentModel model = new ContextDocumentModel(cxt);
        assertEquals(0, model.getLatticeComponents().size());

        model.addLatticeComponent();
        assertEquals(1, model.getLatticeComponents().size());
        Context cxt2 = SetBuilder.makeContext(new int[][]{{1}});
        model.setContext(cxt2);
        assertEquals(0, model.getLatticeComponents().size());
    }

    public void testCorrectAdditionAndCleanUpOfContextListeners(){
        Context cxt = SetBuilder.makeContext(new int[][]{{1, 0},
                                                         {0, 1}});
        final int listenersCount = cxt.getContextListenersCount();
        ContextDocumentModel model = new ContextDocumentModel(cxt);
        model.addLatticeComponent();
        Context cxt2 = SetBuilder.makeContext(new int[][]{{1}});
        model.setContext(cxt2);
        assertEquals(listenersCount, cxt.getContextListenersCount());

    }

}