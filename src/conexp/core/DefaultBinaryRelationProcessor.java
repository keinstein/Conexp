/*
 * User: sergey
 * Date: Jan 7, 2002
 * Time: 10:11:52 PM
 */
package conexp.core;

public class DefaultBinaryRelationProcessor implements BinaryRelationProcessor {
    protected BinaryRelation rel;

    public void setRelation(BinaryRelation relation) {
        this.rel = relation;
    }

    protected BinaryRelation getRelation(){
        return rel;
    }

    public void tearDown() {
        rel = null;
    }
}
