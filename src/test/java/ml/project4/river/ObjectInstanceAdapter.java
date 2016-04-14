package ml.project4.river;

import burlap.oomdp.core.ObjectClass;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.values.Value;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Adapter for ObjectInstance.
 */
public class ObjectInstanceAdapter implements ObjectInstance {
    @Override
    public ObjectInstance copy() {
        return null;
    }

    @Override
    public ObjectInstance setName(String s) {
        return null;
    }

    @Override
    public <T> ObjectInstance setValue(String s, T t) {
        return null;
    }

    @Override
    public ObjectInstance setValue(String s, String s1) {
        return null;
    }

    @Override
    public ObjectInstance setValue(String s, double v) {
        return null;
    }

    @Override
    public ObjectInstance setValue(String s, int i) {
        return null;
    }

    @Override
    public ObjectInstance setValue(String s, boolean b) {
        return null;
    }

    @Override
    public ObjectInstance setValue(String s, int[] ints) {
        return null;
    }

    @Override
    public ObjectInstance setValue(String s, double[] doubles) {
        return null;
    }

    @Override
    public ObjectInstance addRelationalTarget(String s, String s1) {
        return null;
    }

    @Override
    public ObjectInstance addAllRelationalTargets(String s, Collection<String> collection) {
        return null;
    }

    @Override
    public ObjectInstance clearRelationalTargets(String s) {
        return null;
    }

    @Override
    public ObjectInstance removeRelationalTarget(String s, String s1) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ObjectClass getObjectClass() {
        return null;
    }

    @Override
    public String getClassName() {
        return null;
    }

    @Override
    public Value getValueForAttribute(String s) {
        return null;
    }

    @Override
    public double getRealValForAttribute(String s) {
        return 0;
    }

    @Override
    public double getNumericValForAttribute(String s) {
        return 0;
    }

    @Override
    public String getStringValForAttribute(String s) {
        return null;
    }

    @Override
    public int getIntValForAttribute(String s) {
        return 0;
    }

    @Override
    public Set<String> getAllRelationalTargets(String s) {
        return null;
    }

    @Override
    public boolean getBooleanValForAttribute(String s) {
        return false;
    }

    @Override
    public int[] getIntArrayValForAttribute(String s) {
        return new int[0];
    }

    @Override
    public double[] getDoubleArrayValForAttribute(String s) {
        return new double[0];
    }

    @Override
    public List<Value> getValues() {
        return null;
    }

    @Override
    public List<String> unsetAttributes() {
        return null;
    }

    @Override
    public String getObjectDescription() {
        return null;
    }

    @Override
    public StringBuilder buildObjectDescription(StringBuilder stringBuilder) {
        return null;
    }

    @Override
    public String getObjectDescriptionWithNullForUnsetAttributes() {
        return null;
    }

    @Override
    public double[] getFeatureVec() {
        return new double[0];
    }

    @Override
    public double[] getNormalizedFeatureVec() {
        return new double[0];
    }

    @Override
    public boolean valueEquals(ObjectInstance objectInstance) {
        return false;
    }
}
