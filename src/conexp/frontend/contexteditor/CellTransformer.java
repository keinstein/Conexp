/*
 * User: Serhiy Yevtushenko
 * Date: 09.04.2002
 * Time: 13:13:12
 */
package conexp.frontend.contexteditor;

interface CellTransformer{
    Object transformedValue(Object oldValue);
}
