/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jun 17, 2002
 * Time: 10:34:19 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import conexp.core.layout.Layouter;
import conexp.core.layout.LayouterProvider;
import conexp.core.layout.MinIntersectionLayout;

public class MinIntersectionLayouterProvider implements LayouterProvider{
    Layouter layouter = new MinIntersectionLayout();
    public Layouter getLayouter() {
        return layouter;
    }
}
