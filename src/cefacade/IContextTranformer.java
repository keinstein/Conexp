package cefacade;

/**
 * Author: Serhiy Yevtushenko
 * Date: 17.01.2003
 * Time: 17:22:59
 */

/**
 * interface for performing
 * side-effect free transformations on the context
 */
public interface IContextTranformer {
    ISimpleContext transform(ISimpleContext context);
}
