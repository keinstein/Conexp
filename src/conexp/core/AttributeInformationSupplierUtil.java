/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Dec 18, 2001
 * Time: 10:49:31 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core;


public class AttributeInformationSupplierUtil {

    public static void describeSet(StringBuffer buf, AttributeInformationSupplier attrInfo,
                                   Set set, String separator, String emptySetDescriptor) {
        int bound = set.length();
        if (bound > 0) {
            boolean first = true;
            for (int i = 0; i < bound; i++) {
                if (set.in(i)) {
                    if (first) {
                        first = false;
                    } else {
                        buf.append(separator);
                    }
                    buf.append(attrInfo.getAttribute(i).getName());
                }
            }
        } else {
            buf.append(emptySetDescriptor);
        }
    }

    public static String describeSet(AttributeInformationSupplier attrInfo,
                                   Set set, String separator, String emptySetDescriptor) {
        StringBuffer buf = new StringBuffer();
        describeSet(buf, attrInfo, set, separator, emptySetDescriptor);
        return buf.toString();
    }

}
