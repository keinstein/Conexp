/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 24, 2001
 * Time: 11:47:40 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import conexp.core.Context;

public interface DependencySetCalculator extends DependencySetSupplier {
    void setContext(Context cxt);

    void findDependencies();
}
