/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jul 28, 2002
 * Time: 1:20:24 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core;

public class NullDependencySet extends DependencySet {
    public NullDependencySet() {
        super(new NullAttributeInformationSupplier());
    }

}
