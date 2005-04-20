package conexp.frontend.latticeeditor.edgesizecalcstrategies.tests;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 17/4/2005
 * Time: 17:06:25
 * To change this template use File | Settings | File Templates.
 */

import junit.framework.*;
import conexp.frontend.latticeeditor.edgesizecalcstrategies.ConceptConnectionEdgeSizeCalcStrategy;
import conexp.frontend.latticeeditor.tests.DefaultDimensionCalcStrategyTest;
import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.BasicDrawParams;

public class ConceptConnectionEdgeSizeCalcStrategyTest extends DefaultDimensionCalcStrategyTest{

    protected DefaultDimensionCalcStrategy makeNotEqualInstance() {
        return new ConceptConnectionEdgeSizeCalcStrategy(new LatticePainterDrawParams());
    }

    protected DefaultDimensionCalcStrategy makeEqualInstance() {
        return new ConceptConnectionEdgeSizeCalcStrategy(BasicDrawParams.getInstance());
    }
}